package com.dantefung.tool.threadpool;

import lombok.Getter;

import java.util.concurrent.ThreadFactory;

/**
 * <br>线程工厂</br>
 * <p>
 * 命名格式 name-index,index以0开始
 * </p>
 *
 * @since 1.0
 */
public class MyThreadFactory implements ThreadFactory {
    /**
     * 构造器
     *
     * @param name 线程名称
     */
    public MyThreadFactory(String name) {
        this.name = name;
    }

    /**
     * 线程名称
     */
    @Getter
    private String name;

    /**
     * 线程序号
     */
    private int index = -1;

    /**
     * 构建线程
     * <p>
     * 命名格式 name-index
     * </p>
     *
     * @param r runnable
     * @return 线程
     * @see ThreadFactory#newThread(Runnable)
     */
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(name + "-" + (++index));
        return thread;
    }

}
