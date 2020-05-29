package com.youcoupon.john_li.youcouponshopping.YouFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.fastjson.JSON;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OnNavigationBarListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youcoupon.john_li.youcouponshopping.MerchandiseDetialActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouActivity.CategoryListingsActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.SearchResultActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.WebH5Activity;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.MainClassifyAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.MainHotRefreshAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.MaterialItemAdapter;
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteItemOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.MainActivityOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.MainClassifyOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.MaterialClassifyItemOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.MerchandiseOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;
import com.youcoupon.john_li.youcouponshopping.YouView.NoScrollGridView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.GridLayout.HORIZONTAL;

/**
 * 首页碎片
 * Created by John_Li on 25/5/2018.
 */

public class MainFragment extends LazyLoadFragment implements View.OnClickListener{
    public static String TAG = MainFragment.class.getName();
    private RecyclerView mHotListView;
    private NoScrollGridView mListView;
    private NoScrollGridView mGridView;
    private RefreshLayout mRefreshLayout;
    private Toolbar mToolbar;
    private ImageView activity01Iv, activity02Iv;
    private LinearLayout searchLL;
    private TextView mainTv;

    // 猜你喜歡商品列表是否為最後一頁
    private String isDefault = "true";
    // 熱賣商品列表是否為最後一頁
    private String isHotDefault = "true";
    //
    private long totalCount;
    private long pageNo = 1;
    private long pageSize = 10;
    // 定向活動標題列表
    private List<MainClassifyOutModel.DataBean.ResultsBean> mainClassifyList;
    private List<MaterialClassifyItemOutModel.DataBean.MaterialItemModel> mainHotItemModelList;
    // 為您推薦列表
    private List<MaterialClassifyItemOutModel.DataBean.MaterialItemModel> mMapGuessLikeList;
    // 活動
    private List<MainActivityOutModel.DataBean.ActivityListBean> mActivityList;
    private MainHotRefreshAdapter mMainHotRefreshAdapter;
    private MainClassifyAdapter mMainClassifyAdapter;
    private MaterialItemAdapter mMaterialItemAdapter;
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setImageScaleType(ImageView.ScaleType.FIT_XY).setLoadingDrawableId(R.mipmap.activity_bg01).setFailureDrawableId(R.mipmap.activity_bg01).build();

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_main);
        initBar();
        initView();
        setListener();
        initData();
    }

    private void initBar() {
        ImmersionBar.with(getActivity()).titleBar(mToolbar)
                .setOnNavigationBarListener(new OnNavigationBarListener() {
                    @Override
                    public void onNavigationBarChange(boolean show) {
                        //initView();
                        Toast.makeText(MainFragment.this.getApplicationContext(), "导航栏" + (show ? "显示了" : "隐藏了"), Toast.LENGTH_SHORT).show();
                    }
                }).init();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mRefreshLayout = (RefreshLayout) findViewById(R.id.main_srl);
        mHotListView = (RecyclerView) findViewById(R.id.main_hot_lv);
        mHotListView.setHasFixedSize(true);

        mainTv = (TextView) findViewById(R.id.main_tv);
        activity01Iv = (ImageView) findViewById(R.id.main_activity_01);
        activity02Iv = (ImageView) findViewById(R.id.main_activity_02);
        mListView = (NoScrollGridView) findViewById(R.id.main_lv);
        mGridView = (NoScrollGridView) findViewById(R.id.main_classify);
        searchLL = (LinearLayout) findViewById(R.id.main_search_ll);

        mRefreshLayout.setEnableAutoLoadmore(false);//是否启用列表惯性滑动到底部时自动加载更多
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        // 设置header的高度
        // mRefreshLayout.setHeaderHeightPx((int)(BSSMCommonUtils.getDeviceWitdh(getActivity()) / 4.05));//Header标准高度（显示下拉高度>=标准高度 触发刷新）
    }

    private void setListener() {
        searchLL.setOnClickListener(this);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //和最大的数据比较
                if (isDefault.equals("true")){
                    mainTv.setVisibility(View.GONE);
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
                mainTv.setVisibility(View.VISIBLE);
                pageNo = 1;
                mMapGuessLikeList.clear();
                callNetGetMerchandiseList();
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CategoryListingsActivity.class);
                intent.putExtra("ClassifyModel", JSON.toJSONString(mainClassifyList.get(position)));
                intent.putExtra("Positon", 0);
                startActivity(intent);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MerchandiseDetialActivity.class);
                intent.putExtra("MerchandiseModel", JSON.toJSONString(mMapGuessLikeList.get(position)));
                startActivity(intent);
            }
        });
    }


    private void initData() {
        mainClassifyList = new ArrayList<>();
        mMapGuessLikeList = new ArrayList<>();
        mActivityList = new ArrayList<>();

        mMainClassifyAdapter = new MainClassifyAdapter(mainClassifyList, getActivity());
        mGridView.setAdapter(mMainClassifyAdapter);
        mMaterialItemAdapter = new MaterialItemAdapter(mMapGuessLikeList, getActivity());
        mListView.setAdapter(mMaterialItemAdapter);

        int spanCount = 1; // 只显示一行
        mainHotItemModelList = new ArrayList<>();
        mMainHotRefreshAdapter = new MainHotRefreshAdapter(getActivity(), mainHotItemModelList);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, HORIZONTAL);
        mHotListView.setLayoutManager(layoutManager);
        mHotListView.setAdapter(mMainHotRefreshAdapter);

        mMainHotRefreshAdapter.setOnItemClickListenr(new MainHotRefreshAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), MerchandiseDetialActivity.class);
                intent.putExtra("MerchandiseModel", JSON.toJSONString(mainHotItemModelList.get(position)));
                startActivity(intent);
            }
        });

        callNetGetTitleList();
        callNetGetHotMerchandiseList();
        callNetGetActivitList();
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_search_ll:
                startActivity(new Intent(getActivity(), SearchResultActivity.class));
                break;
        }
    }

    /**
     * 分类标题
     */
    private void callNetGetTitleList() {
        Map<String, String> paramsMap = new HashMap<>();
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.MATERIAL_CLASSIFY );
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MainClassifyOutModel model = JSON.parseObject(result, MainClassifyOutModel.class);
                if (model.getStatus() == 0) {
                    mainClassifyList.addAll(model.getData().getResults());
                    mMainClassifyAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "獲取定向活動標題列表失敗！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "獲取定向活動標題列表失敗！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 猜你喜欢
     */
    private void callNetGetMerchandiseList() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageno", String.valueOf(pageNo));
        paramsMap.put("pagesize", String.valueOf(pageSize));
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GUESS_LIKE_LIST + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MaterialClassifyItemOutModel model = JSON.parseObject(result, MaterialClassifyItemOutModel.class);
                if (model.getStatus() == 0) {
                    isDefault = model.getData().getIs_default();
                    mMapGuessLikeList.addAll(model.getData().getMap_data());
                    mMaterialItemAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "获取猜你喜欢数据失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "获取猜你喜欢数据失败！", Toast.LENGTH_SHORT).show();
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

    /**
     * 热卖
     */
    private void callNetGetHotMerchandiseList() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageno", String.valueOf(pageNo));
        paramsMap.put("pagesize", String.valueOf(pageSize));
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.HOT_MERCHANDISE_LIST + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MaterialClassifyItemOutModel model = JSON.parseObject(result, MaterialClassifyItemOutModel.class);
                if (model.getStatus() == 0) {
                    isHotDefault = model.getData().getIs_default();
                    mainHotItemModelList.addAll(model.getData().getMap_data());
                    mMainHotRefreshAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "獲取熱賣数据失敗！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "獲取熱賣数据失敗！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
            }
        });
    }

    /**
     * 获取活動
     */
    private void callNetGetActivitList() {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.ACTIVITY_LIST);
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MainActivityOutModel model = JSON.parseObject(result, MainActivityOutModel.class);
                if (model.getStatus() == 0) {
                    //totalActivityCount = model.getData().getResultCout();
                    mActivityList.addAll(model.getData().getActivityList());
                    for (int i = 0; i < mActivityList.size(); i++) {
                        switch (i) {
                            case 0:
                                x.image().bind(activity01Iv, mActivityList.get(0).getImgUrl(), options);
                                activity01Iv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getActivity(), WebH5Activity.class);
                                        intent.putExtra("title",mActivityList.get(0).getBannerName());
                                        intent.putExtra("webUrl",mActivityList.get(0).getActivityLink());
                                        startActivity(intent);
                                    }
                                });
                                break;
                            case 1:
                                x.image().bind(activity02Iv, mActivityList.get(1).getImgUrl(), options);
                                activity02Iv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getActivity(), WebH5Activity.class);
                                        intent.putExtra("title",mActivityList.get(1).getBannerName());
                                        intent.putExtra("webUrl",mActivityList.get(1).getActivityLink());
                                        startActivity(intent);
                                    }
                                });
                                break;
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "获取活动列表失敗！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "获取活动列表失敗！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
            }
        });
    }

    /**
     * 打开H5页面
     * @param url
     */
    private void openH5(String url) {
        // 打开网址 这个是通过打开android自带的浏览器进行的打开网址
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // 网址正确 跳转成功
            startActivity(intent);
        } else {
            //网址不正确 跳转失败 提示错误
            Toast.makeText(getActivity(), "网址输入错误，请重新输入！", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
