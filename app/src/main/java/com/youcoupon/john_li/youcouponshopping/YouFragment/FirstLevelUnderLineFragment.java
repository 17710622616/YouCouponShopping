package com.youcoupon.john_li.youcouponshopping.YouFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youcoupon.john_li.youcouponshopping.LoginActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.SmartOrderRefreshAdapter;
import com.youcoupon.john_li.youcouponshopping.YouModel.UserOrderOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.SPUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class FirstLevelUnderLineFragment extends LazyLoadFragment {
    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecycleView;
    private List<UserOrderOutModel.DataBean.OrderListBean> orderList;
    private SmartOrderRefreshAdapter mSmartOrderRefreshAdapter;
    private LinearLayout noOrderLL;
    private RadioGroup orderTypeRg;
    private RadioButton allRb, paidRb, settledRb, expireRb;
    private int orderType = 0;

    // 每頁加載數量
    private int pageSize = 10;
    // 頁數
    private int pageNo = 1;
    // 車輛總數
    private long totolCarCount = 30;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_order_list);
        ImmersionBar.with(getActivity()).init();
        initView();
        setListener();
        initData();
    }

    private void initView() {
        mRefreshLayout = (RefreshLayout) findViewById(R.id.order_list_srl);
        mRecycleView = (RecyclerView) findViewById(R.id.order_list_lv);
        noOrderLL = (LinearLayout) findViewById(R.id.no_order_ll);
        orderTypeRg = (RadioGroup) findViewById(R.id.order_list_type);
        allRb = (RadioButton) findViewById(R.id.order_list_all);
        paidRb = (RadioButton) findViewById(R.id.order_list_all);
        settledRb = (RadioButton) findViewById(R.id.order_list_all);
        expireRb = (RadioButton) findViewById(R.id.order_list_all);
        // 设置header的高度
        mRefreshLayout.setEnableAutoLoadmore(false);//是否启用列表惯性滑动到底部时自动加载更多
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        mRefreshLayout.setHeaderHeight((int)(YouCommonUtils.getDeviceWitdh(getActivity()) / 4.05));//Header标准高度（显示下拉高度>=标准高度 触发刷新）
    }

    private void setListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                orderList.clear();
                pageNo = 1;
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    callNetGetCarList();
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //和最大的数据比较
                if (pageSize * (pageNo) > totolCarCount){
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_more_data), Toast.LENGTH_SHORT).show();
                    mRefreshLayout.finishRefresh();
                    mRefreshLayout.finishLoadmore();
                } else {
                    pageNo ++;
                    if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                        callNetGetCarList();
                    } else {
                        startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                    }
                }
            }
        });
        orderTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.order_list_all:
                        orderType = 0;
                        mRefreshLayout.autoRefresh();
                        break;
                    case R.id.order_list_paid:
                        orderType = 12;
                        mRefreshLayout.autoRefresh();
                        break;
                    case R.id.order_list_settled:
                        orderType = 3;
                        mRefreshLayout.autoRefresh();
                        break;
                    case R.id.order_list_expire:
                        orderType = 13;
                        mRefreshLayout.autoRefresh();
                        break;
                }
            }
        });
    }

    private void initData() {
        orderList = new ArrayList<>();
        mSmartOrderRefreshAdapter = new SmartOrderRefreshAdapter(getActivity(), orderList);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(mSmartOrderRefreshAdapter);
        //添加Android自带的分割线
        mRecycleView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        allRb.setChecked(true);
    }

    /**
     * 请求网络刷新数据
     */
    private void callNetGetCarList() {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_FIRST_LEVEL_ORDER_LIST);
        params.setAsJsonContent(true);
        params.addQueryStringParameter("orderStatus", String.valueOf(orderType));
        params.addQueryStringParameter("pageNo", String.valueOf(pageNo));
        params.addQueryStringParameter("pageSize", String.valueOf(pageSize));
        params.addQueryStringParameter("token", String.valueOf(SPUtils.get(getActivity().getApplicationContext(), "UserToken", "")));
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UserOrderOutModel model = JSON.parseObject(result.toString(), UserOrderOutModel.class);
                if (model.getStatus() == 0) {
                    totolCarCount = model.getData().getTotalCount();
                    List<UserOrderOutModel.DataBean.OrderListBean> orderModelsFromNet = model.getData().getOrderList();
                    orderList.addAll(orderModelsFromNet);
                    mSmartOrderRefreshAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(),  "获取订单异常", Toast.LENGTH_SHORT).show();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof SocketTimeoutException) {
                    Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.request_error), Toast.LENGTH_SHORT).show();
                }
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {
                if (orderList.size() > 0) {
                    noOrderLL.setVisibility(View.GONE);
                } else {
                    noOrderLL.setVisibility(View.VISIBLE);
                }
                mSmartOrderRefreshAdapter.refreshListView(orderList);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    mRefreshLayout.autoRefresh();
                    break;
            }
        }
    }
}