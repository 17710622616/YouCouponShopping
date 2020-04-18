package com.youcoupon.john_li.youcouponshopping;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.baichuan.trade.common.utils.AlibcLogger;
import com.alibaba.fastjson.JSON;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.CollapsingAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.ItemRecommendAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.MaterialItemAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.SellerRecommondAdapter;
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteItemOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.ItemInfoOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.ItemRecommondOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.MaterialClassifyItemOutModel;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by John_Li on 31/5/2018.
 */

public class MerchandiseDetialActivity extends AppCompatActivity implements View.OnClickListener{
    public static String TAG = MerchandiseDetialActivity.class.getName();
    private AppBarLayout appbar;
    private Toolbar articalToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ViewPager mViewPager;
    private ImageView shopIconIv;
    private RelativeLayout itemDetialIv;
    private LinearLayout storeLL, shoppingCartLL;
    private TextView merchandiseTitleTv, afterPriceTv, beforePriceTv, inSaleTv, merchantsTypeTv, couponValueTv, couponRemainCountTv, couponRedemptionTv, sellerNickTv, storeRatingTv, sellerRateTv, shopProvcitytV, getCouponTv;
    private RelativeLayout shopRL;
    private NoScrollGridView mStoreRecommonGv;
    private NoScrollGridView mItemRecommonGv;

    // 淘寶相關信息
    private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
    private Map<String, String> exParams;
    //实例化URL打开page
    AlibcBasePage detailPage;

