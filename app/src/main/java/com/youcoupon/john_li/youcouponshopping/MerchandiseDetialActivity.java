package com.youcoupon.john_li.youcouponshopping;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.android.trade.page.AlibcShopPage;
import com.alibaba.fastjson.JSON;
import com.youcoupon.john_li.youcouponshopping.YouActivity.BaseActivity;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.CollapsingAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.ItemRecommendAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.SellerRecommondAdapter;
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteItemOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.ItemInfoOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.ItemRecommondOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.MerchandiseOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.SellerOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;
import com.youcoupon.john_li.youcouponshopping.YouView.NoScrollGridView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by John_Li on 31/5/2018.
 */

public class MerchandiseDetialActivity extends AppCompatActivity implements View.OnClickListener{
    private AppBarLayout appbar;
    private Toolbar articalToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ViewPager mViewPager;
    private ImageView shopIconIv;
    private LinearLayout storeLL, shoppingCartLL;
    private TextView merchandiseTitleTv, afterPriceTv, beforePriceTv, inSaleTv, merchantsTypeTv, couponValueTv, couponRemainCountTv, couponRedemptionTv, sellerNickTv, storeRatingTv, sellerRateTv, shopProvcitytV, itemDetialTv;
    private NoScrollGridView mStoreRecommonGv;
    private NoScrollGridView mItemRecommonGv;

    // 淘寶相關信息
    private Map<String, String> exParams;
    //实例化URL打开page
    AlibcBasePage detailPage;

