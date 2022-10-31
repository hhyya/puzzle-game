package com.example.myapplication1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication1.Util.ScreenUtils;
import com.example.myapplication1.Util.SpUtil;

import java.io.IOException;
import java.io.InputStream;

public class GameActivity extends AppCompatActivity {

    private ContentResolver contentResolver;
    //标示着activity的暂停
    private boolean status = true;
    //拼图的布局
    private GamePintuLayout mGamePintuLayout;
    private TextView mLevel;
    private TextView mTime;
    private TextView mStep;
    private Button mPicture;
    private Button btnstart, btnpause, btnAddLevel, btnReduceLevel;
    private AlertDialog pauseDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);
            SpUtil.clear(GameActivity.this);
            SpUtil.put(GameActivity.this, "save", true);
            contentResolver = this.getContentResolver();
            mTime = findViewById(R.id.id_time);
            if ((Integer) (SpUtil.get(GameActivity.this, "time", 0)) > 0) {
                int i = (Integer) SpUtil.get(GameActivity.this, "time", 0);
                mTime.setText("" + i);
            }
            mLevel = findViewById(R.id.id_level);
            if(GamePintuLayout.mColumn == 2)
                mLevel.setText("练手");
            else if(GamePintuLayout.mColumn == 3)
                mLevel.setText("普通");
            else if(GamePintuLayout.mColumn == 4)
                mLevel.setText("困难");
            else if(GamePintuLayout.mColumn == 5)
                mLevel.setText("炼狱");

            mStep = findViewById(R.id.id_step);
            mStep.setText("" + 0 );

            mPicture = findViewById(R.id.tu);
            mPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });

            Intent intent = getIntent();
            if (intent != null) {
                Bundle b = intent.getExtras();
                switch (b.getInt("flag")) {
                    case 1:

                        break;
                    case 2:
                        Uri fileuri = b.getParcelable("fileUri");
                        GamePintuLayout.mBitmap = ImageSizeCompress(fileuri);
                        break;
                    case 3:
                        Uri picuri = b.getParcelable("picUri");
                        GamePintuLayout.mBitmap = ImageSizeCompress(picuri);
                        break;
                }
            }

            btnstart = findViewById(R.id.btnstart);
            btnpause = findViewById(R.id.btnpause);
            btnAddLevel = findViewById(R.id.btnAddLevel);
            btnReduceLevel = findViewById(R.id.btnReduceLevel);
            btnpause.setEnabled(false);
            mGamePintuLayout = findViewById(R.id.id_game_pintu);
            mGamePintuLayout.setTimeEabled(false);
            mGamePintuLayout.setcanContinuePoint(false);
            //实现自定义view里的接口。
            mGamePintuLayout.setOnGamePintuListener(new GamePintuLayout.GamePintuListener() {
                @Override
                public void timeChanged(int currentTime) {
                    mTime.setText("" + currentTime);
                }

                @Override
                public void stepChanged(int currentStep)  { mStep.setText("" + currentStep);}

                @Override
                public void gamesuccess() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    builder.setTitle(getResources().getString(R.string.remind)).setMessage(getResources().getString(R.string.success));
                    builder.setPositiveButton("退出游戏", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.setNegativeButton(getResources().getString(R.string.restart), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    final AlertDialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.show();
                    //重写“确定”，截取监听

                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            SpUtil.clear(GameActivity.this);
                            Intent intent = new Intent();
                            intent.setClass(GameActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SpUtil.clear(GameActivity.this);
                            //mGamePintuLayout.reStart();
                            GameActivity.this.recreate();
                            dialog.dismiss();
                        }
                    });
                }

            });
            btnstart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnpause.setEnabled(true);
                    mGamePintuLayout.pause();
                    mGamePintuLayout.reSume();
                    btnstart.setEnabled(false);
                    btnAddLevel.setEnabled(true);
                    btnReduceLevel.setEnabled(true);
                }
            });
            btnpause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GameActivity.this.onPause();

                }
            });
            btnAddLevel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTime.setText("0");
                    SpUtil.clear(GameActivity.this);
                    int i = mGamePintuLayout.nextLevel();
                    btnstart.setEnabled(true);
                    if(i == 3)
                        mLevel.setText("普通");
                    else if(i == 4)
                        mLevel.setText("困难");
                    else if(i == 5)
                        mLevel.setText("炼狱");

                }
            });
            btnReduceLevel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTime.setText("0");
                    SpUtil.clear(GameActivity.this);
                    int i = mGamePintuLayout.lastLevel();
                    btnstart.setEnabled(true);
                    if(i == 2)
                        mLevel.setText("练手");
                    else if(i == 3)
                        mLevel.setText("普通");
                    else if(i == 4)
                        mLevel.setText("困难");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 这个方法是游戏在暂停时创建一个弹出框。
     */
    public void play_onPause() {
        mGamePintuLayout.pause();
        status = false;
        AlertDialog.Builder builder=new AlertDialog.Builder(GameActivity.this);
        builder.setTitle(getResources().getString(R.string.remind))
                .setMessage(getResources().getString(R.string.paused))
                .setPositiveButton(getResources().getString(R.string.go_on), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        status = true;
                        GameActivity.this.onResume();
                        dialogInterface.dismiss();
                    }
                })

                .setCancelable(false);
        pauseDialog = builder.create();
        pauseDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mGamePintuLayout.pause();
        if (status) {
            play_onPause();
        }

    }

    @Override
    protected void onResume() {
        if (status) {
            super.onResume();
            mGamePintuLayout.reSume();
        } else {
            GameActivity.this.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pauseDialog!=null&&pauseDialog.isShowing()) {
            pauseDialog.dismiss();
        }
    }


    /**
     * 按返回键时触发事件
     */
    @Override
    public void onBackPressed() {
        status = false;
        mGamePintuLayout.pause();
        new AlertDialog.Builder(GameActivity.this)
                .setTitle(getResources().getString(R.string.remind))
                .setMessage(getResources().getString(R.string.leave_info))
                .setPositiveButton(getResources().getString(R.string.quit_directly), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SpUtil.clear(GameActivity.this);
                        GameActivity.this.finish();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.quit_save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        SpUtil.put(GameActivity.this, "save", true);
                        GameActivity.this.finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();

    }

    /**
     * 查看原图时跳出对话框
     */
    private void showDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.design_dialog,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        ImageView picture = view.findViewById(R.id.yuantu);
        Button btn_huantu = view.findViewById(R.id.huantu);
        picture.setImageBitmap(GamePintuLayout.mBitmap);
        btn_huantu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(GameActivity.this, ChoosePicture.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this)/4*3), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 将uri转换为Bitmap
     * @param uri
     * @return
     */
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
