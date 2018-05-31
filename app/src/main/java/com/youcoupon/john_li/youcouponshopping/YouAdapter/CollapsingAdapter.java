package com.youcoupon.john_li.youcouponshopping.YouAdapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John_Li on 31/5/2018.
 */

public class CollapsingAdapter extends PagerAdapter {
    // 图片集合
    private List<ImageView> adImgList = new ArrayList<ImageView>();

    public CollapsingAdapter(List<ImageView> adImgList) {
        this.adImgList = adImgList;
    }

    @Override
    public int getCount() {
        return adImgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(adImgList.get(position));
        return adImgList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(adImgList.get(position));
    }
}
