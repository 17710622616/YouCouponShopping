package com.youcoupon.john_li.youcouponshopping.YouUtils;

import android.app.Application;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;

/**
 * Created by John on 20/5/2018.
 */

public class YouApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                //初始化成功，设置相关的全局配置参数
                Toast.makeText(YouApplication.this, "阿里百川初始化成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
                Toast.makeText(YouApplication.this, "阿里百川初始化失败,code=" + code + ", msg=" + msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
