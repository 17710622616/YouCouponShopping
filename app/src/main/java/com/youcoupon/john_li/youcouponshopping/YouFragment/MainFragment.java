package com.youcoupon.john_li.youcouponshopping.YouFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.fastjson.JSON;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OnNavigationBarListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youcoupon.john_li.youcouponshopping.LoginActivity;
import com.youcoupon.john_li.youcouponshopping.MerchandiseDetialActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouActivity.CategoryListingsActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.SearchResultActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.WebH5Activity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.XinrenH5Activity;
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
    private RecyclerView mGridView;
    private RefreshLayout mRefreshLayout;
    private Toolbar mToolbar;
    private ImageView activity01Iv, activity02Iv;
    private LinearLayout searchLL;
    private ImageView mainIv;

    // 猜你喜歡商品列表是否為最後一頁
    private String isDefault = "true";
    // 熱賣商品列表是否為最後一頁
    private String isHotDefault = "true";
    //
    private long totalCount;
    private long pageNo = 1;
    private long pageSize = 10;
    // 热门骨架屏
    RecyclerViewSkeletonScreen.Builder hotBuilder;
    private SkeletonScreen hotSkeletonScreen;
    // 分类骨架屏
    RecyclerViewSkeletonScreen.Builder classifyhotBuilder;
    private SkeletonScreen classifySkeletonScreen;
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
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setImageScaleType(ImageView.ScaleType.FIT_XY).setLoadingDrawableId(R.mipmap.activity_floating).setFailureDrawableId(R.mipmap.activity_floating).build();

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

        mainIv = (ImageView) findViewById(R.id.main_iv);
        activity01Iv = (ImageView) findViewById(R.id.main_activity_01);
        activity02Iv = (ImageView) findViewById(R.id.main_activity_02);
        mListView = (NoScrollGridView) findViewById(R.id.main_lv);
        mGridView = (RecyclerView) findViewById(R.id.main_classify);
        searchLL = (LinearLayout) findViewById(R.id.main_search_ll);

        mRefreshLayout.setEnableAutoLoadmore(true);//是否启用列表惯性滑动到底部时自动加载更多
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        //mRefreshLayout.setFooterMaxDragRate(0.01f);//触发加载距离 与 FooterHeight 的比率1.0.4
        // 设置header的高度
        // mRefreshLayout.setHeaderHeightPx((int)(BSSMCommonUtils.getDeviceWitdh(getActivity()) / 4.05));//Header标准高度（显示下拉高度>=标准高度 触发刷新）
    }

    private void setListener() {
        searchLL.setOnClickListener(this);
        mainIv.setOnClickListener(this);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //和最大的数据比较
                if (isDefault.equals("true")){
                    mainIv.setVisibility(View.GONE);
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
                mainIv.setVisibility(View.VISIBLE);
                pageNo = 1;
                mMapGuessLikeList.clear();
                callNetGetMerchandiseList();
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

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            boolean isSlidingToLast = false;
            private boolean scrollFlag = false;// 标记是否滑动
            private int lastVisibleItemPosition = 0;// 标记上次滑动位置
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE://停止滚动时
                        scrollFlag = false;
                        //判断滚动到底部
                        if (mListView.getLastVisiblePosition() == (mListView.getCount() - 1)) {
                            Log.d("665","666");

                        }
                        //判断滚动到顶部
                        if (mListView.getFirstVisiblePosition() == 0) {
                            Log.d("665","666");
                        }


                        int lastVisibleItem = view.getLastVisiblePosition();
                        int totalItemCount = view.getChildCount();

                        if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                            mRefreshLayout.autoLoadmore();
                        }
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL://滚动时
                        scrollFlag = true;
                        break;
                    case SCROLL_STATE_FLING://惯性滚动时
                        scrollFlag = false;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (scrollFlag) {
                    if (firstVisibleItem > lastVisibleItemPosition) {
                        //上滑
                        isSlidingToLast = true;
                    } else if (firstVisibleItem < lastVisibleItemPosition) {
                        //下滑
                        isSlidingToLast = false;
                    } else {
                        return;
                    }
                    lastVisibleItemPosition = firstVisibleItem;
                }
            }
        });
    }


    private void initData() {
        mActivityList = new ArrayList<>();

        // 分类
        mainClassifyList = new ArrayList<>();
        mMainClassifyAdapter = new MainClassifyAdapter(mainClassifyList, getActivity());
        //mGridView.setLayoutManager(new GridLayoutManager(getActivity(), 5, LinearLayoutManager.VERTICAL,false));
        mGridView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        classifyhotBuilder =  Skeleton.bind(mGridView);
        classifySkeletonScreen = classifyhotBuilder
                .adapter(mMainClassifyAdapter)//设置实际adapter
                .shimmer(true)//是否开启动画
                .angle(30)//shimmer的倾斜角度
                .color(R.color.colorLoadingGray)//shimmer的颜色
                .frozen(false)//true则表示显示骨架屏时，RecyclerView不可滑动，否则可以滑动
                .duration(1200)//动画时间，以毫秒为单位
                .count(5)//显示骨架屏时item的个数
                .load(R.layout.item_main_classify_skeleton)//骨架屏UI
                .show(); //default count is 10

        mMainClassifyAdapter.setOnItemClickListenr(new MainClassifyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), CategoryListingsActivity.class);
                intent.putExtra("ClassifyModel", JSON.toJSONString(mainClassifyList.get(position)));
                intent.putExtra("Positon", 0);
                startActivity(intent);
            }
        });

        // 猜你喜欢
        mMapGuessLikeList = new ArrayList<>();
        mMaterialItemAdapter = new MaterialItemAdapter(mMapGuessLikeList, getActivity());
        mListView.setAdapter(mMaterialItemAdapter);

        // 热门
        mainHotItemModelList = new ArrayList<>();
        mMainHotRefreshAdapter = new MainHotRefreshAdapter(getActivity(), mainHotItemModelList);
        mHotListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        hotBuilder = Skeleton.bind(mHotListView);
        hotSkeletonScreen = hotBuilder
                .adapter(mMainHotRefreshAdapter)//设置实际adapter
                .shimmer(true)//是否开启动画
                .angle(30)//shimmer的倾斜角度
                .color(R.color.colorLoadingGray)//shimmer的颜色
                .frozen(true)//true则表示显示骨架屏时，RecyclerView不可滑动，否则可以滑动
                .duration(1200)//动画时间，以毫秒为单位
                .count(3)//显示骨架屏时item的个数
                .load(R.layout.item_main_hot_skeleton)//骨架屏UI
                .show(); //default count is 10
        mMainHotRefreshAdapter.setOnItemClickListenr(new MainHotRefreshAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), MerchandiseDetialActivity.class);
                intent.putExtra("MerchandiseModel", JSON.toJSONString(mainHotItemModelList.get(position)));
                startActivity(intent);
            }
        });

        // 第一次添加了骨架屏所以不刷新
        callNetGetTitleList();
        callNetGetHotMerchandiseList();
        callNetGetActivitList();
        callNetGetMerchandiseList();
        //mRefreshLayout.autoRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_search_ll:
                startActivity(new Intent(getActivity(), SearchResultActivity.class));
                break;
            case R.id.main_iv:
                Intent intent = new Intent(getActivity(), XinrenH5Activity.class);
                intent.putExtra("title", "新人免单");
                intent.putExtra("webUrl", "http://www.youcoupon.top:8082/xinrenmiandan/index.html");
                startActivity(intent);
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
                    classifySkeletonScreen.hide();
                } else {
                    Toast.makeText(getApplicationContext(), "獲取定向活動標題列表失敗！", Toast.LENGTH_SHORT).show();
                    classifySkeletonScreen.hide();
                    classifyhotBuilder.shimmer(false).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "獲取定向活動標題列表失敗！", Toast.LENGTH_SHORT).show();
                classifySkeletonScreen.hide();
                classifyhotBuilder.shimmer(false).show();
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
                    hotSkeletonScreen.hide();
                } else {
                    Toast.makeText(getApplicationContext(), "獲取熱賣数据失敗！", Toast.LENGTH_SHORT).show();
                    hotSkeletonScreen.hide();
                    hotBuilder.shimmer(false).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "獲取熱賣数据失敗！", Toast.LENGTH_SHORT).show();
                hotSkeletonScreen.hide();
                hotBuilder.shimmer(false).show();
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
