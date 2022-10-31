package com.example.myapplication1.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SpUtil {
    public static final String FILE_NAME = "share_data";
    public static final String FILE_NAME1 = "difficult_data";

    /**
     * 本地存储对象
     */
    public static void put(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            if (!(object instanceof Serializable)) {
                throw new IllegalArgumentException(object.getClass().getName() + " 必须实现Serializable接口!");
            }
            // 不是基本类型则是保存对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                String productBase64 = Base64.encodeToString(
                        baos.toByteArray(), Base64.DEFAULT);
                editor.putString(key, productBase64);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        editor.commit();
    }

    /**
     *从本地读取存储的对象
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else {
            return getObject(context, key);
        }
    }


    /**
     * 清除本地存储内容
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    private static Object getObject(Context context, String key) {
        Object object = null;
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String wordBase64 = sp.getString(key, "");
        byte[] base64 = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            ObjectInputStream bis = new ObjectInputStream(bais);
            object = bis.readObject();
            return object;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 往本地存入字符串数组
     */
    public static void putStringArray(Context context, String name, String[] strings) {
        SharedPreferences.Editor edit = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();

        edit.putInt("Count_" + name, strings.length);
        int count = 0;
        for (String x : strings) {
            edit.putString("StringValue_" + name + count++, x);
        }
        edit.commit();
    }

    /**
     * 从本地读取字符串数组
     */
    public static String[] getStringArray(Context context, String name) {

        SharedPreferences prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        int count = prefs.getInt("Count_" + name, 0);
        String[] ret = new String[count];
        for (int i = 0; i < count; i++) {
            ret[i] = prefs.getString("StringValue_" + name + i, "");
        }
        return ret;
    }

}
