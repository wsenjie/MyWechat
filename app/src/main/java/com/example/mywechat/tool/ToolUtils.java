package com.example.mywechat.tool;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import java.text.SimpleDateFormat;

/**
 * 工具类
 */
public class ToolUtils {

    public static boolean isEmpty(String... values){
        for (String value : values) {
            if(TextUtils.isEmpty(value)){
                return true;
            }
        }
        return false;
    }

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
    public  static String getTime(long time){
        return format.format(time);
    }
}
