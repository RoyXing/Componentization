package com.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.annotation.ARouter;
import com.annotation.Parameter;
import com.arouter.api.ParameterManager;
import com.arouter.api.RouterManager;

/**
 * @author roy.xing
 * @date 2019-07-17
 */
@ARouter(path = "/order/OrderActivity")
public class OrderActivity extends AppCompatActivity {

    @Parameter
    String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);
        //懒加载的方式，跳转哪里加载哪个类
        ParameterManager.getInstance().loadParameter(this);
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    public void call(View view) {
        RouterManager.getInstance().build("/app/MainActivity")
                .withResultString("call", "roy")
                .navigation(this, 999);
    }

    public void personal(View view) {
        RouterManager.getInstance()
                .build("/personal/PersonalActivity")
                .navigation(this);
    }
}
