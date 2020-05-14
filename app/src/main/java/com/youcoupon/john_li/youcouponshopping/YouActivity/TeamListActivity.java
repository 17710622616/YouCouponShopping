package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youcoupon.john_li.youcouponshopping.LoginActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.OrderFgAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.SmartOrderRefreshAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.SmartTeamRefreshAdapter;
import com.youcoupon.john_li.youcouponshopping.YouFragment.FirstLevelUnderLineFragment;
import com.youcoupon.john_li.youcouponshopping.YouFragment.MyOrderFragment;
import com.youcoupon.john_li.youcouponshopping.YouFragment.SecondLevelUnderLineFragment;
import com.youcoupon.john_li.youcouponshopping.YouModel.TeamMemberOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.UserOrderOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.SPUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamListActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    public String [] sTitle = null;
    private TabLayout mTabLayout;

    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecycleView;
    private List<TeamMemberOutModel.DataBean.DataModelsBean> memberList;
    private SmartTeamRefreshAdapter mSmartTeamRefreshAdapter;
    private LinearLayout noMemberLL;
    // 下线等级
    private int level;
    // 每頁加載數量
    private int pageSize = 10;
    // 頁數
    private int pageNo = 1;
    // 車輛總數
    private long totolCarCount = 30;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);
        //,getResources().getString(R.string.giving)
        sTitle = new String[]{"Ⅰ级下线","Ⅱ级下线"};
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        headView = findViewById(R.id.member_head);
        mTabLayout = (TabLayout) findViewById(R.id.member_tabLayout);
        mRefreshLayout = (RefreshLayout) findViewById(R.id.member_list_srl);
        mRecycleView = (RecyclerView) findViewById(R.id.member_list_lv);
        noMemberLL = (LinearLayout) findViewById(R.id.no_member_ll);
        // 设置header的高度
        mRefreshLayout.setEnableAutoLoadmore(false);//是否启用列表惯性滑动到底部时自动加载更多
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        mRefreshLayout.setHeaderHeight((int)(YouCommonUtils.getDeviceWitdh(this) / 5.05));//Header标准高度（显示下拉高度>=标准高度 触发刷新）

        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[1]));
    }

    @Override
    public void setListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                level = tab.getPosition();
                memberList.clear();
                mRefreshLayout.autoRefresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                memberList.clear();
                pageNo = 1;
                if (!((String) SPUtils.get(TeamListActivity.this, "UserToken", "")).equals("")) {
                    callNetGetMemberList();
                } else {
                    startActivityForResult(new Intent(TeamListActivity.this, LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //和最大的数据比较
                if (pageSize * (pageNo) > totolCarCount){
                    Toast.makeText(TeamListActivity.this, getResources().getString(R.string.no_more_data), Toast.LENGTH_SHORT).show();
                    mRefreshLayout.finishRefresh();
                    mRefreshLayout.finishLoadmore();
                } else {
                    pageNo ++;
                    if (!((String) SPUtils.get(TeamListActivity.this, "UserToken", "")).equals("")) {
                        callNetGetMemberList();
                    } else {
                        startActivityForResult(new Intent(TeamListActivity.this, LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        headView.setTitle("成员列表");
        headView.setLeft(this);

        memberList = new ArrayList<>();
        mSmartTeamRefreshAdapter = new SmartTeamRefreshAdapter(TeamListActivity.this, memberList);
        mRecycleView.setLayoutManager(new LinearLayoutManager(TeamListActivity.this));
        mRecycleView.setAdapter(mSmartTeamRefreshAdapter);
        
        //添加Android自带的分割线
        mRecycleView.addItemDecoration(new DividerItemDecoration(TeamListActivity.this,DividerItemDecoration.VERTICAL));
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.head_left:
                finish();
                break;
        }
    }

    /**
     * 请求网络刷新数据
     */
    private void callNetGetMemberList() {
        RequestParams params = null;
        if (level == 0) {
            params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_FIRST_LEVEL_MEMBER_LIST);
        } else {
            params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_SECOND_LEVEL_MEMBER_LIST);
        }
        params.setAsJsonContent(true);
        params.addQueryStringParameter("pageNo", String.valueOf(pageNo));
        params.addQueryStringParameter("pageSize", String.valueOf(pageSize));
        params.addQueryStringParameter("token", String.valueOf(SPUtils.get(TeamListActivity.this.getApplicationContext(), "UserToken", "")));
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                TeamMemberOutModel model = JSON.parseObject(result.toString(), TeamMemberOutModel.class);
                if (model.getStatus() == 0) {
                    totolCarCount = model.getData().getTotalCount();
                    List<TeamMemberOutModel.DataBean.DataModelsBean> orderModelsFromNet = model.getData().getDataModels();
                    memberList.addAll(orderModelsFromNet);
                    mSmartTeamRefreshAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(TeamListActivity.this,  "获取成员列表异常", Toast.LENGTH_SHORT).show();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof SocketTimeoutException) {
                    Toast.makeText(TeamListActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TeamListActivity.this, getString(R.string.request_error), Toast.LENGTH_SHORT).show();
                }
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {
                if (memberList.size() > 0) {
                    noMemberLL.setVisibility(View.GONE);
                } else {
                    noMemberLL.setVisibility(View.VISIBLE);
                }
                mSmartTeamRefreshAdapter.refreshListView(memberList);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
            }
        });
    }
}
