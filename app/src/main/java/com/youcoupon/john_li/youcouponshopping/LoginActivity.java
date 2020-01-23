package com.youcoupon.john_li.youcouponshopping;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ali.auth.third.core.model.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.gyf.immersionbar.ImmersionBar;
import com.youcoupon.john_li.youcouponshopping.YouActivity.BaseActivity;
import com.youcoupon.john_li.youcouponshopping.YouModel.CommonModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.UserInfoOutsideModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.SPUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by John_Li on 2019/10/30.
 */

public class LoginActivity extends BaseActivity {
    private Button loginBtn;
    private EditText phoneEt, pwEt;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initImmersionBar();
        initView();
        setListener();
        initData();
    }

    public  void initImmersionBar() {
        ImmersionBar.with(this).titleBar(R.id.login_toolbar).keyboardEnable(true).init();
    }

    @Override
    public void initView() {
        loginBtn = findViewById(R.id.btn_login);
        phoneEt = findViewById(R.id.login_et_phone);
        pwEt = findViewById(R.id.login_et_password);
    }

    @Override
    public void setListener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneEt.getText().toString();
                String pw = pwEt.getText().toString();
                if (phone != null && pw != null) {
                    callNetLogin(phone, pw);
                } else {
                    Toast.makeText(LoginActivity.this, "请填写账户密码！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    private void callNetLogin(String phone, String pw) {
        dialog = new ProgressDialog(this);
        dialog.setTitle("提示");
        dialog.setMessage("正在登錄中......");
        dialog.setCancelable(false);
        dialog.show();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userName", phone);
        paramsMap.put("passWord", pw);
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.USER_LOGIN + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setAsJsonContent(true);
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.POST ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model = JSON.parseObject(result, CommonModel.class);
                if (model.getStatus() == 0) {
                    SPUtils.put(LoginActivity.this, "UserToken", model.getData().toString());
                    // 註冊極光別名
                    //String alias = "user" + username;
                    //给极光推送设置标签和别名
                    //JPushInterface.setAlias(LoginActivity.this, alias, tagAliasCallback);
                    //Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                    //setResult(YouConfigor.LOGIN_FOR_RESULT);
                    EventBus.getDefault().post("LOGIN");
                    dialog.dismiss();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_fail) + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dialog.dismiss();
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(LoginActivity.this, R.string.callnet_timeout, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.login_fail, Toast.LENGTH_SHORT).show();
                }
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

}
