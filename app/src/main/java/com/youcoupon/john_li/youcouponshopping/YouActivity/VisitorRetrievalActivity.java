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
 * 绑定邀请人
 */
public class VisitorRetrievalActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    private EditText visitorCodeEt;
    private TextView submitTv, tipsTv;
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
        visitorCodeEt = findViewById(R.id.order_retrieval_et);
        submitTv = findViewById(R.id.order_retrieval_submit);
        tipsTv = findViewById(R.id.order_retrieval_tips);
        tipsTv.setVisibility(View.GONE);
        visitorCodeEt.setHint("请输入邀请码");
    }

    @Override
    public void setListener() {
        submitTv.setOnClickListener(this);
    }

    @Override
    public void initData() {
        headView.setLeft(this);
        headView.setTitle("绑定邀请人");
        headView.setRightText("规则", this);

        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setTitle("系统");
            dialog.setMessage("查询中......");
            dialog.setCancelable(false);
        }

        if (!dialog.isShowing()) {
            dialog.show();
        }

        callNetCheckHasSupior();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_left:
                finish();
                break;
            case R.id.head_right_tv:
                Intent intent2 = new Intent(VisitorRetrievalActivity.this, WebH5Activity.class);
                intent2.putExtra("title", "常见问题");
                intent2.putExtra("webUrl", "https://test-pic-666.oss-cn-hongkong.aliyuncs.com/0html/YouCoupon/common_problem.html");
                startActivity(intent2);
                break;
            case R.id.order_retrieval_submit:
                if (dialog == null) {
                    dialog = new ProgressDialog(this);
                    dialog.setTitle("系统");
                    dialog.setMessage("提交中......");
                    dialog.setCancelable(false);
                }
                dialog.setMessage("提交中......");

                if (!dialog.isShowing()) {
                    dialog.show();
                }
                String visitorCode = visitorCodeEt.getText().toString();
                if (visitorCode != null &&  !visitorCode.equals("")) {
                    callNetSubmitOrderRrtrieval(visitorCode);
                } else {
                    Toast.makeText(this, "请填写全资料", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                break;
        }
    }

    private void callNetCheckHasSupior() {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_SUPIOR);
        params.addQueryStringParameter("token", String.valueOf(SPUtils.get(this, "UserToken", "")));
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model = JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 0) {
                    Toast.makeText(VisitorRetrievalActivity.this, "快去绑定上级吧，大家一起领取高额返利！" + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                } else if(model.getStatus() == 1){
                    Toast.makeText(VisitorRetrievalActivity.this, "查询上下级成功！" + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(VisitorRetrievalActivity.this, "查询上下级失败！" + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(VisitorRetrievalActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VisitorRetrievalActivity.this, getString(R.string.request_error), Toast.LENGTH_SHORT).show();
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

    private void callNetSubmitOrderRrtrieval(String visitorCode) {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.POST_VISITOR_RETRIEVAL);
        params.addQueryStringParameter("invitationCode", visitorCode);
        params.addQueryStringParameter("token", String.valueOf(SPUtils.get(this, "UserToken", "")));
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model = JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 0) {
                    Toast.makeText(VisitorRetrievalActivity.this, "绑定订单成功！" + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VisitorRetrievalActivity.this, "绑定订单失败！" + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(VisitorRetrievalActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VisitorRetrievalActivity.this, getString(R.string.request_error), Toast.LENGTH_SHORT).show();
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
