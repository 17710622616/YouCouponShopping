package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.youcoupon.john_li.youcouponshopping.LoginActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.CommonModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.UserInfoOutsideModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.CountDownButtonHelper;
import com.youcoupon.john_li.youcouponshopping.YouUtils.SPUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by John_Li on 10/5/2018.
 */

public class RegisterActivity extends BaseActivity {
    private TextView registerTv, codeTv;
    private Button registerBtn;
    private EditText phoneEt, pwEt,verificaEt,visitorTv;
    private ProgressDialog dialog;
    private CountDownButtonHelper helper;
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
        registerBtn = findViewById(R.id.btn_register);
        registerTv = findViewById(R.id.tv_register);
        codeTv = findViewById(R.id.register_verifica_tv);
        phoneEt = findViewById(R.id.register_et_phone);
        pwEt = findViewById(R.id.register_et_password);
        verificaEt = findViewById(R.id.register_et_verifica);
        visitorTv = findViewById(R.id.register_et_visitor);

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
                    callNetRegister(phone, pw);
                } else {
                    Toast.makeText(RegisterActivity.this, "请填写账户密码或验证码！", Toast.LENGTH_LONG).show();
                }
            }
        });

        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(RegisterActivity.this, LoginActivity.class), 10001);
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
    }

    @Override
    public void initData() {
        helper = new CountDownButtonHelper(codeTv, "倒計時", 60,1);
    }

    private void callNetRegister(String phone, String pw) {
        dialog = new ProgressDialog(this);
        dialog.setTitle("提示");
        dialog.setMessage("正在注册中......");
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
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败" + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dialog.dismiss();
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(RegisterActivity.this, R.string.callnet_timeout, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
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


    /**
     * 獲取验证码
     */
    private void callNetGetVerCode() {
        /*RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_VERIFICATION_CODE);
        params.addQueryStringParameter("mobile", phoneEt.getText().toString());
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model = JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 200) {
                    Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT).show();
                } else {
                    helper.finishTimer(getString(R.string.get_verification));
                    Toast.makeText(RegisterActivity.this, getString(R.string.get_verification_code_fail), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                helper.finishTimer(getString(R.string.get_verification));
                Toast.makeText(RegisterActivity.this, getString(R.string.get_verification_code_fail), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });*/
    }
}

