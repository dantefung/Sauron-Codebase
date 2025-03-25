package com.dantefung.thread.dp.twophasetermination;

public class CountupTread extends Thread {

    private long counter = 0;
    private volatile  boolean shutdownRequested = false;

    /**
     * 中止请求
     */
    public void shutdownRequest(){
        shutdownRequested = true;
        interrupt();//中断所有等待队列中的线程
    }

    /**
     * 检查是否发出了终止请求
     * @return
     */
    public boolean isShutdownRequested(){
        return shutdownRequested;
    }

    /**
     * 线程体
     */
    @Override
    public void run() {
        try {
            while (!isShutdownRequested()){
                doWork();
            }
        }catch (InterruptedException e){

        }finally {
            doShutdown();
        }
    }

    /**
     * 终止处理
     */
    private void doShutdown() {
        System.out.println("doShutdown:counter = "+counter);
    }

    /**
     * 正常操作
     * @throws InterruptedException
     */
    private void doWork()throws InterruptedException {
        counter++;
        System.out.println("doWork :counter = "+counter);
        Thread.sleep(500);

    }
    
}