package com.example.myapplication1;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class zidingyitupian extends AppCompatActivity implements View.OnClickListener {

    private Button pz;
    private Button tk;
    private Uri imageUri;
    private File file;
    private Uri fileUri;
    private ImageView picture;
    private ActivityResultLauncher<Intent> mResultLauncher1;
    private ActivityResultLauncher<Intent> mResultLauncher2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zidingyitupian);
        pz = findViewById(R.id.pz);
        pz.setOnClickListener(this);
        tk = findViewById(R.id.tk);
        picture = findViewById(R.id.picture);
        tk.setOnClickListener(this);

        //拍照，把拍得照片作为拼图背景
        mResultLauncher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent pzintent = new Intent();
                    pzintent.setClass(zidingyitupian.this, GameActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("fileUri", fileUri);
                    b.putInt("flag", 2);
                    pzintent.putExtras(b);
                    startActivity(pzintent);
                }
            }
        });


        //跳转到系统相册，选择图片，并返回
        mResultLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent picintent = result.getData();
                    Uri picUri = picintent.getData();   //获得选中图片的路径对象
//                    picUri = actualPath(picUri);
//                    Toast.makeText(zidingyitupian.this,picUri.toString(),Toast.LENGTH_SHORT).show();
                    Intent tkintent = new Intent();
                    tkintent.setClass(zidingyitupian.this, GameActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("picUri", picUri);
                    b.putInt("flag", 3);
                    tkintent.putExtras(b);
                    startActivity(tkintent);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pz:

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定名字
                String f = System.currentTimeMillis() + ".jpg";
                // 指定文件
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), f);
                // 路径转换
                fileUri = FileProvider.getUriForFile(zidingyitupian.this, getPackageName() + ".fileprovider", file);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); //指定图片存放位置，指定后，在onActivityResult里得到的Data将为null
                mResultLauncher1.launch(cameraIntent);
                break;

            case R.id.tk:
//                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                startActivityForResult(intent, TAKE_PHOTO);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                mResultLauncher2.launch(intent);
                break;
        }
    }

    private Uri actualPath(Uri uri)
    {
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor actualimagecursor = getContentResolver().query(uri,proj,null,null,null);
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor.getString(actual_image_column_index);
            File file = new File(img_path);
//            return Uri.fromFile(file);
            return uri;
        }catch (Exception e){
            return null;
        }

    }

}
