package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author roy.xing
 * @date 2019-07-17
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ARouter {

    //详细路由路径(必填)如"/app/MainActivity"
    String path();

    //从path中截取出来
    String group() default "";

}
