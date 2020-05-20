package com.youcoupon.john_li.youcouponshopping.YouActivity;

import com.alibaba.fastjson.JSON;
import com.youcoupon.john_li.youcouponshopping.LoginActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.SmartWalletRecordRefreshAdapter;
import com.youcoupon.john_li.youcouponshopping.YouModel.WithDrawalDetialModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.SPUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class WalletRecrodActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecycleView;

    // 總數
    private long totalCount;
    // 提現明細集合
    private List<WithDrawalDetialModel.DataBean.WalletRecordListBean> withDrawalInsideModelList;
    private SmartWalletRecordRefreshAdapter mSmartWalletRecordRefreshAdapter;
    private int pageNo = 1;
    private int pageSize = 10;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_record);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        headView = findViewById(R.id.wallet_record_head);
        mRefreshLayout = findViewById(R.id.wallet_record_srl);
        mRecycleView = findViewById(R.id.wallet_record_lv);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            headView.setHeadHight();
        }
        mRefreshLayout.setEnableAutoLoadmore(false);//是否启用列表惯性滑动到底部时自动加载更多
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        // 设置header的高度
        mRefreshLayout.setHeaderHeightPx((int)(YouCommonUtils.getDeviceWitdh(this) / 4.05));//Header标准高度（显示下拉高度>=标准高度 触发刷新）
    }

    @Override
    public void setListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                withDrawalInsideModelList.clear();
                pageNo = 1;
                if (!SPUtils.get(WalletRecrodActivity.this, "UserToken", "").equals("")) {
                    callNetChangeData();
                } else {
                    startActivityForResult(new Intent(WalletRecrodActivity.this, LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //和最大的数据比较
                if (pageSize * (pageNo + 1) > totalCount){
                    Toast.makeText(WalletRecrodActivity.this, "沒有更多數據了誒~", Toast.LENGTH_SHORT).show();
                    mRefreshLayout.finishRefresh();
                    mRefreshLayout.finishLoadmore();
                } else {
                    pageNo ++;
                    if (!SPUtils.get(WalletRecrodActivity.this, "UserToken", "").equals("")) {
                        callNetChangeData();
                    } else {
                        startActivityForResult(new Intent(WalletRecrodActivity.this, LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                    }
                }
            }
        });

    }

    @Override
    public void initData() {
        Intent intent = getIntent();

        headView.setLeft(this);
        headView.setTitle("钱包记录");
        withDrawalInsideModelList = new ArrayList<>();
        mSmartWalletRecordRefreshAdapter = new SmartWalletRecordRefreshAdapter(this, withDrawalInsideModelList);
        mSmartWalletRecordRefreshAdapter.setOnItemClickListenr(new SmartWalletRecordRefreshAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 点击事件
            }
        });
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mSmartWalletRecordRefreshAdapter);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_left:
                finish();
                break;
        }
    }
    private void callNetChangeData() {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_WALLET_RECORDE + SPUtils.get(this, "UserToken", ""));
        params.addQueryStringParameter("pageSize", String.valueOf(pageSize));
        params.addQueryStringParameter("pageNo", String.valueOf(pageNo));
        params.addQueryStringParameter("token", (String) SPUtils.get(WalletRecrodActivity.this, "UserToken", ""));
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                WithDrawalDetialModel model = JSON.parseObject(result, WithDrawalDetialModel.class);
                if (model.getStatus() == 0) {
                    List<WithDrawalDetialModel.DataBean.WalletRecordListBean> list = model.getData().getWalletRecordList();
                    totalCount = model.getData().getTotalCount();
                    withDrawalInsideModelList.addAll(list);
                }  else if (model.getStatus() == 10000) {
                    SPUtils.put(WalletRecrodActivity.this, "UserToken", "");
                    startActivityForResult(new Intent(WalletRecrodActivity.this, LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }else {
                    Toast.makeText(WalletRecrodActivity.this, "钱包记录获取失败╮(╯▽╰)╭" + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof SocketTimeoutException) {
                    Toast.makeText(WalletRecrodActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WalletRecrodActivity.this, getString(R.string.request_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                refreshContentsList();
            }
        });
    }

    private void refreshContentsList() {
        mSmartWalletRecordRefreshAdapter.notifyDataSetChanged();
        mRefreshLayout.finishLoadmore();
        mRefreshLayout.finishRefresh();
    }
}
