package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class WalletActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    private TextView moneyTv;
    private LinearLayout pwdLL, withdrwaLL;
    private double balance;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).init();
        headView = findViewById(R.id.wallet_head);
        moneyTv = findViewById(R.id.wallet_money_tv);
        pwdLL = findViewById(R.id.wallet_pwd_ll);
        withdrwaLL = findViewById(R.id.wallet_withdraw_ll);
    }

    @Override
    public void setListener() {
        pwdLL.setOnClickListener(this);
        withdrwaLL.setOnClickListener(this);
    }

    @Override
    public void initData() {
        callNetGetBalance();
        headView.setTitle(getResources().getString(R.string.my_wallet));
        headView.setRightText("余额记录", this);
        headView.setLeft(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_left:
                finish();
                break;
            case R.id.head_right_tv:
                startActivity(new Intent(this, WalletRecrodActivity.class));
                break;
            case R.id.wallet_pwd_ll:
                dialog = new ProgressDialog(this);
                dialog.setTitle("系统");
                dialog.setMessage("检查是否有支付密码......");
                dialog.setCancelable(false);
                dialog.show();
                getHasPayPw(1);
                break;
            case R.id.wallet_withdraw_ll:
                dialog = new ProgressDialog(this);
                dialog.setTitle("系统");
                dialog.setMessage("检查是否有支付密码......");
                dialog.setCancelable(false);
                dialog.show();
                if (balance > 0) {
                    getHasPayPw(2);
                } else {
                    dialog.dismiss();;
                    Toast.makeText(WalletActivity.this, "您暂无可提现余额！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 获取余额
     */
    private void callNetGetBalance() {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_BALANCE);
        params.addQueryStringParameter("token", String.valueOf(SPUtils.get(this, "UserToken", "")));
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model = JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 0) {
                    balance = Double.parseDouble(model.getData().toString());
                    moneyTv.setText(String.valueOf(balance));
                } else {
                    Toast.makeText(WalletActivity.this, getString(R.string.balance_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(WalletActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WalletActivity.this, getString(R.string.request_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 判断是否有支付密码
     */
    private void getHasPayPw(final int i) {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_USER_HAS_PAY_PW + SPUtils.get(WalletActivity.this, "UserToken", ""));
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model =  JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 0) {
                    String hasPayPw =  JSON.toJSONString(model.getData()).toString();

                    if (i == 2){
                        if (hasPayPw.equals("true")) {
                            SPUtils.put(WalletActivity.this, "HasPayPw", "1");
                            Intent intent2 = new Intent(WalletActivity.this, WithDrawActivity.class);
                            intent2.putExtra("balance", !moneyTv.getText().toString().equals("") ? Double.parseDouble(moneyTv.getText().toString()) : 0.0);
                            startActivity(intent2);
                        } else {
                            Toast.makeText(WalletActivity.this, "您暂未设置支付密码，请设置支付密码！", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(WalletActivity.this, UpdatePayPwdActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        if (hasPayPw.equals("true")) {
                            Intent intent = new Intent(WalletActivity.this, UpdatePayPwdActivity.class);
                            intent.putExtra("startWay", "UPDATE");
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(WalletActivity.this, UpdatePayPwdActivity.class);
                            intent.putExtra("startWay", "CREATE");
                            startActivity(intent);
                        }
                    }
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
                dialog.dismiss();;
            }
        });
    }
}
