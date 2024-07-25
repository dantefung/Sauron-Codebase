package com.dantefung.debug;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class DebugInfoTemplate<R> {

    private static final AtomicReference<DebugInfoTemplate<?>> instance = new AtomicReference<>();
    private final Supplier<R> operation;
    private boolean debug;

    private DebugInfoTemplate(Supplier<R> operation) {
        this.operation = operation;
    }

    public static <R> DebugInfoTemplate<R> getInstance(Supplier<R> operation) {
        DebugInfoTemplate<?> inst = instance.get();
        if (inst == null) {
            inst = new DebugInfoTemplate<>(operation);
            if (!instance.compareAndSet(null, inst)) {
                // 如果设置失败，说明另一个线程已经设置了实例，使用已设置的实例
                inst = instance.get();
            }
        }
        @SuppressWarnings("unchecked")
        DebugInfoTemplate<R> result = (DebugInfoTemplate<R>) inst;
        return result;
    }

    public DebugInfoTemplate setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public ExecutionResult<R> execute() {
        DebugInfoCollector.AccessibleByTemplate accessor = DebugInfoCollector.getAccessor();
        accessor.startCollecting(debug);
        R result = null;
        DebugInfo current = null;
        try {
            result = operation.get();
            current = DebugInfoCollector.getCurrent();
        } finally {
            accessor.stopCollecting();
        }

        return new ExecutionResult<R>(result, current);
    }
}