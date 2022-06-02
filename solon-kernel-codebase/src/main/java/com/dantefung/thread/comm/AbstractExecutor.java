package com.dantefung.thread.comm;

import lombok.extern.slf4j.Slf4j;
/*import org.springframework.beans.factory.DisposableBean;*/

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
public abstract class AbstractExecutor /*implements DisposableBean*/ {

    /**
     * 线程池
     */
    private ExecutorService executorService;


    public AbstractExecutor(ThreadPoolExecutorFactory.Config config) {
        //初始化线程池
        executorService = ThreadPoolExecutorFactory.create(config);
    }


    public ExecutorService getExecutorService() {
        return executorService;
    }

    /**
     * 执行任务并获取结果
     *
     * @param callable
     * @param <T>
     * @return
     */
    public <T> Future<T> submit(Callable<T> callable) {
        return executorService.submit(callable);
    }

    /**
     * 执行任务
     *
     * @param runnable
     */
    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

 /*   @Override
    public void destroy() throws Exception {
        if (executorService != null) {
            log.info("shutting down {} ...", getName());
            executorService.shutdown();
            ThreadPoolExecutorFactory.getPoolMap().remove(getName());
            log.info("{} shutdown!", getName());
        }
    }*/


    /**
     * 获取线程池名称
     *
     * @return
     */
    public abstract String getName();

}