    // 界面數據
    private List<ImageView> imgList;
    private MaterialClassifyItemOutModel.DataBean.MaterialItemModel mMaterialItemModel;
    private ItemInfoOutModel.DataBean.ItemInfoModel mItemInfoModel;
    private SellerOutModel.DataBean.SellerModel mSellerModel;
    private List<SellerOutModel.DataBean.SellerModel> sellerModelList;
    private long totalCount = 20;
    private long pageNo = 1;
    private long pageSize = 10;
    private List<MaterialClassifyItemOutModel.DataBean.MaterialItemModel> mItemRecommendModelList;
    private CollapsingAdapter mCollapsingAdapter;
    private SellerRecommondAdapter mSellerRecommondAdapter;
    private MaterialItemAdapter mMaterialItemAdapter;
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setLoadingDrawableId(R.mipmap.img_loading).setFailureDrawableId(R.mipmap.load_img_fail).build();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/
        setContentView(R.layout.activity_merchandise_detial);
        initImmersionBar();
        initView();
        setListener();
        initData();
    }

    private void initImmersionBar() {
        ImmersionBar.with(this).titleBar(articalToolbar).init();
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
        //getCouponTv = (TextView) findViewById(R.id.merchandise_get_coupon);
        itemDetialIv = (RelativeLayout) findViewById(R.id.merchandise_detial_detial_title_rl);
        shopRL = (RelativeLayout) findViewById(R.id.merchandise_detial_shop_rl);
        mStoreRecommonGv = (NoScrollGridView) findViewById(R.id.merchandise_store_recommended);
        mItemRecommonGv = (NoScrollGridView) findViewById(R.id.merchandise_item_recommended);

        beforePriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        //setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
    }

    public void setListener() {
        shoppingCartLL.setOnClickListener(this);
        storeLL.setOnClickListener(this);
        couponRedemptionTv.setOnClickListener(this);
        itemDetialIv.setOnClickListener(this);
        //getCouponTv.setOnClickListener(this);
        shopRL.setOnClickListener(this);
        mStoreRecommonGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openH5Url(sellerModelList.get(position).getShopUrl());
            }
        });
        mItemRecommonGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MerchandiseDetialActivity.this, MerchandiseDetialActivity.class);
                intent.putExtra("MerchandiseModel", JSON.toJSONString(mItemRecommendModelList.get(position)));
                startActivity(intent);
            }
        });
    }

    public void initData() {
        // 獲取產品資料
        Intent intent = getIntent();
        mMaterialItemModel = JSON.parseObject(intent.getStringExtra("MerchandiseModel"), MaterialClassifyItemOutModel.DataBean.MaterialItemModel.class);

        // 初始化阿里百川
        //提供给三方传递配置参数
        exParams = new HashMap<>();
        exParams.put("alibaba", "appisvcode");

        // toolbar
        /*setSupportActionBar(articalToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        setSupportActionBar(articalToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //  設置標題
        mCollapsingToolbarLayout.setTitle("标题");//设置标题的名字
        /*mCollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置
        mCollapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER);////设置展开后标题的位置
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorPrimary));//设置展开后标题的颜色*/

        // 商品详情
        callNetGetItemInfo(mMaterialItemModel.getItemId());

        // 設置標題及內容
        if (mMaterialItemModel.getCouponInfo() != null) {
            couponValueTv.setText(String.valueOf(mMaterialItemModel.getCouponInfo()));
            couponRemainCountTv.setText("剩餘數量：" + String.valueOf(mMaterialItemModel.getCouponRemainCount()));
            beforePriceTv.setText("原價¥" + mMaterialItemModel.getZkFinalPrice());
            // 按指定模式在字符串查找
            Pattern p=Pattern.compile("\\d+");
            Matcher m=p.matcher(mMaterialItemModel.getCouponInfo());
            double afterCoupon = 0.01;
            while(m.find()) {
                if (Double.parseDouble(mMaterialItemModel.getZkFinalPrice()) >= Double.parseDouble(m.group())) {
                    if (m.find()) {
                        afterPriceTv.setText("¥" + String.format("%.2f", (Double.parseDouble(mMaterialItemModel.getZkFinalPrice()) - Double.parseDouble(m.group()))));
                        afterCoupon = Double.parseDouble(mMaterialItemModel.getZkFinalPrice()) - Double.parseDouble(m.group());
                    }

                    break;
                }
            }

            couponRedemptionTv.setText("领券购买，返" +String.format("%.2f", (0.78 * (afterCoupon * Double.parseDouble(mMaterialItemModel.getCommissionRate()) * 0.01))));
        } else {
            beforePriceTv.setVisibility(View.GONE);
            couponValueTv.setVisibility(View.GONE);
            couponRemainCountTv.setVisibility(View.GONE);
            afterPriceTv.setText(String.valueOf(mMaterialItemModel.getZkFinalPrice()));

            couponRedemptionTv.setText("点我购买，返" +String.format("%.2f", (0.78 * (Double.parseDouble(mMaterialItemModel.getZkFinalPrice()) * Double.parseDouble(mMaterialItemModel.getCommissionRate()) * 0.01))));
        }
        merchandiseTitleTv.setText(mMaterialItemModel.getTitle());
        inSaleTv.setText("售" + String.valueOf(mMaterialItemModel.getVolume()) + "件");
        if (mMaterialItemModel.getUserType() == 0) {
            merchantsTypeTv.setText("淘寶包郵");
        } else {
            merchantsTypeTv.setText("天貓包郵");
        }

        // 商家资料
        shopProvcitytV.setText(mMaterialItemModel.getProvcity());
        if (mMaterialItemModel.getUserType() != 0) {
            shopIconIv.setImageResource(R.mipmap.tianmao);
        }
        callNetGetSellerInfo(mMaterialItemModel.getNick());

        // 頭部的圖片列表
        imgList = new ArrayList<>();
        for (String imgUrl : mMaterialItemModel.getSmallImages()) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundColor(getResources().getColor(R.color.colorMineGray));
            iv.setImageResource(R.mipmap.img_loading);
            x.image().bind(iv, imgUrl.contains("http") ? imgUrl : "http:" + imgUrl, options);
            imgList.add(iv);
        }

        mCollapsingAdapter = new CollapsingAdapter(imgList);
        mViewPager.setAdapter(mCollapsingAdapter);

        // 相关店铺推荐
        sellerModelList = new ArrayList<>();
        mSellerRecommondAdapter = new SellerRecommondAdapter(sellerModelList, this, mStoreRecommonGv);
        mStoreRecommonGv.setAdapter(mSellerRecommondAdapter);
        callNetGetStoreRecommond(mMaterialItemModel.getSellerId());

        // 相关商品推荐
        mItemRecommendModelList = new ArrayList<>();
        mMaterialItemAdapter = new MaterialItemAdapter(mItemRecommendModelList, this);
        mItemRecommonGv.setAdapter(mMaterialItemAdapter);
        callNetGetItemRecommond(mMaterialItemModel.getItemId());
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
        paramsMap.put("sellername", sellerName);
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.SHOP_INFO + YouCommonUtils.createLinkStringByGet(paramsMap));
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
     * @param ItemId
     */
    private void callNetGetItemRecommond(long ItemId) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageno", "1");
        paramsMap.put("pagesize", "20");
        paramsMap.put("Item_id", String.valueOf(ItemId));
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.ITEM_RECOMMEND_LIST + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MaterialClassifyItemOutModel model = JSON.parseObject(result, MaterialClassifyItemOutModel.class);
                if (model.getStatus() == 0) {
                    mItemRecommendModelList.addAll(model.getData().getMap_data());
                    mMaterialItemAdapter.notifyDataSetChanged();
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
                if (mSellerModel != null) {
                    openH5Url(String.valueOf(mSellerModel.getShopUrl()));
                }
                break;
            case R.id.merchandise_detial_shopping_cart:
                //设置页面打开方式
//                AlibcShowParams showParamsCart = new AlibcShowParams(OpenType.H5, false);;
//                //实例化我的订单打开page
//                detailPage = new AlibcMyCartsPage();
//                //使用百川sdk提供默认的Activity打开detail
//                AlibcTrade.show(this, detailPage, showParamsCart, null, exParams , new AlibcTradeCallback() {
//                    @Override
//                    public void onTradeSuccess(TradeResult tradeResult) {
//                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
//                    }
//                });
                break;
            case R.id.merchandise_detial_coupon_redemption:
                //设置页面打开方式
                /*AlibcShowParams showParamsCouponRedemption = new AlibcShowParams(OpenType.Native, false);;
                //实例化URL打开page
                detailPage = new AlibcTrade(mMaterialItemModel.getCouponClickUrl());
                //使用百川sdk提供默认的Activity打开detail
                AlibcTrade.show(this, detailPage, showParamsCouponRedemption, null, exParams , new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                        Log.d("TAG", "onTradeSuccess: ");
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                        Log.d("TAG", "onFailure: ");
                    }
                });*/

                //展示参数配置
                AlibcTaokeParams taokeParams = new AlibcTaokeParams("", "", "");
                taokeParams.setPid("mm_132021823_45408225_571244745");
                //taokeParams.setAdzoneid("29932014");
                //adzoneid是需要taokeAppkey参数才可以转链成功&店铺页面需要卖家id（sellerId），具体设置方式如下：
                taokeParams.extraParams = new HashMap<>();
                taokeParams.extraParams.put("taokeAppkey", "24882815");
                //自定义参数
                Map<String, String> trackParams = new HashMap<>();
                trackParams.put("isv_code", "appisvcode");
                trackParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

                alibcShowParams = new AlibcShowParams(OpenType.Auto);
                alibcShowParams.setClientType("taobao");
                String couponUrl = mMaterialItemModel.getCouponShareUrl();
                if (couponUrl == null) {
                    couponUrl = mMaterialItemModel.getItemUrl();
                }

                // 以显示传入url的方式打开页面（第二个参数是套件名称）
                AlibcTrade.openByUrl(MerchandiseDetialActivity.this, "", couponUrl, null,
                        new WebViewClient(), new WebChromeClient(), alibcShowParams,
                        taokeParams, trackParams, new AlibcTradeCallback() {
                            @Override
                            public void onTradeSuccess(AlibcTradeResult tradeResult) {
                                AlibcLogger.i(TAG, "request success");
                            }
                            @Override
                            public void onFailure(int code, String msg) {
                                AlibcLogger.e(TAG, "code=" + code + ", msg=" + msg);
                                if (code == -1) {
                                    Toast.makeText(MerchandiseDetialActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case R.id.merchandise_detial_detial_title_rl:
//                //设置页面打开方式
//                AlibcShowParams showParamsItemDetial = new AlibcShowParams(OpenType.Native, false);;
//                //实例化URL打开page
//                detailPage = new AlibcDetailPage(String.valueOf(mMaterialItemModel.getNumIid()));
//                //使用百川sdk提供默认的Activity打开detail
//                AlibcTrade.show(this, detailPage, showParamsItemDetial, null, exParams , new AlibcTradeCallback() {
//                    @Override
//                    public void onTradeSuccess(TradeResult tradeResult) {
//                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
//                        Log.d("TAG", "onTradeSuccess: ");
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
//                        Log.d("TAG", "onFailure: ");
//                    }
//                });
                break;
            case R.id.merchandise_get_coupon:
                break;
            case R.id.merchandise_detial_shop_rl:
                if (mSellerModel != null) {
                    openH5Url(String.valueOf(mSellerModel.getShopUrl()));
                }
                break;
        }
    }

    /**
     * 设置页面打开方式
     */
    private void openH5Url(String url) {
//        AlibcShowParams showParamsStore = new AlibcShowParams(OpenType.H5, false);;
//        //实例化URL打开page
//        detailPage = new AlibcPage(url);
//        //使用百川sdk提供默认的Activity打开detail
//        AlibcTrade.show(this, detailPage, showParamsStore, null, exParams , new AlibcTradeCallback() {
//            @Override
//            public void onTradeSuccess(TradeResult tradeResult) {
//                //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
//            }
//        });
    }
}
