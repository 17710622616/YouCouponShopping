package com.youcoupon.john_li.youcouponshopping.YouFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ali.auth.third.login.LoginService;
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
import com.alipay.sdk.tid.Tid;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youcoupon.john_li.youcouponshopping.LoginActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouActivity.BecomePartnerActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.BussinesActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.ChangePwdActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.OrderListActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.ServiceActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.SuggestActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.TeamListActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.TutorialActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.UserInfoActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.WalletActivity;
import com.youcoupon.john_li.youcouponshopping.YouModel.CommonModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.UserInfoOutsideModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.SPUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by John_Li on 25/5/2018.
 */

public class MineFragment extends LazyLoadFragment implements View.OnClickListener{
    public static String TAG = MineFragment.class.getName();
    private TextView nickTv, invitationCodeTv,taobaoAuthTv, performanceThisMonthTv, performanceLastMonthTv, balanceTv;
    private ImageView headIv;//userInfoLL
    private RelativeLayout userInfoRl;
    private RefreshLayout mRefreshLayout;
    private LinearLayout taobaoLL,courseLL,changePwdLL, suggestLL, shareLL, serviceLL, bussinessLL, loginOutLL;
    private LinearLayout incomeLL, teamLL, orderLL;
    private ProgressDialog dialog;

