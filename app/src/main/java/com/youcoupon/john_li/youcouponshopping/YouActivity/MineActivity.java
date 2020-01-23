package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.youcoupon.john_li.youcouponshopping.R;

import org.xutils.image.ImageOptions;

import java.util.Map;

/**
 * 我的界面
 * Created by John_Li on 21/5/2018.
 */

public class MineActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView nickTv;
    private ImageView headIv;//userInfoLL
    private LinearLayout obligationLL, toBeSendLL, waitForReceivingLL, toEvaluateLL, allOrderLL, shoppingCartLL;
    private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
    private Map<String, String> exParams;//yhhpass参数
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setLoadingDrawableId(R.mipmap.head_boy).setFailureDrawableId(R.mipmap.head_boy).build();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        initView();
        setListener();
        initData();
    }

    public void initView() {
        nickTv = (TextView) findViewById(R.id.user_nick_name);
        headIv = (ImageView) findViewById(R.id.user_head);
        //userInfoLL = (LinearLayout) findViewById(R.id.user_info);
        /*allOrderLL = (LinearLayout) findViewById(R.id.mine_all_order);
        obligationLL = (LinearLayout) findViewById(R.id.mine_obligation);
        toBeSendLL = (LinearLayout) findViewById(R.id.mine_to_be_send);
        waitForReceivingLL = (LinearLayout) findViewById(R.id.mine_wait_for_receiving);
        toEvaluateLL = (LinearLayout) findViewById(R.id.mine_to_evaluate);
        shoppingCartLL = (LinearLayout) findViewById(R.id.mine_shopping_cart);*/
    }

    public void setListener() {
        //userInfoLL.setOnClickListener(this);
        /*allOrderLL.setOnClickListener(this);
        obligationLL.setOnClickListener(this);
        toBeSendLL.setOnClickListener(this);
        waitForReceivingLL.setOnClickListener(this);
        toEvaluateLL.setOnClickListener(this);
        shoppingCartLL.setOnClickListener(this);*/
    }

    public void initData() {
//        alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
//
//        exParams = new HashMap<>();
//        exParams.put("isv_code", "appisvcode");
//        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
//
//        //获取淘宝用户信息
//        if (!JSON.toJSONString(AlibcLogin.getInstance().getSession()).equals("{}")) {
//            String nick = AlibcLogin.getInstance().getSession().nick;
//            String avatarUrl = AlibcLogin.getInstance().getSession().avatarUrl;
//            x.image().bind(headIv, avatarUrl, options);
//            nickTv.setText(nick);
//        } else {
//            nickTv.setText("请先登录！");
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.user_info:
//                if (JSON.toJSONString(AlibcLogin.getInstance().getSession()).equals("{}")) {
//                    login();
//                }
                break;*/
            /*case R.id.mine_all_order:
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
                break;*/
        }
    }

    /**
     *  顯示我的所有訂單
     * @param orderType 0：全部；1：待付款；2：待发货；3：待收货；4：待评价
     */
    private void showAllOrder(int orderType) {
//        boolean isAllOrder = true; //false 进行订单分域（只展示通过当前app下单的订单），true 显示所有订单
//        AlibcBasePage alibcBasePage = new AlibcMyOrdersPage(orderType, isAllOrder);
//        AlibcTrade.show(this, alibcBasePage, alibcShowParams, null, exParams, new AlibcTradeCallback() {
//            @Override
//            public void onTradeSuccess(TradeResult tradeResult) {
//                //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
//                Toast.makeText(MineActivity.this, "成功打開所有訂單", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
//                Toast.makeText(MineActivity.this, "打開失敗", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    /**
     * 顯示我的購物車
     */
    private void showMyShoppingCart() {
//        AlibcBasePage alibcBasePage = new AlibcMyCartsPage();
//        AlibcTrade.show(this, alibcBasePage, alibcShowParams, null, exParams, new AlibcTradeCallback() {
//            @Override
//            public void onTradeSuccess(TradeResult tradeResult) {
//                //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
//                Toast.makeText(MineActivity.this, "成功打開所有訂單", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
//                Toast.makeText(MineActivity.this, "打開失敗", Toast.LENGTH_LONG).show();
//            }
//        });
    }


    /**
     * 登录
     */
    public void login() {
//        final AlibcLogin alibcLogin = AlibcLogin.getInstance();
//        alibcLogin.showLogin(this, new AlibcLoginCallback() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(MineActivity.this, "登录成功 ", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                Toast.makeText(MineActivity.this, "登录失敗 ", Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