    // 界面數據
    private List<ImageView> imgList;
    private FavoriteItemOutModel.DataBean.FavoriteItemModel mFavoriteItemModel;
    private ItemInfoOutModel.DataBean.ItemInfoModel mItemInfoModel;
    private SellerOutModel.DataBean.SellerModel mSellerModel;
    private List<SellerOutModel.DataBean.SellerModel> sellerModelList;
    private long totalCount = 20;
    private long pageNo = 1;
    private long pageSize = 10;
    private List<ItemRecommondOutModel.DataBean.ItemRecommendModel> mItemRecommendModelList;
    private CollapsingAdapter mCollapsingAdapter;
    private SellerRecommondAdapter mSellerRecommondAdapter;
    private ItemRecommendAdapter mItemRecommendAdapter;
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setLoadingDrawableId(R.mipmap.img_loading).setFailureDrawableId(R.mipmap.load_img_fail).build();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_merchandise_detial);
        initView();
        setListener();
        initData();
    }

    public void initView() {
        appbar = (AppBarLayout) findViewById(R.id.merchandise_detial_appbar);
        articalToolbar = (Toolbar) findViewById(R.id.merchandise_detial_toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.merchandise_detial_collapsing_toolbar);
        mViewPager = (ViewPager) findViewById(R.id.merchandise_detial_vp);
        shopIconIv = (ImageView) findViewById(R.id.merchandise_detial_shop_icon);
        merchandiseTitleTv = (TextView) findViewById(R.id.merchandise_detial_title);
        afterPriceTv = (TextView) findViewById(R.id.merchandise_detial_after);
        beforePriceTv = (TextView) findViewById(R.id.merchandise_detial_before);
        inSaleTv = (TextView) findViewById(R.id.merchandise_detial_in_sales);
        merchantsTypeTv = (TextView) findViewById(R.id.merchandise_detial_merchants_type);
        couponValueTv = (TextView) findViewById(R.id.merchandise_detial_coupon);
        couponRemainCountTv = (TextView) findViewById(R.id.merchandise_detial_coupon_remain_coun);
        storeLL = (LinearLayout) findViewById(R.id.merchandise_detial_store);
        shoppingCartLL = (LinearLayout) findViewById(R.id.merchandise_detial_shopping_cart);
        couponRedemptionTv = (TextView) findViewById(R.id.merchandise_detial_coupon_redemption);
        sellerNickTv = (TextView) findViewById(R.id.merchandise_detial_seller_nick);
        storeRatingTv = (TextView) findViewById(R.id.merchandise_detial_store_ratings);
        sellerRateTv = (TextView) findViewById(R.id.merchandise_detial_seller_rating);
        shopProvcitytV = (TextView) findViewById(R.id.merchandise_detial_seller_shop_provcity);
        itemDetialTv = (TextView) findViewById(R.id.merchandise_detial);
        mStoreRecommonGv = (NoScrollGridView) findViewById(R.id.merchandise_store_recommended);
        mItemRecommonGv = (NoScrollGridView) findViewById(R.id.merchandise_item_recommended);

        beforePriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        //setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
    }

    public void setListener() {
        shoppingCartLL.setOnClickListener(this);
        storeLL.setOnClickListener(this);
        couponRedemptionTv.setOnClickListener(this);
        itemDetialTv.setOnClickListener(this);
    }

    public void initData() {
        // 獲取帖文資料
        Intent intent = getIntent();
        mFavoriteItemModel = JSON.parseObject(intent.getStringExtra("MerchandiseModel"), FavoriteItemOutModel.DataBean.FavoriteItemModel.class);

        // 初始化阿里百川
        //提供给三方传递配置参数
        exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");

        // toolbar
        setSupportActionBar(articalToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  設置標題
        mCollapsingToolbarLayout.setTitle("商品詳情");//设置标题的名字
        /*mCollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置
        mCollapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER);////设置展开后标题的位置
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorPrimary));//设置展开后标题的颜色*/

        // 商品详情
        callNetGetItemInfo(mFavoriteItemModel.getNumIid());

        // 設置標題及內容
        merchandiseTitleTv.setText(mFavoriteItemModel.getTitle());
        afterPriceTv.setText(String.valueOf(mFavoriteItemModel.getZkFinalPrice()));
        beforePriceTv.setText("原價¥" + mFavoriteItemModel.getReservePrice());
        inSaleTv.setText("月銷量：" + String.valueOf(mFavoriteItemModel.getVolume()));
        if (mFavoriteItemModel.getUserType() == 0) {
            merchantsTypeTv.setText("淘寶：包郵");
        } else {
            merchantsTypeTv.setText("天貓：包郵");
        }
        couponValueTv.setText(String.valueOf(mFavoriteItemModel.getCouponInfo()));
        couponRemainCountTv.setText("剩餘數量：" + String.valueOf(mFavoriteItemModel.getCouponRemainCount()));

        // 商家资料
        shopProvcitytV.setText(mFavoriteItemModel.getProvcity());
        if (mFavoriteItemModel.getType() != 0) {
            shopIconIv.setImageResource(R.mipmap.tianmao);
        }
        callNetGetSellerInfo(mFavoriteItemModel.getNick());

        // 頭部的圖片列表
        imgList = new ArrayList<>();
        for (String imgUrl : mFavoriteItemModel.getSmallImages()) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundColor(getResources().getColor(R.color.colorMineGray));
            iv.setImageResource(R.mipmap.img_loading);
            x.image().bind(iv, imgUrl, options);
            imgList.add(iv);
        }

        mCollapsingAdapter = new CollapsingAdapter(imgList);
        mViewPager.setAdapter(mCollapsingAdapter);

        // 相关店铺推荐
        sellerModelList = new ArrayList<>();
        mSellerRecommondAdapter = new SellerRecommondAdapter(sellerModelList, this, mStoreRecommonGv);
        mStoreRecommonGv.setAdapter(mSellerRecommondAdapter);
        callNetGetStoreRecommond(mFavoriteItemModel.getSellerId());

        // 相关商品推荐
        mItemRecommendModelList = new ArrayList<>();
        mItemRecommendAdapter = new ItemRecommendAdapter(mItemRecommendModelList, this);
        mItemRecommonGv.setAdapter(mItemRecommendAdapter);
        callNetGetItemRecommond(mFavoriteItemModel.getNumIid());
    }

    /**
     * 获取商品详情
     */
    private void callNetGetItemInfo(long numIids) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageno", "1");
        paramsMap.put("pagesize", "1");
        paramsMap.put("numIids", String.valueOf(numIids));
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.ITEM_INFO + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ItemInfoOutModel model = JSON.parseObject(result, ItemInfoOutModel.class);
                if (model.getStatus() == 0) {
                    mItemInfoModel = model.getData().getResults().get(0);
                    storeRatingTv.setText("店铺评分:" + mItemInfoModel.getShopDsr());
                    sellerRateTv.setText("卖家等级:" + mItemInfoModel.getRatesum());
                } else {
                    Toast.makeText(getApplicationContext(), "获取商品详情失敗！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "获取商品详情失敗！", Toast.LENGTH_SHORT).show();
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
     * 获取商家详情
     */
    private void callNetGetSellerInfo(String sellerName) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageno", "1");
        paramsMap.put("pagesize", "1");
        paramsMap.put("sellername", sellerName);
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.SELLER_LIST + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SellerOutModel model = JSON.parseObject(result, SellerOutModel.class);
                if (model.getStatus() == 0) {
                    for (int i = 0; i < model.getData().getResults().size(); i++) {
                        // 只取一个所以只会轮询到第一个
                        mSellerModel = model.getData().getResults().get(i);
                        sellerNickTv.setText(model.getData().getResults().get(i).getShopTitle() + "/" + model.getData().getResults().get(i).getSellerNick());
                        x.image().bind(shopIconIv, model.getData().getResults().get(i).getPictUrl());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "獲取商家详情失敗！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "獲取商家详情失敗！", Toast.LENGTH_SHORT).show();
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
     * 获取相关店铺
     */
    private void callNetGetStoreRecommond(long sellerId) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageno", "1");
        paramsMap.put("pagesize", "10");
        paramsMap.put("userId", String.valueOf(sellerId));
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.SELLER_LIST + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SellerOutModel model = JSON.parseObject(result, SellerOutModel.class);
                if (model.getStatus() == 0) {
                    sellerModelList.addAll(model.getData().getResults());
                    mSellerRecommondAdapter.refreshData(sellerModelList);
                } else {
                    Toast.makeText(getApplicationContext(), "獲取相关店铺失敗！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "獲取相关店铺失敗！", Toast.LENGTH_SHORT).show();
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
     * 获取商品相关推荐
     * @param numIid
     */
    private void callNetGetItemRecommond(long numIid) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageno", "1");
        paramsMap.put("pagesize", "20");
        paramsMap.put("numIid", String.valueOf(numIid));
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.ITEM_RECOMMEND_LIST + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ItemRecommondOutModel model = JSON.parseObject(result, ItemRecommondOutModel.class);
                if (model.getStatus() == 0) {
                    mItemRecommendModelList.addAll(model.getData().getResults());
                    mItemRecommendAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "获取商品相关推荐失敗！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "获取商品相关推荐失敗！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.merchandise_detial_store:
                //设置页面打开方式
                AlibcShowParams showParamsStore = new AlibcShowParams(OpenType.H5, false);;
                //实例化URL打开page
                detailPage = new AlibcShopPage(String.valueOf(mFavoriteItemModel.getSellerId()));
                //使用百川sdk提供默认的Activity打开detail
                AlibcTrade.show(this, detailPage, showParamsStore, null, exParams , new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                    }
                });
                break;
            case R.id.merchandise_detial_shopping_cart:
                //设置页面打开方式
                AlibcShowParams showParamsCart = new AlibcShowParams(OpenType.H5, false);;
                //实例化我的订单打开page
                detailPage = new AlibcMyCartsPage();
                //使用百川sdk提供默认的Activity打开detail
                AlibcTrade.show(this, detailPage, showParamsCart, null, exParams , new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                    }
                });
                break;
            case R.id.merchandise_detial_coupon_redemption:
                //设置页面打开方式
                AlibcShowParams showParamsCouponRedemption = new AlibcShowParams(OpenType.Native, false);;
                //实例化URL打开page
                detailPage = new AlibcPage(mFavoriteItemModel.getCouponClickUrl());
                //使用百川sdk提供默认的Activity打开detail
                AlibcTrade.show(this, detailPage, showParamsCouponRedemption, null, exParams , new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                    }
                });
                break;
            case R.id.merchandise_detial:
                break;
        }
    }
}
