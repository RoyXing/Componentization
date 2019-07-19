package com.annotation.model;

import javax.lang.model.element.Element;

/**
 * @author roy.xing
 * @date 2019-07-18
 */
public class RouterBean {

    public enum Type {
        ACTIVITY
    }

    //枚举类型:Activity
    private Type type;

    //类节点
    private Element element;

    //注解使用的类对象
    private Class<?> clazz;

    //路由地址
    private String path;

    //路由组
    private String group;

    private RouterBean(Builder builder) {
        this.type = builder.type;
        this.element = builder.element;
        this.clazz = builder.clazz;
        this.path = builder.path;
        this.group = builder.group;
    }

    public static RouterBean create(Type type, Class<?> clazz, String path, String group) {
        return new RouterBean(type, clazz, path, group);
    }

    public RouterBean(Type type, Class<?> clazz, String path, String group) {
        this.type = type;
        this.clazz = clazz;
        this.path = path;
        this.group = group;
    }

    public static class Builder {

        private Type type;

        //类节点
        private Element element;

        //注解使用的类对象
        private Class<?> clazz;

        //路由地址
        private String path;

        //路由组
        private String group;

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setElement(Element element) {
            this.element = element;
            return this;
        }

        public Builder setClazz(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setGroup(String group) {
            this.group = group;
            return this;
        }

        public RouterBean build() {
            if (path == null || path.length() == 0) {
                throw new IllegalArgumentException("path必填项为空，如：/app/MainActivity");
            }
            return new RouterBean(this);
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getPath() {
        return path;
    }

    public String getGroup() {
        return group;
    }
}
