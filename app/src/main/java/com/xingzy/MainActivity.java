package com.xingzy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.annotation.ARouter;
import com.annotation.model.RouterBean;
import com.arouter.api.ARouterLoadPath;
import com.order.OrderActivity;
import com.personal.PersonalActivity;
import com.xingzy.debug.ARouter$$Group$$order;
import com.xingzy.debug.ARouter$$Path$$order;

import java.util.Map;

@ARouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void order(View view) {
        ARouter$$Group$$order loadGroup = new ARouter$$Group$$order();
        Map<String, Class<? extends ARouterLoadPath>> map = loadGroup.loadGroup();
        Class<? extends ARouterLoadPath> aRouterLoadPath = map.get("order");
        try {
            ARouter$$Path$$order order = (ARouter$$Path$$order) aRouterLoadPath.newInstance();
            Map<String, RouterBean> loadPath = order.loadPath();
            RouterBean bean = loadPath.get("order/OrderActivity");

            Intent intent = new Intent();
            intent.setClass(this, bean.getClazz());
            intent.putExtra("name", "xingzy");
            startActivity(intent);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void personal(View view) {
        startActivity(new Intent(this, PersonalActivity.class));
    }
}
