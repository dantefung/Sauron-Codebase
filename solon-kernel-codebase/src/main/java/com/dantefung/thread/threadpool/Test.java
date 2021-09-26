package com.dantefung.thread.threadpool;

import java.util.concurrent.ExecutorService;

public class Test implements Runnable {
	private static ExecutorService es = MonitorThreadPoolExecutor.newCachedThreadPool();

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 100; i++) {
			es.execute(new Test());
		}
		es.shutdown();
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}