package com.dantefung.tool.threadpool;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.*;

/**
 * <br>线程池定义</br>
 * <p>屏蔽了少参数的构造函数，必须指定所有运行参数</p>
 * 阿里的ttl项目,如果采用javaagent形式引入,会自动包装java默认的线程池
 * 但是由于TtlExecutorTransformlet里使用类名识别而不是实例类型识别,继承java默认的线程池的实现不会被自动包装
 * 所以这里需要使用TtlRunnable来包装过后再执行
 *
 *
 * @since 1.0
 */
public class MyThreadPool extends ThreadPoolExecutor {
    /**
     * 线程池已满异常
     */
    public static class ThreadPoolFullException extends Exception {
    }

    public static class NotAllowOperationException extends RuntimeException {
    }

    /**
     * 最大容量
     */
    public static final int MAX_CAPACITY = 4096;


    public Future<?> submit2(Runnable task) throws ThreadPoolFullException {
        try {
            Runnable ttlRunnable = TtlRunnable.get(task);
            return super.submit(ttlRunnable);
        } catch (RejectedExecutionException e) {
            throw new ThreadPoolFullException();
        }
    }

    public <T> Future<T> submit2(Runnable task, T result) throws ThreadPoolFullException {
        try {
            Runnable ttlRunnable = TtlRunnable.get(task);
            return super.submit(ttlRunnable, result);
        } catch (RejectedExecutionException e) {
            throw new ThreadPoolFullException();
        }
    }

    public <T> Future<T> submit2(Callable<T> task) throws ThreadPoolFullException {
        try {
            Callable ttlCallable = TtlCallable.get(task);
            return super.submit(ttlCallable);
        } catch (RejectedExecutionException e) {
            throw new ThreadPoolFullException();
        }
    }

    @Override
    @Deprecated
    public Future<?> submit(Runnable task) {
        throw new IllegalStateException("请使用submitE(Runnable task)方法！");
    }

    @Override
    @Deprecated
    public <T> Future<T> submit(Runnable task, T result) {
        throw new IllegalStateException("请使用submit(Runnable task, T result)方法！");
    }

    @Override
    @Deprecated
    public <T> Future<T> submit(Callable<T> task) {
        throw new IllegalStateException("请使用submit(Callable<T> task)方法！");
    }

    @Override
    public void execute(Runnable command) {
        Runnable ttlRunnable = TtlRunnable.get(command);
        super.execute(ttlRunnable);
    }

    public MyThreadPool(int corePoolSize, int maximumPoolSize, long aliveSecond, String threadName) {
        super(corePoolSize, maximumPoolSize, aliveSecond, TimeUnit.SECONDS, new LinkedBlockingQueue<>(MAX_CAPACITY), new MyThreadFactory(threadName), new AbortPolicy());
        ExecutorShutdownHook.getExecutorShutdownHook().addHook(this);
    }

    public MyThreadPool(int corePoolSize, int maximumPoolSize, long aliveSecond, String threadName, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, aliveSecond, TimeUnit.SECONDS, workQueue, new MyThreadFactory(threadName), new AbortPolicy());
        ExecutorShutdownHook.getExecutorShutdownHook().addHook(this);
    }

    public MyThreadPool(int corePoolSize, int maximumPoolSize, long aliveSecond, String threadName, RejectedExecutionHandler reh) {
        super(corePoolSize, maximumPoolSize, aliveSecond, TimeUnit.SECONDS, new LinkedBlockingQueue<>(MAX_CAPACITY), new MyThreadFactory(threadName), reh);
        ExecutorShutdownHook.getExecutorShutdownHook().addHook(this);
    }

    private MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        ExecutorShutdownHook.getExecutorShutdownHook().addHook(this);
    }

    private MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        ExecutorShutdownHook.getExecutorShutdownHook().addHook(this);
    }

    private MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        ExecutorShutdownHook.getExecutorShutdownHook().addHook(this);
    }

}
