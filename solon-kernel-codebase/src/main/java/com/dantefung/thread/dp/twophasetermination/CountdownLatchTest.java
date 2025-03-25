package com.dantefung.thread.dp.twophasetermination;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountdownLatchTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdAnswer = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("线程"+Thread.currentThread().getName()+"正准备接受命令");
                        //cdOrder的初始值为1,当线程执行到cdOrder.await();会阻塞在这里。
                        // 当执行了cdOrder.countDown();会减为0,一旦为0,就开始继续执行
                        cdOrder.await();
                        System.out.println("线程"+Thread.currentThread().getName()+"已经接受命令");
                        Thread.sleep((long) (Math.random()*10000));
                        System.out.println("线程"+Thread.currentThread().getName()+"回应命令处理结果");
                        //共有三个线程，每个线程执行到这里，cdAnswer就会减少一个
                        cdAnswer.countDown();

                    }catch (InterruptedException e){

                    }
                }
            };
            executorService.execute(runnable);
        }
        try {
            Thread.sleep((long) (Math.random()*10000));
            System.out.println("线程"+Thread.currentThread().getName()+"即将发布命令");
            cdOrder.countDown();
            System.out.println("线程"+Thread.currentThread().getName()+"已经发送命令，正在等待结果");
            //cdAnswer初始值是3,主线程执行到这里时会阻塞，直到上面的cdAnswer.countDown();减少为0,
            //主线程才继续执行
            cdAnswer.await();
            System.out.println("线程"+Thread.currentThread().getName()+"已经收到所有响应结果");
        }catch (Exception e){

        }
        executorService.shutdown();

    }
}