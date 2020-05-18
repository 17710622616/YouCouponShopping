package com.youcoupon.john_li.youcouponshopping.YouActivity;

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
import org.xutils.http.RequestParams;
import org.xutils.x;

public class WalletActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    private TextView moneyTv;
    private LinearLayout pwdLL, withdrwaLL;
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
        headView.setLeft(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_left:
                finish();
                break;
            case R.id.wallet_pwd_ll:
                startActivity(new Intent(this, UpdatePayPwdActivity.class));
                break;
            case R.id.wallet_withdraw_ll:
                Toast.makeText(WalletActivity.this, "如需充值錢包，請聯繫客服！", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, ServiceActivity.class);
                startActivity(intent2);
                break;
        }
    }

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
                    moneyTv.setText(model.getData().toString());
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
}
