package com.dantefung.thread.dp.threadpermessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPerMessageExample {
    /**
     * 创建一个线程池，防止创建过多线程
     */
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public void handleRequest(String message) {
        executor.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " - Handling message: " + message);
            try {
                Thread.sleep(2000); // 模拟任务处理
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(Thread.currentThread().getName() + " - Finished processing: " + message);
        });
    }

    public void shutdown() {
        executor.shutdown();
    }

    public static void main(String[] args) {
        ThreadPerMessageExample handler = new ThreadPerMessageExample();

        // 发送多个请求，每个请求由独立线程处理
        for (int i = 1; i <= 5; i++) {
            handler.handleRequest("Message " + i);
        }

        // 关闭线程池
        handler.shutdown();
    }
}