    private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
    private Map<String, String> exParams;//yhhpass参数
    private UserInfoOutsideModel.DataBean mUserInfoModel;
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setLoadingDrawableId(R.mipmap.head_iimg).setFailureDrawableId(R.mipmap.head_iimg).build();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.activity_mine);
        initView();
        setListener();
        initData();
    }

    public void initView() {
        mRefreshLayout = (RefreshLayout) findViewById(R.id.mine_srl);
        nickTv = (TextView) findViewById(R.id.user_nick_name);
        invitationCodeTv = (TextView) findViewById(R.id.user_invitation_code);
        taobaoAuthTv = (TextView) findViewById(R.id.mine_taobao_auth);
        headIv = (ImageView) findViewById(R.id.user_head);
        performanceThisMonthTv = (TextView) findViewById(R.id.mine_performance_this_month);
        performanceLastMonthTv = (TextView) findViewById(R.id.mine_performance_last_month);
        balanceTv = (TextView) findViewById(R.id.mine_balance);
        //userInfoLL = (LinearLayout) findViewById(R.id.user_info);
        incomeLL = (LinearLayout) findViewById(R.id.mine_income);
        teamLL = (LinearLayout) findViewById(R.id.mine_team);
        orderLL = (LinearLayout) findViewById(R.id.mine_order);
        taobaoLL = (LinearLayout) findViewById(R.id.mine_taobao_ll);
        courseLL = (LinearLayout) findViewById(R.id.mine_course);
        changePwdLL = (LinearLayout) findViewById(R.id.mine_update_pwd);
        suggestLL = (LinearLayout) findViewById(R.id.mine_suggest);
        shareLL = (LinearLayout) findViewById(R.id.mine_share);
        serviceLL = (LinearLayout) findViewById(R.id.mine_service);
        bussinessLL = (LinearLayout) findViewById(R.id.mine_bussiness);
        loginOutLL = (LinearLayout) findViewById(R.id.mine_login_out);
        userInfoRl = (RelativeLayout) findViewById(R.id.user_info_rl);

        mRefreshLayout.setEnableAutoLoadmore(false);//是否启用列表惯性滑动到底部时自动加载更多
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        // 设置header的高度
        mRefreshLayout.setHeaderHeightPx((int)(YouCommonUtils.getDeviceWitdh(getActivity()) / 4.05));//Header标准高度（显示下拉高度>=标准高度 触发刷新）
    }

    public void setListener() {
        performanceThisMonthTv.setOnClickListener(this);
        performanceLastMonthTv.setOnClickListener(this);
        balanceTv.setOnClickListener(this);
        userInfoRl.setOnClickListener(this);
        incomeLL.setOnClickListener(this);
        teamLL.setOnClickListener(this);
        orderLL.setOnClickListener(this);
        taobaoLL.setOnClickListener(this);
        courseLL.setOnClickListener(this);
        changePwdLL.setOnClickListener(this);
        suggestLL.setOnClickListener(this);
        shareLL.setOnClickListener(this);
        serviceLL.setOnClickListener(this);
        bussinessLL.setOnClickListener(this);
        loginOutLL.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshUI();
            }
        });
    }
    public void initData() {
        alibcShowParams = new AlibcShowParams();

        exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

        String userToken = (String) SPUtils.get(getActivity(), "UserToken", "");
        if (userToken != null) {
            mRefreshLayout.autoRefresh();
        }

        // 获取阿里百川授权信息
        //taobaoAuth();

        // 显示用户信息
        showUserInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_taobao_ll:
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    //taobaoLogin();
                    // 检查是否为合作者
                    checkIsPartner();
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
                break;
            case R.id.mine_performance_this_month:

                break;
            case R.id.mine_performance_last_month:

                break;
            case R.id.mine_balance:
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    Intent walletIntent = new Intent(getActivity(), WalletActivity.class);
                    startActivity(walletIntent);
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
                break;
            case R.id.mine_income:

                break;
            case R.id.mine_team:
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    Intent orderIntent = new Intent(getActivity(), TeamListActivity.class);
                    startActivity(orderIntent);
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
                break;
            case R.id.mine_order:
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    Intent orderIntent = new Intent(getActivity(), OrderListActivity.class);
                    orderIntent.putExtra("orderType", "myOrder");
                    startActivity(orderIntent);
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
                break;
            case R.id.mine_course:
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    startActivity(new Intent(getActivity(), TutorialActivity.class));
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
                break;
            case R.id.mine_update_pwd:
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    startActivityForResult(new Intent(getActivity(), ChangePwdActivity.class), 10003);
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
                break;
            case R.id.mine_suggest:
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    startActivity(new Intent(getActivity(), SuggestActivity.class));
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
                break;
            case R.id.mine_share:
                loginOut();
                break;
            case R.id.mine_service:
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    startActivity(new Intent(getActivity(), ServiceActivity.class));
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
                break;
            case R.id.mine_bussiness:
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    startActivity(new Intent(getActivity(), BussinesActivity.class));
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
                break;
            case R.id.mine_login_out:
                loginOut();
                //taobaoAuth();
                break;
            case R.id.user_info_rl:
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    if (!((String) SPUtils.get(getActivity(), "UserInfo", "")).equals("")) {
                        startActivityForResult(new Intent(getActivity(), UserInfoActivity.class), 10002);
                    } else {
                        startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                        Toast.makeText(getActivity(), "登录信息错误，请重新登录", Toast.LENGTH_LONG).show();
                    }
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(String msg){
        if (msg.equals("LOGIN")) {
            mRefreshLayout.autoRefresh();
        } else {
            mRefreshLayout.autoRefresh();
        }
    }

    /**
     * 显示用戶信息
     */
    private void showUserInfo() {
        String userInfoJson =  (String) SPUtils.get(getActivity(), "UserInfo", "");
        if (!userInfoJson.equals("")){
            mUserInfoModel = JSON.parseObject(userInfoJson, UserInfoOutsideModel.DataBean.class);
            nickTv.setText(mUserInfoModel.getNick_name());
            invitationCodeTv.setVisibility(View.VISIBLE);
            if (mUserInfoModel.getInviteCode() != null) {
                invitationCodeTv.setText("邀请码：" + mUserInfoModel.getInviteCode());
            } else {
                invitationCodeTv.setText("邀请码：点我获取");
            }

            //显示余额
            balanceTv.setText(mUserInfoModel.getBalance() + "\n余额");
            //头像
            x.image().bind(headIv, mUserInfoModel.getHead_img(), options);

            // 检查是否为合作者
            checkIsPartner();
        } else {
            mUserInfoModel = new UserInfoOutsideModel.DataBean();
            x.image().bind(headIv, "", options);
            nickTv.setText("立即登录");
            invitationCodeTv.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(), "您暂未等，请先登录！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 检查是否为合作者
     */
    private void checkIsPartner() {
        if (mUserInfoModel.getRelationId() == 0) {
            //当非合作者提示成为合作者
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("请完成淘宝登录");
            builder.setMessage("淘宝授权后下单或分享产品可以获得收益哦");
            builder.setIcon(R.mipmap.logo);
            //点击对话框以外的区域是否让对话框消失
            builder.setCancelable(true);
            //设置正面按钮
            builder.setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String userInfoJson = (String) SPUtils.get(getActivity(), "UserInfo", "");
                    UserInfoOutsideModel.DataBean userInfoModel = JSON.parseObject(userInfoJson, UserInfoOutsideModel.DataBean.class);
                    Intent intent = new Intent(getActivity(), BecomePartnerActivity.class);
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
            //设置反面按钮
            builder.setNeutralButton("联系客服", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getActivity(), ServiceActivity.class));
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            //显示对话框
            dialog.show();
        } else {
            taobaoAuthTv.setText("尊贵的合作者，快去购物可拿返现金吧！");
        }
    }

    /**
     * 刷新界面
     */
    private void refreshUI() {
        String userToken = (String) SPUtils.get(getActivity(), "UserToken", "");
        String userInfoJson = (String) SPUtils.get(getActivity(), "UserInfo", "");
        if (!userToken.equals("")){
            // 获取本月绩效
            callNetGetPerformanceThisMonth(userToken);
            // 获取上月绩效
            callNetGetPerformanceLastMonth(userToken);
            // 显示用户信息
            showUserInfo();
            getHasPayPw(userToken);
        } else {
            x.image().bind(headIv, "", options);
            nickTv.setText("立即登录");
            invitationCodeTv.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(), "您暂未等，请先登录！", Toast.LENGTH_SHORT).show();
        }

        mRefreshLayout.finishRefresh(1000);
    }

    /**
     * 判断是否有支付密码
     * @param token
     */
    private void getHasPayPw(String token) {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_USER_HAS_PAY_PW + SPUtils.get(getActivity(), "UserToken", ""));
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model =  JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 0) {
                    String hasPayPw =  JSON.toJSONString(model.getData()).toString();
                    if (hasPayPw.equals("true")) {
                        SPUtils.put(getActivity(), "HasPayPw", "1");
                    }
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
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

    /**
     * 取本月绩效
     * @param userToken
     */
    private void callNetGetPerformanceThisMonth(String userToken) {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_PERFORMANCE_THIS_MONTH + SPUtils.get(getActivity(), "UserToken", ""));
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model =  JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 0) {
                    String performanceThisMonth =  model.getData().toString();
                    performanceThisMonthTv.setText(performanceThisMonth + "\n本月预估");
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                performanceThisMonthTv.setText("0" + "\n本月预估");
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                performanceThisMonthTv.setText("0" + "\n本月预估");
            }
            @Override
            public void onFinished() {
            }
        });
    }

    /**
     * 取上月绩效
     * @param userToken
     */
    private void callNetGetPerformanceLastMonth(String userToken) {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_PERFORMANCE_LAST_MONTH + SPUtils.get(getActivity(), "UserToken", ""));
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model =  JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 0) {
                    String performanceLastMonth = model.getData().toString();
                    performanceLastMonthTv.setText(performanceLastMonth + "\n上月预估");
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                performanceLastMonthTv.setText("0" + "\n上月预估");
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                performanceLastMonthTv.setText("0" + "\n上月预估");
            }
            @Override
            public void onFinished() {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 10001) {
                callNetchechIsPartner();
            } else if (requestCode == 10002) {

            } else if (requestCode == 10003) {
                String userName = data.getStringExtra("userName");
                String passWord = data.getStringExtra("passWord");
                callNetLogin(userName, passWord);
            }
        }
    }

    /**
     * 检查是否成为合作者
     */
    private void callNetchechIsPartner() {
        String userInfoJson = (String) SPUtils.get(getActivity(), "UserInfo", "");
        UserInfoOutsideModel.DataBean userInfoModel = JSON.parseObject(userInfoJson, UserInfoOutsideModel.DataBean.class);
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("sessionKey", "610082335d0674b2ae78f3e2a7785821ad848d0632c478e2298128659");
        paramsMap.put("rtag", userInfoModel.getMobile() + userInfoModel.getNick_name());
        paramsMap.put("token", (String) SPUtils.get(getActivity(), "UserToken", ""));
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.CHECK_IS_PARTNER + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model =  JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 0) {
                    String relationId =  model.getData().toString();
                    // 修改用户信息中的relationId
                    UserInfoOutsideModel.DataBean userInfoModel = JSON.parseObject((String) SPUtils.get(getActivity(), "UserInfo", ""), UserInfoOutsideModel.DataBean.class);
                    userInfoModel.setRelationId(Long.parseLong(relationId));

                    String userInfoJson = JSON.toJSONString(model.getData());
                    SPUtils.put(getActivity(), "UserInfo", userInfoJson);

                    // 刷新合作者界面
                    taobaoAuthTv.setText("尊敬的合作者，快去分享或者购买吧！");
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

    /**
     * 刷新登录token
     * @param phone
     * @param pw
     */
    private void callNetLogin(String phone, String pw) {
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("提示");
        dialog.setMessage("刷新登錄中......");
        dialog.setCancelable(false);
        dialog.show();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userName", phone);
        paramsMap.put("passWord", pw);
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.USER_LOGIN + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setAsJsonContent(true);
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.POST ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model = JSON.parseObject(result, CommonModel.class);
                if (model.getStatus() == 0) {
                    SPUtils.put(getActivity(), "UserToken", model.getData().toString());
                    Toast.makeText(getActivity(), "刷新登录信息成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.login_fail) + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(getActivity(), R.string.callnet_timeout, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), R.string.login_fail, Toast.LENGTH_SHORT).show();
                }
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
                dialog.dismiss();
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

    /**
     * 淘宝授权
     */
    public void taobaoLogin() {
        final AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.showLogin(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int loginResult, String openId, String userNick) {
                // 参数说明：
                // loginResult(0--登录初始化成功；1--登录初始化完成；2--登录成功)
                // openId：用户id
                // userNick: 用户昵称
                Toast.makeText(getActivity(), "授权成功 " + AlibcLogin.getInstance().getSession(), Toast.LENGTH_LONG).show();
                // 刷新界面淘宝信息
                taobaoAuth();
                // 查看是否为合作者
                String userInfoJson = (String) SPUtils.get(getActivity(), "UserInfo", "");
                UserInfoOutsideModel.DataBean userInfoModel = JSON.parseObject(userInfoJson, UserInfoOutsideModel.DataBean.class);
                if (userInfoModel != null) {
                    if (userInfoModel.getRelationId() == 0) {
                        //当非合作者提示成为合作者
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("请完成淘宝登录");
                        builder.setMessage("淘宝授权后下单或分享产品可以获得收益哦");
                        builder.setIcon(R.mipmap.ic_launcher_round);
                        //点击对话框以外的区域是否让对话框消失
                        builder.setCancelable(true);
                        //设置正面按钮
                        builder.setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userInfoJson = (String) SPUtils.get(getActivity(), "UserInfo", "");
                                UserInfoOutsideModel.DataBean userInfoModel = JSON.parseObject(userInfoJson, UserInfoOutsideModel.DataBean.class);
                                Intent intent = new Intent(getActivity(), BecomePartnerActivity.class);
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
                        AlertDialog dialog = builder.create();
                        //显示对话框
                        dialog.show();
                    }
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                // code：错误码  msg： 错误信息
                Toast.makeText(getActivity(), "授权失败，" + msg + ":" + code, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 显示淘宝授权信息
     */
    private void taobaoAuth() {
        try {
            //获取淘宝用户信息
            String nick = AlibcLogin.getInstance().getSession().nick;
            String avatarUrl = AlibcLogin.getInstance().getSession().avatarUrl;
            if (nick != null && avatarUrl != null) {
                if (!nick.equals("") && !avatarUrl.equals("")) {
                    taobaoAuthTv.setText(nick);
                } else {
                    taobaoAuthTv.setText("点我授权，购物可拿返现金");
                }
            } else {
                taobaoAuthTv.setText("点我授权，购物可拿返现金");
            }
        } catch (Exception e) {
            taobaoAuthTv.setText("点我授权，购物可拿返现金");
        }
    }

    /**
     * 取消淘宝授权
     */
    private void loginOut() {
        SPUtils.put(getActivity(), "UserToken", "");
        SPUtils.put(getActivity(), "UserInfo", "");
        showUserInfo();
        /*AlibcLogin alibcLogin = AlibcLogin.getInstance();
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
        });*/
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
}
