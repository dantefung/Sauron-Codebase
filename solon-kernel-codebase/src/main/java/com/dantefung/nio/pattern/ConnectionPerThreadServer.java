/*
 * Copyright (C), 2015-2020
 * FileName: ConnectionPerThread
 * Author:   DANTE FUNG
 * Date:     2021/7/30 下午10:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/7/30 下午10:10   V1.0.0
 */
package com.dantefung.nio.pattern;

import com.dantefung.nio.common.SystemConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Title: ConnectionPerThread
 * @Description: Tomcat早期版本是这样实现的，对于每一个请求都分发给一个线程，每个线程都独自处理。
 *
 * 多线程并发模式，一个连接一个线程的优点是：
 *
 * 一定程度上极大地提高了服务器的吞吐量，因为之前的请求在 read 阻塞以后，不会影响到后续的请求，因为他们在不同的线程中。
 * 这也是为什么通常会讲 “一个线程只能对应一个 socket” 的原因。另外有个问题，如果一个线程中对应多个 socket 连接不行吗？
 * 语法上确实可以，但是实际上没有用，每一个 socket 都是阻塞的，所以在一个线程里只能处理一个 socket，
 * 就算 accept 了多个也没用，前一个 socket 被阻塞了，后面的是无法被执行到的。
 *
 * 多线程并发模式，一个连接一个线程的缺点是：
 *
 * 缺点在于资源要求太高，系统中创建线程是需要比较高的系统资源的，如果连接数太高，系统无法承受，而且，线程的反复创建 - 销毁也需要代价。
 *
 * 改进方法是：
 *
 * 采用基于事件驱动的设计，当有事件触发时，才会调用处理器进行数据处理。使用 Reactor 模式，对线程的数量进行控制，一个线程处理大量的事件
 *
 * @author DANTE FUNG
 * @date 2021/07/30 22/10
 * @since JDK1.8
 */
@Slf4j
public class ConnectionPerThreadServer {

	public static void main(String[] args) {
		Thread serverThread = new Thread(new BasicModel());
		serverThread.start();
	}

	static class BasicModel implements Runnable {
		public void run() {
			try {
				ServerSocket ss = new ServerSocket(SystemConfig.SOCKET_SERVER_PORT);
				while (!Thread.interrupted())
					new Thread(new Handler(ss.accept())).start();
				//创建新线程来handle
				// or, single-threaded, or a thread pool
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		}

		static class Handler implements Runnable {
			final Socket socket;

			Handler(Socket s) {
				socket = s;
			}

			public void run() {
				try {
					byte[] input = new byte[SystemConfig.INPUT_SIZE];
					// 读取来自客户端的消息
					socket.getInputStream().read(input);
					log.info("tcp的服务端接收到的数据：" + new String(input, 0, input.length));
					byte[] output = process(input);
					socket.getOutputStream().write(output);
				} catch (IOException ex) {
					log.error(ex.getMessage(), ex);
				}
			}

			private byte[] process(byte[] input) {
				byte[] output = new byte[SystemConfig.INPUT_SIZE];
				System.arraycopy(input, 0, output, 0, output.length);
				return output;
			}
		}
	}

}
