package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouUtils.StatusBarUtil;

/**
 * Created by John_Li on 10/5/2018.
 */

public abstract class BaseActivity extends FragmentActivity {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorPrimary));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            //window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.setContentView(layoutResID);
    }

    public abstract void initView();
    public abstract void setListener();
    public abstract void initData();
}
