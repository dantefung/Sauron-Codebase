package com.dantefung.reflecttest.template;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 定义模板关键字名称
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
public @interface TemplateKeyword {


    /**
     * 关键字名称
     *
     * @return
     */
    String value() default "";
}
