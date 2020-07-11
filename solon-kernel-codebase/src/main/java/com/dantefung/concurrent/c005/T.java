package com.dantefung.concurrent.c005;

import java.util.concurrent.TimeUnit;

public class T implements Runnable {
 
    private int count = 10;
     
    public synchronized void run() {
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
     
    public static void main(String[] args) {
        T t = new T();
        for(int i=0; i<5; i++) {
            new Thread(t, "THREAD" + i).start();
        }
    }
     
}