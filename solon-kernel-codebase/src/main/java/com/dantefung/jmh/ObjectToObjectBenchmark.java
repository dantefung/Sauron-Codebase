package com.dantefung.jmh;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 解释：
 * 引入JMH相关的注解和Blackhole类。
 * 定义一个State类ObjectToObjectBenchmark，其中包含ObjectMapper、TypeReference、sourceObject等成员变量。setUp方法用于每次迭代前初始化sourceObject。
 * @Benchmark注解标记了要进行基准测试的方法benchmarkObjectToObject。该方法接收一个Blackhole参数，用于消耗基准测试结果，防止编译器进行不必要的优化。
 * @OutputTimeUnit注解指定了基准测试结果的时间单位为毫秒。
 * 实现您提供的objectToObject方法。
 *
 * 要运行这个微基准测试，请确保已经配置好JMH，并在项目根目录下执行以下命令：
 *
 * mvn clean compile exec:java -Dexec.mainClass=org.openjdk.jmh.Main -Dexec.classpathScope=test -Dexec.cleanupDaemonThreads=false -Dexec.args="your.package.ObjectToObjectBenchmark"
 *
 * 请将your.package替换为实际的包名。运行后，JMH将输出基准测试结果，展示objectToObject方法的性能指标。您可以根据需要调整基准测试的参数（如迭代次数、预热次数、线程数等）以获得更准确的测量结果。
 *
 *
 */
@State(Scope.Benchmark)
public class ObjectToObjectBenchmark {

    private static final Logger log = LoggerFactory.getLogger(ObjectToObjectBenchmark.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    private TypeReference<Map<String, Integer>> typeReference = new TypeReference<Map<String, Integer>>() {};

    private Object sourceObject;

    @Setup(Level.Iteration)
    public void setUp() {
        Map<String, Integer> sourceMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            sourceMap.put("key" + i, i);
        }
        sourceObject = sourceMap;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void benchmarkObjectToObject(Blackhole blackhole) {
        blackhole.consume(objectToObject(sourceObject, typeReference));
    }

    public static <T> T objectToObject(Object obj, TypeReference<T> typeReference) {
        try {
            return objectMapper.convertValue(obj, typeReference);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
