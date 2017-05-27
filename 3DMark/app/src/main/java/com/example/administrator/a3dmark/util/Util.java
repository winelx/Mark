package com.example.administrator.a3dmark.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static void putString(String key, String value, Context context) {
        if (key == null || value == null || context == null)
            return;
        SharedPreferences sp = getSP(context);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(String key, Context context) {
        if (key == null || context == null) {
            return "";
        }
        SharedPreferences sp = getSP(context);
        return sp.getString(key, "");
    }

    /**
     * 利用SharedPreferences存储boolwan的�??
     */
    public static void putBoolean(String key, boolean value, Context context) {
        if (key == null || context == null) {
            return;
        }
        SharedPreferences sp = getSP(context);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 利用SharedPreferences获取boolwan的�??
     *
     * @param key     存储的文件的key
     * @param context 上下�?
     */
    public static boolean getBoolean(String key, Context context) {
        if (key == null || context == null) {
            return false;
        }
        SharedPreferences sp = getSP(context);
        return sp.getBoolean(key, false);
    }

    public static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    public static boolean isPhone(String phone) {
        if (phone == null) {
            return false;
        }
        phone = phone.trim();
        if (phone.length() != 11 || !phone.startsWith("1")) {
            return false;
        }

        return isNumeric(phone);

    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String getPercent(long y, long z) {
        String baifenbi = "";// 接受百分比的�??
        double baiy = y * 1.0;
        double baiz = z * 1.0;
        double fen = baiy / baiz;
        // NumberFormat nf = NumberFormat.getPercentInstance(); 注释掉的也是�?种方�?
        // nf.setMinimumFractionDigits( 2 ); 保留到小数点点后几位
        DecimalFormat df1 = new DecimalFormat("##.00%"); // ##.00%
        // 百分比格式，后面不足2位的�?0补齐
        // baifenbi=nf.format(fen);
        baifenbi = df1.format(fen);
        System.out.println(baifenbi);
        return baifenbi;
    }

    public static void install(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + filePath),
                "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }



}
