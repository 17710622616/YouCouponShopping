package com.youcoupon.john_li.youcouponshopping;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.youcoupon.john_li.youcouponshopping.YouActivity.BaseActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.MineActivity;
import com.youcoupon.john_li.youcouponshopping.YouFragment.MainFragment;
import com.youcoupon.john_li.youcouponshopping.YouFragment.MineFragment;
import com.youcoupon.john_li.youcouponshopping.YouFragment.RecommendFragment;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioButton park_rb,forum_rb,mine_rb;
    private RadioGroup bottom_group;
    private FragmentManager fm;
    private Fragment cacheFragment;

    private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
    private Map<String, String> exParams;//yhhpass参数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        initData();


        /*alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
        AlibcTradeSDK.setForceH5(false);
        couponTestTv = (TextView) findViewById(R.id.taobao_coupon_test);
        couponTestTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlibcTrade.show(MainActivity.this, new AlibcPage("https://uland.taobao.com/coupon/edetail?e=IFYm0w6Tqb0GQASttHIRqa0KuqDKtdl7mOe3Zy3ORIrSJ8DRB+FABT4G6p9xPaetXP616cnJp9+s6qWhAtAEiAas4jbyTDVDDfqEFBOhTcxNQ0HDGNWn0sKx7sA6hYu1TG+QkQpnVyCA+Lj8OlpHEGPfrr0N2WBehkvjNmX0iiHk92+M7h46cxOTXEDgQqt83bbMXRH/cNI=&traceId=0bfaef8215272314603452398"), alibcShowParams, null, exParams , new AlibcTradeCallback() {
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
        });*/
    }

    @Override
    public void initView() {
        bottom_group = (RadioGroup)findViewById(R.id.bottom_main_group);
        park_rb = (RadioButton) findViewById(R.id.bottom_main);
        forum_rb = (RadioButton) findViewById(R.id.bottom_main_recommend);
        mine_rb = (RadioButton) findViewById(R.id.bottom_main_mine);

        //iv = findViewById(R.id.order_icon);
    }

    @Override
    public void setListener() {
        bottom_group.setOnCheckedChangeListener(this);
    }

    @Override
    public void initData() {
        fm = getSupportFragmentManager();
        FragmentTransaction traslation = fm.beginTransaction();
        cacheFragment = new MainFragment();
        traslation.add(R.id.main_containor,cacheFragment,MainFragment.TAG);
        traslation.commit();
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i){
            case R.id.bottom_main:
                park_rb.setTextColor(getResources().getColor(R.color.colorSkyBlue));
                forum_rb.setTextColor(getResources().getColor(R.color.colorDrakGray));
                mine_rb.setTextColor(getResources().getColor(R.color.colorDrakGray));
                switchPages(MainFragment.class,MainFragment.TAG);
                break;
            case R.id.bottom_main_recommend:
                park_rb.setTextColor(getResources().getColor(R.color.colorDrakGray));
                forum_rb.setTextColor(getResources().getColor(R.color.colorSkyBlue));
                mine_rb.setTextColor(getResources().getColor(R.color.colorDrakGray));
                switchPages(RecommendFragment.class,RecommendFragment.TAG);
                break;
            case R.id.bottom_main_mine:
                park_rb.setTextColor(getResources().getColor(R.color.colorDrakGray));
                forum_rb.setTextColor(getResources().getColor(R.color.colorDrakGray));
                mine_rb.setTextColor(getResources().getColor(R.color.colorSkyBlue));
                switchPages(MineFragment.class,MineFragment.TAG);
                break;
        }
    }

    private void switchPages(Class<?> cls, String tag){
        FragmentTransaction transaction = fm.beginTransaction();
        if (cacheFragment != null){
            transaction.hide(cacheFragment);
        }
        cacheFragment = fm.findFragmentByTag(tag);
        if (cacheFragment != null){
            transaction.show(cacheFragment);
        } else {
            try{
                cacheFragment = (Fragment) cls.getConstructor().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
            transaction.add(R.id.main_containor, cacheFragment, tag);
        }
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
