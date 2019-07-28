package com.xingzy.debug;

import com.arouter.api.core.ARouterLoadGroup;
import com.arouter.api.core.ARouterLoadPath;

import java.util.HashMap;
import java.util.Map;

/**
 * @author roy.xing
 * @date 2019-07-18
 */
public class ARouter$$Group$$order implements ARouterLoadGroup {

    @Override
    public Map<String, Class<? extends ARouterLoadPath>> loadGroup() {
        Map<String, Class<? extends ARouterLoadPath>> groupMap = new HashMap<>();
        groupMap.put("order", ARouter$$Path$$order.class);
        return groupMap;
    }

}
