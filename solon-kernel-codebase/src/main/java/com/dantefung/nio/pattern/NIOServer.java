/*
 * Copyright (C), 2015-2020
 * FileName: NIOServer
 * Author:   DANTE FUNG
 * Date:     2021/7/31 14:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/7/31 14:54   V1.0.0
 */
package com.dantefung.nio.pattern;

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
 * @Title: NIOServer
 * @Description:
 *
 * 我们可以看到上述的 NIO 例子已经差不多拥有 reactor 的影子了
 *
 *   1.  基于事件驱动 -> selector（支持对多个 socketChannel 的监听）
 *
 *   2.  统一的事件分派中心 -> dispatch
 *
 *   3.  事件处理服务 -> read & write
 *
 *
 * @author DANTE FUNG
 * @date 2021/07/31 14/54
 * @since JDK1.8
 */
@Slf4j
public class NIOServer implements Runnable {

	private Selector selector;
	private ServerSocketChannel serverSocket;

	private String storeClientMsg = null;

	public static void main(String[] args) throws Exception {
		new Thread(new NIOServer(9090)).start();
	}

	public NIOServer(int port) throws Exception {
		selector = Selector.open();
		serverSocket = ServerSocketChannel.open();
		serverSocket.socket().bind(new InetSocketAddress(port));
		serverSocket.configureBlocking(false);
		serverSocket.register(selector, SelectionKey.OP_ACCEPT);
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				// 阻塞等待事件(就绪事件到达前，先阻塞)
				selector.select();
				// 事件列表
				Set selected = selector.selectedKeys();
				log.info(" 当前所有就绪的事件为: {}", selected);
				Iterator it = selected.iterator();
				while (it.hasNext()) {
					//分发事件
					dispatch((SelectionKey) (it.next()));
				}
				// 这个很关键，防止重复处理.
				selected.clear();
			} catch (Exception e) {

			}
		}
	}

	private void dispatch(SelectionKey key) throws Exception {
		if (key.isAcceptable()) {
			log.info(" Tests whether this key's channel is ready to accept a new socket ！准备处理新连接请求 ...");
			register(key);//新链接建立，注册
		} else if (key.isReadable()) { // 客户端1，客户端2，... 客户端N 连接通道的SelectionKey就绪
			log.info(" 处理[{}]读就绪事件 ...", key);
			read(key);//读事件处理
		} else if (key.isWritable()) {
			log.info(" 处理[{}]写就绪事件 ...", key);
			write(key);//写事件处理
		}
	}

	private void write(SelectionKey selectedKey) throws IOException {
		if (selectedKey.isValid()) {
			log.info(" 处理写就绪事件[{}]...", selectedKey);
			SocketChannel socketChannel = (SocketChannel) selectedKey.channel();
			ByteBuffer outputBuffer = ByteBuffer.allocate(1024);
			outputBuffer.clear();
			String resMsg = String.format("我收到来自%s的信息辣：%s,  200ok;", socketChannel.getRemoteAddress(), storeClientMsg);
			log.info(" 准备写出内容: {}", resMsg);
			outputBuffer.put(resMsg.getBytes());
			outputBuffer.flip();
			int count = socketChannel.write(outputBuffer); //write方法结束，意味着本次写就绪变为写完毕，标记着一次事件的结束
			selectedKey.interestOps(SelectionKey.OP_READ);
			if (count < 0) {
				selectedKey.cancel();
				socketChannel.close();
				log.info("write时-------连接关闭");
			}
		}
	}

	private void read(SelectionKey selectedKey) throws IOException {
		if (selectedKey.isValid()) {
			log.info("处理读就绪事件[{}]...", selectedKey);
			// 获取该选择器上的“读就绪”状态的通道
			SocketChannel socketChannel = (SocketChannel) selectedKey.channel();
			log.info("处理当前读请求的channel:{}", socketChannel);

			// 读取数据
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			int length = 0;
			int count = 0;
			while ((length = socketChannel.read(byteBuffer)) > 0) {
				byteBuffer.flip();
				storeClientMsg = new String(byteBuffer.array(), 0, length);
				log.info("收到来自客户端消息:" + storeClientMsg);
				byteBuffer.clear();
				count++;
			}

			if (count > 0) {
				selectedKey.interestOps(SelectionKey.OP_WRITE);
			} else {
				selectedKey.cancel();
				// 如果读不到数据，意味着客户端断开
				socketChannel.close();
				log.info("read时-------连接关闭");
			}
		}
	}

	private void register(SelectionKey key) throws Exception {
		ServerSocketChannel server = (ServerSocketChannel) key.channel();
		// 获得和客户端连接的通道
		SocketChannel channel = server.accept();
		if (channel != null) {
			log.info(" 新客户端建立通道:{} ... ", channel);
			channel.configureBlocking(false);
			//客户端通道注册到selector 上
			log.info(" 客户端通道注册到selector，关心读事件 ...");
			channel.register(this.selector, SelectionKey.OP_READ);
		}
	}
}
