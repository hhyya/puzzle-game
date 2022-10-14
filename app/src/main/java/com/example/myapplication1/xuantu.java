package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class xuantu extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuantu);
        findViewById(R.id.yxtp).setOnClickListener(this);
        findViewById(R.id.zidingyi).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.yxtp:
                Intent intent1 = new Intent();
                intent1.setClass(this,youxitupian.class);
                startActivity(intent1);

                break;
            case R.id.zidingyi:
                Intent intent2 = new Intent();
                intent2.setClass(this,zidingyitupian.class);
                startActivity(intent2);
                break;

        }

    }
}