package com.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.annotation.ARouter;
import com.common.RecordPathManager;

/**
 * @author roy.xing
 * @date 2019-07-17
 */
@ARouter(path = "order/OrderActivity")
public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);
        String name = getIntent().getStringExtra("name");
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    public void personal(View view) {
//        try {
//            Class<?> clazz = Class.forName("com.personal.PersonalActivity");
//            startActivity(new Intent(this, clazz));
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        Class<?> targetClass = RecordPathManager.getTargetClass("personal", "PersonalActivity");

        startActivity(new Intent(this, targetClass));



    }
}
