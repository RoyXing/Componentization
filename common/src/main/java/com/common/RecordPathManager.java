package com.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author roy.xing
 * @date 2019-07-17
 * 全局路径记录器(根据子模块分组)
 */
public class RecordPathManager {

    private static Map<String, List<PathBean>> groupMap = new HashMap<>();

    /**
     * 将路径信息加入全局Map
     *
     * @param groupName 组名，如："personal"
     * @param pathName  路劲名，如："Personal_MainActivity"
     * @param clazz     类对象，如：Personal_MainActivity.class
     */

    public static void joinGroup(String groupName, String pathName, Class<?> clazz) {
        List<PathBean> list = groupMap.get(groupName);
        if (list == null) {
            list = new ArrayList<>();
            list.add(new PathBean(pathName, clazz));
        } else {
            boolean contained = false;
            for (PathBean pathBean : list) {
                if (pathBean.getPath().equals(pathName)) {
                    contained = true;
                    break;
                }
            }
            if (contained) {
                list.add(new PathBean(pathName, clazz));
            }
        }
        groupMap.put(groupName, list);
    }

    public static Class<?> getTargetClass(String groupName, String pathName) {
        List<PathBean> list = groupMap.get(groupName);
        if (list == null) {
            return null;
        }

        for (PathBean pathBean : list) {
            if (pathName.equalsIgnoreCase(pathBean.getPath())) {
                return pathBean.getClazz();
            }
        }
        return null;
    }

    public static void recycleGroup() {
        groupMap.clear();
        groupMap = null;
        System.gc();
    }

}
