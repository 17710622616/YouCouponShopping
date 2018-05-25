package com.youcoupon.john_li.youcouponshopping.YouFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.alibaba.fastjson.JSON;
import com.youcoupon.john_li.youcouponshopping.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by John_Li on 25/5/2018.
 */

public class MineFragment extends LazyLoadFragment implements View.OnClickListener{
    public static String TAG = MineFragment.class.getName();
    private TextView nickTv;
    private ImageView headIv;
    private LinearLayout userInfoLL, obligationLL, toBeSendLL, waitForReceivingLL, toEvaluateLL, allOrderLL, shoppingCartLL, loginOutLL;
    private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
    private Map<String, String> exParams;//yhhpass参数
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setLoadingDrawableId(R.mipmap.head_boy).setFailureDrawableId(R.mipmap.head_boy).build();
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.activity_mine);
        initView();
        setListener();
        initData();
    }

    public void initView() {
        nickTv = (TextView) findViewById(R.id.user_nick_name);
        headIv = (ImageView) findViewById(R.id.user_head);
        userInfoLL = (LinearLayout) findViewById(R.id.user_info);
        allOrderLL = (LinearLayout) findViewById(R.id.mine_all_order);
        obligationLL = (LinearLayout) findViewById(R.id.mine_obligation);
        toBeSendLL = (LinearLayout) findViewById(R.id.mine_to_be_send);
        waitForReceivingLL = (LinearLayout) findViewById(R.id.mine_wait_for_receiving);
        toEvaluateLL = (LinearLayout) findViewById(R.id.mine_to_evaluate);
        shoppingCartLL = (LinearLayout) findViewById(R.id.mine_shopping_cart);
        loginOutLL = (LinearLayout) findViewById(R.id.mine_setting);
    }

    public void setListener() {
        userInfoLL.setOnClickListener(this);
        allOrderLL.setOnClickListener(this);
        obligationLL.setOnClickListener(this);
        toBeSendLL.setOnClickListener(this);
        waitForReceivingLL.setOnClickListener(this);
        toEvaluateLL.setOnClickListener(this);
        shoppingCartLL.setOnClickListener(this);
        loginOutLL.setOnClickListener(this);
    }

    public void initData() {
        alibcShowParams = new AlibcShowParams(OpenType.Auto, false);

        exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

        //获取淘宝用户信息
        String nick = AlibcLogin.getInstance().getSession().nick;
        String avatarUrl = AlibcLogin.getInstance().getSession().avatarUrl;
        if (!nick.equals("") && !avatarUrl.equals("")) {
            x.image().bind(headIv, avatarUrl, options);
            nickTv.setText(nick);
        } else {
            nickTv.setText("请先登录！");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info:
                String nick = AlibcLogin.getInstance().getSession().nick;
                String avatarUrl = AlibcLogin.getInstance().getSession().avatarUrl;
                if (nick.equals("") || avatarUrl.equals("")) {
                    login();
                }
                break;
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
            case R.id.mine_setting:
                loginOut();
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
        AlibcTrade.show(getActivity(), alibcBasePage, alibcShowParams, null, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(TradeResult tradeResult) {
                //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                Toast.makeText(getActivity(), "成功打開所有訂單", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, String s) {
                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                Toast.makeText(getActivity(), "打開失敗", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 顯示我的購物車
     */
    private void showMyShoppingCart() {
        AlibcBasePage alibcBasePage = new AlibcMyCartsPage();
        AlibcTrade.show(getActivity(), alibcBasePage, alibcShowParams, null, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(TradeResult tradeResult) {
                //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                Toast.makeText(getActivity(), "成功打開所有訂單", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, String s) {
                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                Toast.makeText(getActivity(), "打開失敗", Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * 登录
     */
    public void login() {
        final AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.showLogin(getActivity(), new AlibcLoginCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), "登录成功 ", Toast.LENGTH_LONG).show();
                reFreshUI();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getActivity(), "登录失敗 ", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void loginOut() {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.logout(getActivity(), new LogoutCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), "退出登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(getActivity(), "退出登录失败 " + code + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void reFreshUI() {
        //获取淘宝用户信息
        String userSession = JSON.toJSONString(AlibcLogin.getInstance().getSession());
        if (!userSession.equals("{}")) {
            String nick = AlibcLogin.getInstance().getSession().nick;
            String avatarUrl = AlibcLogin.getInstance().getSession().avatarUrl;
            x.image().bind(headIv, avatarUrl, options);
            nickTv.setText(nick);
        } else {
            nickTv.setText("请先登录！");
        }
    }
}
