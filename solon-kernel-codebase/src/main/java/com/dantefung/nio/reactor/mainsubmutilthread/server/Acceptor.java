package com.dantefung.nio.reactor.mainsubmutilthread.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

// ---------------Reactor 3: Acceptor-------------------------
@Slf4j
public class Acceptor implements Runnable {

	private final ServerSocketChannel serverSocketChannel;
	private final SubReactor subReactor;

	public Acceptor(SubReactor subReactor, ServerSocketChannel serverSocketChannel) {
		this.serverSocketChannel = serverSocketChannel;
		this.subReactor = subReactor;
	}


	@Override
	public void run() {
		log.info("[Acceptor] 开始回调处理...");
		try {
			log.info("[Acceptor] 开始获取来自客户端的连接 ...");
			SocketChannel socketChannel = serverSocketChannel.accept();
			if (socketChannel != null) {
				log.info(String.format("[Acceptor] 收到来自 %s 的连接", socketChannel.getRemoteAddress()));
				//这里把客户端通道传给Handler，Handler负责接下来的事件处理（除了连接事件以外的事件均可）
				log.info("[Acceptor] 将连接{} 交给SubReactor:{} 进行IO事件处理...", socketChannel.getRemoteAddress(), subReactor);
				subReactor.register(socketChannel);// 初始化Handler，并注册读事件
				subReactor.run();// 开启监听IO事件.
			}
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
	}
}