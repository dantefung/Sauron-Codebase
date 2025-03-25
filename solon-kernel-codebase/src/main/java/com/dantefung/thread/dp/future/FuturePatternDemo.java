package com.dantefung.thread.dp.future;

/**
 * 数据访问接口
 */
interface Data {
    String getContent();
}

/**
 *  真实数据类
 */
class RealData implements Data {
    private final String content;

    public RealData(String content) {
        // 模拟构造函数处理时间较长
        System.out.println("RealData: 正在构造真实数据...");
        try {
            Thread.sleep(3000); // 模拟耗时操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.content = content;
        System.out.println("RealData: 构造完成");
    }

    @Override
    public String getContent() {
        return content;
    }
}

/**
 * FutureData 作为 RealData 的代理
 */
class FutureData implements Data {
    private RealData realData;
    private boolean isReady = false;

    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }
        this.realData = realData;
        this.isReady = true;
        notifyAll(); // 唤醒等待的线程
    }

    @Override
    public synchronized String getContent() {
        while (!isReady) {
            try {
                wait(); // 等待 RealData 准备好
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.getContent();
    }
}

/**
 * 负责创建 FutureData 并启动后台线程加载 RealData
 */
class Host {
    public Data request(final String content) {
        System.out.println("Host: 请求数据...");
        FutureData futureData = new FutureData();
        
        // 启动新线程异步创建 RealData
        new Thread(() -> {
            RealData realData = new RealData(content);
            futureData.setRealData(realData);
        }).start();

        System.out.println("Host: 立即返回 FutureData");
        return futureData;
    }
}

/**
 * 测试代码
 *
 * 通常，每个进程都拥有彼此独立的内存空间。一个进程不可以擅自读取、写入其他进程的内存。
 * 由于进程的内存空间是彼此独立的，所以一个进程无须担心被其他进程破坏。线程之间共享内存。
 * 我们经常让一个线程向内存中写入内容,来供其他线程读取。所谓“共享内存”，在Java中就是“共享实例”的意思。
 * Java的实例分配在内存上，可由多个线程进行读写。
 *
 * 由于线程之间共享内存，所以线程之间的通信可以很自然、简单地实现。
 * 一个线程向实例中写入内容，其他线程就可以读取该实例的内容。
 * 而由于多个线程可以访问同一个实例，所以我们必须正确执行互斥处理。
 */
public class FuturePatternDemo {
    public static void main(String[] args) {
        Host host = new Host();
        
        // 发送请求，立即返回 FutureData
        Data data = host.request("Hello, Future!");

        System.out.println("Main: 执行其他任务中...");
        
        // 模拟执行其他操作
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 需要数据时再获取，确保数据已准备好
        System.out.println("Main: 获取数据 -> " + data.getContent());
    }
}
