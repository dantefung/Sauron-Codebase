package com.dantefung.thread.future;

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
