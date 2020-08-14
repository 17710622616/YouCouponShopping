package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.youcoupon.john_li.youcouponshopping.LoginActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.CommonModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.SPUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

public class BussinesActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    private LinearLayout loadingLL;
    private EditText bussinesEt, bussinesName,bussinesPhone;
    private ProgressDialog dialog;
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
                String name = bussinesName.getText().toString();
                String content = bussinesEt.getText().toString();
                String tel = bussinesPhone.getText().toString();
                if (name != null && content != null && tel != null ){
                    if (!name.equals("") && !content.equals("") && !tel.equals("")){
                        callNetSubmitBussines();
                    } else {
                        loadingLL.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "请完善资料！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loadingLL.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "请完善资料！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void callNetSubmitBussines() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("contact", String.valueOf(bussinesName.getText()));
        paramsMap.put("tel", String.valueOf(bussinesPhone.getText()));
        paramsMap.put("content",  String.valueOf(bussinesEt.getText()));
        paramsMap.put("token",  String.valueOf(SPUtils.get(BussinesActivity.this, "UserToken", "")));
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.POST_BUSINESS + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setAsJsonContent(true);
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.POST ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model = JSON.parseObject(result, CommonModel.class);
                if (model.getStatus() == 0) {
                    Toast.makeText(BussinesActivity.this, String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                    loadingLL.setVisibility(View.GONE);
                    finish();
                } else {
                    Toast.makeText(BussinesActivity.this,  String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(BussinesActivity.this, getString(R.string.callnet_timeout), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BussinesActivity.this, getString(R.string.request_error)  + "-->" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
                loadingLL.setVisibility(View.GONE);
            }
        });
    }
}
