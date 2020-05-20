package com.youcoupon.john_li.youcouponshopping.YouFragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.baichuan.trade.common.utils.AlibcLogger;
import com.youcoupon.john_li.youcouponshopping.R;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by John_Li on 25/5/2018.
 */

public class ShopCartFragment extends LazyLoadFragment {
    public static String TAG = ShopCartFragment.class.getName();
    private LinearLayout openShopCart;
    private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_shop_cart);
        openShopCart = (LinearLayout) findViewById(R.id.shop_cart_go);
        openShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyShoppingCart();
            }
        });
        alibcShowParams = new AlibcShowParams();
        showMyShoppingCart();
    }

    /**
     * 顯示我的購物車
     */
    private void showMyShoppingCart() {
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

        AlibcBasePage alibcBasePage = new AlibcMyCartsPage();
        alibcShowParams = new AlibcShowParams(OpenType.Native);
        //AlibcTrade.openByBizCode(getActivity(), alibcBasePage, alibcShowParams, null, exParams, new WebChromeClient());

        AlibcTrade.openByBizCode(getActivity(), alibcBasePage, null, new WebViewClient(),
                new WebChromeClient(), "cart", alibcShowParams, taokeParams,
                trackParams, new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult tradeResult) {
                        // 交易成功回调（其他情形不回调）
                        AlibcLogger.i(TAG, "open detail page success");
                    }
                    @Override
                    public void onFailure(int code, String msg) {
                        // 失败回调信息
                        AlibcLogger.e(TAG, "code=" + code + ", msg=" + msg);
                        if (code == -1) {
                            Toast.makeText(getActivity(), "唤端失败，失败模式为none",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
