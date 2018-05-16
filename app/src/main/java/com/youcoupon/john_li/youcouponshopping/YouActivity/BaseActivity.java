package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;

/**
 * Created by John_Li on 10/5/2018.
 */

public abstract class BaseActivity extends FragmentActivity {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    public abstract void initView();
    public abstract void setListener();
    public abstract void initData();
}
