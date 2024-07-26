package com.dantefung.debug;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class DebugInfoTemplate<R> {

    private static final AtomicReference<DebugInfoTemplate<?>> instance = new AtomicReference<>();

    private DebugInfoTemplate() {
    }

    public static <R> DebugInfoTemplate<R> getInstance() {
        DebugInfoTemplate<?> inst = instance.get();
        if (inst == null) {
            inst = new DebugInfoTemplate<>();
            if (!instance.compareAndSet(null, inst)) {
                // 如果设置失败，说明另一个线程已经设置了实例，使用已设置的实例
                inst = instance.get();
            }
        }
        @SuppressWarnings("unchecked")
        DebugInfoTemplate<R> result = (DebugInfoTemplate<R>) inst;
        return result;
    }

    public <R> ExecutionResult<R> execute(Supplier<R> operation) {
        return execute(true, operation);
    }

    public <R> ExecutionResult<R> execute(boolean debug, Supplier<R> operation) {
        DebugInfoCollector.AccessibleByTemplate accessor = DebugInfoCollector.getAccessor();
        accessor.startCollecting(debug);
        R result = null;
        DebugInfo current = null;
        try {
            if (Objects.nonNull(operation)) {
                result = operation.get();
            }
        } finally {
            current = accessor.stopCollecting();
        }

        return new ExecutionResult<R>(result, current);
    }
}