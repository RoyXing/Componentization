package com.arouter.api;

import java.util.Map;

/**
 * @author roy.xing
 * @date 2019-07-18
 */
public interface ARouterLoadGroup {

    /**
     * 加载路由组Group数据
     * 比如："app", ARouter$$Path$$app.class（实现了ARouterLoadPath接口）
     *
     * @return key:"app", value:"app"分组对应的路由详细对象类
     */
    Map<String, Class<? extends ARouterLoadPath>> loadGroup();
}
