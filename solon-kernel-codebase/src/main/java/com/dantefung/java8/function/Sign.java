package com.dantefung.java8.function;

import lombok.*;

import java.util.function.Supplier;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Sign {
    private String alg;
    private String value;
    private String cert;

    public static void main(String[] args) {
        //创建Supplier容器，声明为TestSupplier类型，此时并不会调用对象的构造方法，即不会创建对象
        Supplier<Sign> sign = Sign::new;
        //调用get()方法，此时会调用对象的构造方法，即获得到真正对象
        System.out.println(sign.get());
    }
}
