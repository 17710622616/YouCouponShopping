package com.youcoupon.john_li.youcouponshopping.YouFragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.alibaba.baichuan.trade.common.utils.AlibcLogger;
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
        alibcShowParams = new AlibcShowParams();

        exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

        //获取淘宝用户信息
        String nick = AlibcLogin.getInstance().getSession().nick;
        String avatarUrl = AlibcLogin.getInstance().getSession().avatarUrl;
        if (nick != null && avatarUrl != null) {
            if (!nick.equals("") && !avatarUrl.equals("")) {
                x.image().bind(headIv, avatarUrl, options);
                nickTv.setText(nick);
            } else {
                nickTv.setText("请先登录！");
            }
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
                if (nick == null || avatarUrl == null) {
                    login();
                } else {
                    if (nick.equals("") || avatarUrl.equals("")) {
                        login();
                    }
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
        Toast.makeText(getActivity(),"orderType=" + orderType, Toast.LENGTH_SHORT).show();
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

        boolean isAllOrder = true; //false 进行订单分域（只展示通过当前app下单的订单），true 显示所有订单
        AlibcBasePage alibcBasePage = new AlibcMyOrdersPage(orderType, isAllOrder);
        alibcShowParams = new AlibcShowParams(OpenType.Native);

        AlibcTrade.openByBizCode(getActivity(), alibcBasePage, null, new WebViewClient(),
                new WebChromeClient(), "order", alibcShowParams, taokeParams,
                trackParams, new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult tradeResult) {
                        // 交易成功回调（其他情形不回调）
                        Toast.makeText(getActivity(),"open detail page success", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(int code, String msg) {
                        // 失败回调信息
                        AlibcLogger.e(TAG, "code=" + code + ", msg=" + msg);
                        if (code == -1) {
                            Toast.makeText(getActivity(), "唤端失败，失败模式为none" + ",code=" + code + ", msg=" + msg, Toast.LENGTH_LONG).show();
                        }
                    }
                });
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


    /**
     * 登录
     */
    public void login() {
        final AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.showLogin(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int loginResult, String openId, String userNick) {
                // 参数说明：
                // loginResult(0--登录初始化成功；1--登录初始化完成；2--登录成功)
                // openId：用户id
                // userNick: 用户昵称
                Toast.makeText(getActivity(), "登录成功 " + AlibcLogin.getInstance().getSession(), Toast.LENGTH_LONG).show();
                reFreshUI();
            }

            @Override
            public void onFailure(int code, String msg) {
                // code：错误码  msg： 错误信息
                Toast.makeText(getActivity(), "登录失敗，" + msg + ":" + code, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void loginOut() {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.logout(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int loginResult, String openId, String userNick) {
                // 参数说明：
                // loginResult(3--登出成功)
                // openId：用户id
                // userNick: 用户昵称
                Toast.makeText(getActivity(), "退出登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                // code：错误码  msg： 错误信息
                Toast.makeText(getActivity(), "退出登录失败 " + msg + code, Toast.LENGTH_SHORT).show();
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
