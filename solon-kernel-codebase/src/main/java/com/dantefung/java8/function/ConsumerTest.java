package com.dantefung.java8.function;

import java.util.Objects;
import java.util.function.Consumer;

public class ConsumerTest {
	public static void main(String[] args) {
		Consumer<Integer> consumer = (x) -> {
			int num = x * 1;
			System.out.println(num);
		};
		Consumer<Integer> consumer1 = (x) -> {
			int num = x * 2;
			System.out.println(num);
		};
		consumer.andThen(consumer1).accept(1);
	}
}


/*@FunctionalInterface
public interface Consumer<T> {

	//接收一个参数
	void accept(T t);

	//调用者方法执行完后再执行after的方法
	default Consumer<T> andThen(Consumer<? super T> after) {
		Objects.requireNonNull(after);
		return (T t) -> { accept(t); after.accept(t); };
	}
}*/