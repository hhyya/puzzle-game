package com.example.myapplication1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class pintu extends AppCompatActivity {

    private ImageView img;
    private Bitmap bitmap;
    private ActivityResultLauncher<Intent> mResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pintu);
        img = findViewById(R.id.img);
        Intent intent = getIntent();

        mResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == RESULT_OK) {
//                            Intent picintent = result.getData();
//                            Bundle bundle = picintent.getExtras();
//                            if (bundle != null) {
//                                //在这里获得了剪裁后的Bitmap对象，可以用于上传
//                                Bitmap image = bundle.getParcelable("data");
//                                //设置到ImageView上
////                                ivPicture.setImageBitmap(image);
//                            }
//
//                        }
                    }
                });


        if (intent != null) {
            Bundle b = intent.getExtras();
            switch (b.getInt("flag")){
                case 1:
                    bitmap =  intent.getParcelableExtra("yxtp");
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
//                    String uriPath = fileuri.getPath();
//                    Boolean isFilePathWithExtension = ReaderUtils.isFilePathWithExtension(uriPath);
//                    if (!isFilePathWithExtension) {
//                        String path = ReaderUtils.getRealPathFromUri(getApplicationContext(), fileuri);
//                        if (!TextUtils.isEmpty(path)) {
//                            uriPath = path;
//                        }
//                    }

                    Toast.makeText(pintu.this,fileuri.toString(),Toast.LENGTH_SHORT).show();
//                    pictureCropping(fileuri);
                    bitmap = ImageSizeCompress(fileuri);
//                    img.setImageBitmap(bitmap);
                    break;
                case 3:
                    Uri picuri = b.getParcelable("picUri");
                    Toast.makeText(pintu.this,picuri.toString(),Toast.LENGTH_SHORT).show();
//                    pictureCropping(picuri);
                    bitmap = ImageSizeCompress(picuri);
//                    img.setImageBitmap(bitmap);
                    break;
            }
        }

//        List<ImagePiece> pieces = ImageSplitter.split(bitmap,3,3);
//        img.setImageBitmap(pieces.get(0).bitmap);
    }

    private void pictureCropping(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        // 返回裁剪后的数据
        intent.putExtra("return-data", false);
//        intent.putExtra("scale", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        mResultLauncher.launch(intent);
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