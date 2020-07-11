package com.dantefung.concurrent.c010;

import java.util.concurrent.TimeUnit;

public class T {

	synchronized void m() {
		System.out.println("m start!");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("m end !");
	}

	public static void main(String[] args) {
		new TT().m();
	}

}

 class TT extends T {

	@Override
	synchronized void m() {
		System.out.println("child m start");
		super.m();
		System.out.println("child m end");
	}
}
