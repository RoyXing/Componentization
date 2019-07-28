package com.arouter.api;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.LruCache;

import com.arouter.api.core.ParameterLoad;

/**
 * 参数Parameter加载管理类
 */
public class ParameterManager {

    private static ParameterManager instance;

    //Lru缓存，key类名 value 参数parameter加载接口
    private LruCache<String, ParameterLoad> cache;

    //APT生成的获取参数类文件，后缀名
    private static final String FILE_SUFFIX_NAME = "$$Parameter";

    public static ParameterManager getInstance() {
        if (instance == null) {
            synchronized (ParameterManager.class) {
                if (instance == null) {
                    instance = new ParameterManager();
                }
            }
        }
        return instance;
    }

    private ParameterManager() {
        //初始化，并赋值缓存条目中的最大值
        cache = new LruCache<>(163);
    }

    public void loadParameter(@NonNull Activity activity) {
        String className = activity.getClass().getName();
        ParameterLoad iParameter = cache.get(className);
        //找不到 加入缓存
        try {
            if (iParameter == null) {
                Class<?> clazz = Class.forName(className + FILE_SUFFIX_NAME);
                iParameter = (ParameterLoad) clazz.newInstance();
                cache.put(className, iParameter);
            }
            iParameter.loadParameter(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
