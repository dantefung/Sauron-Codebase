> 本文由 [简悦 SimpRead](http://ksria.com/simpread/) 转码， 原文地址 [hollischuang.gitee.io](https://hollischuang.gitee.io/tobetopjavaer/#/basics/concurrent-coding/create-thread-with-callback-future-task)

> Description

自从 Java 1.5 开始，提供了 Callable 和 Future，通过它们可以在任务执行完毕之后得到任务执行结果。

```
public class MultiThreads {
    public static void main(String[] args) throws InterruptedException {
        CallableThread callableThread = new CallableThread();
        FutureTask futureTask = new FutureTask<>(callableThread);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
}

class CallableThread implements Callable {
    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        return "Hollis";
    }

}
```

输出结果：

```
main
通过Callable和FutureTask创建线程
Thread-2
Hollis
```

Callable 位于 java.util.concurrent 包下，它也是一个接口，在它里面也只声明了一个方法，只不过这个方法 call()，和 Runnable 接口中的 run() 方法不同的是，call() 方法有返回值。

以上代码中，我们在 CallableThread 的 call 方法中返回字符串 "Hollis"，在主线程是可以获取到的。

FutureTask 可用于异步获取执行结果或取消执行任务的场景。通过传入 Callable 的任务给 FutureTask，直接调用其 run 方法或者放入线程池执行，之后可以在外部通过 FutureTask 的 get 方法异步获取执行结果，因此，FutureTask 非常适合用于耗时的计算，主线程可以在完成自己的任务后，再去获取结果。

另外，FutureTask 还可以确保即使调用了多次 run 方法，它都只会执行一次 Runnable 或者 Callable 任务，或者通过 cancel 取消 FutureTask 的执行等。

值得注意的是，`futureTask.get()`会阻塞主线程，一直等子线程执行完并返回后才能继续执行主线程后面的代码。

一般，在 Callable 执行完之前的这段时间，主线程可以先去做一些其他的事情，事情都做完之后，再获取 Callable 的返回结果。可以通过`isDone()`来判断子线程是否执行完。

以上代码改造下就是如下内容：

```
public class MultiThreads {
    public static void main(String[] args) throws InterruptedException {
        CallableThread callableThread = new CallableThread();
        FutureTask futureTask = new FutureTask<>(callableThread);
        new Thread(futureTask).start();

        System.out.println("主线程先做其他重要的事情");
        if(!futureTask.isDone()){
            // 继续做其他事儿
        }
        System.out.println(future.get()); // 可能会阻塞等待结果
}
```

一般，我们会把 Callable 放到线程池中，然后让线程池去执行 Callable 中的代码。关于线程池前面介绍过了，是一种避免重复创建线程的开销的技术手段，线程池也可以用来创建线程。