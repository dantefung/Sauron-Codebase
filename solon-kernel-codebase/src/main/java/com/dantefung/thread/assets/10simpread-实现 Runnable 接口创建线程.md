> 本文由 [简悦 SimpRead](http://ksria.com/simpread/) 转码， 原文地址 [hollischuang.gitee.io](https://hollischuang.gitee.io/tobetopjavaer/#/basics/concurrent-coding/create-thread-with-Implement)

> Description

```
public class MultiThreads {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());


        System.out.println("实现Runnable接口创建线程");
        RunnableThread runnableThread = new RunnableThread();
        new Thread(runnableThread).start();

      }
}

class RunnableThread implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
```

输出结果：

```
main
实现Runnable接口创建线程
Thread-1
```

通过实现接口，同样覆盖`run()`就可以创建一个新的线程了。

我们都知道，Java 是不支持多继承的，所以，使用 Runnbale 接口的形式，就可以避免要多继承 。比如有一个类 A，已经继承了类 B，就无法再继承 Thread 类了，这时候要想实现多线程，就需要使用 Runnable 接口了。

除此之外，两者之间几乎无差别。

但是，这两种创建线程的方式，其实是有一个缺点的，那就是：在执行完任务之后无法获取执行结果。

如果我们希望再主线程中得到子线程的执行结果的话，就需要用到 Callable 和 FutureTask