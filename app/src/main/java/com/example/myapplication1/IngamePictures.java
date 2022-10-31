package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class IngamePictures extends AppCompatActivity implements View.OnClickListener {

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private ImageView img6;
    private ImageView img7;
    private ImageView img8;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingamepictures);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        img6 = findViewById(R.id.img6);
        img7 = findViewById(R.id.img7);
        img8 = findViewById(R.id.img8);

        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);
        img6.setOnClickListener(this);
        img7.setOnClickListener(this);
        img8.setOnClickListener(this);
    }

    //点击哪张图片就把哪张图片作为拼图背景
    @Override
    public void onClick(View view) {
        img = findViewById(view.getId());
        img.setDrawingCacheEnabled(Boolean.TRUE);
        Intent yxtpintent = new Intent();
        yxtpintent.setClass(this,GameActivity.class);
        Bundle b = new Bundle();
        b.putInt("flag", 1);
        yxtpintent.putExtras(b);
        GamePintuLayout.mBitmap = img.getDrawingCache();
//        yxtpintent.putExtra("yxtp", img.getDrawingCache());
        startActivity(yxtpintent);
    }


}