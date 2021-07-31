package com.dantefung.nio.reactor.singlethread.server;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Reactor 单线程模型.
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
 *
 */
@Slf4j
public class Reactor implements Runnable {

	public static void main(String[] args) throws IOException {
		new Thread(new Reactor(9090)).start();
	}

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
		sk.attach(new Acceptor());
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
				selector.select();
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

	// ---------------Reactor 3: Acceptor-------------------------
	// inner class
	class Acceptor implements Runnable {

		@Override
		public void run() {
			log.info("[Acceptor] 开始回调处理...");
			try {
				SocketChannel socketChannel = serverSocketChannel.accept();
				if (socketChannel != null) {
					log.info(String.format("[Acceptor] 收到来自 %s 的连接", socketChannel.getRemoteAddress()));
					//这里把客户端通道传给Handler，Handler负责接下来的事件处理（除了连接事件以外的事件均可）
					new Handler(selector, socketChannel);
				}
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
	}

	//---------------Reactor 4: Handler setup--------------------
	public static int MAXIN = 1024;
	public static int MAXOUT = 2048;

	final class Handler implements Runnable {

		final SocketChannel socketChannel;
		final SelectionKey sk;
		ByteBuffer inputBuffer = ByteBuffer.allocate(MAXIN);
		ByteBuffer outputBuffer = ByteBuffer.allocate(MAXOUT);
		static final int READING = 0, SENDING = 1;
		int state = READING;

		public Handler(Selector sel, SocketChannel c) throws IOException {
			socketChannel = c;
			// 设置非阻塞
			c.configureBlocking(false);
			// Optionally try first read now
			// 将当前的socket连接注册到selector上
			sk = socketChannel.register(sel, 0);
			// 把handler注册为callback
			sk.attach(this);
			// 当前的socket连接关注读事件
			sk.interestOps(SelectionKey.OP_READ);
			sel.wakeup();
		}

		boolean inputIsComplete() throws IOException {
			if (sk.isValid()) {
				inputBuffer.clear();
				int count = socketChannel.read(inputBuffer); //read方法结束，意味着本次"读就绪"变为"读完毕"，标记着一次就绪事件的结束
				if (count > 0) {
					return true;
				} else {
					//读模式下拿到的值是-1，说明客户端已经断开连接，那么将对应的selectKey从selector里清除，否则下次还会select到，因为断开连接意味着读就绪不会变成读完毕，也不cancel，下次select会不停收到该事件
					//所以在这种场景下，（服务器程序）你需要关闭socketChannel并且取消key，最好是退出当前函数。注意，这个时候服务端要是继续使用该socketChannel进行读操作的话，就会抛出“远程主机强迫关闭一个现有的连接”的IO异常。
					sk.cancel();
					socketChannel.close();
					log.info("read时-------连接关闭");
					return false;
				}
			}
			log.info("[Handler] inputCompletion() sk is invalid ...");
			return false;
		}

		boolean outputIsComplete() throws IOException {
			if (sk.isValid()) {
				outputBuffer.clear();
				String resMsg = String.format("我收到来自%s的信息辣：%s,  200ok;", socketChannel.getRemoteAddress(),
						new String(inputBuffer.array()));
				log.info("[Handler] 准备写出内容: {}", resMsg);
				outputBuffer.put(resMsg.getBytes());
				outputBuffer.flip();
				int count = socketChannel.write(outputBuffer); //write方法结束，意味着本次写就绪变为写完毕，标记着一次事件的结束

				//				if (count < 0) {
				//					//同上，write场景下，取到-1，也意味着客户端断开连接
				//					sk.cancel();
				//					socketChannel.close();
				//					log.info("send时-------连接关闭");
				//				}

				if (count < 0) {
					return false;
				}
				//没断开连接，则再次切换到读
				state = READING;
				sk.interestOps(SelectionKey.OP_READ);
				return true;

			}
			log.info("[Handler] outputCompletion() sk is invalid ...");
			return false;
		}

		void process() throws IOException {
			log.info("[Handler] input completed , start process ...");
			log.info(String.format("[Handler] 收到来自 %s 的消息: %s", socketChannel.getRemoteAddress(),
					new String(inputBuffer.array())));
			state = SENDING;
			sk.interestOps(SelectionKey.OP_WRITE); //注册写方法
		}

		@Override
		public void run() {
			log.info("[Handler] 开始回调处理...");
			try {
				if (state == READING) {
					log.info("[Handler] read ...");
					read();
				} else if (state == SENDING) {
					log.info("[Handler] write ...");
					send();
				}
			} catch (IOException ex) {
				log.error("read或send时发生异常！异常信息：" + ex.getMessage(), ex);
				sk.cancel();
				try {
					socketChannel.close();
				} catch (IOException e) {
					log.error("关闭通道时发生异常！异常信息：" + e.getMessage(), e);
				}
			}
		}

		void read() throws IOException {
			//			socketChannel.read(inputBuffer);
			if (inputIsComplete()) {
				process();
				state = SENDING;
				// Normally also do first write now
				sk.interestOps(SelectionKey.OP_WRITE);

			}
		}

		void send() throws IOException {
			//			socketChannel.write(outputBuffer);
			if (!outputIsComplete()) {
				sk.cancel();
				socketChannel.close();
				log.info("send时-------连接关闭");
			}
		}
	}

}

