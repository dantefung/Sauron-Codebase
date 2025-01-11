package com.dantefung.concurrent.c013;

import java.util.ArrayList;
import java.util.List;

public class T {

    /**
     * volatile只保证了可见性
     * 只保证线程读得到被修改的数据，线程一开始只会将数据先读到自己的线程独占内存(内存包含cpu的缓冲区)。
     */
    volatile int count = 0;

    void m() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        T t = new T();

        List<Thread> threads = new ArrayList<Thread>();

        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(t::m, "thread" + i));
        }

        threads.forEach((o)->o.start());

        threads.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count);
    }
}
