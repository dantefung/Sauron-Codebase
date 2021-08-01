package com.dantefung.nio.reactor.mainsubmutilthread.server;

/**
 * 主从Reactor多线程模型
 */
public class Bootrap {

    public static void main(String[] args) {
        /**
         * 初始化一个线程池，然后创建一个主Reactor，并加入一个从Reactor.
         */
        ThreadPool.getPool().init(3);
        new MainReactor(9090).addSub(new SubReactor()).run();

    }

}