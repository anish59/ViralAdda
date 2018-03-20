package com.ronak.viral.adda.drawer;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.ronak.viral.adda.viralModels.DailyMotionData;
import com.ronak.viral.adda.viralModels.Tab;

import java.io.Serializable;
import java.util.List;

public class NavItem implements Serializable {

    private String mText;
    private int mTextResource;
    private String[] mData;
    private Class<? extends Fragment> mFragment;
    private Tab tab;

    public String categoryImageUrl;

    //Create a new item with a resource string, resource drawable, type, fragment and data
    public NavItem(int text, Class<? extends Fragment> fragment, String[] data, Tab tab) {
        this(null, fragment, data, tab);
        mTextResource = text;
    }

    //Create a new item with a text string, resource drawable, type, fragment, data and purchase requirement
    public NavItem(String text, Class<? extends Fragment> fragment, String[] data, Tab tab) {
        mText = text;
        mFragment = fragment;
        mData = data;
        this.tab = tab;
    }

    public String getText(Context c) {
        if (mText != null) {
            return mText;
        } else {
            return c.getResources().getString(mTextResource);
        }
    }

    public Class<? extends Fragment> getFragment() {
        return mFragment;
    }

    public String[] getData() {
        return mData;
    }

    public Tab getTab() {
        return tab;
    }

    public void setCategoryImageUrl(String url) {
        this.categoryImageUrl = url;
    }
}
