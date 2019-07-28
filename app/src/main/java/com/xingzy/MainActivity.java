package com.xingzy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.annotation.ARouter;
import com.annotation.Parameter;
import com.arouter.api.ParameterManager;
import com.arouter.api.RouterManager;
import com.common.OrderDrawable;

@ARouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Parameter(name = "/order/getDrawable")
    OrderDrawable orderDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParameterManager.getInstance().loadParameter(this);

        findViewById(R.id.imageView).setBackgroundResource(orderDrawable.getDrawable());
    }

    public void order(View view) {
        RouterManager.getInstance()
                .build("/order/OrderActivity")
                .withString("name", "xingzy")
                .navigation(this, 163);
    }

    public void personal(View view) {
        RouterManager.getInstance()
                .build("/personal/PersonalActivity")
                .navigation(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 163 && resultCode == 999) {
            Log.e("roy", data.getStringExtra("call"));
        }
    }
}
