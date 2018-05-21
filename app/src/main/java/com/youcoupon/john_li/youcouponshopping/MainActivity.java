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
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.youcoupon.john_li.youcouponshopping.YouActivity.MineActivity;

public class MainActivity extends AppCompatActivity {
    private TextView doLoginTv, mineTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doLoginTv = (TextView) findViewById(R.id.taobao_login);
        doLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
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
