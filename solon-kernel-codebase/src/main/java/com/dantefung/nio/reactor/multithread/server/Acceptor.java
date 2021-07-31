package com.dantefung.nio.reactor.multithread.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

// ---------------Reactor 3: Acceptor-------------------------
@Slf4j
public class Acceptor implements Runnable {

	private final ServerSocketChannel serverSocketChannel;
	private final Selector selector;

	public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
		this.serverSocketChannel = serverSocketChannel;
		this.selector = selector;
	}

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

	@Override
	public String toString() {
		return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
	}
}