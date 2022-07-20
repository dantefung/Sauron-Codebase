package com.dantefung.tool.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * 平滑关闭线程池收集器
 */
@Slf4j
public class ExecutorShutdownHook implements GracefulShutdown {
    private static final ExecutorShutdownHook EXECUTOR_SHUTDOWN_HOOK = new ExecutorShutdownHook();

    public static ExecutorShutdownHook getExecutorShutdownHook() {
        return EXECUTOR_SHUTDOWN_HOOK;
    }

    private ExecutorShutdownHook() {
        this.registerToShutdownHook();
    }

    private List<ExecutorService> list = new ArrayList<>();

    public List<ExecutorService> getList() {
        return list;
    }

    public void addHook(ExecutorService executor) {
        list.add(executor);
    }

    @Override
    public String gracefulShutdownName() {
        return "自定义线程池";
    }

    @Override
    public int gracefulShutdownOrder() {
        return 50;
    }

    // 关闭自定义线程池
    @Override
    public void gracefulShutdown() {
        for (int i = 0; i < list.size(); i++) {
            ExecutorService executor = list.get(i);
            String name = executor.getClass().getSimpleName();
            if(executor instanceof MyThreadPool) {
                ThreadFactory threadFactory = ((MyThreadPool)executor).getThreadFactory();
                if(threadFactory instanceof MyThreadFactory) {
                    name = ((MyThreadFactory) threadFactory).getName();
                }
            }
            log.info("自定义线程池[{}][{}]正在关闭", i, name);
            executor.shutdown();
            while(!executor.isTerminated()) {}
        }
    }
}
