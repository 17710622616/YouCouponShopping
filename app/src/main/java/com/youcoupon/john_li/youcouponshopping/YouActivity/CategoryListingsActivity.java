package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youcoupon.john_li.youcouponshopping.MerchandiseDetialActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.MaterialItemAdapter;
import com.youcoupon.john_li.youcouponshopping.YouModel.MainClassifyOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.MaterialClassifyItemOutModel;
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
    //private RadioGroup mRg;
    private GridView mGv;
    private RefreshLayout mRefreshLayout;
    private TabLayout mTabLayout;

    //private String favoriteId;
    private MainClassifyOutModel.DataBean.ResultsBean mClassifyTitleModel;
    private String isDefault = "true";
    private long pageNo = 1;
    private long pageSize = 10;
    private List<MaterialClassifyItemOutModel.DataBean.MaterialItemModel> mMaterialItemModelList;
    private MaterialItemAdapter mMetrialItemAdapter;
    private boolean isFirst = true;
    private int position;
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
        //mRg = findViewById(R.id.category_listing_rg);
        mGv = findViewById(R.id.category_listing_gv);
        mRefreshLayout = findViewById(R.id.category_listing_srl);
        mTabLayout = findViewById(R.id.tab_layout);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            headView.setHeadHight();
        }
        mRefreshLayout.setEnableAutoLoadmore(true);//是否启用列表惯性滑动到底部时自动加载更多
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
    }

    @Override
    public void setListener() {
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //和最大的数据比较
                if (isDefault.equals("true")){
                    Toast.makeText(CategoryListingsActivity.this, "没有更多数据了~", Toast.LENGTH_SHORT).show();
                    mRefreshLayout.finishRefresh();
                    mRefreshLayout.finishLoadmore();
                } else {
                    pageNo ++;
                    callNetGetItemList();
                }
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNo = 1;
                mMaterialItemModelList.clear();
                callNetGetItemList();
            }
        });
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CategoryListingsActivity.this, MerchandiseDetialActivity.class);
                intent.putExtra("MerchandiseModel", JSON.toJSONString(mMaterialItemModelList.get(position)));
                startActivity(intent);
            }
        });

        mTabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    position = tab.getPosition();
                    mRefreshLayout.autoRefresh();
                }
                if (mMaterialItemModelList != null) {
                    mMaterialItemModelList.clear();
                }
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
        Intent intent = getIntent();
        mClassifyTitleModel = JSON.parseObject(intent.getStringExtra("ClassifyModel"), MainClassifyOutModel.DataBean.ResultsBean.class);
        position = intent.getIntExtra("Positon", 0);
        if (mClassifyTitleModel != null) {
            headView.setTitle(String.valueOf(mClassifyTitleModel.getActivity_title()));
        }
        headView.setLeft(this);

        mMaterialItemModelList = new ArrayList<>();
        mMetrialItemAdapter = new MaterialItemAdapter(mMaterialItemModelList, this);
        mGv.setAdapter(mMetrialItemAdapter);


        /**动态添加值**/
        for (int i = 0; i < mClassifyTitleModel.getChildItem().size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab());
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setText(mClassifyTitleModel.getChildItem().get(i).getChild_activity_title());
            /*if (i == 0) {
                tab.setIcon(R.mipmap.clothing);
            } else {
                tab.setIcon(R.mipmap.furniture);
            }*/
            /*if (tab != null) {
                tab.setCustomView(R.layout.tab_wash_type);
                View view = tab.getCustomView();
                TextView textView = (TextView) view.findViewById(R.id.tab_wash_type_tv);
                ImageView imageView = (ImageView) view.findViewById(R.id.tab_wash_type_iv);
                textView.setText(mWashingCategoryList.get(i).getName());
                String imgUrl = mWashingCategoryList.get(i).getImg();
                if (!mWashingCategoryList.get(i).getImg().contains("https")) {
                    imgUrl = mWashingCategoryList.get(i).getImg().replace("http", "https");
                }
                Glide.with(this).load(imgUrl).apply(options).into(imageView);
                //tab.setIcon(R.mipmap.ic_launcher);
            }*/
        }

        mTabLayout.getTabAt(position).select();
        if (position == 0) {
            mRefreshLayout.autoRefresh();
        }

        if (mClassifyTitleModel.getChildItem().size() == 1) {
            mTabLayout.setVisibility(View.GONE);
        }
    }

    private void callNetGetItemList() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageno", String.valueOf(pageNo));
        paramsMap.put("pagesize", String.valueOf(pageSize));
        paramsMap.put("materialId", String.valueOf(mClassifyTitleModel.getChildItem().get(position).getChild_activity_id()));
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.MATERIAL_CLASSIFY_ITEM + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MaterialClassifyItemOutModel model = JSON.parseObject(result, MaterialClassifyItemOutModel.class);
                if (model.getStatus() == 0) {
                    isDefault = model.getData().getIs_default();
                    mMaterialItemModelList.addAll(model.getData().getMap_data());
                    mMetrialItemAdapter.notifyDataSetChanged();
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
