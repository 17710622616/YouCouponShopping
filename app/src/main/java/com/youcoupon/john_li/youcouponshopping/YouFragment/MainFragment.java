package com.youcoupon.john_li.youcouponshopping.YouFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youcoupon.john_li.youcouponshopping.MerchandiseDetialActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.MainClassifyAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.FavoritesItemAdapter;
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteItemOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.MerchandiseOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;
import com.youcoupon.john_li.youcouponshopping.YouView.NoScrollGridView;
import com.youcoupon.john_li.youcouponshopping.YouView.NoScrollListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页碎片
 * Created by John_Li on 25/5/2018.
 */

public class MainFragment extends LazyLoadFragment implements View.OnClickListener{
    public static String TAG = MainFragment.class.getName();
    private NoScrollGridView mListView;
    private NoScrollGridView mGridView;
    private RefreshLayout mRefreshLayout;

    private long totalCount = 20;
    private long pageNo = 1;
    private long pageSize = 10;
    private List<FavoriteOutModel.DataBean.Favorites> favoritesList;
    private List<FavoriteItemOutModel.DataBean.FavoriteItemModel> mFavoriteItemModelList;
    private MainClassifyAdapter mMainClassifyAdapter;
    private FavoritesItemAdapter mFavoritesItemAdapter;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_main);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        mRefreshLayout = (RefreshLayout) findViewById(R.id.main_srl);
        mListView = (NoScrollGridView) findViewById(R.id.main_lv);
        mGridView = (NoScrollGridView) findViewById(R.id.main_classify);

        mRefreshLayout.setEnableAutoLoadmore(false);//是否启用列表惯性滑动到底部时自动加载更多
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        // 设置header的高度
        // mRefreshLayout.setHeaderHeightPx((int)(BSSMCommonUtils.getDeviceWitdh(getActivity()) / 4.05));//Header标准高度（显示下拉高度>=标准高度 触发刷新）
    }

    private void setListener() {
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //和最大的数据比较
                if (pageSize * (pageNo + 1) > totalCount){
                    Toast.makeText(getActivity(), "没有更多数据了~", Toast.LENGTH_SHORT).show();
                    mRefreshLayout.finishRefresh();
                    mRefreshLayout.finishLoadmore();
                } else {
                    pageNo ++;
                    callNetGetMerchandiseList();
                }
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mFavoriteItemModelList.clear();
                callNetGetMerchandiseList();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MerchandiseDetialActivity.class);
                intent.putExtra("MerchandiseModel", JSON.toJSONString(mFavoriteItemModelList.get(position)));
                startActivity(intent);
            }
        });
    }


    private void initData() {
        favoritesList = new ArrayList<>();
        mFavoriteItemModelList = new ArrayList<>();
        mMainClassifyAdapter = new MainClassifyAdapter(favoritesList, getActivity());
        mGridView.setAdapter(mMainClassifyAdapter);
        mFavoritesItemAdapter = new FavoritesItemAdapter(mFavoriteItemModelList, getActivity());
        mListView.setAdapter(mFavoritesItemAdapter);

        callNetGetTitleList();
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void callNetGetTitleList() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageno", "1");
        paramsMap.put("pagesize", "20");
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.FAVORITES_LIST + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                FavoriteOutModel model = JSON.parseObject(result, FavoriteOutModel.class);
                if (model.getStatus() == 0) {
                    for (int i = 0; i < model.getData().getResults().size(); i++) {
                        favoritesList.add(model.getData().getResults().get(i));
                        mMainClassifyAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "獲取產品標題列表失敗！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "獲取產品標題列表失敗！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void callNetGetMerchandiseList() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageno", String.valueOf(pageNo));
        paramsMap.put("pagesize", String.valueOf(pageSize));
        paramsMap.put("favoritesid", "17481407");
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.FAVORITES_ITEM_LIST + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                FavoriteItemOutModel model = JSON.parseObject(result, FavoriteItemOutModel.class);
                if (model.getStatus() == 0) {
                    totalCount = model.getData().getTotal_results();
                    mFavoriteItemModelList.addAll(model.getData().getResults());
                    mFavoritesItemAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "獲取每日上新数据失敗！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "獲取每日上新数据失敗！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
            }
        });
    }
}
