package com.dantefung.java8.function;

import java.util.function.Supplier;

public class Lazy<T> {
    private final Supplier<T> supplier;
    private T value;
    private boolean isEvaluated = false;

    private Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    public T get() {
        if (!isEvaluated) {
            value = supplier.get();
            isEvaluated = true;
        }
        return value;
    }

	public static void main(String[] args) {
		Lazy<String> lazyValue = Lazy.of(() -> {
			System.out.println("计算数据");
			return "懒加载的数据";
		});

		System.out.println("调用get()之前");
		System.out.println(lazyValue.get()); // 第一次调用get()会计算数据
		System.out.println("调用get()之后");
		System.out.println(lazyValue.get()); // 后续的调用get()会直接返回已经计算好的数据
	}
}
