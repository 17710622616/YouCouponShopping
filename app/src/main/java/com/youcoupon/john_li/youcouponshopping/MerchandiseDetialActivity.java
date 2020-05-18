package com.youcoupon.john_li.youcouponshopping;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.ali.auth.third.core.MemberSDK;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.LoginService;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.alibaba.baichuan.trade.common.utils.AlibcLogger;
import com.alibaba.fastjson.JSON;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.youcoupon.john_li.youcouponshopping.YouActivity.BecomePartnerActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.ServiceActivity;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.CollapsingAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.ItemRecommendAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.MaterialItemAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.SellerRecommondAdapter;
import com.youcoupon.john_li.youcouponshopping.YouModel.CommonModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteItemOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.ItemInfoOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.ItemRecommondOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.MaterialClassifyItemOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.SellerOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.UserInfoOutsideModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.SPUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;
import com.youcoupon.john_li.youcouponshopping.YouView.NoScrollGridView;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
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
    private LinearLayout shareLL;
    private TextView merchandiseTitleTv, afterPriceTv, beforePriceTv, inSaleTv, sahreTv, merchantsTypeTv, couponValueTv, couponRemainCountTv, couponRedemptionTv, sellerNickTv, storeRatingTv, sellerRateTv, shopProvcitytV;
    private RelativeLayout shopRL;
    private FrameLayout sellerRecommondFL;
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
    // 淘口令
    private String mTpwd = "";
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setLoadingDrawableId(R.mipmap.img_loading).setFailureDrawableId(R.mipmap.load_img_fail).build();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchandise_detial);
        initView();
        setListener();
        initData();
    }

    public void initView() {
        ImmersionBar.with(this).titleBar(articalToolbar).init();
        appbar = (AppBarLayout) findViewById(R.id.merchandise_detial_appbar);
        articalToolbar = (Toolbar) findViewById(R.id.merchandise_detial_toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.merchandise_detial_collapsing_toolbar);
        mViewPager = (ViewPager) findViewById(R.id.merchandise_detial_vp);
        shopIconIv = (ImageView) findViewById(R.id.merchandise_detial_shop_icon);
        merchandiseTitleTv = (TextView) findViewById(R.id.merchandise_detial_title);
        afterPriceTv = (TextView) findViewById(R.id.merchandise_detial_after);
        beforePriceTv = (TextView) findViewById(R.id.merchandise_detial_before);
        inSaleTv = (TextView) findViewById(R.id.merchandise_detial_in_sales);
        sahreTv = findViewById(R.id.merchandise_share_tv);
        merchantsTypeTv = (TextView) findViewById(R.id.merchandise_detial_merchants_type);
        couponValueTv = (TextView) findViewById(R.id.merchandise_detial_coupon);
        couponRemainCountTv = (TextView) findViewById(R.id.merchandise_detial_coupon_remain_coun);
        shareLL = (LinearLayout) findViewById(R.id.merchandise_share);
        couponRedemptionTv = (TextView) findViewById(R.id.merchandise_detial_coupon_redemption);
        sellerNickTv = (TextView) findViewById(R.id.merchandise_detial_seller_nick);
        storeRatingTv = (TextView) findViewById(R.id.merchandise_detial_store_ratings);
        sellerRateTv = (TextView) findViewById(R.id.merchandise_detial_seller_rating);
        shopProvcitytV = (TextView) findViewById(R.id.merchandise_detial_seller_shop_provcity);
        itemDetialIv = (RelativeLayout) findViewById(R.id.merchandise_detial_detial_title_rl);
        shopRL = (RelativeLayout) findViewById(R.id.merchandise_detial_shop_rl);
        sellerRecommondFL = findViewById(R.id.merchandise_detial_seller_recommond);
        mStoreRecommonGv = (NoScrollGridView) findViewById(R.id.merchandise_store_recommended);
        mItemRecommonGv = (NoScrollGridView) findViewById(R.id.merchandise_item_recommended);

        beforePriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        //setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
    }

    public void setListener() {
        shareLL.setOnClickListener(this);
        couponRedemptionTv.setOnClickListener(this);
        itemDetialIv.setOnClickListener(this);
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
        mCollapsingToolbarLayout.setTitle("详情");//设置标题的名字
        /*mCollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置
        mCollapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER);////设置展开后标题的位置
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorPrimary));//设置展开后标题的颜色*/

        // 商品详情
        callNetGetItemInfo(mMaterialItemModel.getItemId());

        // 設置標題及內容
        if (mMaterialItemModel.getCouponInfo() != null) {
            if(!mMaterialItemModel.getCouponInfo().equals("")) {
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
                            if (Double.parseDouble(m.group()) == 0.0) {
                                if (m.find()) {
                                    afterPriceTv.setText("¥" + String.format("%.2f", (Double.parseDouble(mMaterialItemModel.getZkFinalPrice()) - Double.parseDouble(m.group()))));
                                    afterCoupon = Double.parseDouble(mMaterialItemModel.getZkFinalPrice()) - Double.parseDouble(m.group());
                                }
                            } else {
                                afterPriceTv.setText("¥" + String.format("%.2f", (Double.parseDouble(mMaterialItemModel.getZkFinalPrice()) - Double.parseDouble(m.group()))));
                                afterCoupon = Double.parseDouble(mMaterialItemModel.getZkFinalPrice()) - Double.parseDouble(m.group());
                            }
                        }

                        break;
                    }
                }

                sahreTv.setText("分享賺" +String.format("%.2f", (0.78 * (afterCoupon * Double.parseDouble(mMaterialItemModel.getCommissionRate()) * 0.01))));
                couponRedemptionTv.setText("领券购买，返" +String.format("%.2f", (0.78 * (afterCoupon * Double.parseDouble(mMaterialItemModel.getCommissionRate()) * 0.01))));
            } else {
                beforePriceTv.setVisibility(View.GONE);
                couponValueTv.setVisibility(View.GONE);
                couponRemainCountTv.setVisibility(View.GONE);
                afterPriceTv.setText(String.valueOf(mMaterialItemModel.getZkFinalPrice()));

                couponRedemptionTv.setText("点我购买，返" +String.format("%.2f", (0.78 * (Double.parseDouble(mMaterialItemModel.getZkFinalPrice()) * Double.parseDouble(mMaterialItemModel.getCommissionRate()) * 0.01))));
            }
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
                    if (model.getData().getResults().size() > 0) {
                        sellerModelList.addAll(model.getData().getResults());
                        mSellerRecommondAdapter.refreshData(sellerModelList);
                    } else {
                        sellerRecommondFL.setVisibility(View.GONE);
                    }
                } else {
                    sellerRecommondFL.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "獲取相关店铺失敗！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                sellerRecommondFL.setVisibility(View.GONE);
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

    /**
     * 获取商品淘口令
     */
    private void callNetGetTPWD() {
        Map<String, String> paramsMap = new HashMap<>();
        String shareUrl = "";
        String url = mMaterialItemModel.getCouponShareUrl().contains("https") ? mMaterialItemModel.getCouponShareUrl() : "https:" + mMaterialItemModel.getCouponShareUrl();
        String userInfoJson = (String) SPUtils.get(MerchandiseDetialActivity.this, "UserInfo", "");
        if(!userInfoJson.equals("")) {
            UserInfoOutsideModel.DataBean userInfoModel = JSON.parseObject(userInfoJson, UserInfoOutsideModel.DataBean.class);
            if (userInfoModel.getRelationId() != 0) {
                shareUrl = url + "&relationId=" + userInfoModel.getRelationId();
            } else {
                // 打开提示成为合作者视窗
                shareUrl = url;
            }
        } else {
            Toast.makeText(MerchandiseDetialActivity.this, "登录信息异常！请重新登录", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(MerchandiseDetialActivity.this, LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
        }
        paramsMap.put("shareUrl", shareUrl);
        paramsMap.put("shareTxt", mMaterialItemModel.getTitle() + "，" + mMaterialItemModel.getCouponInfo());
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.ITEM_GET_TPWD + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model = JSON.parseObject(result, CommonModel.class);
                if (model.getStatus() == 0) {
                    mTpwd = String.valueOf(model.getData());
                    openShare();
                } else {
                    Toast.makeText(getApplicationContext(), "获取商品淘口令失敗！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "获取商品淘口令失敗！", Toast.LENGTH_SHORT).show();
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
            case R.id.merchandise_share:
                /*if (mSellerModel != null) {
                    openH5Url(String.valueOf(mSellerModel.getShopUrl()));
                }*/
                // 获取淘口令
                // 判断是否登录APP账户
                if (!((String) SPUtils.get(MerchandiseDetialActivity.this, "UserToken", "")).equals("")) {
                    String userInfoJson1 = (String) SPUtils.get(MerchandiseDetialActivity.this, "UserInfo", "");
                    if(!userInfoJson1.equals("")) {
                        UserInfoOutsideModel.DataBean userInfoModel = JSON.parseObject(userInfoJson1, UserInfoOutsideModel.DataBean.class);
                        if (userInfoModel.getRelationId() != 0) {
                            callNetGetTPWD();
                        } else {
                            // 打开提示成为合作者视窗
                            showTBAuthDialog();
                        }
                    } else {
                        Toast.makeText(MerchandiseDetialActivity.this, "登录信息异常！请重新登录", Toast.LENGTH_SHORT).show();
                        startActivityForResult(new Intent(MerchandiseDetialActivity.this, LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                    }
                } else {
                    startActivityForResult(new Intent(MerchandiseDetialActivity.this, LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
                break;
            case R.id.merchandise_detial_coupon_redemption:
                // 判断是否登录APP账户
                if (!((String) SPUtils.get(MerchandiseDetialActivity.this, "UserToken", "")).equals("")) {
                    // 判断relationId是否为0
                    String userInfoJson = (String) SPUtils.get(MerchandiseDetialActivity.this, "UserInfo", "");
                    if(!userInfoJson.equals("")) {
                        UserInfoOutsideModel.DataBean userInfoModel = JSON.parseObject(userInfoJson, UserInfoOutsideModel.DataBean.class);
                        if (userInfoModel.getRelationId() != 0) {
                            // 拼接relationId打开领券链接
                            openTBKUrl(userInfoModel.getRelationId());
                        } else {
                            // 打开提示成为合作者视窗
                            showTBAuthDialog();
                        }
                    } else {
                        Toast.makeText(MerchandiseDetialActivity.this, "登录信息异常！请重新登录", Toast.LENGTH_SHORT).show();
                        startActivityForResult(new Intent(MerchandiseDetialActivity.this, LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                    }
                } else {
                    startActivityForResult(new Intent(MerchandiseDetialActivity.this, LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }

                /*//展示参数配置
                AlibcTaokeParams taokeParams = new AlibcTaokeParams("", "", "");
                //taokeParams.setPid("mm_132021823_45408225_571244745");
                taokeParams.setPid("mm_132021823_45408225_109946850496");
                taokeParams.setAdzoneid("109946850496");
                //adzoneid是需要taokeAppkey参数才可以转链成功&店铺页面需要卖家id（sellerId），具体设置方式如下：
                taokeParams.extraParams = new HashMap<>();
                taokeParams.extraParams.put("taokeAppkey", "24882815");
                taokeParams.extraParams.put("sellerId", String.valueOf(mMaterialItemModel.getSellerId()));
                //自定义参数
                Map<String, String> trackParams = new HashMap<>();
                trackParams.put("isv_code", "appisvcode");
                trackParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

                alibcShowParams = new AlibcShowParams(OpenType.Auto);
                alibcShowParams.setClientType("taobao");
                String couponUrl = null;
                if (mMaterialItemModel.getCouponShareUrl() == null) {
                    couponUrl = "https:" +  mMaterialItemModel.getItemUrl();
                } else {
                    couponUrl = "https:" + mMaterialItemModel.getCouponShareUrl();
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
                        });*/
                break;
            case R.id.merchandise_detial_detial_title_rl:
                // 判断是否登录APP账户
                if (!((String) SPUtils.get(MerchandiseDetialActivity.this, "UserToken", "")).equals("")) {
                    // 判断relationId是否为0
                    String userInfoJson = (String) SPUtils.get(MerchandiseDetialActivity.this, "UserInfo", "");
                    if(!userInfoJson.equals("")) {
                        UserInfoOutsideModel.DataBean userInfoModel = JSON.parseObject(userInfoJson, UserInfoOutsideModel.DataBean.class);
                        if (userInfoModel.getRelationId() != 0) {
                            // 拼接relationId打开领券链接
                            openTBKUrl(userInfoModel.getRelationId());
                        } else {
                            // 打开提示成为合作者视窗
                            showTBAuthDialog();
                        }
                    } else {
                        Toast.makeText(MerchandiseDetialActivity.this, "登录信息异常！请重新登录", Toast.LENGTH_SHORT).show();
                        startActivityForResult(new Intent(MerchandiseDetialActivity.this, LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                    }
                } else {
                    startActivityForResult(new Intent(MerchandiseDetialActivity.this, LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }

                //展示参数配置
                /*AlibcTaokeParams detialTaokeParams = new AlibcTaokeParams("", "", "");
                //detialTaokeParams.setPid("mm_132021823_45408225_571244745");
                detialTaokeParams.setPid("mm_132021823_45408225_109946850496");
                detialTaokeParams.setAdzoneid("109946850496");
                //adzoneid是需要taokeAppkey参数才可以转链成功&店铺页面需要卖家id（sellerId），具体设置方式如下：
                detialTaokeParams.extraParams = new HashMap<>();
                detialTaokeParams.extraParams.put("taokeAppkey", "24882815");
                detialTaokeParams.extraParams.put("sellerId", String.valueOf(mMaterialItemModel.getSellerId()));
                //自定义参数
                Map<String, String> detialTrackParams = new HashMap<>();
                detialTrackParams.put("isv_code", "appisvcode");
                detialTrackParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

                alibcShowParams = new AlibcShowParams(OpenType.Auto);
                alibcShowParams.setClientType("taobao");
                String detialUrl = null;
                if (mMaterialItemModel.getCouponShareUrl() == null) {
                    detialUrl = mMaterialItemModel.getItemUrl().contains("http") ? mMaterialItemModel.getItemUrl() : "http:" + mMaterialItemModel.getItemUrl();
                } else {
                    detialUrl = mMaterialItemModel.getCouponShareUrl().contains("http") ? mMaterialItemModel.getCouponShareUrl() : "http:" + mMaterialItemModel.getCouponShareUrl();
                }

                // 以显示传入url的方式打开页面（第二个参数是套件名称）
                AlibcTrade.openByUrl(MerchandiseDetialActivity.this, "", detialUrl, null,
                        new WebViewClient(), new WebChromeClient(), alibcShowParams,
                        detialTaokeParams, detialTrackParams, new AlibcTradeCallback() {
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
                        });*/
                break;
            case R.id.merchandise_detial_shop_rl:
                if (mSellerModel != null) {
                    openH5Url(String.valueOf(mSellerModel.getShopUrl()));
                }
                break;
        }
    }

    /**
     * 显示淘宝授权的视图
     */
    private void showTBAuthDialog() {
        //dialog_taobao_auth
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请完成淘宝登录");
        builder.setMessage("淘宝授权后下单或分享产品可以获得收益哦");
        builder.setIcon(R.mipmap.logo);
        //点击对话框以外的区域是否让对话框消失
        builder.setCancelable(true);
        //设置正面按钮
        builder.setPositiveButton("去授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userInfoJson = (String) SPUtils.get(MerchandiseDetialActivity.this, "UserInfo", "");
                UserInfoOutsideModel.DataBean userInfoModel = JSON.parseObject(userInfoJson, UserInfoOutsideModel.DataBean.class);
                Intent intent = new Intent(MerchandiseDetialActivity.this, BecomePartnerActivity.class);
                intent.putExtra("rtag", userInfoModel.getMobile() + userInfoModel.getNick_name());
                startActivityForResult(intent, 10001);
                dialog.dismiss();
            }
        });
        //设置反面按钮
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("联系客服", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MerchandiseDetialActivity.this, ServiceActivity.class));
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        //显示对话框
        dialog.show();
    }

    /**
     * 检查是否成为合作者
     */
    private void callNetchechIsPartner() {
        String userInfoJson = (String) SPUtils.get(MerchandiseDetialActivity.this, "UserInfo", "");
        UserInfoOutsideModel.DataBean userInfoModel = JSON.parseObject(userInfoJson, UserInfoOutsideModel.DataBean.class);
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("sessionKey", "610082335d0674b2ae78f3e2a7785821ad848d0632c478e2298128659");
        String rtag = userInfoModel.getMobile() + userInfoModel.getNick_name();
        //String rtag = AlibcLogin.getInstance().getSession().nick;
        paramsMap.put("rtag", rtag);
        paramsMap.put("token", (String) SPUtils.get(MerchandiseDetialActivity.this, "UserToken", ""));
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.CHECK_IS_PARTNER + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model =  JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 0) {
                    String relationId =  model.getData().toString();
                    UserInfoOutsideModel.DataBean userInfoModel = JSON.parseObject((String) SPUtils.get(MerchandiseDetialActivity.this, "UserInfo", ""), UserInfoOutsideModel.DataBean.class);
                    userInfoModel.setRelationId(Long.parseLong(relationId));

                    String userInfoJson = JSON.toJSONString(model.getData());
                    SPUtils.put(MerchandiseDetialActivity.this, "UserInfo", userInfoJson);
                    Toast.makeText(getApplicationContext(), "申请成为合作者成功，快去分享或者购买吧！" + model.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "申请成为合作者失败，请重新申请成为合作者领取返利！" + model.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "申请成为合作者异常，请重新申请成为合作者领取返利！", Toast.LENGTH_SHORT).show();
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    // 打开领券链接
    private void openTBKUrl(long relationId) {
        //展示参数配置
        AlibcTaokeParams taokeParams = new AlibcTaokeParams("", "", "");
        //taokeParams.setPid("mm_132021823_45408225_571244745");
        taokeParams.setPid("mm_132021823_45408225_109946850496");
        taokeParams.setAdzoneid("109946850496");
        //adzoneid是需要taokeAppkey参数才可以转链成功&店铺页面需要卖家id（sellerId），具体设置方式如下：
        taokeParams.extraParams = new HashMap<>();
        taokeParams.extraParams.put("taokeAppkey", "24882815");
        taokeParams.extraParams.put("sellerId", String.valueOf(mMaterialItemModel.getSellerId()));
        //自定义参数
        Map<String, String> trackParams = new HashMap<>();
        trackParams.put("isv_code", "appisvcode");
        trackParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

        alibcShowParams = new AlibcShowParams(OpenType.Auto);
        alibcShowParams.setClientType("taobao");
        String couponUrl = null;
        if (mMaterialItemModel.getCouponShareUrl() == null) {
            couponUrl = mMaterialItemModel.getItemUrl().contains("https") ? mMaterialItemModel.getItemUrl() : "https:" + mMaterialItemModel.getItemUrl() + "&relationId=" + relationId;
        } else {
            couponUrl = mMaterialItemModel.getCouponShareUrl().contains("https") ? mMaterialItemModel.getCouponShareUrl() : "https:" + mMaterialItemModel.getCouponShareUrl() + "&relationId=" + relationId;
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
    }

    /**
     * 设置打开H5页面
     */
    private void openH5Url(String url) {
        AlibcTaokeParams taokeParams = new AlibcTaokeParams("", "", "");
        //taokeParams.setPid("mm_132021823_45408225_571244745");
        taokeParams.setPid("mm_132021823_45408225_109946850496");
        taokeParams.setAdzoneid("109946850496");
        //adzoneid是需要taokeAppkey参数才可以转链成功&店铺页面需要卖家id（sellerId），具体设置方式如下：
        taokeParams.extraParams = new HashMap<>();
        taokeParams.extraParams.put("taokeAppkey", "24882815");
        taokeParams.extraParams.put("sellerId", String.valueOf(mMaterialItemModel.getSellerId()));
        //自定义参数
        Map<String, String> trackParams = new HashMap<>();
        trackParams.put("isv_code", "appisvcode");
        trackParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

        alibcShowParams = new AlibcShowParams(OpenType.Auto);
        alibcShowParams.setClientType("taobao");

        // 以显示传入url的方式打开页面（第二个参数是套件名称）
        AlibcTrade.openByUrl(MerchandiseDetialActivity.this, "", url, null,
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
    }

    /**
     * 打开分享提示
     */
    private void openShare() {
        String content = "複至这条消息去桃寶," + mTpwd + ",【" + mMaterialItemModel.getTitle() + "," + mMaterialItemModel.getCouponInfo() + "】";
        YouCommonUtils.copyToClipboard(this, content);
        AlertDialog.Builder builder = new AlertDialog.Builder(MerchandiseDetialActivity.this);
        LayoutInflater inflater = LayoutInflater.from(MerchandiseDetialActivity.this);
        View v = inflater.inflate(R.layout.dialog_share, null);
        LinearLayout wechat = (LinearLayout) v.findViewById(R.id.dialog_wechat);
        LinearLayout whatsapp = (LinearLayout) v.findViewById(R.id.dialog_whatsapp);
        LinearLayout fb = (LinearLayout) v.findViewById(R.id.dialog_fb);
        TextView shareTv = (TextView) v.findViewById(R.id.dialog_share_tv);
        ImageView cancelIv = v.findViewById(R.id.dialog_share_cancel);
        //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        dialog.setCancelable(true);
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
        shareTv.setText(content);
        cancelIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YouCommonUtils.openWechat(MerchandiseDetialActivity.this);
                dialog.dismiss();
            }
        });
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YouCommonUtils.openWhatsApp(MerchandiseDetialActivity.this);
                dialog.dismiss();
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YouCommonUtils.openFb(MerchandiseDetialActivity.this);
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10001) {
                callNetchechIsPartner();
            }
        }
    }
}
