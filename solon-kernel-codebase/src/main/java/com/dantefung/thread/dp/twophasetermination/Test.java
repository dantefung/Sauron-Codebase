package com.dantefung.thread.dp.twophasetermination;

public class Test {
    public static void main(String[] args) {
        System.out.println( " main : begin ");
        try {
            //启动线程
            CountupTread t = new CountupTread();
            t.start();

            //模拟处理业务
            Thread.sleep(10000);
            //线程的终止请求
            System.out.println(" main shutdownRequest");
            t.shutdownRequest();

            System.out.println(" main : join");
            //等待线程终止
            t.join();

        }catch (InterruptedException e){

        }
        System.out.println( " main : end ");
    }

}