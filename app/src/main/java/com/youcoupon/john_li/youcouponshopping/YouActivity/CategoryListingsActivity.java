package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youcoupon.john_li.youcouponshopping.MerchandiseDetialActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.FavoritesItemAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.MainClassifyAdapter;
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteItemOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by John_Li on 17/4/2019.
 */

public class CategoryListingsActivity extends BaseActivity implements View.OnClickListener{
    private YouHeadView headView;
    private RadioGroup mRg;
    private GridView mGv;
    private RefreshLayout mRefreshLayout;

    private String favoriteId;
    private long totalCount = 20;
    private long pageNo = 1;
    private long pageSize = 10;
    private List<FavoriteItemOutModel.DataBean.FavoriteItemModel> mFavoriteItemModelList;
    private FavoritesItemAdapter mFavoritesItemAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_listings);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        headView = findViewById(R.id.category_listing_head);
        mRg = findViewById(R.id.category_listing_rg);
        mGv = findViewById(R.id.category_listing_gv);
        mRefreshLayout = findViewById(R.id.category_listing_srl);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            headView.setHeadHight();
        }
        mRefreshLayout.setEnableAutoLoadmore(false);//是否启用列表惯性滑动到底部时自动加载更多
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
    }

    @Override
    public void setListener() {
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //和最大的数据比较
                if (pageSize * (pageNo + 1) > totalCount){
                    Toast.makeText(CategoryListingsActivity.this, "没有更多数据了~", Toast.LENGTH_SHORT).show();
                    mRefreshLayout.finishRefresh();
                    mRefreshLayout.finishLoadmore();
                } else {
                    pageNo ++;
                    callNetGetFavorites();
                }
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNo = 1;
                mFavoriteItemModelList.clear();
                callNetGetFavorites();
            }
        });
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CategoryListingsActivity.this, MerchandiseDetialActivity.class);
                intent.putExtra("MerchandiseModel", JSON.toJSONString(mFavoriteItemModelList.get(position)));
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        favoriteId = intent.getStringExtra("FavoriteId");
        headView.setTitle(intent.getStringExtra("FavoriteName"));
        headView.setLeft(this);

        mFavoriteItemModelList = new ArrayList<>();
        mFavoritesItemAdapter = new FavoritesItemAdapter(mFavoriteItemModelList, this);
        mGv.setAdapter(mFavoritesItemAdapter);
        mRefreshLayout.autoRefresh();
    }

    private void callNetGetFavorites() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageno", String.valueOf(pageNo));
        paramsMap.put("pagesize", String.valueOf(pageSize));
        paramsMap.put("favoritesid", favoriteId);
        paramsMap.put("orderby", "1");
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
                    Toast.makeText(getApplicationContext(), "獲取数据失敗！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "獲取数据失敗！", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_left:
                finish();
                break;
        }
    }
}
