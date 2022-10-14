package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class pintu extends AppCompatActivity {

    private ImageView img;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pintu);
        img = findViewById(R.id.img);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle b = intent.getExtras();
            switch (b.getInt("flag")){
                case 1:
                    Bitmap bitmap =  intent.getParcelableExtra("yxtp");
//                    if(yxtpid == R.id.img1)
//                    {
//                        TextView title = findViewById(R.id.title);
//                        title.setText(yxtpid+"");
//                    }


//                    bitmap = BitmapFactory.decodeResource(getResources(),R.id.img1);
                    img.setImageBitmap(bitmap);
//                    Drawable drawable = getDrawable(R.id.img1);
//                    img.setImageDrawable(drawable);
                    break;
                case 2:
                    Uri fileuri = b.getParcelable("fileUri");
                    bitmap = ImageSizeCompress(fileuri);
                    img.setImageBitmap(bitmap);
                    break;
                case 3:
                    Uri picuri = b.getParcelable("picUri");
                    bitmap = ImageSizeCompress(picuri);
                    img.setImageBitmap(bitmap);
                    break;
            }

        }
    }

    private Bitmap ImageSizeCompress(Uri uri) {
        InputStream Stream = null;
        InputStream inputStream = null;
        try {
            //根据uri获取图片的流
            inputStream = getContentResolver().openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options的in系列的设置了，injustdecodebouond只解析图片的大小，而不加载到内存中去
            options.inJustDecodeBounds = true;
            //1.如果通过options.outHeight获取图片的宽高，就必须通过decodestream解析同options赋值
            //否则options.outheight获取不到宽高
            BitmapFactory.decodeStream(inputStream, null, options);
            //2.通过 btm.getHeight()获取图片的宽高就不需要1的解析，我这里采取第一张方式
//            Bitmap btm = BitmapFactory.decodeStream(inputStream);
            //以屏幕的宽高进行压缩
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int heightPixels = displayMetrics.heightPixels;
            int widthPixels = displayMetrics.widthPixels;
            //获取图片的宽高
            int outHeight = options.outHeight;
            int outWidth = options.outWidth;
            //heightPixels就是要压缩后的图片高度，宽度也一样
            int a = (int) Math.ceil((outHeight / (float) heightPixels));
            int b = (int) Math.ceil(outWidth / (float) widthPixels);
            //比例计算,一般是图片比较大的情况下进行压缩
            int max = Math.max(a, b);
            if (max > 1) {
                options.inSampleSize = max;
            }
            //解析到内存中去
            options.inJustDecodeBounds = false;
//            根据uri重新获取流，inputstream在解析中发生改变了
            Stream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(Stream, null, options);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (Stream != null) {
                    Stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

}