package com.example.administrator.a3dmark.util;

import com.example.administrator.a3dmark.bean.DColorBean;
import com.example.administrator.a3dmark.bean.DSizeBean;

import java.util.List;

/**
 * Created by Stability.Yang on 2017/4/2.
 */

public class SizeOrColorManager {

    /**
     * 清空Color状态
     */
    public static List<DColorBean> clearColorAdapterStates(List<DColorBean> mList) {
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            DColorBean bean = mList.get(i);
            bean.setStates("1");
            mList.set(i, bean);
        }
        return mList;
    }


    /**
     * 设置Color选中状态
     */
    public static List<DColorBean> setColorAdapterStates(List<DColorBean> mList,String key) {
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            DColorBean bean = mList.get(i);
            String str=bean.getColor();
            if(str.equals(key)){
                bean.setStates("0");
            }else{
                bean.setStates("1");
            }
            mList.set(i, bean);
        }
        return mList;
    }

    /**
     * 更新Color适配器状态
     *
     * @param states
     */
    public static List<DColorBean> updateColorAdapterStates(List<DColorBean> mList,String states, int postion) {
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            DColorBean bean = mList.get(i);
            if (i == postion) {
                bean.setStates(states);
            } else {
                bean.setStates("1");
            }
            mList.set(i, bean);
        }
        return mList;
    }



    /**
     * 清空Size状态
     */
    public static List<DSizeBean> clearSizeAdapterStates(List<DSizeBean> mList) {
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            DSizeBean bean = mList.get(i);
            bean.setStates("1");
            mList.set(i, bean);
        }
        return mList;
    }


    /**
     * 设置Size选中状态
     */
    public static List<DSizeBean> setSizeAdapterStates(List<DSizeBean> mList,String key) {
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            DSizeBean bean = mList.get(i);
            String str=bean.getSize();
            if(str.equals(key)){
                bean.setStates("0");
            }else{
                bean.setStates("1");
            }
            mList.set(i, bean);
        }
        return mList;
    }


    /**
     * 更新Size适配器状态
     *
     * @param states
     */
    public static List<DSizeBean> updateSizeAdapterStates(List<DSizeBean> mList,String states, int postion) {
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            DSizeBean bean = mList.get(i);
            if (i == postion) {
                bean.setStates(states);
            } else {
                bean.setStates("1");
            }
            mList.set(i, bean);
        }
        return mList;
    }
}
