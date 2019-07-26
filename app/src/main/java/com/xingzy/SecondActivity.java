package com.xingzy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.annotation.ARouter;

/**
 * @author roy.xing
 * @date 2019-07-26
 */
@ARouter(path = "/app/SecondActivity")
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
