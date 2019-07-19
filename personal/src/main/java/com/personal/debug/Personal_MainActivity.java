package com.personal.debug;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.personal.PersonalActivity;
import com.personal.R;

public class Personal_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity_main);
    }

    public void onclick(View view) {
        startActivity(new Intent(this, PersonalActivity.class));
    }
}
