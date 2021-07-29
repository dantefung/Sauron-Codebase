/*
 * Copyright (C), 2015-2020
 * FileName: FunctionUtil
 * Author:   DANTE FUNG
 * Date:     2021/3/30 16:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/30 16:50   V1.0.0
 */
package com.dantefung.tool;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * @Title: FunctionUtil
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/30 16/50
 * @since JDK1.8
 */
@UtilityClass
public class FunctionUtil {

	public static <T,R> R sleepFunction(T t, Function<T, R> callback) {
		R r = null;
		try {
			r =callback.apply(t);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return r;
	}

	public static <T, U, R> R sleepFunction(T t,U u, BiFunction<T, U, R> callback) {
		R r = null;
		try {
			r =callback.apply(t, u);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return r;
	}

	public static <T, U> void sleepFunction(T t, U u, BiConsumer<T, U> callback) {
		try {
			callback.accept(t, u);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static <T, R> List<R> multiGetResult(List<Function<List<T>, R>> functions, List<T> list) {
		return functions.stream().map(f -> f.apply(list)).collect(Collectors.toList());
	}

	public static <K, R> List<R> mergeList(List<R> srcList, List<R> destList,
			Function<R, K> keyFunc,
			BinaryOperator<R> mergeFunc) {
		return mergeList(srcList, destList, keyFunc, keyFunc, mergeFunc);
	}

	public static <T, S, K, R> List<R> mergeList(List<S> srcList, List<T> destList,
			Function<S, K> skeyFunc, Function<T, K> dkeyFunc,
			BiFunction<S, T, R> mergeFunc) {
		return join(destList, mapKey(srcList, skeyFunc)).apply(dkeyFunc, (BiFunction) mergeFunc);

	}

	public static <T, K> Map<K, T> mapKey(List<T> list, Function<T, K> keyFunc) {
		return list.stream().collect(Collectors.toMap(keyFunc, t -> t, (k1, k2) -> k1));
	}

	public static <T, S, K, R> BiFunction<Function<T, K>, BiFunction<S, T, R>, List<R>> join(List<T> destList, Map<K, S> srcMap) {
		return (dkeyFunc, mergeFunc) -> destList.stream().map(
				dest -> {
					K key = dkeyFunc.apply(dest);
					S src = srcMap.get(key);
					return mergeFunc.apply(src, dest);
				}).collect(Collectors.toList());
	}

	/**
	 * 对给定的值 x,y 应用指定的二元操作函数
	 */
	public static <T, S, R> Function<BiFunction<T, S, R>, R> op(T x, S y) {
		return opFunc -> opFunc.apply(x, y);
	}

	/**
	 * 将两个函数使用组合成一个函数，这个函数接受一个二元操作函数
	 */
	public static <T, S, Q, R> Function<BiFunction<S, Q, R>, R> op(Function<T, S> funcx, Function<T, Q> funcy, T x) {
		return opFunc -> opFunc.apply(funcx.apply(x), funcy.apply(x));
	}

	public static <T, S, Q, R> Function<BiFunction<S, Q, R>, Function<T, R>> op(Function<T, S> funcx, Function<T, Q> funcy) {
		return opFunc -> aT -> opFunc.apply(funcx.apply(aT), funcy.apply(aT));
	}

	/**
	 * 将两个函数组合成一个叠加函数, compose(f,g) = f(g)
	 */
	public static <T> Function<T, T> compose(Function<T, T> funcx, Function<T, T> funcy) {
		return x -> funcx.apply(funcy.apply(x));
	}

	/**
	 * 将若干个函数组合成一个叠加函数, compose(f1,f2,...fn) = f1(f2(...(fn)))
	 */
	public static <T> Function<T, T> compose(Function<T, T>... extraFuncs) {
		if (extraFuncs == null || extraFuncs.length == 0) {
			return x -> x;
		}
		return x -> Arrays.stream(extraFuncs).reduce(y -> y, FunctionUtil::compose).apply(x);
	}

	public static void main(String[] args) {
		System.out.println(multiGetResult(
				Arrays.asList(
						list -> list.stream().collect(Collectors.summarizingInt(x -> x)),
						list -> list.stream().filter(x -> x < 50).sorted().collect(Collectors.toList()),
						list -> list.stream().collect(Collectors.groupingBy(x -> (x % 2 == 0 ? "even" : "odd"))),
						list -> list.stream().sorted().collect(Collectors.toList()),
						list -> list.stream().sorted().map(Math::sqrt).collect(Collectors.toMap(x -> x, y -> Math.pow(2, y)))),
				Arrays.asList(64, 49, 25, 16, 9, 4, 1, 81, 36)));

		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
		Supplier<Map<Integer, Integer>> mapSupplier = () -> list.stream().collect(Collectors.toMap(x -> x, y -> y * y));

		Map<Integer, Integer> mapValueAdd = list.stream().collect(Collectors.toMap(x -> x, y -> y, (v1, v2) -> v1 + v2, mapSupplier));
		System.out.println(mapValueAdd);

		List<Integer> nums = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(1, 4, 9), Arrays.asList(1, 8, 27))
				.stream().flatMap(x -> x.stream()).collect(Collectors.toList());
		System.out.println(nums);

		List<Integer> squares = Arrays.asList(1, 2, 3, 4, 5).stream().map(x -> x * x).collect(Collectors.toList());
		System.out.println(squares);

		List<Integer> fibo = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream().collect(new FiboCollector());
		System.out.println(fibo);

		System.out.println(op(new Integer(3), Integer.valueOf(3)).apply((x, y) -> x.equals(y.toString())));

		System.out.println(op(x -> x.length(), y -> y + ",world", "hello").apply((x, y) -> x + " " + y));

		System.out.println(op(x -> x, y -> y + ",world").apply((x, y) -> x + " " + y).apply("hello"));

		System.out.println(op(x -> x.toString().length(), y -> y + ",world").apply((x, y) -> x + " " + y).apply("hello"));

		System.out.println(mergeList(Arrays.asList(1, 2), Arrays.asList("an", "a"),
				s -> s, t -> t.toString().length(), (s, t) -> s + t));

	/*	List<Student> studentList = Arrays.asList(new Student("222", "qin", "reading,writing"),
				new Student("111", "ni", "flowers"));

		sort(studentList, (s1, s2) -> s1.getId().compareTo(s2.getId()));
		System.out.println(studentList);

		sort(studentList, (s1, s2) -> s1.getName().compareTo(s2.getName()));
		System.out.println(studentList);*/

	}

	public static <T> void sort(List<T> unsorted, BiFunction<T, T, Integer> cmp) {
		Collections.sort(unsorted, (o1, o2) -> cmp.apply(o1, o2));
	}
}
