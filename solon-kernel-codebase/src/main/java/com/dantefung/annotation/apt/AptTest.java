package com.dantefung.annotation.apt;

import com.dantefung.annotation.apt.shape.IShape;
import com.dantefung.annotation.apt.shape.IShapeFactory;

/**
 * @Description: TODO
 * @Author: DANTE FUNG
 * @Date:2019/9/29 18:29
 */
public class AptTest {

    public static void main(String[] args) {
        System.out.printf("Hello, Apt World!");
        // IShapeFactory 这个类是靠依赖了(详见jsr269-sample工程如何实现自定义注解处理器)jsr269-sample.jar
        // ,在编译时生成。所以需要先compile一下.
        // 类似querydsl或lombok一样.
        IShapeFactory iShapeFactory = new IShapeFactory();
        IShape circle = iShapeFactory.create("Circle");
        System.out.println(circle);
    }
}
