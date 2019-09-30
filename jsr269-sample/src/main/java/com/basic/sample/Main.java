package com.basic.sample;

public class Main {

    public static void main(String[] args) throws Exception{
        System.out.println("success");
        test();
    }

    @Test(value = "method is test")
    public static void test()throws Exception{

    }
}