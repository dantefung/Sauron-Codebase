package com.dantefung.money;

import java.text.DecimalFormat;

/**
 * @author fenghaolin
 * @date 2025/01/11 17/43
 * @since JDK1.8
 */
public class DecimalFormatTest {

    public static void main(String[] args) {
        testDecimalFormatExamples();
    }

    private static void testDecimalFormatExamples() {


        double number1 = 1234.5678;
        DecimalFormat df1 = new DecimalFormat("#.00");
        System.out.println("DecimalFormat Example 1: " + df1.format(number1));  // 输出: 1234.57

        double number11 = 1234.0000;
        System.out.println("DecimalFormat Example 11: " + df1.format(number11));  // 输出: 1234.57

        double number2 = 1234567.89;
        DecimalFormat df2 = new DecimalFormat("#,###.00");
        System.out.println("DecimalFormat Example 2: " + df2.format(number2));  // 输出: 1,234,567.89

        double number3 = 0.75;
        DecimalFormat df3 = new DecimalFormat("##.00%");
        System.out.println("DecimalFormat Example 3: " + df3.format(number3));  // 输出: 75.00%

        double number4 = 1234.5678;
        DecimalFormat df4 = new DecimalFormat("000000.0000");
        System.out.println("DecimalFormat Example 4: " + df4.format(number4));  // 输出: 001234.5678
    }
}
