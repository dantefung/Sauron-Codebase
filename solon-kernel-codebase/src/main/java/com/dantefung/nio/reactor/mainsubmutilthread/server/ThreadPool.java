package com.dantefung.nio.reactor.mainsubmutilthread.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 示例-线程池
 */
public class ThreadPool{

    public static final ThreadPool pool = new ThreadPool();

    private boolean init=false;

    private  static ExecutorService executorService;

    private ThreadPool(){};

    public synchronized void init(int size){
        if(!init){
            executorService= Executors.newFixedThreadPool(size);
            init=true;
        }else {
            System.out.println("the thread pool had inited");
        }

    }

    public static ThreadPool getPool(){
       return pool;
    }

    public  void submit(Runnable runnable){
        if(init){
            executorService.submit(runnable);
        }else {
            throw new RuntimeException("the thread pool is not inited");
        }
    }
}