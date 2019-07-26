package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author roy.xing
 * @date 2019-07-26
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {
    // 不填写name的注解值表示该属性名就是key，填写了就用注解值作为key
    // 从getIntent()方法中获取传递参数值
    String name() default "";
}
