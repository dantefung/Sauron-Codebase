package com.dantefung.tool;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * 一些常用的数学工具方法
 */
public class MathUtil {

    public static String transferDecimalToString(BigDecimal decimal, String defaultStr) {
        if (decimal == null) {
            return defaultStr;
        }
        return decimal.toPlainString();
    }

    public static String transferDecimalStripTrailingZerosToString(BigDecimal decimal, String defaultStr) {
        if (decimal == null) {
            return defaultStr;
        }
        return decimal.stripTrailingZeros().toPlainString();
    }

    public static BigDecimal transferDecimalStripTrailingZeros(BigDecimal decimal) {
        return transferDecimalStripTrailingZeros(decimal, BigDecimal.ZERO);
    }

    public static BigDecimal transferDecimalStripTrailingZeros(BigDecimal decimal, BigDecimal defaultBigDecimal) {
        if (decimal == null) {
            return defaultBigDecimal;
        }
        return decimal.scale() == 0 ? decimal : new BigDecimal(decimal.stripTrailingZeros().toPlainString());
    }

    /**
     * 组合算法
     *
     * @param list
     * @param k  指多少个元素为一组
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> combinations(List<T> list, int k) {
        if (k == 0 || list.isEmpty()) {
            //去除K大于list.size的情况。即取出长度不足K时清除此list
            return Collections.emptyList();
        }
        if (k == 1) {
            //递归调用最后分成的都是1个1个的，从这里面取出元素
            return list.stream().map(e -> Stream.of(e).collect(toList())).collect(toList());
        }
        Map<Boolean, List<T>> headAndTail = split(list, 1);
        List<T> head = headAndTail.get(true);
        List<T> tail = headAndTail.get(false);
        List<List<T>> c1 = combinations(tail, (k - 1)).stream().map(e -> {
            List<T> l = new ArrayList<>();
            l.addAll(head);
            l.addAll(e);
            return l;
        }).collect(Collectors.toList());
        List<List<T>> c2 = combinations(tail, k);
        c1.addAll(c2);
        return c1;
    }

    /**
     *根据n将集合分成两组
     **/
    public static <T> Map<Boolean, List<T>> split(List<T> list, int n) {
        return IntStream
                .range(0, list.size())
                .mapToObj(i -> new AbstractMap.SimpleEntry<>(i, list.get(i)))
                .collect(partitioningBy(entry -> entry.getKey() < n, mapping(AbstractMap.SimpleEntry::getValue, toList())));
    }

    public static BigDecimal div(BigDecimal d1, BigDecimal d2, int scale) {
        if(d2.compareTo(BigDecimal.ZERO) > 0) {
            return d1.divide(d2, scale, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

	public static void main(String[] args) {

		List<Integer> list = Lists.newArrayList(1, 2, 3);
		for (int i = 1; i <= list.size(); i++) {
			System.out.println(combinations(list, i));
		}
	}
}
