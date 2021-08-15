package com.dantefung.thread.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:   Condition 接口在使用前必须先调用 ReentrantLock 的 lock() 方法获得锁。
 * 				  之后调用 Condition 接口的 await() 将释放锁, 并且在该 Condition 上等待,
 * 				  直到有其他线程调用 Condition 的 signal() 方法唤醒线程。使用方式和 wait,notify 类似
 *
 * @Author: DANTE FUNG
 * @Date: 2021/8/15 15:57
 * @since JDK 1.8
 */
public class ConditionTest {

    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    public static void main(String[] args) throws InterruptedException {

        lock.lock();
        new Thread(new SignalThread()).start();
        System.out.println("主线程等待通知");
        try {
            condition.await();
        } finally {
            lock.unlock();
        }
        System.out.println("主线程恢复运行");
    }
    static class SignalThread implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                condition.signal();
                System.out.println("子线程通知");
            } finally {
                lock.unlock();
            }
        }
    }
}