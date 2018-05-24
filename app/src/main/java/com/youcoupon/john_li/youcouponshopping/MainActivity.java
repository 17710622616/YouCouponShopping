package com.youcoupon.john_li.youcouponshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.LoginService;
import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.fastjson.JSON;
import com.youcoupon.john_li.youcouponshopping.YouActivity.MineActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView doLoginTv, loginOutTv, mineTv, couponTestTv;

    private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
    private Map<String, String> exParams;//yhhpass参数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
        AlibcTradeSDK.setForceH5(false);
        doLoginTv = (TextView) findViewById(R.id.taobao_login);
        doLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        loginOutTv = (TextView) findViewById(R.id.taobao_login_out);
        loginOutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginOut();
            }
        });
        mineTv = (TextView) findViewById(R.id.taobao_mine);
        mineTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MineActivity.class));
            }
        });
        couponTestTv = (TextView) findViewById(R.id.taobao_coupon_test);
        couponTestTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlibcTrade.show(MainActivity.this, new AlibcPage("https:\\/\\/uland.taobao.com\\/coupon\\/edetail?e=3gHdzhvvfsIbwa0ArmopK2WYw/uDVc7E6PY46DJ9FdPsDPqV6z5bwXlk/0rWSVNXZAKyMKvD7j3QFdbmkMGiBbPKSUvn0t4PbBob79wv3zvITlCiu79rPkbAz7YOFP0iYfFeq5PfaO00neHPq6gjTVhAmztsbMhPVe6qZbIEZohIH07HK3v5wE7zov4IbAY1AO/Yfxygms8=&amp;traceId=0bfa322515270666651678793e"), alibcShowParams, null, exParams , new AlibcTradeCallback() {
                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(MainActivity.this, "打开领券界面失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        Toast.makeText(MainActivity.this, "打开领券界面成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void loginOut() {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.logout(MainActivity.this, new LogoutCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "退出登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(MainActivity.this, "退出登录失败 " + code + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 登录
     */
    public void login() {
        final AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.showLogin(this, new AlibcLoginCallback() {
            @Override
            public void onSuccess() {
                //获取淘宝用户信息
                String s = JSON.toJSONString(AlibcLogin.getInstance().getSession());
                Log.i("MainActivity", "获取淘宝用户信息: "+AlibcLogin.getInstance().getSession());
                String nick = AlibcLogin.getInstance().getSession().nick;
                String avatarUrl = AlibcLogin.getInstance().getSession().avatarUrl;
                Toast.makeText(MainActivity.this, "登录成功 ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this, "登录失敗 ", Toast.LENGTH_LONG).show();
            }
        });
    }
}
