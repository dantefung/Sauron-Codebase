package com.dantefung.debug;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.function.Supplier;

public abstract class DebugInfoCollector {

    private static final TransmittableThreadLocal<DebugInfo> threadLocalDebugInfo = new TransmittableThreadLocal<>();

    private static volatile AccessibleByTemplate accessorInstance;

    /* ---------------- 私有方法 -------------- */
    private static void startCollecting(Boolean debug) {
        threadLocalDebugInfo.set(new DebugInfo().setDebug(debug));
    }

    private static DebugInfo stopCollecting() {
        DebugInfo info = threadLocalDebugInfo.get();
        threadLocalDebugInfo.remove(); // 防止内存泄漏
        return info;
    }

    private static DebugInfo getCurrent() {
        return threadLocalDebugInfo.get();
    }

    /* ---------------- 公开方法 -------------- */

    public static void addMessage(String message) {
        DebugInfo info = getCurrent();
        if (info != null && info.getDebug()) {
            info.debugMessage(message);
        }
    }

    public static void addMessage(String message, Object... argArray) {
        DebugInfo info = getCurrent();
        if (info != null && info.getDebug()) {
            info.debugMessage(message, argArray);
        }
    }

    public static void addMessage(boolean condition, String message, Supplier<?>... paramSuppliers) {
        DebugInfo info = getCurrent();
        if (info != null && info.getDebug()) {
            info.debugMessage(condition, message, paramSuppliers);
        }
    }

    public static void addNsMessage(String message) {
        DebugInfo info = getCurrent();
        if (info != null && info.getDebug()) {
            info.debugMessage(message);
        }
    }

    public static void addNsMessage(String namespace, String message, Object... argArray) {
        DebugInfo info = getCurrent();
        if (info != null && info.getDebug()) {
            info.debugMessage(namespace, message, argArray);
        }
    }

    public static void addNsMessage(String namespace, boolean condition, String message, Supplier<?>... paramSuppliers) {
        DebugInfo info = getCurrent();
        if (info != null && info.getDebug()) {
            info.debugMessage(namespace, condition, message, paramSuppliers);
        }
    }

    public static void setVariable(String name, Object value) {
        DebugInfo info = getCurrent();
        if (info != null && info.getDebug()) {
            info.setVariable(name, value);
        }
    }

    public static void setNsVariable(String namespace, String name, Object value) {
        DebugInfo info = getCurrent();
        if (info != null && info.getDebug()) {
            info.setVariable(namespace, name, value);
        }
    }

    static class AccessibleByTemplate {

        private AccessibleByTemplate() {}

        void startCollecting(boolean debug) {
            DebugInfoCollector.startCollecting(debug);
        }

        DebugInfo stopCollecting() {
            return DebugInfoCollector.stopCollecting();
        }
    }

    public static AccessibleByTemplate getAccessor() {
        if (accessorInstance == null) {
            synchronized (DebugInfoCollector.class) {
                if (accessorInstance == null) {
                    accessorInstance = new AccessibleByTemplate();
                }
            }
        }
        return accessorInstance;
    }

}
