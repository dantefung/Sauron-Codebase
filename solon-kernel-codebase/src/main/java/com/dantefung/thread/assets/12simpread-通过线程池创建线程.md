> 本文由 [简悦 SimpRead](http://ksria.com/simpread/) 转码， 原文地址 [hollischuang.gitee.io](https://hollischuang.gitee.io/tobetopjavaer/#/basics/concurrent-coding/create-thread-with-thead-pool)

> Description

Java 中提供了对线程池的支持，有很多种方式。Jdk 提供给外部的接口也很简单。直接调用 ThreadPoolExecutor 构造一个就可以了：

```
public class MultiThreads {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println(Thread.currentThread().getName());
        System.out.println("通过线程池创建线程");
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(10));
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
    }
}
```

输出结果：

```
main
通过线程池创建线程
pool-1-thread-1
```

所谓线程池本质是一个 hashSet。多余的任务会放在阻塞队列中。

线程池的创建方式其实也有很多，也可以通过 Executors 静态工厂构建，但一般不建议。建议使用线程池来创建线程，并且建议使用带有 ThreadFactory 参数的 ThreadPoolExecutor（需要依赖 guava）构造方法设置线程名字，具体原因我们在后面的章节中在详细介绍。