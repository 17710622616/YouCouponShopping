package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.gyf.immersionbar.ImmersionBar;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.CommonModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.SPUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class UpdatePayPwdActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    private TextView submitTv;
    private EditText oldPwdEt, newPwdEt, confirmPwdEt;
    private LinearLayout oldPwdLL;
    private ProgressDialog dialog;

    private String startWay = "CREATE";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pay_pwd);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).init();
        headView = findViewById(R.id.update_pay_pwd_head);
        submitTv = findViewById(R.id.update_pwd_submit);
        oldPwdEt = findViewById(R.id.old_pwd_et);
        newPwdEt = findViewById(R.id.new_pwd_et);
        confirmPwdEt = findViewById(R.id.confirm_new_pwd_et);
        oldPwdLL = findViewById(R.id.old_pwd_ll);
    }

    @Override
    public void setListener() {
        submitTv.setOnClickListener(this);
    }

    @Override
    public void initData() {
        headView.setLeft(this);
        startWay = getIntent().getStringExtra("startWay");
        if (!startWay.equals("") && startWay.equals("UPDATE")) {
            headView.setTitle("修改密码");
            oldPwdLL.setVisibility(View.VISIBLE);
        } else {
            headView.setTitle("新增密码");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_left:
                finish();
                break;
            case R.id.update_pwd_submit:
                checkData();
                break;
        }
    }


    /**
     * 確認編輯框是否不為空
     */
    private void checkData() {
        dialog = new ProgressDialog(this);
        dialog.setTitle("系统");
        dialog.setMessage("提交中......");
        dialog.setCancelable(false);
        dialog.show();
        String oldPwd = oldPwdEt.getText().toString();
        String pw = newPwdEt.getText().toString();
        String confirmPwd = confirmPwdEt.getText().toString();
        if (startWay.equals("UPDATE")) {
            if (oldPwd != null && pw != null && confirmPwd != null && !oldPwd.equals("") && !pw.equals("") && !confirmPwd.equals("")) {
                if (pw.equals(confirmPwd)) {
                    callNetUpdatePwd(oldPwd, pw);
                } else {
                    Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            } else {
                Toast.makeText(this, "请填写全资料", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        } else {
            if (pw != null && confirmPwd != null && !pw.equals("") && !confirmPwd.equals("")) {
                if (pw.equals(confirmPwd)) {
                    callNetAddPwd(pw);
                } else {
                    Toast.makeText(this, "兩次密碼不一致", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            } else {
                Toast.makeText(this, "请填写全资料", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }
    }

    private void callNetUpdatePwd(String oldPwd, String pw) {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.UPDATE_PAY_PWD);
        params.addQueryStringParameter("oldPayPwd", oldPwd);
        params.addQueryStringParameter("newPayPwd", pw);
        params.addQueryStringParameter("timeStamp", String.valueOf(System.currentTimeMillis()));
        params.addQueryStringParameter("token", String.valueOf(SPUtils.get(this, "UserToken", "")));
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model = JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 0) {
                    if (model.getData().toString().equals("true")) {
                        Toast.makeText(UpdatePayPwdActivity.this, "修改密码成功!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdatePayPwdActivity.this, getString(R.string.update_payment_pw_fail) + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else {
                    Toast.makeText(UpdatePayPwdActivity.this, getString(R.string.update_payment_pw_fail) + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(UpdatePayPwdActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdatePayPwdActivity.this, getString(R.string.request_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                if (dialog != null) {
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            }
        });
    }

    private void callNetAddPwd(String pw) {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.ADD_PAY_PWD);
        params.addQueryStringParameter("payPwd", pw);
        params.addQueryStringParameter("timeStamp", String.valueOf(System.currentTimeMillis()));
        params.addQueryStringParameter("token", String.valueOf(SPUtils.get(this, "UserToken", "")));
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model= JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 0) {
                    if (model.getData().toString().equals("true")) {
                        Toast.makeText(UpdatePayPwdActivity.this, "新增密码成功!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdatePayPwdActivity.this, getString(R.string.add_payment_pw_fail) + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else {
                    Toast.makeText(UpdatePayPwdActivity.this, getString(R.string.add_payment_pw_fail) + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(UpdatePayPwdActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdatePayPwdActivity.this, getString(R.string.request_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                if (dialog != null) {
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            }
        });
    }
}
