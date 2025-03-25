package com.dantefung.thread.dp.workthread;

import java.util.Random;

public class Request {

    private final String name;
    private final int number;
    private static final Random random = new Random();
    public Request(String name,int number){
        this.name = name;
        this.number = number;
    }

    /**
     * 处理该请求的方法
     */
    public void execute(){
        System.out.println(Thread.currentThread().getName()+" executes "+this);
        try {
            Thread.sleep(random.nextInt(1000));
        }catch (InterruptedException e){

        }
    }

    @Override
    public String toString() {
        return " [ Request from "+ name + " NO." +number+" ] ";
    }
}