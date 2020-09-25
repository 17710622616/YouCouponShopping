package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.CommonModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.SPUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 绑定订单
 */
public class OrderRetrievalActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    private EditText orderNoEt;
    private TextView submitTv;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_retrieval);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        headView = findViewById(R.id.order_retrieval_head);
        orderNoEt = findViewById(R.id.order_retrieval_et);
        submitTv = findViewById(R.id.order_retrieval_submit);
    }

    @Override
    public void setListener() {
        submitTv.setOnClickListener(this);
    }

    @Override
    public void initData() {
        headView.setLeft(this);
        headView.setTitle("找回订单");
        headView.setRightText("规则", this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_left:
                finish();
                break;
            case R.id.head_right_tv:
                Intent intent2 = new Intent(OrderRetrievalActivity.this, WebH5Activity.class);
                intent2.putExtra("title", "常见问题");
                intent2.putExtra("webUrl", "https://test-pic-666.oss-cn-hongkong.aliyuncs.com/0html/YouCoupon/common_problem.html");
                startActivity(intent2);
                break;
            case R.id.order_retrieval_submit:
                dialog = new ProgressDialog(this);
                dialog.setTitle("系统");
                dialog.setMessage("提交中......");
                dialog.setCancelable(false);
                dialog.show();
                String orderNo = orderNoEt.getText().toString();
                if (orderNo != null &&  !orderNo.equals("")) {
                    callNetSubmitOrderRrtrieval(orderNo);
                } else {
                    Toast.makeText(this, "请填写全资料", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                break;
        }
    }

    private void callNetSubmitOrderRrtrieval(String orderNo) {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.POST_ORDER_RETRIRVAL);
        params.addQueryStringParameter("orderNo", orderNo);
        params.addQueryStringParameter("token", String.valueOf(SPUtils.get(this, "UserToken", "")));
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model = JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 0) {
                    Toast.makeText(OrderRetrievalActivity.this, "绑定订单成功！" + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderRetrievalActivity.this, "绑定订单失败！" + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(OrderRetrievalActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderRetrievalActivity.this, getString(R.string.request_error), Toast.LENGTH_SHORT).show();
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
