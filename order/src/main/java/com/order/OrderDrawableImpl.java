package com.order;

import com.annotation.ARouter;
import com.common.OrderDrawable;

@ARouter(path = "/order/getDrawable")
public class OrderDrawableImpl implements OrderDrawable {
    @Override
    public int getDrawable() {
        return R.drawable.ic_headset_mic_black_24dp;
    }
}
