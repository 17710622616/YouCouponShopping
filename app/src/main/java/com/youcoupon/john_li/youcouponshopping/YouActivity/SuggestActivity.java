package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

public class SuggestActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    private LinearLayout loadingLL;
    private EditText opinionEt, opinionName,opinionPhone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
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
        headView.setTitle("意见反馈");
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
                if (opinionEt.getText() != null && opinionName.getText() != null && opinionPhone.getText() != null ){
                    callNetSubmitSuggest();
                } else {
                    loadingLL.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "请完善资料！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void callNetSubmitSuggest() {
        try {
            Thread.sleep(2000);
            loadingLL.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "提交成功！感谢您的建议，我们会加倍努力的！", Toast.LENGTH_SHORT).show();
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
