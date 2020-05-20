package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.net.SocketTimeoutException;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.CommonModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.WithDrawalModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.SPUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

public class WithDrawActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    private EditText withdrawNum;
    private CheckBox isagreeCb;
    private TextView withdrawAgreement, withdrawSubmit, balanceTv;
    private EditText nameEv, telEv, alipayEt;
    private WithDrawalModel mWithDrawalModel;
    private double balance;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        headView = findViewById(R.id.wallet_withdraw_head);
        isagreeCb = findViewById(R.id.wallet_withdraw_isagree);
        withdrawNum = findViewById(R.id.wallet_withdraw_num);
        withdrawAgreement = findViewById(R.id.wallet_withdraw_agreement);
        withdrawSubmit = findViewById(R.id.wallet_withdraw_submit);
        balanceTv = findViewById(R.id.wallet_withdraw_balance);
        nameEv = findViewById(R.id.wallet_withdraw_name_et);
        telEv = findViewById(R.id.wallet_withdraw_tel_et);
        alipayEt = findViewById(R.id.wallet_withdraw_alipay_et);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            headView.setHeadHight();
        }
    }

    @Override
    public void setListener() {
        withdrawAgreement.setOnClickListener(this);
        withdrawSubmit.setOnClickListener(this);
    }

    @Override
    public void initData() {
        headView.setLeft(this);
        headView.setTitle("余额提现");
        mWithDrawalModel = new WithDrawalModel();
        balance = getIntent().getDoubleExtra("balance", 0.0);
        balanceTv.setText("可提现余额RMB：" + balance);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_left:
                finish();
                break;
            case R.id.wallet_withdraw_agreement:
                //startActivity(new Intent(this, PaymentAgreementActivity.class));
                break;
            case R.id.wallet_withdraw_submit:
                if (isagreeCb.isChecked() && !withdrawNum.getText().toString().equals("") && !alipayEt.getText().toString().equals("") && !telEv.getText().toString().equals("") && !nameEv.getText().toString().equals("")) {
                    if (Double.parseDouble(withdrawNum.getText().toString()) <= balance) {
                        try {
                            final ProgressDialog progress = new ProgressDialog(this);
                            progress.setMessage("請求中......");
                            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progress.setIndeterminate(true);
                            progress.show();

                            RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.POST_WITH_DRAWAL + SPUtils.get(this, "UserToken", ""));
                            params.setAsJsonContent(true);
                            JSONObject jsonObj = new JSONObject();
                            try {
                                jsonObj.put("money",withdrawNum.getText().toString());
                                jsonObj.put("realName",nameEv.getText().toString());
                                jsonObj.put("phoneNumber",telEv.getText().toString());
                                jsonObj.put("zfbAccount",alipayEt.getText().toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String urlJson = jsonObj.toString();
                            params.setBodyContent(urlJson);
                            params.setConnectTimeout(30 * 1000);
                            x.http().request(HttpMethod.POST ,params, new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    CommonModel model = JSON.parseObject(result.toString(), CommonModel.class);
                                    if (model.getStatus() == 0) {
                                        Toast.makeText(WithDrawActivity.this, "提現申請提交成功！", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else if (model.getStatus() ==10000){
                                        SPUtils.put(WithDrawActivity.this, "UserToken", "");
                                        Toast.makeText(WithDrawActivity.this,  String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(WithDrawActivity.this, "提現申請提交失敗！" + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                //请求异常后的回调方法
                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {
                                    if (ex instanceof SocketTimeoutException) {
                                        Toast.makeText(WithDrawActivity.this, "提現申請提交超時，請重試！", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(WithDrawActivity.this, "提現申請提交失敗！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                //主动调用取消请求的回调方法
                                @Override
                                public void onCancelled(CancelledException cex) {
                                }
                                @Override
                                public void onFinished() {
                                    progress.dismiss();
                                }
                            });
                        } catch (Exception e) {
                            Toast.makeText(WithDrawActivity.this, "提交失敗，請檢查提交信息是否正確！", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(WithDrawActivity.this, "提现金额不可大于可提现金额！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WithDrawActivity.this, "请填写全提现资料及勾选支付协议！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
