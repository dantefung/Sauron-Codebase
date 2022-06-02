package com.dantefung.thread.comm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ThreadPoolExecutorFactory {
    private static ConcurrentHashMap<String, ThreadPoolExecutor> poolMap = new ConcurrentHashMap(32);

    public static ConcurrentHashMap<String, ThreadPoolExecutor> getPoolMap() {
        return poolMap;
    }

    /**
     * 打印状态
     */
    public static List<PoolMetrics> printStats() {
        List<PoolMetrics> list = new ArrayList<>();
        if (poolMap != null && poolMap.size() > 0) {
            poolMap.forEach((name, executor) -> {
                try {
                    if (executor != null) {
                        PoolMetrics metric = new PoolMetrics(name, executor);
                        list.add(metric);
                        log.info("[线程池状态]" + metric.toString());
                    }
                } catch (Exception e) {
                    log.error("遍历线程池状态发生异常:", e.getMessage(), e);
                }
            });
        }
        return list;
    }

    /**
     * 创建指定的线程池
     *
     * @param confg
     * @return
     */
    public static ThreadPoolExecutor create(Config confg) {
        //初始化线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(confg.corePoolSize,
                confg.maximumPoolSize,
                confg.keepAliveTime,
                confg.unit,
                confg.workQueue,
                r -> new Thread(r, confg.threadPoolName + "-pool-" + confg.threadSeq.getAndIncrement()),
                confg.handler) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {

            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                printException(r, t);
            }
        };
        poolMap.put(confg.getThreadPoolName(), executor);
        return executor;
    }

    /**
     * 根据线程数及名称创建线程池
     *
     * @param nThreads
     * @param threadPoolName
     * @return
     */
    public static ExecutorService create(int nThreads, String threadPoolName) {
        return create(Config.builder()
                .corePoolSize(nThreads)
                .maximumPoolSize(nThreads)
                .keepAliveTime(60L)
                .workQueue(new ArrayBlockingQueue<>(100))
                .unit(TimeUnit.SECONDS)
                .handler(new ThreadPoolExecutor.CallerRunsPolicy())
                .threadPoolName(threadPoolName)
                .build());
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = ThreadPoolExecutorFactory.create(Config.builder()
                .corePoolSize(1)
                .maximumPoolSize(1)
                .keepAliveTime(5)
                .workQueue(new ArrayBlockingQueue<>(10))
                .unit(TimeUnit.MINUTES)
                .handler(new ThreadPoolExecutor.CallerRunsPolicy())
                .threadPoolName("myExecutor")
                .build());
        executor.execute(() -> {
            System.out.println("for test");
        });
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Config {
        /**
         * 核心线程数
         */
        private int corePoolSize;
        /**
         * 最大线程数
         */
        private int maximumPoolSize;
        /**
         * 避免回收空闲最大时间
         */
        private long keepAliveTime;
        /**
         * 时间单位
         */
        private TimeUnit unit;

        /**
         * 缓冲队列
         */
        private BlockingQueue<Runnable> workQueue;

        /**
         * 拒绝策略
         */
        private RejectedExecutionHandler handler;

        /**
         * 线程池名称
         */
        private String threadPoolName;

        /**
         * 线程id生成
         */
        @Builder.Default
        private AtomicInteger threadSeq = new AtomicInteger(0);

    }

    protected static void printException(Runnable r, Throwable t) {
        if (t == null && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
        if (t != null) {
            log.error(t.getMessage(), t);
        }

    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PoolMetrics {
        private String poolName;
        private int corePoolSize;
        private int poolSize;
        private int maxPoolSize;
        private int activeThreads;
        /**
         * 队列当前的长度
         */
        private int queueTaskCount;
        /**
         * 还未执行的任务
         */
        private long taskCount;
        /**
         * 已完成的任务
         */
        private long completedTaskCount;

        public PoolMetrics(String name, ThreadPoolExecutor executor) {
            this.poolName = name;
            this.corePoolSize = executor.getCorePoolSize();
            this.poolSize = executor.getPoolSize();
            this.maxPoolSize = executor.getMaximumPoolSize();
            this.activeThreads = executor.getActiveCount();
            this.queueTaskCount = executor.getQueue().size();
            this.taskCount = executor.getTaskCount();
            this.completedTaskCount = executor.getCompletedTaskCount();
        }

        @Override
        public String toString() {
            return "PoolMetrics{" +
                    "poolName='" + poolName + '\'' +
                    ", corePoolSize=" + corePoolSize +
                    ", poolSize=" + poolSize +
                    ", maxPoolSize=" + maxPoolSize +
                    ", activeThreads=" + activeThreads +
                    ", queueTaskCount=" + queueTaskCount +
                    ", taskCount=" + taskCount +
                    ", completedTaskCount=" + completedTaskCount +
                    '}';
        }
    }

}
