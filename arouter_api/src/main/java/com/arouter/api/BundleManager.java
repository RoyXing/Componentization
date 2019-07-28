package com.arouter.api;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arouter.api.core.Call;

/**
 * 参数管理
 */
public class BundleManager {

    private Bundle bundle = new Bundle();
    //是否需要回调的setResult
    private boolean isResult = false;

    private Call call;

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public boolean isResult() {
        return isResult;
    }

    public BundleManager withString(@NonNull String key, @Nullable String value) {
        bundle.putString(key, value);
        return this;
    }

    public BundleManager withResultString(@NonNull String key, @Nullable String value) {
        bundle.putString(key, value);
        isResult = true;
        return this;
    }

    public BundleManager withBoolean(@NonNull String key, boolean value) {
        bundle.putBoolean(key, value);
        return this;
    }

    public BundleManager withInt(@NonNull String key, int value) {
        bundle.putInt(key, value);
        return this;
    }

    public BundleManager withBundle(@NonNull Bundle bundle) {
        this.bundle = bundle;
        return this;
    }

    //直接跳转
    public Object navigation(Context context) {
        return navigation(context, -1);
    }

    /**
     * @param context
     * @param code    可能是requestCode, 也可能是resultCode
     * @return
     */
    public Object navigation(Context context, int code) {
        return RouterManager.getInstance().navigation(context, this, code);
    }
}
