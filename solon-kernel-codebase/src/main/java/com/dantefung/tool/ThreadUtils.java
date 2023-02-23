package com.dantefung.tool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ThreadUtils
 */
public abstract class ThreadUtils {
    
    private final static Logger LOG = LoggerFactory.getLogger(ThreadUtils.class);

    /**
     * @param time < 0 not sleep
     * @param timeUnit
     * @throws IllegalStateException
     */
    public final static void sleep(long time, TimeUnit timeUnit) {
        final long ms = timeUnit.toMillis(time);
        if (ms < 0) {
            return;
        }
        
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
    
    /**
     * @param ms
     * @throws IllegalStateException
     */
    public final static void sleep(long ms) {
       sleep(ms, TimeUnit.MILLISECONDS);
    }
    
    /**
     * <pre>
     * Create a new thread, manual start method
     * 
     * {@code
     * ThreadUtils.newThread("AtomDomesticSearchService-Refresh", new Runnable() {
            
            @Override
            public void run() {
                doSearch();
            }
        }, false)<b>.start()</b>;
     * }
     * </pre>
     * 
     * 
     * @see Thread#start()
     *
     * @param name The name of the thread
     * @param runnable The work for the thread to do
     * @param daemon Should the thread block JVM stop?
     * @return The unstarted thread
     */
    public final static Thread newThread(String name, Runnable runnable, boolean daemon) {
        Thread thread = new Thread(runnable, name);
        thread.setDaemon(daemon);
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
			public void uncaughtException(Thread t, Throwable e) {
                LOG.error("Uncaught exception in thread '" + t.getName() + "':", e);
            }
        });
        return thread;
    }
    
    /**
     * e.g:"HandleEventPool-Worker-0"
     * e.g:"HandleEventPool-Worker-1"
     * e.g:"HandleEventPool-Worker-n"
     * 
     * @param namePrefix HandleEventPool-Worker
     * @param isDaemon
     * @return
     */
    public final static ThreadFactory newThreadFactory(final String namePrefix, final boolean isDaemon) {
        return new NamedThreadFactory(namePrefix, isDaemon);
    }
    
    public final static class NamedThreadFactory implements ThreadFactory {

        final AtomicInteger threadNumber = new AtomicInteger();
        final String namePrefix;
        final boolean isDaemon;

        public NamedThreadFactory(String namePrefix) {
            this(namePrefix, true);
        }
        
        public NamedThreadFactory(String namePrefix, boolean isDaemon) {
            this.namePrefix = namePrefix;
            this.isDaemon = isDaemon;
        }

        @Override public Thread newThread(Runnable r) {
            Thread t = new Thread(r, namePrefix + "-" + threadNumber.getAndIncrement());
            t.setDaemon(isDaemon);
            return t;
        }

    }
    
    public final static <RS> Future<RS> getFuture(Executor e, Runnable r, RS v) {
        RunnableFuture<RS> task = newTaskFor(r, v);
        e.execute(task);
        return task;
    }
    
    public final static <RS> Future<RS> getFuture(Executor e, Callable<RS> c) {
        RunnableFuture<RS> task = newTaskFor(c);
        e.execute(task);
        return task;
    }
    
    protected final static <RS> RunnableFuture<RS> newTaskFor(Runnable r, RS value) {
        return new FutureTask<RS>(r, value);
    }
    
    protected final static <RS> RunnableFuture<RS> newTaskFor(Callable<RS> c) {
        return new FutureTask<RS>(c);
    }

    /**
     * Destroy passed thread using isAlive and join.
     *
     * @param t Thread to stop
     */
    public final static void destroy(final Thread t) {
        destroy(t, 0);
    }
    
    /**
     * Destroy passed thread using isAlive and join.
     *
     * @param millis Pass 0 if we're to wait forever.
     * @param t Thread to stop
     */
    public final static void destroy(final Thread t, final long millis) {
        if (t == null) {
            return;
        }
        while (t.isAlive()) {
            try {
                t.interrupt();
                t.join(millis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * 按照ExecutorService JavaDoc示例代码编写的Graceful Shutdown方法.
     * 先使用shutdown, 停止接收新任务并尝试完成所有已存在任务.
     * 如果超时, 则调用shutdownNow, 取消在workQueue中Pending的任务,并中断所有阻塞函数.
     * 如果仍人超時，則強制退出.
     * 另对在shutdown时线程本身被调用中断做了处理.
     */
     static void gracefulShutdown(ExecutorService pool, int shutdownTimeout, int shutdownNowTimeout,
            TimeUnit timeUnit) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(shutdownTimeout, timeUnit)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(shutdownNowTimeout, timeUnit)) {
                    System.err.println("Pool did not terminated");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        
        Future<Void> future = ThreadUtils.getFuture(Executors.newCachedThreadPool(), new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                System.out.println("start");
                Thread.sleep(5000L);
                System.out.println("end");
                return null;
            }
            
        });
        
        future.get(1, TimeUnit.SECONDS);
        
        System.out.println("e");
    }
}
