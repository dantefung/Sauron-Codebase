package com.dantefung.concurrent;

public class SafeCalc02 {

	static long value = 0L;

	synchronized long get() {
		return value;
	}

	synchronized static void addOne() {
		value += 1;
	}

}