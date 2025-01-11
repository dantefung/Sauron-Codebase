package com.dantefung.jmh;

import com.dantefung.tool.JacksonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 30, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class JacksonStringToObjectBenchmark {

    private static final TypeReference<Map<String, Object>> CLASS_TYPE = new TypeReference<Map<String, Object>>(){};

    private static MyTestClass INSTANCE; // 假设MyTestClass是你要转换的对象类型
    static {
        INSTANCE = MyTestClass.createTestData();
    }


    @Benchmark
    public Map<String, Object> testStringToObject() {
        try {
            return JacksonUtils.stringToObject(JacksonUtils.objectToString(INSTANCE), CLASS_TYPE);
        } catch (Exception e) {
            // 在实际基准测试中应避免异常处理影响性能测试准确性，这里仅为了代码完整性
            throw new RuntimeException("Error during deserialization", e);
        }
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(JacksonStringToObjectBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }


}