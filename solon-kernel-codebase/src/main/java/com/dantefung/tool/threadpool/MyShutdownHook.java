package com.dantefung.tool.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@Slf4j
public class MyShutdownHook {
    private static List<ShutdownConsumer> shutdownConsumers = new ArrayList<>(16);
    private static List<Thread> threads = new ArrayList<>(16);

    private volatile static AtomicBoolean shuttingDown = new AtomicBoolean(false);

    public static void init() throws Exception {
        String className = "java.lang.ApplicationShutdownHooks";
        Class<?> clazz = Class.forName(className);
        Field field = clazz.getDeclaredField("hooks");
        field.setAccessible(true);

        Thread shutdownThread = new Thread(() -> shutdownInOrder());
        shutdownThread.setName("my-shutdownThread");
        IdentityHashMap<Thread, Thread> replaceIdentityHashMap = new IdentityHashMap<>();
        replaceIdentityHashMap.put(shutdownThread, shutdownThread);

        synchronized (clazz) {
            IdentityHashMap<Thread, Thread> map = (IdentityHashMap<Thread, Thread>) field.get(clazz);
            for (Thread thread : map.keySet()) {
                log.info("found shutdownHook: " + thread.getName());
                if (thread.getName().startsWith("my-")) {
                    replaceIdentityHashMap.put(thread, thread);
                } else {
                    threads.add(thread);
                }
            }

            field.set(clazz, replaceIdentityHashMap);
        }
    }

    public static void register(String name, Integer order, Consumer consumer) {
        if(order == null || consumer == null) {
            return;
        }
        register(new ShutdownConsumer(name, order, consumer));
    }

    public static void register(ShutdownConsumer shutDownThread) {
        shutdownConsumers.add(shutDownThread);
    }

    /**
     * 关闭顺序:
     * 1. tomcat/undertow请求连接池
     * 2. rabbitmq/rocketmq消费者
     * 3. dubbo服务提供者
     * 4. 自定义线程池
     */
    private static void shutdownInOrder() {
        if (!shuttingDown.compareAndSet(false, true)) {
            log.info("平滑关闭:shut down in progress");
            return;
        }

        Collections.sort(shutdownConsumers);
        for (ShutdownConsumer shutDownConsumer : shutdownConsumers) {
            log.info("ShutdownConsumer:{} 正在关闭", shutDownConsumer.getName());
            shutDownConsumer.shutDown();
            log.info("ShutdownConsumer:{} 已关闭", shutDownConsumer.getName());
        }

        // 执行其他钩子线程
        for (Thread thread : threads) {
            // 同步方法执行
            thread.run();
        }

    }

}