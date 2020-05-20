package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.PerformanceOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.SPUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class PerformanceActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    private TextView todayOrderCountTv, todayPerformanceTv;
    private TextView thisOrderCountTv, thisPerformanceTv;
    private TextView lastOrderCountTv, lastPerformanceTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_performance);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        headView = findViewById(R.id.performance_head);
        todayOrderCountTv = findViewById(R.id.today_order_num_tv);
        todayPerformanceTv = findViewById(R.id.today_performance_tv);
        thisOrderCountTv = findViewById(R.id.this_month_order_num_tv);
        thisPerformanceTv = findViewById(R.id.this_month_performance_tv);
        lastOrderCountTv = findViewById(R.id.last_month_order_num_tv);
        lastPerformanceTv = findViewById(R.id.last_month_performance_tv);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {
        headView.setLeft(this);
        headView.setTitle("我的效益");
        headView.setRight(R.mipmap.question, this);
        callNetGetPerformanceLastMonth();
        callNetGetPerformanceLastMonth();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_left:
                finish();
                break;
            case R.id.head_right:
                break;
        }
    }


    /**
     * 取本月绩效
     */
    private void callNetGetPerformanceThisMonth() {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_PERFORMANCE_THIS_MONTH + SPUtils.get(PerformanceActivity.this, "UserToken", ""));
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                PerformanceOutModel model =  JSON.parseObject(result.toString(), PerformanceOutModel.class);
                if (model.getStatus() == 0) {
                    String performanceThisMonth =  model.getData().getCommisonAmount().toString();
                    thisPerformanceTv.setText("本月预估\n" +performanceThisMonth + "元");
                    thisOrderCountTv.setText("订单数量\n"+model.getData().getTotalCount());
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                thisPerformanceTv.setText("本月预估\n0元");
                thisOrderCountTv.setText("订单数量\n0");
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                thisPerformanceTv.setText("本月预估\n0元");
                thisOrderCountTv.setText("订单数量\n0");
            }
            @Override
            public void onFinished() {
            }
        });
    }

    /**
     * 取上月绩效
     */
    private void callNetGetPerformanceLastMonth() {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_PERFORMANCE_LAST_MONTH + SPUtils.get(PerformanceActivity.this, "UserToken", ""));
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                PerformanceOutModel model =  JSON.parseObject(result.toString(), PerformanceOutModel.class);
                if (model.getStatus() == 0) {
                    String performanceLastMonth = model.getData().getCommisonAmount().toString();
                    lastPerformanceTv.setText("上月预估\n" + performanceLastMonth + "元");
                    lastOrderCountTv.setText("订单数量\n" + model.getData().getTotalCount());
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                lastPerformanceTv.setText("上月预估\n0元");
                lastOrderCountTv.setText("订单数量\n0");
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                lastPerformanceTv.setText("上月预估\n0元");
                lastOrderCountTv.setText("订单数量\n0");
            }
            @Override
            public void onFinished() {
            }
        });
    }
}
