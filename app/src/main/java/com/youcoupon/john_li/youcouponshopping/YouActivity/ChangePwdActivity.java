package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.gyf.immersionbar.ImmersionBar;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.CommonModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.SmsOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.CountDownButtonHelper;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by John_Li on 10/5/2018.
 */

public class ChangePwdActivity extends BaseActivity {
    private Toolbar mToolBar;
    private TextView loginTv, codeTv;
    private Button registerBtn;
    private EditText phoneEt, pwEt,verificaEt;
    private LinearLayout mVisitorLL;
    private ProgressDialog dialog;

    private CountDownButtonHelper helper;
    private SmsOutModel.SmsModel mSmsModel;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).titleBar(R.id.login_toolbar).keyboardEnable(true).init();
        mToolBar = findViewById(R.id.register_toolbar);
        registerBtn = findViewById(R.id.btn_register);
        loginTv = findViewById(R.id.tv_login);
        codeTv = findViewById(R.id.register_verifica_tv);
        phoneEt = findViewById(R.id.register_et_phone);
        pwEt = findViewById(R.id.register_et_password);
        verificaEt = findViewById(R.id.register_et_verifica);
        mVisitorLL = findViewById(R.id.register_visitor_ll);
        mVisitorLL.setVisibility(View.GONE);
        loginTv.setVisibility(View.GONE);

        codeTv.setTextColor(getResources().getColor(R.color.colorDrakGray));
        codeTv.setEnabled(false);
    }

    @Override
    public void setListener() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneEt.getText().toString();
                String pw = pwEt.getText().toString();
                String verifica = verificaEt.getText().toString();
                if (phone != null && verifica != null&& pw != null) {
                    if (!YouCommonUtils.compareTwoTimes(mSmsModel.getOverTime())) {
                        if (verifica.equals(mSmsModel.getCode())) {
                            callNetRegister(phone, pw);
                        } else {
                            Toast.makeText(ChangePwdActivity.this, "验证码错误！", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ChangePwdActivity.this, "验证码已超时！", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChangePwdActivity.this, "请填写账户密码或验证码！", Toast.LENGTH_LONG).show();
                }
            }
        });

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivityForResult(new Intent(RegisterActivity.this, LoginActivity.class), 10001);
            }
        });
        codeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
                    @Override
                    public void finish() {
                        codeTv.setText("获取验证码");
                    }
                });
                helper.start();

                callNetGetVerCode();
            }
        });
        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (phoneEt.getText().toString().length() >= 8) {
                    codeTv.setEnabled(true);
                    codeTv.setTextColor(Color.BLACK);
                } else {
                    codeTv.setEnabled(false);
                    codeTv.setTextColor(Color.WHITE);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initData() {
        helper = new CountDownButtonHelper(codeTv, "倒計時", 60,1);
        mToolBar.setTitle("修改密码");
    }

    /**
     * 注修改密码
     * @param phone
     * @param pw
     */
    private void callNetRegister(final String phone, final String pw) {
        dialog = new ProgressDialog(this);
        dialog.setTitle("提示");
        dialog.setMessage("正在修改密码中......");
        dialog.setCancelable(false);
        dialog.show();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("mobile", phone);
        paramsMap.put("passWord", pw);
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.CHG_PWD + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setAsJsonContent(true);
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.POST ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model = JSON.parseObject(result, CommonModel.class);
                if (model.getStatus() == 0) {
                    Toast.makeText(ChangePwdActivity.this, "修改密码成功" + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("userName", phone);
                    intent.putExtra("passWord", pw);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(ChangePwdActivity.this, "修改密码失败" + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(ChangePwdActivity.this, R.string.callnet_timeout, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePwdActivity.this, "修改密码失败", Toast.LENGTH_SHORT).show();
                }
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
                dialog.dismiss();
            }
        });
    }

    /**
     * 獲取验证码
     */
    private void callNetGetVerCode() {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_VERIFICATION_CODE);
        params.addQueryStringParameter("mobile", phoneEt.getText().toString());
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SmsOutModel model = JSON.parseObject(result.toString(), SmsOutModel.class);
                if (model.getStatus() == 0) {
                    mSmsModel = model.getData();
                    Toast.makeText(ChangePwdActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                } else {
                    helper.finishTimer("获取验证码");
                    Toast.makeText(ChangePwdActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                helper.finishTimer("获取验证码");
                Toast.makeText(ChangePwdActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}

