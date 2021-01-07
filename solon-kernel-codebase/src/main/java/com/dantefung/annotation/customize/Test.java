package com.dantefung.annotation.customize;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * @Description: TODO
 * @Author: DANTE FUNG
 * @Date:2019/9/29 15:52
 */
public class Test {

    public static void main(String[] args) throws Exception {

        // 新建Person
        Person person = new Person();
        // 获取Person的Class实例
        Class<Person> c = Person.class;
        // 获取 somebody() 方法的Method实例
        Method mSomebody = c.getMethod("somebody", new Class[]{String.class, int.class});
        // 执行该方法
        mSomebody.invoke(person, new Object[]{"lily", 18});
        iteratorAnnotations(mSomebody);


        // 获取 somebody() 方法的Method实例
        Method mEmpty = c.getMethod("empty", new Class[]{});
        // 执行该方法
        mEmpty.invoke(person, new Object[]{});
        iteratorAnnotations(mEmpty);

		Field[] fields = c.getDeclaredFields();
		if (!ArrayUtil.isEmpty(fields)) {
			Stream.of(fields).filter(field -> field.isAnnotationPresent(MyAnnotation.class)).forEach(field -> {
				MyAnnotation myAnnotation = field.getAnnotation(MyAnnotation.class);
				String[] value = myAnnotation.value();
				System.out.println("被@MyAnnotation修饰的字段: " + field.getName() + "注解值为:"+ ArrayUtil.toString(value));
			});
		}
	}

    public static void iteratorAnnotations(Method method) {

        // 判断 somebody() 方法是否包含MyAnnotation注解
        if(method.isAnnotationPresent(MyAnnotation.class)){
            // 获取该方法的MyAnnotation注解实例
            MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
            // 获取 myAnnotation的值，并打印出来
            String[] values = myAnnotation.value();
            for (String str:values)
                System.out.printf(str+", ");
            System.out.println();
        }

        // 获取方法上的所有注解，并打印出来
        Annotation[] annotations = method.getAnnotations();
        for(Annotation annotation : annotations){
            System.out.println(annotation);
        }
    }
}
