package com.example.administrator.a3dmark.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */
public class Mark {
    public String typeName;
    public List<Child> childList;

    public Mark() {
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
}
