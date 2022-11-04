package com.example.myapplication1.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseOperation extends SQLiteOpenHelper {
    public static final String DATABASE = "puzzlegame.db";
    public static final int VERSION = 1;

    //建表语句定义成字符串常量
    public static final String CREATE_USER = "create table user ("
            + "account text primary key,"
            + "password text)";

    //创建DB对象时的构造函数
    public DataBaseOperation(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
