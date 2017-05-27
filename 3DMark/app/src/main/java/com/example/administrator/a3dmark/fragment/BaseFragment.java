package com.example.administrator.a3dmark.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.administrator.a3dmark.MyApplication;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheManager;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Stability.Yang on 2017/3/17.
 */

public class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    /**
     * 刷新回顶部
     */
    protected void refreshFocusUp(BaseAdapter adapter, final ScrollView scrollView) {

        try {
            adapter.notifyDataSetChanged();
            //定位到顶部
            scrollView.post(new Runnable() {//可用于mScrollView定位到顶部
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "没有数据，请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 登录判断
     *
     * @return
     */
    protected String isLogin() {
        try {
            String status = (String) CacheManager.INSTANCE.get("loginStatus").getData();
            JSONObject json = new JSONObject(status);
            JSONObject userId = new JSONObject(json.getString("success"));
            SharedUtil.setParam(getActivity(), "userid", userId.getString("userId"));
//            SharedUtil.setParam(getActivity(),"psw");
            return userId.getString("userId");
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 头像Img
     *
     * @return
     */
    protected String headImg() {
        try {
            String status = (String) CacheManager.INSTANCE.get("loginStatus").getData();
            JSONObject json = new JSONObject(status);
            JSONObject headImg = new JSONObject(json.getString("success"));
            return headImg.getString("headImg");
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 昵称
     *
     * @return
     */
    protected String nickName() {
        try {
            String status = (String) CacheManager.INSTANCE.get("loginStatus").getData();
            JSONObject json = new JSONObject(status);
            JSONObject nickName = new JSONObject(json.getString("success"));
            return nickName.getString("nickname");
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 个性签名
     *
     * @return
     */
    protected String signature() {
        try {
            String status = (String) CacheManager.INSTANCE.get("loginStatus").getData();
            JSONObject json = new JSONObject(status);
            JSONObject signature = json.getJSONObject("success");
            return signature.getString("signature");
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "暂无个性签名";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "暂无个性签名";
    }
}
