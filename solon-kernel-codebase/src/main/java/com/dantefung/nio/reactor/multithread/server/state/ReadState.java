package com.dantefung.nio.reactor.multithread.server.state;

import com.dantefung.nio.reactor.multithread.server.Handler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class ReadState implements HandlerState {

	private SelectionKey sk;

	public ReadState() {
	}

	@Override
	public void changeState(Handler h) {
		// TODO Auto-generated method stub
		h.setState(new WorkState());
	}

	@Override
	public void handle(Handler h, SelectionKey sk, SocketChannel sc, ThreadPoolExecutor pool)
			throws IOException { // read()
		this.sk = sk;
		// non-blocking下不可用Readers，因為Readers不支援non-blocking
		byte[] arr = new byte[1024];
		ByteBuffer buf = ByteBuffer.wrap(arr);

		int numBytes = sc.read(buf); // 讀取字符串
		if (numBytes == -1) {
			log.warn("[Warning!] A client has been closed.");
			sk.cancel();
			h.closeChannel();
			return;
		}
		String str = new String(arr); // 將讀取到的byte內容轉為字符串型態
		if ((str != null) && !str.equals(" ")) {
			h.setState(new WorkState()); // 改變狀態(READING->WORKING)
			pool.execute(new WorkerThread(h, str)); // do process in worker thread
			log.info("[Handler->ReadState]"+sc.socket().getRemoteSocketAddress().toString() + " > " + str);
		}

	}

	/*
	 * 執行邏輯處理之函數
	 */
	synchronized void process(Handler h, String str) {
		// do process(decode, logically process, encode)..
		// ..
		// 模拟工作线程处理耗时...
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("[Handler->ReadState->Work Thread] input completed , start process ...");
		h.setState(new WriteState()); // 改變狀態(WORKING->SENDING)
		this.sk.interestOps(SelectionKey.OP_WRITE); // 通過key改變通道註冊的事件
		this.sk.selector().wakeup(); // 使一個阻塞住的selector操作立即返回
	}

	/*
	 * 工作者線程
	 */
	class WorkerThread implements Runnable {

		Handler h;
		String str;

		public WorkerThread(Handler h, String str) {
			this.h = h;
			this.str = str;
		}

		@Override
		public void run() {
			process(h, str);
		}

	}
}