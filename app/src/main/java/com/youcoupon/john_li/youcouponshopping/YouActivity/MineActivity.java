package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.alibaba.fastjson.JSON;
import com.youcoupon.john_li.youcouponshopping.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的界面
 * Created by John_Li on 21/5/2018.
 */

public class MineActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout obligationLL, toBeSendLL, waitForReceivingLL, toEvaluateLL, allOrderLL, shoppingCartLL;
    private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
    private Map<String, String> exParams;//yhhpass参数
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        allOrderLL = findViewById(R.id.mine_all_order);
        obligationLL = findViewById(R.id.mine_obligation);
        toBeSendLL = findViewById(R.id.mine_to_be_send);
        waitForReceivingLL = findViewById(R.id.mine_wait_for_receiving);
        toEvaluateLL = findViewById(R.id.mine_to_evaluate);
        shoppingCartLL = findViewById(R.id.mine_shopping_cart);
    }

    @Override
    public void setListener() {
        allOrderLL.setOnClickListener(this);
        obligationLL.setOnClickListener(this);
        toBeSendLL.setOnClickListener(this);
        waitForReceivingLL.setOnClickListener(this);
        toEvaluateLL.setOnClickListener(this);
        shoppingCartLL.setOnClickListener(this);
    }

    @Override
    public void initData() {
        alibcShowParams = new AlibcShowParams(OpenType.Auto, false);

        exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_all_order:
                showAllOrder(0);
                break;
            case R.id.mine_obligation:
                showAllOrder(1);
                break;
            case R.id.mine_to_be_send:
                showAllOrder(2);
                break;
            case R.id.mine_wait_for_receiving:
                showAllOrder(3);
                break;
            case R.id.mine_to_evaluate:
                showAllOrder(4);
                break;
            case R.id.mine_shopping_cart:
                showMyShoppingCart();
                break;
        }
    }

    /**
     *  顯示我的所有訂單
     * @param orderType 0：全部；1：待付款；2：待发货；3：待收货；4：待评价
     */
    private void showAllOrder(int orderType) {
        boolean isAllOrder = true; //false 进行订单分域（只展示通过当前app下单的订单），true 显示所有订单
        AlibcBasePage alibcBasePage = new AlibcMyOrdersPage(orderType, isAllOrder);
        AlibcTrade.show(this, alibcBasePage, alibcShowParams, null, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(TradeResult tradeResult) {
                //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                Toast.makeText(MineActivity.this, "成功打開所有訂單", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, String s) {
                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                Toast.makeText(MineActivity.this, "打開失敗", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 顯示我的購物車
     */
    private void showMyShoppingCart() {
        AlibcBasePage alibcBasePage = new AlibcMyCartsPage();
        AlibcTrade.show(this, alibcBasePage, alibcShowParams, null, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(TradeResult tradeResult) {
                //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                Toast.makeText(MineActivity.this, "成功打開所有訂單", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, String s) {
                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                Toast.makeText(MineActivity.this, "打開失敗", Toast.LENGTH_LONG).show();
            }
        });
    }
}
