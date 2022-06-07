package com.dantefung.java8.money;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @Title: Demo1
 * @Description:
 * @author DANTE FUNG
 * @date 2022/06/07 09/29
 * @since JDK1.8
 */
public class Demo1 {

	public static void main(String[] args) {
		int money = 10;
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
		System.out.println(nf.format(money));
		NumberFormat nf2 = NumberFormat.getCurrencyInstance(Locale.CHINA);
		System.out.println(nf2.format(money));
	}
}
