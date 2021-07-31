/*
 * Copyright (C), 2015-2020
 * FileName: SimpleReactorServer
 * Author:   DANTE FUNG
 * Date:     2021/7/30 下午10:48
 * Description: 朴素Reactor Server
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/7/30 下午10:48   V1.0.0
 */
package com.dantefung.nio.pattern;

import com.dantefung.nio.common.SystemConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Title: SimpleReactorServer
 * @Description: 朴素Reactor Server
 *
 * 实际上的 Reactor 模式，是基于 Java NIO 的，在他的基础上，抽象出来两个组件——Reactor 和 Handler 两个组件：
 *
 * （1）Reactor：负责响应 IO 事件，当检测到一个新的事件，将其发送给相应的 Handler 去处理；新的事件包含连接建立就绪、读就绪、写就绪等。
 *
 * （2）Handler: 将自身（handler）与事件绑定，负责事件的处理，完成 channel 的读入，完成处理业务逻辑后，负责将结果写出 channel
 *
 * @author DANTE FUNG
 * @date 2021/07/30 22/48
 * @since JDK1.8
 */
@Slf4j
public class SimpleReactorServer {


	public static void testServer() throws IOException {

		// 1、获取Selector选择器
		/**
		 * nio通过selector可以使用一个线程去管理多个socket句柄，
		 * 说是管理也不太合适，nio是采用的事件驱动模型，selector负责的是监控各个连接句柄的状态，不是去轮询每个句柄，
		 * 而是在数据就绪后，将消息通知给selector，而具体的socket句柄管理则是采用多路复用的模型，交由【操作系统】来完成。
		 *
		 * selector充当的是一个消息的监听者，负责监听channel在其注册的事件，
		 * 这样就可以通过一个线程完成了大量连接的管理，当注册的事件发生后，
		 * 再调用相应线程进行处理。
		 * 这样就不需要为每个连接都使用一个线程去维持长连接，减少了长连接的开销，同时减少了上下文的切换提高了系统的吞吐量。
		 */
		Selector selector = Selector.open();

		// 2、获取通道
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 3.设置为非阻塞
		serverSocketChannel.configureBlocking(false);
		// 4、绑定连接
		serverSocketChannel.bind(new InetSocketAddress(SystemConfig.SOCKET_SERVER_PORT));

		// 5、将通道注册到选择器上,并注册的操作为：“接收”操作
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		// 6、采用轮询的方式，查询获取“准备就绪”的注册过的操作
		while (selector.select() > 0) {
			// 7、获取当前选择器中所有注册的选择键（“已经准备就绪的操作”）
			Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
			while (selectedKeys.hasNext()) {
				// 8、获取“准备就绪”的时间
				SelectionKey selectedKey = selectedKeys.next();

				// 9、判断key是具体的什么事件
				if (selectedKey.isAcceptable()) { // 连接请求
					log.info("处理接受就绪事件...");
					// 10、若接受的事件是“接收就绪” 操作,就获取客户端连接
					SocketChannel socketChannel = serverSocketChannel.accept();
					log.info("处理当前连接请求的channel:{}", socketChannel);
					// 11、切换为非阻塞模式
					socketChannel.configureBlocking(false);
					// 12、将该通道注册到selector选择器上
					socketChannel.register(selector, SelectionKey.OP_READ);
				} else if (selectedKey.isReadable()) { // 读请求
					log.info("处理读就绪事件...");
					// 13、获取该选择器上的“读就绪”状态的通道
					SocketChannel socketChannel = (SocketChannel) selectedKey.channel();
					log.info("处理当前读请求的channel:{}", socketChannel);

					// 14、读取数据
					ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
					int length = 0;
					int count = 0;
					while ((length = socketChannel.read(byteBuffer)) > 0) {
						byteBuffer.flip();
						log.info("收到来自客户端消息:" + new String(byteBuffer.array(), 0, length));
						byteBuffer.clear();
						count++;
					}
					if (count > 0) {
						selectedKey.interestOps(SelectionKey.OP_WRITE);
					} else {
						selectedKey.cancel();
						socketChannel.close();
					}
				} else if (selectedKey.isWritable()) {
					if (selectedKey.isValid()) {
						log.info(" 处理写就绪事件[{}]...", selectedKey);
						SocketChannel socketChannel = (SocketChannel) selectedKey.channel();
						ByteBuffer outputBuffer = ByteBuffer.allocate(1024);
						outputBuffer.clear();
						String resMsg = String.format("你好啊！%s", socketChannel.getRemoteAddress());
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

				// 15、移除选择键
				selectedKeys.remove();
			}
		}

		// 7、关闭连接
		serverSocketChannel.close();
	}

	public static void main(String[] args) throws IOException {
		testServer();
	}
}
