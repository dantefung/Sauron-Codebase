package com.dantefung.tool.threadpool;

public interface GracefulShutdown {
    /**
     * 需要平滑关闭的组件名称
     *
     * @return
     */
    String gracefulShutdownName();

    /**
     * 平滑关闭的顺序
     *
     * @return
     */
    int gracefulShutdownOrder();

    /**
     * 关闭组件的方法
     */
    void gracefulShutdown();

    /**
     * 注册到平滑关闭钩子
     */
    default void registerToShutdownHook() {
        MyShutdownHook.register(gracefulShutdownName(), gracefulShutdownOrder(), (x) -> this.gracefulShutdown());
    }
}
