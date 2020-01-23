package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

public class ServiceActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    private LinearLayout loadingLL;
    private EditText opinionEt, opinionName,opinionPhone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        headView = findViewById(R.id.suggest_head);
        loadingLL = findViewById(R.id.suggest_loading);
        opinionEt = findViewById(R.id.opinion_et);
        opinionName = findViewById(R.id.opinion_name);
        opinionPhone = findViewById(R.id.opinion_phone);
    }

    @Override
    public void setListener() {
    }

    @Override
    public void initData() {
        headView.setTitle("客服服务");
        headView.setLeft(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_left:
                finish();
                break;
        }
    }
}
