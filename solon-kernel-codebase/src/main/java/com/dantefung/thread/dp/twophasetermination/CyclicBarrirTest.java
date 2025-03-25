package com.dantefung.thread.dp.twophasetermination;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrirTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random()*10000));
                        System.out.println("线程"+Thread.currentThread().getName()+"即将到达集合地点" +
                                "，当前已经有 "+(cyclicBarrier.getNumberWaiting()+1)+"已经到达。"+
                                (cyclicBarrier.getNumberWaiting()==2?"都到齐了，一起走啊":"继续等待"));

                        cyclicBarrier.await();


                    }catch (InterruptedException e){

                    }catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.execute(runnable);
        }
        executorService.shutdown();
    }

}