package com.dantefung.debug;

// 定义结果容器类
public class ExecutionResult<R> {
    private final R result;
    private final DebugInfo debugInfo;

    public ExecutionResult(R result, DebugInfo debugInfo) {
        this.result = result;
        this.debugInfo = debugInfo;
    }

    public R getResult() {
        return result;
    }

    public DebugInfo getDebugInfo() {
        return debugInfo;
    }
}