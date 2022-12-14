package com.example.myapplication1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication1.functional.Permission;
import com.example.myapplication1.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt1;
    private Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = findViewById(R.id.ks);    //ctrl+alt+f，提取变量为全局变量
        bt2 = findViewById(R.id.exit);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Permission.checkPermission(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Permission.isPermissionGranted(this)) {
            Log.i("PERMISSION","请求权限成功");
        }
    }



    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permission.REQUEST_CODE) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission","授权失败！");
                    // 授权失败，退出应用
                    this.finish();
                    return;
                }
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ks:
                Intent intent = new Intent();
                intent.setClass(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.exit:
                finish();
        }

    }
}