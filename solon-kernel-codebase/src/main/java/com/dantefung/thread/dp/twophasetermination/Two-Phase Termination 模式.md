> 本文由 [简悦 SimpRead](http://ksria.com/simpread/) 转码， 原文地址 [www.cnblogs.com](https://www.cnblogs.com/inspred/p/9439466.html)

一，Two-Phase Termination 模式

翻译过来就是：分两阶段终止

二，示例程序

[![](http://assets.cnblogs.com/images/copycode.gif)](javascript:void(0); "复制代码")

```
public class CountupTread extends Thread {

    private long counter = 0;
    private volatile  boolean shutdownRequested = false;

    //中止请求
    public void shutdownRequest(){
        shutdownRequested = true;
        interrupt();//中断所有等待队列中的线程
    }

    //检查是否发出了终止请求
    public boolean isShutdownRequested(){
        return shutdownRequested;
    }

    //线程体
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

    //终止处理
    private void doShutdown() {
        System.out.println("doShutdown:counter = "+counter);
    }

    //正常操作
    private void doWork()throws InterruptedException {
        counter++;
        System.out.println("doWork :counter = "+counter);
            Thread.sleep(500);

    }
    
}

```

[![](http://assets.cnblogs.com/images/copycode.gif)](javascript:void(0); "复制代码")

[![](http://assets.cnblogs.com/images/copycode.gif)](javascript:void(0); "复制代码")

```
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

```

[![](http://assets.cnblogs.com/images/copycode.gif)](javascript:void(0); "复制代码")

三，特点

安全地终止线程  
必定会进行终止处理  
发出终止请求后尽快进行终止处理

四，java.util.cocurrent 包与线程同步

1.CountDownLatch 类

[![](http://assets.cnblogs.com/images/copycode.gif)](javascript:void(0); "复制代码")

```
public class CountdownLatchTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdAnswer = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("线程"+Thread.currentThread().getName()+"正准备接受命令");
                        //cdOrder的初始值为1,当线程执行到cdOrder.await();会阻塞在这里。
                        // 当执行了cdOrder.countDown();会减为0,一旦为0,就开始继续执行
                        cdOrder.await();
                        System.out.println("线程"+Thread.currentThread().getName()+"已经接受命令");
                        Thread.sleep((long) (Math.random()*10000));
                        System.out.println("线程"+Thread.currentThread().getName()+"回应命令处理结果");
                        //共有三个线程，每个线程执行到这里，cdAnswer就会减少一个
                        cdAnswer.countDown();

                    }catch (InterruptedException e){

                    }
                }
            };
            executorService.execute(runnable);
        }
        try {
            Thread.sleep((long) (Math.random()*10000));
            System.out.println("线程"+Thread.currentThread().getName()+"即将发布命令");
            cdOrder.countDown();
            System.out.println("线程"+Thread.currentThread().getName()+"已经发送命令，正在等待结果");
            //cdAnswer初始值是3,主线程执行到这里时会阻塞，直到上面的cdAnswer.countDown();减少为0,
            //主线程才继续执行
            cdAnswer.await();
            System.out.println("线程"+Thread.currentThread().getName()+"已经收到所有响应结果");
        }catch (Exception e){

        }
        executorService.shutdown();

    }
}

```

[![](http://assets.cnblogs.com/images/copycode.gif)](javascript:void(0); "复制代码")

2.CyclicBarrier 类

[![](http://assets.cnblogs.com/images/copycode.gif)](javascript:void(0); "复制代码")

```
public class CyclicBarrirTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random()*10000));
                        System.out.println("线程"+Thread.currentThread().getName()+"即将到达集合地点" +
                                "，当前已经有 "+(cyclicBarrier.getNumberWaiting()+1)+"已经到达。"+
                                (cyclicBarrier.getNumberWaiting()==2?"都到齐了，一起走啊":"继续等待"));

                        cyclicBarrier.await();


                    }catch (InterruptedException e){

                    }catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.execute(runnable);
        }
        executorService.shutdown();
    }

}

```

[![](http://assets.cnblogs.com/images/copycode.gif)](javascript:void(0); "复制代码")