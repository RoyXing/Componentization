package com.common;

/**
 * @author roy.xing
 * @date 2019-07-17
 */
public class PathBean {

    private String path;
    private Class clazz;

    public PathBean() {
    }

    public PathBean(String path, Class clazz) {
        this.path = path;
        this.clazz = clazz;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
