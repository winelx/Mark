package com.example.administrator.a3dmark.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 工具类
 */
public class Utils {
    private Context context;
    private static long lastClickTime;// 上次点击时间

    /**
     * 是否为null或""
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())
                || "null".equalsIgnoreCase(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 默认适配器
     *
     * @param arrayId
     * @return
     */
    public static ArrayAdapter<CharSequence> createAdapter(Activity mActivity,
                                                           int arrayId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                mActivity, arrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }


    /**
     * 获取屏幕宽度
     *
     * @param ctx
     * @return
     */
    public static int getScreenWidth(Context ctx) {
        return ctx.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 对话框LayoutParams
     *
     * @param
     * @return
     */
    public static LayoutParams getLayoutParams(Context ctx) {
        return new LayoutParams((int) (getScreenWidth(ctx) * 0.9),
                LayoutParams.WRAP_CONTENT);
    }

    /**
     * 页面跳转
     *
     * @param context
     * @param cls
     */
    @SuppressWarnings("rawtypes")
    public static void sendIntent(Context context, Class cls) {
        sendIntent(context, cls, null);
    }

    /**
     * 页面跳转
     *
     * @param context
     * @param cls
     */
    @SuppressWarnings("rawtypes")
    public static void sendIntent(Context context, Class cls, Bundle extras) {
        Intent intent = new Intent(context, cls);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @SuppressWarnings("rawtypes")
    public static void sendIntentWithClearTop(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    /**
     * 替换fragment
     *
     * @param activity
     * @param fragmentId
     * @param fragment
     */
    public static void replaceFragment(FragmentActivity activity,
                                       int fragmentId, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(fragmentId, fragment);
        transaction.commit();
    }

    /**
     * 替换fragment
     *
     * @param oldFragment
     * @param newFragment
     * @param fragmentId
     */
    public static void replaceFragment(Fragment oldFragment,
                                       Fragment newFragment, int fragmentId) {
        FragmentManager manager = oldFragment.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(fragmentId, newFragment);
        transaction.commit();
    }

    /**
     * 获取客户端versionName
     *
     * @param ctx
     * @return
     */
    public static String getVersionName(Context ctx) {
        PackageManager packageManager = ctx.getPackageManager();
        PackageInfo packInfo = null;
        String versionName = "-1";
        try {
            packInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
            if (packInfo != null)
                versionName = packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            Log.d("NameNotFoundException", e.getMessage());
        }
        return versionName;
    }

    /**
     * 获取客户端versionCode
     *
     * @param ctx
     * @return
     */
    public static int getVersionCode(Context ctx) {
        PackageManager packageManager = ctx.getPackageManager();
        PackageInfo packInfo = null;
        int versionCode = -1;
        try {
            packInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
            if (packInfo != null)
                versionCode = packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            Log.d("NameNotFoundException", e.getMessage());
        }
        return versionCode;
    }

    /**
     * 手机是否联网 需要android.permission.ACCESS_NETWORK_STATE权限
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        }
        return false;
    }


    /**
     * 打开浏览器
     *
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /**
     * 获取时间
     *
     * @param time
     * @return
     */
    public static String getNormalTime(String time) {
        String result = "";
        if (isEmpty(time) || time.length() < 10) {
            return result;
        }
        result = time.substring(0, 10);
        return result;
    }

    /**
     * 字符串为空,返回""
     *
     * @param value
     * @return
     */
    public static String getValue(String value) {
        return isEmpty(value) ? "" : value;
    }

    /**
     * ScrollView与ListView兼容问题
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 隐藏键盘
     *
     * @param view
     */
    public static void hideKeyBoard(Context context, View view) {
        try {
            InputMethodManager im = (InputMethodManager) context
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            IBinder windowToken = view.getWindowToken();
            if (windowToken != null) {
                im.hideSoftInputFromWindow(windowToken, 0);
            }
        } catch (Exception e) {

        }
    }


    /**
     * 获取intent参数
     *
     * @param mActivity
     * @param savedInstanceState
     * @param paramName
     * @return
     */
    public static String getIntentStringParams(Activity mActivity,
                                               Bundle savedInstanceState, String paramName) {
        return savedInstanceState != null ? savedInstanceState
                .getString(paramName) : mActivity.getIntent().getStringExtra(
                paramName);
    }

    /**
     * 获取intent参数
     *
     * @param mActivity
     * @param savedInstanceState
     * @param paramName
     * @return
     */
    public static ArrayList<String> getIntentStringArrayList(
            Activity mActivity, Bundle savedInstanceState, String paramName) {
        return savedInstanceState != null ? savedInstanceState
                .getStringArrayList(paramName) : mActivity.getIntent()
                .getStringArrayListExtra(paramName);
    }


    /**
     * 输入框获取焦点
     *
     * @param et
     */
    public static void requestFocus(EditText et) {
        et.requestFocus();
        et.setSelection(et.getText().length());
    }


    /**
     * 格式化double
     *
     * @param money
     * @return
     */
    public static String formatDouble(String money) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(Double.parseDouble(money));
    }


    /**
     * 获取开始时间
     *
     * @param startTime
     * @return
     */
    public static String getStartTime(String startTime) {
        if (isEmpty(startTime)) {
            return "";
        }
        return startTime + " 00:00:00";
    }

    /**
     * 获取结束时间
     *
     * @param
     * @return
     */
    public static String getEndTime(String endTime) {
        if (isEmpty(endTime)) {
            return "";
        }
        return endTime + " 23:59:59";
    }


    /**
     * 是否快速双击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD > 0 && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
    /**
     * 根据路径转byte数组
     */
    public static byte[] File2byte(String filePath)
    {
        byte[] buffer = null;
        try
        {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }
    public static String biemap(byte[] b) {
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0" + stmp);

            } else {
                sb.append(stmp);

            }

        }
        return sb.toString();
    }
    /**
     * @author chenzheng
     * @since 2014-5-9
     * @Description: 获取屏幕宽度
     * @throws
     * @param context
     * @return int
     */
    public static int getScreenW(Context context) {
        return getScreenSize(context, true);
    }

    /**
     * @author chenzheng
     * @since 2014-5-9
     * @Description: 获取屏幕高度
     * @throws
     * @param context
     * @return int
     */
    public static int getScreenH(Context context) {
        return getScreenSize(context, false);
    }

    private static int getScreenSize(Context context, boolean isWidth) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return isWidth ? dm.widthPixels : dm.heightPixels;
    }





}

//	/**
//	 * app信息(生成二维码)
//	 *
//	 * @return
//	 */
//	public static String getAppInfo(Context context) {
//		return Const.SITE_APP_ADDR;
//	}



