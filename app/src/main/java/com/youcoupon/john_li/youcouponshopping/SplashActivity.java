package com.youcoupon.john_li.youcouponshopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.youcoupon.john_li.youcouponshopping.YouActivity.BaseActivity;

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
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
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
