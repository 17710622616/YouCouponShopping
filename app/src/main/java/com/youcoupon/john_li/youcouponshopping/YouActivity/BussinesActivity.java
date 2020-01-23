package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

public class BussinesActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    private LinearLayout loadingLL;
    private EditText bussinesEt, bussinesName,bussinesPhone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussines);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        headView = findViewById(R.id.bussines_head);
        loadingLL = findViewById(R.id.bussines_loading);
        bussinesEt = findViewById(R.id.bussines_et);
        bussinesName = findViewById(R.id.bussines_name);
        bussinesPhone = findViewById(R.id.bussines_phone);
    }

    @Override
    public void setListener() {
    }

    @Override
    public void initData() {
        headView.setTitle("商业合作");
        headView.setLeft(this);
        headView.setRightText("提交",this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_left:
                finish();
                break;
            case R.id.head_right_tv:
                loadingLL.setVisibility(View.VISIBLE);
                if (bussinesEt.getText() != null && bussinesName.getText() != null && bussinesPhone.getText() != null ){
                    callNetSubmitBussines();
                } else {
                    loadingLL.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "请完善资料！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void callNetSubmitBussines() {
        try {
            Thread.sleep(2000);
            loadingLL.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "提交成功！我们的工作人员会尽快与您取得联系！", Toast.LENGTH_SHORT).show();
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
