package com.dantefung.nio.reactor.multithread.server;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 单Reactor 多线程模型.
 *
 * Channels:
 *   Connections to files, sockets etc that support
 *   non-blocking reads
 *
 * Buffers:
 *   Array-like objects that can be directly read or written by Channels
 *
 * Selectors:
 * 	Tell which of a set of Channels have IO events
 *
 * SelectionKeys:
 *  Maintain IO event status and bindings
 *
 *
 *  ---------------------------------------------------
 *
 *  Reactor 将 I/O 事件分派给对应的 Handler
 *
 *  Acceptor 处理客户端新连接，并分派请求到处理器链中
 *
 *  Handlers 执行非阻塞读 / 写 任务
 *
 *  ----------------------------------------------------
 *
 *  Selector 一个
 *
 *  步骤1：
 *  Reactor构建时， ServerSocketChannel 关注接收事件，注册SelectionKey.OP_ACCEPT事件 到selector上。
 *  步骤2:
 *  Reactor启动时，死循环Selector获取所有就绪事件，当前只有SelectionKey.OP_ACCEPT事件。
 *
 *   步骤3:
 *  当客户端1连接进来，Reactor派发事件给Acceptor(通过Reactor自己关注的OP_ACCEPT事件的SelectionKey上的附件来实现.)
 *  Acceptor则负责创建Handler1对象，用于关注读写事件，并把自己关心的读写事件注册到Selector上，且将自己作为附件放置在属于自己的读写事件的SelectionKey上。
 *
 *  步骤4:
 *  当Reactor通过Selector获取所有就绪事件时，此时，可能读事件就绪或写事件就绪或OP_ACCEPT事件就绪，则遍历对应的SelectionKey集合
 *  该集合可能包含:
 *  附件为Handler1的SelectionKey, 派发执行Handler1的run方法，实现读写事件的处理。
 *  附件为Acceptor的SelectionKey, 派发执行步骤3.
 *
 *  当客户端N连接进来,执行步骤3
 *
 *
 *  Selector
 *  = {Reactor关心的OP_ACCEPT事件的SelectionKey, Handler1关心的读写事件的SelectionKey, Handler2关心的读写事件的SelectionKey, ..., HandlerN关心的读写事件的SelectionKey}
 *
 * Reactor关心的OP_ACCEPT事件的SelectionKey就绪:
 *     Acceptor 负责创建关心读写事件的Handler
 *
 * Handler关心的读写事件的SelectionKey就绪处理:
 *     Handler 自己处理读写事件.
 *
 *  关键点: SelectionKey[附件回调处理类] --注册--> Selector
 *  则可实现遍历Selector的就绪事件上可以从就绪的key中找到相应的处理类。
 *
 * 消息处理流程:
 * 1. Reactor对象通过epoll监控连接事件，收到事件后通过回调函数进行转发。
 * 2. 如果是连接建立的事件，则由acceptor接受连接，并创建handler处理后续事件。
 * 3. 如果不是建立连接事件，如read事件，则Reactor会分发调用Handler来响应。
 * 4. handler会完成read->业务处理->send的完整业务流程。
 *
 */
@Slf4j
public class Reactor implements Runnable {



	private final Selector selector;
	private final ServerSocketChannel serverSocketChannel;

	// ----------------Reactor 1: Setup--------------------------
	public Reactor(int port) throws IOException {
		// reactor 初始化
		// reactor线程是个多面手，负责多路分离套接字。
		selector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		// 设置非阻塞
		serverSocketChannel.configureBlocking(false);
		// 分步处理，第一步，接收accept事件
		SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		// attach callback object, Acceptor
		/**
		 * 法允许您在键上放置一个“附件”，并在后面获取它。这是一种允许您将任意对象与
		 * 键关联的便捷的方法。这个对象可以引用任何对您而言有意义的对象，例如业务对象、会话句柄、
		 * 其他通道等等。这将允许您遍历与选择器相关的键，使用附加在上面的对象句柄作为引用来获取相
		 * 关的上下文。
		 * attach( )方法将在键对象中保存所提供的对象的引用。SelectionKey 类除了保存它之外，不
		 * 会将它用于任何其他用途。任何一个之前保存在键中的附件引用都会被替换。可以使用 null 值来清
		 * 除附件。可以通过调用 attachment( )方法来获取与键关联的附件句柄。
		 * 如果没有附件，或者显式地
		 * 通过 null 方法进行过设置，这个方法将返回 null。
		 */
		sk.attach(new Acceptor(selector, serverSocketChannel));
		log.info("[Reactor] 注册OP_ACCEPT事件SelectionKey:{} 成功, SelectionKey绑定接收连接的处理器为Acceptor(负责就绪连接时创建Handler)!", sk);
	}

	// ----------------Reactor 2:Dispatch Loop----------------------
	@Override
	public void run() {
		// normally in a new Thread
		try {
			log.info("[Reactor] 开始响应相关就绪的事件 ...");
			while (!Thread.interrupted()) {
				// 就绪事件到达之前，阻塞
				//selector.select();
				if (selector.select() == 0) // 若沒有事件就緒則不往下執行
					continue;
				// 拿到本次select获取的所有就绪事件
				Set selected = selector.selectedKeys();
				log.info("[Reactor] 当前所有就绪的事件为: {}", selected);
				Iterator it = selected.iterator();
				// 遍历所有就绪请求
				while (it.hasNext()) {
					// 进行任务派发
					dispatch((SelectionKey) (it.next()));
				}
				selected.clear();
			}
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	void dispatch(SelectionKey k) {
		// 使用之前注册的callback对象. 即Acceptor, 或 handler
		//这里很关键，拿到每次selectKey里面附带的处理对象，然后调用其run，这个对象在具体的Handler里会进行创建，初始化的附带对象为Acceptor（看上面构造器
		Runnable r = (Runnable) (k.attachment());
		log.info("[Reactor] 就绪事件(SelectionKey) {} 的回调处理类为: {}", k, r);
		if (r != null) {
			r.run();
		}
	}
}

