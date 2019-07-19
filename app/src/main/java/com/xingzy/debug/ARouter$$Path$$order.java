package com.xingzy.debug;

import com.annotation.model.RouterBean;
import com.arouter.api.ARouterLoadPath;
import com.order.OrderActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author roy.xing
 * @date 2019-07-18
 */
public class ARouter$$Path$$order implements ARouterLoadPath {

    @Override
    public Map<String, RouterBean> loadPath() {
        Map<String, RouterBean> pathMap = new HashMap<>();
        pathMap.put("order/OrderActivity",
                RouterBean.create(RouterBean.Type.ACTIVITY,
                        OrderActivity.class,
                        "/order/OrderActivity",
                        "order"));
        return pathMap;
    }
}
