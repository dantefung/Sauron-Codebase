package com.dantefung.nio.reactor.mainsubmutilthread.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 主Reactor
 *
 * 主 - 从 reactor 模式
 * 下面的这张图描述了主 - 从 reactor 模式是如何工作的。
 * 主 - 从这个模式的核心思想是，主反应堆线程只负责分发 Acceptor 连接建立，已连接套接字上的 I/O 事件交给 sub-reactor 负责分发。其中 sub-reactor 的数量，可以根据 CPU 的核数来灵活设置。
 * 比如一个四核 CPU，我们可以设置 sub-reactor 为 4。相当于有 4 个身手不凡的反应堆线程同时在工作，这大大增强了 I/O 分发处理的效率。而且，同一个套接字事件分发只会出现在一个反应堆线程中，这会大大减少并发处理的锁开销。
 */
@Slf4j
public class MainReactor {


	/**
	 * 维护一个从Reactor
	 */
	private SubReactor subReactor;

	private int port;

	private Selector selector;

	private ServerSocketChannel servChannel;

	private volatile boolean stop;

	public MainReactor(int port) {
		try {
			selector = Selector.open();
			servChannel = ServerSocketChannel.open();
			servChannel.configureBlocking(false);
			servChannel.socket().bind(new InetSocketAddress(port), 1024);
			SelectionKey sk = servChannel.register(selector, SelectionKey.OP_ACCEPT);
			// 维护一下主从关系
			SubReactor subReactor = new SubReactor();
			addSub(subReactor);
			sk.attach(new Acceptor(subReactor, servChannel));
			stop = false;
			this.port = port;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * 添加子Reactor
	 * @param subReactor
	 * @return
	 */
	public MainReactor addSub(SubReactor subReactor) {
		this.subReactor = subReactor;
		return this;
	}

	public void run() {
		log.info("[MainReactor] 主reactor开始启动了,监听端口：" + port + ".......");
		while (!stop) {
			try {
				if (selector.select(1000)==0) {
					continue;
				}
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectedKeys.iterator();
				SelectionKey key = null;
				while (it.hasNext()) {
					key = it.next();
					it.remove();
					try {
						disptach(key);
					} catch (Exception e) {
						if (key != null) {
							key.cancel();
							if (key.channel() != null)
								key.channel().close();
						}
					}
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private void disptach(SelectionKey key) {
		if (key.isValid()) {
			/**
			 * 主Reactor只关心Accept事件
			 */
			if (key.isAcceptable()) {
				Runnable acceptor = (Runnable) key.attachment();
				log.info("[MainReactor] 连接就绪, 主Reactor派发 连接事件给 Acceptor:{} 执行 ...", acceptor);
				acceptor.run();
			}
		}
	}
}