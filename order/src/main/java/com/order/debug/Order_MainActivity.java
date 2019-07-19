package com.order.debug;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.order.OrderActivity;
import com.order.R;

public class Order_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_main);

    }

    public void onClick(View view) {
        startActivity(new Intent(this, OrderActivity.class));
    }
}
