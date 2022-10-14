package com.example.myapplication1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class zidingyitupian extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_CAMERA = 1;
    public static final int TAKE_PHOTO = 1;
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
        mResultLauncher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {

                    Intent pzintent = new Intent();
                    pzintent.setClass(zidingyitupian.this, pintu.class);
                    Bundle b = new Bundle();
                    b.putParcelable("fileUri", fileUri);
                    b.putInt("flag", 2);
                    pzintent.putExtras(b);
                    startActivity(pzintent);
                }
            }
        });


        //跳转到系统相册，选择图片，并返回
        mResultLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent picintent = result.getData();
                    Uri picUri = picintent.getData();   //获得选中图片的路径对象
//                     Bitmap bitmap = ImageSizeCompress(picUri);
//                     picture.setImageBitmap(bitmap);
                    Intent tkintent = new Intent();
                    tkintent.setClass(zidingyitupian.this, pintu.class);
                    Bundle b = new Bundle();
                    b.putParcelable("picUri", picUri);
                    b.putInt("flag", 3);
                    tkintent.putExtras(b);
                    startActivity(tkintent);

//                     if(picUri != null)
//                     {
//                         Log.d("ning","picUri:"+picUri.toString());
//                     }
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pz:
//                File outputFile = new File(getExternalCacheDir(),"output_image.jpg");
//                //getExternalCacheDir()方法得到当前应用缓存数据位置
//                try {
//                    if (outputFile.exists()) {
//                        outputFile.delete();
//                    }
//                    outputFile.createNewFile();
//                    imageUri = FileProvider.getUriForFile(this,
//                            "com.example.cameraalbumtest.fileprovider",outputFile);
//                    //启动相机程序
//                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                    if (intent.resolveActivity(getPackageManager()) != null){
//                        startActivityForResult(intent, TAKE_PHOTO);//第二个参数是请求码
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


//            try {
//                // 打开相机Intent
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                // 给拍摄的照片指定存储位置
//                String f = System.currentTimeMillis() + ".jpg"; // 指定名字
//                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), f); // 指定文件
//                fileUri = FileProvider.getUriForFile(zidingyitupian.this, getPackageName() + ".fileprovider", file); // 路径转换
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); //指定图片存放位置，指定后，在onActivityResult里得到的Data将为null
//                startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String f = System.currentTimeMillis() + ".jpg"; // 指定名字
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), f); // 指定文件
                fileUri = FileProvider.getUriForFile(zidingyitupian.this, getPackageName() + ".fileprovider", file); // 路径转换
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

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
//            try {
//                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
//                        .openInputStream(imageUri));
//                picture.setImageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri)));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
