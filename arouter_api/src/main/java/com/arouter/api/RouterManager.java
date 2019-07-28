package com.arouter.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.LruCache;

import com.annotation.model.RouterBean;
import com.arouter.api.core.ARouterLoadGroup;
import com.arouter.api.core.ARouterLoadPath;
import com.arouter.api.core.Call;

public class RouterManager {

    private static RouterManager instance;

    //路由组名
    private String group;
    //路由path路径
    private String path;
    //Lru缓存 key group组名， value路由path路径加载接口
    private LruCache<String, ARouterLoadGroup> groupLruCache;
    //Lru缓存 key 路径， value路由path路径加载接口
    private LruCache<String, ARouterLoadPath> pathLruCache;
    //APT生成类文件后缀名
    private static final String GROUP_FILE_PREFIX_NAME = "ARouter$$Group$$";

    public static RouterManager getInstance() {
        if (instance == null) {
            synchronized (RouterManager.class) {
                if (instance == null) {
                    instance = new RouterManager();
                }
            }
        }
        return instance;
    }

    private RouterManager() {
        groupLruCache = new LruCache<>(163);
        pathLruCache = new LruCache<>(163);
    }

    //传递路由地址
    public BundleManager build(String path) {
        if (TextUtils.isEmpty(path) || !path.startsWith("/")) {
            throw new IllegalArgumentException("未按照规范配置，如：/app/MainActivity");
        }
        group = subFromPath2Group(path);
        this.path = path;
        return new BundleManager();
    }

    private String subFromPath2Group(String path) {
        if (path.lastIndexOf("/") == 0) {
            throw new IllegalArgumentException("未按照规范配置，如：/app/MainActivity");
        }
        //   /app/MainActivity
        String finalGroup = path.substring(1, path.indexOf("/", 1));
        if (TextUtils.isEmpty(finalGroup)) {
            throw new IllegalArgumentException("未按照规范配置，如：/app/MainActivity");
        }
        return finalGroup;
    }

    /**
     * @param context       上下文
     * @param bundleManager 参数管理
     * @param code          可能是requestCode, 也可能是resultCode
     * @return 普通跳转可以忽略，跨模块CALL接口
     */
    public Object navigation(Context context, BundleManager bundleManager, int code) {
        //com.xingzy.modular.apt.ARouter$$Group$$app
        String groupClassName = "com.xingzy.modular.apt." + GROUP_FILE_PREFIX_NAME + group;
        try {
            ARouterLoadGroup aRouterLoadGroup = groupLruCache.get(group);
            if (aRouterLoadGroup == null) {
                Class<?> clazz = Class.forName(groupClassName);
                aRouterLoadGroup = (ARouterLoadGroup) clazz.newInstance();
                groupLruCache.put(group, aRouterLoadGroup);
            }

            if (aRouterLoadGroup.loadGroup().isEmpty()) {
                throw new RuntimeException("路由表加载失败~~");
            }
            //读取路由path懒加载的类文件缓存
            ARouterLoadPath aRouterLoadPath = pathLruCache.get(path);
            if (aRouterLoadPath == null) {
                Class<? extends ARouterLoadPath> clazz = aRouterLoadGroup.loadGroup().get(group);
                if (clazz != null) {
                    aRouterLoadPath = clazz.newInstance();
                    pathLruCache.put(path, aRouterLoadPath);
                }
            }

            RouterBean routerBean = aRouterLoadPath.loadPath().get(path);
            if (routerBean != null) {
                switch (routerBean.getType()) {
                    case ACTIVITY:
                        Intent intent = new Intent(context, routerBean.getClazz());
                        intent.putExtras(bundleManager.getBundle());

                        //resultCode
                        if (bundleManager.isResult()) {
                            ((Activity) context).setResult(code, intent);
                            ((Activity) context).finish();
                        }

                        //requestCode
                        if (code > 0) {
                            ((Activity) context).startActivityForResult(intent, code);
                        } else {
                            context.startActivity(intent);
                        }
                        break;
                    case CALL:
                        Class<?> clazz = routerBean.getClazz();
                        Call call = (Call) clazz.newInstance();
                        bundleManager.setCall(call);
                        return bundleManager.getCall();
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
