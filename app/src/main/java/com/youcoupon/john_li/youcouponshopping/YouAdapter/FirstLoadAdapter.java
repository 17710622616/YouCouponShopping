package com.youcoupon.john_li.youcouponshopping.YouAdapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FirstLoadAdapter extends PagerAdapter {
    private List<View> ivList = new ArrayList<View>();

    public FirstLoadAdapter(List<View> ivList) {
        super();
        this.ivList = ivList;
    }

    @Override
    public int getCount() {
        return ivList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(ivList.get(position));
        return ivList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(ivList.get(position));
    }



}
