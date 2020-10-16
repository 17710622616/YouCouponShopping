package com.youcoupon.john_li.youcouponshopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.youcoupon.john_li.youcouponshopping.YouActivity.BaseActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.FirstLoadActivity;
import com.youcoupon.john_li.youcouponshopping.YouUtils.SPUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;

/**
 * Created by John_Li on 25/5/2018.
 */

public class SplashActivity extends BaseActivity {
    private ImageView splashIV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_splash);

        splashIV = (ImageView) findViewById(R.id.splash_iv);
        splashIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                int loadCount = (int) SPUtils.get(SplashActivity.this, "loadCount", 0);
                if (loadCount == 0) {
                    SPUtils.put(SplashActivity.this, "loadCount", ++loadCount);
                    intent = new Intent(SplashActivity.this, FirstLoadActivity.class);
                    finish();
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                    finish();
                }

                //如果启动app的Intent中带有额外的参数，表明app是从点击通知栏的动作中启动的
                //将参数取出，传递到MainActivity中
                if(getIntent().getBundleExtra(YouConfigor.EXTRA_BUNDLE) != null){
                    intent.putExtra(YouConfigor.EXTRA_BUNDLE, getIntent().getBundleExtra(YouConfigor.EXTRA_BUNDLE));
                }

                startActivity(intent);
                finish();
            }
        }, 1500);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }
}
