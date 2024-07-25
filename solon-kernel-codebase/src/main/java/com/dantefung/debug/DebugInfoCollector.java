package com.dantefung.debug;

import com.alibaba.ttl.TransmittableThreadLocal;

public abstract class DebugInfoCollector {

    private static final TransmittableThreadLocal<DebugInfo> threadLocalDebugInfo = new TransmittableThreadLocal<>();
    private static final TransmittableThreadLocal<Boolean> debugable = new TransmittableThreadLocal<>();

    private static void startCollecting(Boolean debug) {
        threadLocalDebugInfo.set(new DebugInfo());
        debugable.set(debug);
    }

    private static boolean isDebug() {
        return debugable.get() != null && debugable.get();
    }

    private static DebugInfo stopCollecting() {
        DebugInfo info = threadLocalDebugInfo.get();
        threadLocalDebugInfo.remove(); // 防止内存泄漏
        debugable.remove();
        return info;
    }

    public static void addMessage(String message) {
        DebugInfo info = getCurrent();
        if (info != null && isDebug()) {
            info.addMessage(message);
        }
    }

    public static void setVariable(String name, Object value) {
        DebugInfo info = getCurrent();
        if (info != null && isDebug()) {
            info.setVariable(name, value);
        }
    }

    public static DebugInfo getCurrent() {
        return threadLocalDebugInfo.get();
    }

    static class AccessibleByTemplate {
        void startCollecting(boolean debug) {
            DebugInfoCollector.startCollecting(debug);
        }

        void stopCollecting() {
            DebugInfoCollector.stopCollecting();
        }
    }

    // 仅允许DebugInfoTemplate通过AccessibleByTemplate调用
    public static AccessibleByTemplate getAccessor() {
        return new AccessibleByTemplate();
    }

    // 其他辅助方法，如添加消息等
}
