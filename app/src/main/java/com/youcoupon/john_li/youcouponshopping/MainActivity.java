package com.youcoupon.john_li.youcouponshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.LoginService;
import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.youcoupon.john_li.youcouponshopping.YouActivity.MineActivity;

public class MainActivity extends AppCompatActivity {
    private TextView doLoginTv, loginOutTv, mineTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.showLogin(this, new AlibcLoginCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "登录成功 ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this, "登录失敗 ", Toast.LENGTH_LONG).show();
            }
        });
    }
}
