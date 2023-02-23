package com.dantefung.tool.threadpool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShutdownConsumer implements Comparable<ShutdownConsumer> {
    private String name;
    private Integer order;
    private Consumer consumer;

    public void shutDown() {
        consumer.accept(null);
    }

    @Override
    public int compareTo(ShutdownConsumer o) {
        return this.order.compareTo(o.getOrder());
    }
}
