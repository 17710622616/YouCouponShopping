package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.google.android.material.tabs.TabLayout;
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
    public String [] sTitle = null;
    private TextView todayOrderCountTv, todayPerformanceTv;
    private TextView thisOrderCountTv, thisPerformanceTv;
    private TextView lastOrderCountTv, lastPerformanceTv;
    private TabLayout mTabLayout;
    private int position;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_performance);
        sTitle = new String[]{"我的效益","Ⅰ级下线","Ⅱ级下线"};
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        headView = findViewById(R.id.performance_head);
        mTabLayout = (TabLayout) findViewById(R.id.performance_tabLayout);
        todayOrderCountTv = findViewById(R.id.today_order_num_tv);
        todayPerformanceTv = findViewById(R.id.today_performance_tv);
        thisOrderCountTv = findViewById(R.id.this_month_order_num_tv);
        thisPerformanceTv = findViewById(R.id.this_month_performance_tv);
        lastOrderCountTv = findViewById(R.id.last_month_order_num_tv);
        lastPerformanceTv = findViewById(R.id.last_month_performance_tv);
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[2]));
    }

    @Override
    public void setListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                headView.setTitle(sTitle[position]);
                callNetGetPerformanceToday();
                callNetGetPerformanceThisMonth();
                callNetGetPerformanceLastMonth();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void initData() {
        headView.setLeft(this);
        headView.setRight(R.mipmap.question, this);
        callNetGetPerformanceToday();
        callNetGetPerformanceThisMonth();
        callNetGetPerformanceLastMonth();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_left:
                finish();
                break;
            case R.id.head_right:
                Intent intent = new Intent(PerformanceActivity.this, WebH5Activity.class);
                intent.putExtra("title", "绩效讲解");
                intent.putExtra("webUrl", "https://test-pic-666.oss-cn-hongkong.aliyuncs.com/0html/YouCoupon/performance_explanation.html");
                startActivity(intent);
                break;
        }
    }

    /**
     * 取当日绩效
     */
    private void callNetGetPerformanceToday() {
        RequestParams params = null;
        switch (position) {
            case 0:
                params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_PERFORMANCE_TODAY + SPUtils.get(PerformanceActivity.this, "UserToken", ""));
                break;
            case 1:
                params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_FIRST_PERFORMANCE_TODAY + SPUtils.get(PerformanceActivity.this, "UserToken", ""));
                break;
            case 2:
                params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_SECOND_PERFORMANCE_TODAY + SPUtils.get(PerformanceActivity.this, "UserToken", ""));
                break;
        }
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                PerformanceOutModel model =  JSON.parseObject(result.toString(), PerformanceOutModel.class);
                if (model.getStatus() == 0) {
                    String performanceThisMonth =  model.getData().getCommisonAmount().toString();
                    todayPerformanceTv.setText("当日预估\n" +performanceThisMonth + "元");
                    todayOrderCountTv.setText("订单数量\n"+model.getData().getTotalCount());
                } else {
                    todayPerformanceTv.setText("当日预估\n0元");
                    todayOrderCountTv.setText("订单数量\n0");
                    Toast.makeText(PerformanceActivity.this, "获取当日预估效益失败！", Toast.LENGTH_SHORT).show();
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                todayPerformanceTv.setText("当日预估\n0元");
                todayOrderCountTv.setText("订单数量\n0");
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
     * 取本月绩效
     */
    private void callNetGetPerformanceThisMonth() {
        RequestParams params = null;
        switch (position) {
            case 0:
                params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_PERFORMANCE_THIS_MONTH + SPUtils.get(PerformanceActivity.this, "UserToken", ""));
                break;
            case 1:
                params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_FIRST_PERFORMANCE_THIS_MONTH + SPUtils.get(PerformanceActivity.this, "UserToken", ""));
                break;
            case 2:
                params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_SECOND_PERFORMANCE_THIS_MONTH + SPUtils.get(PerformanceActivity.this, "UserToken", ""));
                break;
        }
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                PerformanceOutModel model =  JSON.parseObject(result.toString(), PerformanceOutModel.class);
                if (model.getStatus() == 0) {
                    String performanceThisMonth =  model.getData().getCommisonAmount().toString();
                    thisPerformanceTv.setText("本月预估\n" +performanceThisMonth + "元");
                    thisOrderCountTv.setText("订单数量\n"+model.getData().getTotalCount());
                } else {
                    thisPerformanceTv.setText("本月预估\n0元");
                    thisOrderCountTv.setText("订单数量\n0");
                    Toast.makeText(PerformanceActivity.this, "获取本月预估效益失败！", Toast.LENGTH_SHORT).show();
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
        RequestParams params = null;
        switch (position) {
            case 0:
                params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_PERFORMANCE_LAST_MONTH + SPUtils.get(PerformanceActivity.this, "UserToken", ""));
                break;
            case 1:
                params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_FIRST_PERFORMANCE_LAST_MONTH + SPUtils.get(PerformanceActivity.this, "UserToken", ""));
                break;
            case 2:
                params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_SECOND_PERFORMANCE_LAST_MONTH + SPUtils.get(PerformanceActivity.this, "UserToken", ""));
                break;
        }
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                PerformanceOutModel model =  JSON.parseObject(result.toString(), PerformanceOutModel.class);
                if (model.getStatus() == 0) {
                    String performanceLastMonth = model.getData().getCommisonAmount().toString();
                    lastPerformanceTv.setText("上月预估\n" + performanceLastMonth + "元");
                    lastOrderCountTv.setText("订单数量\n" + model.getData().getTotalCount());
                } else {
                    lastPerformanceTv.setText("上月预估\n0元");
                    lastOrderCountTv.setText("订单数量\n0");
                    Toast.makeText(PerformanceActivity.this, "获取上月预估效益失败！", Toast.LENGTH_SHORT).show();
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
            }
            @Override
            public void onFinished() {
            }
        });
    }
}
