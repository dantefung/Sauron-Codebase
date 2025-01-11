package com.dantefung.jmh;

import com.dantefung.tool.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class JacksonSerializationBenchmark {

    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 订单session
     * Map<订单ID, OrderSimpleDTO>
     */
    private Map<String, OrderSimpleDTO> orderSimpleSession = new HashMap<>();

    @Setup(Level.Trial)
    public void setup() {
        // 初始化 OrderSimpleSession 对象
        orderSimpleSession = new HashMap<>();
        orderSimpleSession.put("71772217", new OrderSimpleDTO("71772217"));
        // ... 其他初始化逻辑
    }

    @Benchmark
    @Threads(100) // 设置并发线程数
    @Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    public String serializeObject() throws JsonProcessingException {
        String s = null;
        try {
            // t1 = null , t2 = null, t3 = null
//          orderSimpleSession = JacksonUtils.stringToObject(orderSessionJsonMap.get(OrderSessionThreadLocalHolder.ORDER_SIMPLE_SESSION), new TypeReference<Map<String, OrderSimpleDTO>>() {});
            String orderId = System.currentTimeMillis() + "";
            if (orderSimpleSession == null) {
                orderSimpleSession = new HashMap<>();// t1 赋值 或  t1 赋值  后  t2 赋值
            }
            if (orderSimpleSession.containsKey(orderId)) {
                //线程变量中有则直接返回
                return orderId;
            }
            // 写
            //region Description
            orderSimpleSession.put(orderId, new OrderSimpleDTO(orderId));// t2 在设置值
            s = JacksonUtils.objectToString(orderSimpleSession);// t1 在序列化
            //endregion
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return s;
    }

    /**
     * 解决方案声明为局部变量, 跟着线程压入栈, 存放在栈帧的结构里边
     *
     * https://pdai.tech/md/java/jvm/java-jvm-struct.html
     * 《揭秘java虚拟机》
     *
     * @return
     * @throws JsonProcessingException
     */
    public String serializeObject2() throws JsonProcessingException {
        String s = null;
        try {
            // t1 = null , t2 = null, t3 = null
            //          orderSimpleSession = JacksonUtils.stringToObject(orderSessionJsonMap.get(OrderSessionThreadLocalHolder.ORDER_SIMPLE_SESSION), new TypeReference<Map<String, OrderSimpleDTO>>() {});
            // 声明为局部变量, 会跟着线程压入栈
            Map<String, OrderSimpleDTO> orderSimpleSession = null;
            String orderId = System.currentTimeMillis() + "";
            if (orderSimpleSession == null) {
                orderSimpleSession = new HashMap<>();// t1 赋值 或  t1 赋值  后  t2 赋值
            }
            if (orderSimpleSession.containsKey(orderId)) {
                //线程变量中有则直接返回
                return orderId;
            }
            // 写
            //region Description
            orderSimpleSession.put(orderId, new OrderSimpleDTO(orderId));// t2 在设置值
            s = JacksonUtils.objectToString(orderSimpleSession);// t1 在序列化
            //endregion
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return s;
    }

    public static void main(String[] args) throws RunnerException {
//        Options opt = new OptionsBuilder()
//                .include(JacksonSerializationBenchmark.class.getSimpleName())
//                .forks(1)
//                .build();
//
//        new Runner(opt).run();
        JacksonSerializationBenchmark benchmark = new JacksonSerializationBenchmark();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                try {
                    //benchmark.serializeObject();
                    benchmark.serializeObject2();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

    @Data
    public static class OrderSimpleDTO implements Serializable {

        /**
         * 订单号
         */
        private String orderId;

        public OrderSimpleDTO(String orderId) {
            this.orderId = orderId;
        }
    }


    /*

    //region 这种写法会有并发修改问题
    orderSimpleSession.put(orderId, new OrderSimpleDTO(orderId));
    s = JacksonUtils.objectToString(orderSimpleSession);
    //endregion

    com.fasterxml.jackson.databind.JsonMappingException: (was java.util.ConcurrentModificationException) (through reference chain: java.util.HashMap["1724296402301"])
	at com.fasterxml.jackson.databind.JsonMappingException.wrapWithPath(JsonMappingException.java:390)
	at com.fasterxml.jackson.databind.JsonMappingException.wrapWithPath(JsonMappingException.java:349)
	at com.fasterxml.jackson.databind.ser.std.StdSerializer.wrapAndThrow(StdSerializer.java:316)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serializeFields(MapSerializer.java:811)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serializeWithoutTypeInfo(MapSerializer.java:764)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serialize(MapSerializer.java:720)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serialize(MapSerializer.java:35)
	at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
	at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
	at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4487)
	at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3742)
	at com.dantefung.jmh.JacksonUtils.objectToString(JacksonUtils.java:28)
	at com.dantefung.jmh.JacksonSerializationBenchmark.serializeObject(JacksonSerializationBenchmark.java:49)
	at com.dantefung.jmh.JacksonSerializationBenchmark.lambda$main$0(JacksonSerializationBenchmark.java:68)
	at java.lang.Thread.run(Thread.java:750)
Caused by: java.util.ConcurrentModificationException: null
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1469)
	at java.util.HashMap$EntryIterator.next(HashMap.java:1503)
	at java.util.HashMap$EntryIterator.next(HashMap.java:1501)
	at com.fasterxml.jackson.databind.ser.std.MapSerializer.serializeFields(MapSerializer.java:786)
	... 11 common frames omitted


	从提供的异常堆栈跟踪来看，这个异常发生在尝试序列化一个HashMap对象时。异常信息显示为 Exception: null，这通常意味着在处理异常时遇到了问题，而异常的具体类型丢失了。
根据堆栈跟踪，异常发生在HashMap$HashIterator.nextNode方法中，这表明在迭代HashMap的条目时出现了问题。具体来说，在MapSerializer.serializeFields方法中调用此迭代器来序列化HashMap的内容。


    从你提供的测试方法来看，确实有可能是因为并发修改导致的ConcurrentModificationException。这是因为HashMap在迭代过程中不允许结构上的修改，而Jackson在序列化HashMap时可能会触发迭代操作。
    以下是可能的问题点和解决方案：
    可能的问题点：
    当其他线程在迭代HashMap的同时对其进行修改（例如添加或删除元素）时，会抛出ConcurrentModificationException。
    如果在序列化过程中恰好遇到HashMap的扩容操作，也可能会引发异常，因为扩容会导致内部结构的变化。
    解决方案：
    使用线程安全的集合：可以将HashMap替换为ConcurrentHashMap，它支持并发操作并且在大多数情况下不会抛出ConcurrentModificationException。
    同步访问：如果必须使用HashMap，可以通过加锁的方式确保在序列化过程中没有其他线程修改HashMap。
    避免迭代期间的修改：确保在序列化之前不修改HashMap，或者在序列化之后再进行修改。

     */

}
