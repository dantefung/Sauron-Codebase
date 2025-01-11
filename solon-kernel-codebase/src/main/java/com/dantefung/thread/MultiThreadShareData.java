/*
 * Copyright (C), 2015-2020
 * FileName: MultiThreadShareData
 * Author:   DANTE FUNG
 * Date:     2020/11/27 12:32 上午
 * Description: 多线程访问共享数据
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/27 12:32 上午   V1.0.0
 */
package com.dantefung.thread;

/**
 * @Title: MultiThreadShareData
 * @Description: 多线程访问共享数据
 * @author DANTE FUNG
 * @date 2020/11/27 00/32
 * @since JDK1.8
 */
public class MultiThreadShareData {

    public static void main(String[] args) {
        /**
         * 如果每个线程执行的代码相同，可以使用同一个Runnable对象，
         * 这个Runnable对象中有哪个共享数据，
         * 例如，卖票系统就可以这么做。
         */
        ShareData1 shareData1 = new ShareData1();
        new Thread(shareData1).start();
        new Thread(shareData1).start();
        /**
         * 如果每个线程执行的代码不同，这个时候需要用不同的Runnable对象，
         * 有如下两种方式实现这些Runnable对象之间的数据共享：
         *
         * 将共享数据封装在另外一个对象中，然后将这个对象逐一传递给各个Runnable对象。
         * 每个线程对共享数据的操作方法也分配到哪个对象身上去完成，这样容易实现针对该数据进行的各个
         * 操作的互斥和通信。
         *
         * 将这些Runnable对象作为某一个类中的内部类，共享数据作为这个外部类中的
         * 成员变量，每个线程对共享数据的操作方法也分配给外部类，
         * 以便实现对共享数据进行的各个操作的互斥和通信，作为内部类的各个Runnable对象调用外部类的这些方法。
         *
         * 上面两种方式的组合：将共享数据封装在另外一个对象中，每个线程对共享数据的操作方法
         * 也分配到那个对象身上去完成，对象作为这个外部类中的成员变量或方法中的局部变量，
         * 每个线程的Runnable对象作为外部类中的成员内部类或局部内部类
         *
         * 总之，要同步互斥的几段代码最好分别放在几个独立的方法中，这些方法再放在同一个类中，
         * 这样比较容易实现他们之间的同步互斥和通信。
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                shareData1.decrement();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                shareData1.increment();
            }
        }).start();
    }
}

class ShareData1 implements Runnable {
    private int j = 0;
    private int count = 0;

    public synchronized void increment() {
        j++;
    }

    public synchronized void decrement() {
        j--;
    }

    @Override
    public void run() {
        while (true) {
            count --;
            System.out.println(count);
        }
    }
}

