package com.dantefung.nio.reactor.multithread.server;

import com.dantefung.nio.common.SystemConfig;
import com.dantefung.nio.reactor.multithread.server.state.HandlerState;
import com.dantefung.nio.reactor.multithread.server.state.ReadState;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//---------------Reactor 4: Handler setup--------------------
@Slf4j
public class Handler implements Runnable {

	//多线程处理业务逻辑
	private static final int THREAD_COUNTING = 10;
	final SocketChannel socketChannel;
	final SelectionKey sk;
	ByteBuffer inputBuffer = ByteBuffer.allocate(SystemConfig.MAXIN);
	ByteBuffer outputBuffer = ByteBuffer.allocate(SystemConfig.MAXOUT);
	private HandlerState state; // 以狀態模式實現Handler
	private ThreadPoolExecutor pool = new ThreadPoolExecutor(THREAD_COUNTING, THREAD_COUNTING, 10, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>()); // 線程池


	public Handler(Selector sel, SocketChannel c) throws IOException {
		socketChannel = c;
		// 设置非阻塞
		c.configureBlocking(false);
		// Optionally try first read now
		// 将当前的socket连接注册到selector上
		sk = socketChannel.register(sel, 0);
		// 把handler注册为callback
		sk.attach(this);
		state = new ReadState();
		// 当前的socket连接关注读事件
		sk.interestOps(SelectionKey.OP_READ);
		pool.setMaximumPoolSize(32); // 設置線程池最大線程數
		sel.wakeup();
	}

	@Override
	public void run() {
		log.info("[Handler] 开始回调处理...");
		try {
			log.info("[Handler] handler current state is {}", state.getClass().getSimpleName());
			state.handle(this, sk, socketChannel, pool);
		} catch (IOException ex) {
			log.error("read或send时发生异常！异常信息：" + ex.getMessage(), ex);
			closeChannel();
		}
	}

	public void closeChannel() {
		try {
			sk.cancel();
			socketChannel.close();
		} catch (IOException e) {
			log.error("关闭通道时发生异常！异常信息：" + e.getMessage(), e);
		}
	}

	public void setState(HandlerState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
	}
}