package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChoosePicture extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosepicture);
        findViewById(R.id.yxtp).setOnClickListener(this);
        findViewById(R.id.zidingyi).setOnClickListener(this);
    }

    /**
     * 点击事件，选择游戏内图片或者自定义图片
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.yxtp:
                Intent intent1 = new Intent();
                intent1.setClass(this, IngamePictures.class);
                startActivity(intent1);

                break;
            case R.id.zidingyi:
                Intent intent2 = new Intent();
                intent2.setClass(this, CustomImages.class);
                startActivity(intent2);
                break;

        }

    }
}