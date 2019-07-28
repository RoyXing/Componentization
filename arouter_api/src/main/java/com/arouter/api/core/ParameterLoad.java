package com.arouter.api.core;

/**
 * @author roy.xing
 * @date 2019-07-26
 */
public interface ParameterLoad {

    /**
     * 目标对象.属性名 = getIntent().属性类型("注解值or属性名");完成赋值
     *
     * @param target 目标对象，如：MainActivity（中的某些属性）
     */
    void loadParameter(Object target);
}
