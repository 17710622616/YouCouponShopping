package com.youcoupon.john_li.youcouponshopping.YouFragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

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
import com.alibaba.fastjson.JSONObject;
import com.mob.MobSDK;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youcoupon.john_li.youcouponshopping.LoginActivity;
import com.youcoupon.john_li.youcouponshopping.MainActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouActivity.BecomePartnerActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.BussinesActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.ChangePwdActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.OrderListActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.OrderRetrievalActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.PerformanceActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.ServiceActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.SuggestActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.TeamListActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.TutorialActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.UserInfoActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.VisitorRetrievalActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.WalletActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.WalletRecrodActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.WebH5Activity;
import com.youcoupon.john_li.youcouponshopping.YouModel.CommonModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.PerformanceOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.UserInfoOutsideModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.PhotoUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.QrCodeUtil;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import static android.app.Activity.RESULT_OK;

/**
 * Created by John_Li on 25/5/2018.
 */

public class MineFragment extends LazyLoadFragment implements View.OnClickListener{
    public static String TAG = MineFragment.class.getName();
    private TextView nickTv, invitationCodeTv,taobaoAuthTv, performanceThisMonthTv, performanceLastMonthTv, balanceTv;
    private ImageView headIv;//userInfoLL
    private RelativeLayout userInfoRl;
    private RefreshLayout mRefreshLayout;
    private LinearLayout taobaoLL,courseLL,changePwdLL, suggestLL, shareLL,orderRetrievalLL, visitorBindLL, serviceLL, bussinessLL, commonLL, loginOutLL;
    private LinearLayout incomeLL, teamLL, orderLL;
    private ProgressDialog dialog;

    private File dir; //圖片文件夾路徑
    private File fileUri;//照片文件路徑
    private Uri imageUri;//照片文件路徑
    private static final int CODE_GALLERY_REQUEST = 2;   //0xa0
    private static final int CODE_CAMERA_REQUEST = 1;    //0xa1
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    //RequestOptions options = new RequestOptions().placeholder(R.mipmap.head_boy).error(R.mipmap.load_img_fail_list).diskCacheStrategy(DiskCacheStrategy.ALL).override(Target.SIZE_ORIGINAL);
    private AsyncTask asyncTask;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:     // 頭像提交至OSS成功
                    mUserInfoModel.setHead_img(fileUri.getName());
                    Toast.makeText(getActivity(), "上傳頭像成功！", Toast.LENGTH_LONG).show();
                    break;
                case -1:    // 頭像提交至OSS失敗
                    Toast.makeText(getActivity(), "上傳頭像失败！", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
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
        orderRetrievalLL = (LinearLayout) findViewById(R.id.mine_bind_order);
        visitorBindLL = (LinearLayout) findViewById(R.id.mine_bind_inviter);
        commonLL = (LinearLayout) findViewById(R.id.mine_common);
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
        commonLL.setOnClickListener(this);
        orderRetrievalLL.setOnClickListener(this);
        visitorBindLL.setOnClickListener(this);
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
        //showUserInfo();
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
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    Intent performanceIntent = new Intent(getActivity(), PerformanceActivity.class);
                    startActivity(performanceIntent);
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
                break;
            case R.id.mine_performance_last_month:
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    Intent performanceIntent = new Intent(getActivity(), PerformanceActivity.class);
                    startActivity(performanceIntent);
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
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
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    Intent walletRecrodIntent = new Intent(getActivity(), WalletRecrodActivity.class);
                    startActivity(walletRecrodIntent);
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
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
                /*if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    startActivity(new Intent(getActivity(), TutorialActivity.class));
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }*/

                Intent intent = new Intent(getActivity(), WebH5Activity.class);
                intent.putExtra("title", "教程讲解");
                intent.putExtra("webUrl", "https://test-pic-666.oss-cn-hongkong.aliyuncs.com/0html/YouCoupon/tutorial.html");
                startActivity(intent);
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
                mobShare();
                break;
            case R.id.mine_service:
                /*if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    startActivity(new Intent(getActivity(), ServiceActivity.class));
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }*/
                Intent intent1 = new Intent(getActivity(), WebH5Activity.class);
                intent1.putExtra("title", "关于我们");
                intent1.putExtra("webUrl", "https://test-pic-666.oss-cn-hongkong.aliyuncs.com/0html/YouCoupon/about_us.html");
                startActivity(intent1);
                break;
            case R.id.mine_common:
                /*if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    startActivity(new Intent(getActivity(), ServiceActivity.class));
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }*/
                Intent intent2 = new Intent(getActivity(), WebH5Activity.class);
                intent2.putExtra("title", "常见问题");
                intent2.putExtra("webUrl", "https://test-pic-666.oss-cn-hongkong.aliyuncs.com/0html/YouCoupon/common_problem.html");
                startActivity(intent2);
                break;
            case R.id.mine_bussiness:
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    startActivity(new Intent(getActivity(), BussinesActivity.class));
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
                break;
            case R.id.mine_bind_inviter:
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    if (!((String) SPUtils.get(getActivity(), "UserInfo", "")).equals("")) {
                        startActivityForResult(new Intent(getActivity(), VisitorRetrievalActivity.class), 10004);
                    } else {
                        startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                        Toast.makeText(getActivity(), "登录信息错误，请重新登录", Toast.LENGTH_LONG).show();
                    }
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
                break;
            case R.id.mine_bind_order:
                if (!((String) SPUtils.get(getActivity(), "UserToken", "")).equals("")) {
                    if (!((String) SPUtils.get(getActivity(), "UserInfo", "")).equals("")) {
                        startActivityForResult(new Intent(getActivity(), OrderRetrievalActivity.class), 10005);
                    } else {
                        startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                        Toast.makeText(getActivity(), "登录信息错误，请重新登录", Toast.LENGTH_LONG).show();
                    }
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
                        startActivityForResult(new Intent(getActivity(), UserInfoActivity.class), 10006);
                    } else {
                        startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                        Toast.makeText(getActivity(), "登录信息错误，请重新登录", Toast.LENGTH_LONG).show();
                    }
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), YouConfigor.LOGIN_FOR_RQUEST);
                }
                break;
            case R.id.dialog_app_wechat:
                break;
            case R.id.dialog_app_whatsapp:
                break;
            case R.id.dialog_app_fb:
                break;
        }
    }

    private void mobShare() {
        /*OnekeyShare oks = new OnekeyShare();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("优券商城");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://easybangbangda.top");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("优券商城");
        // setImageUrl是网络图片的url
        oks.setImageUrl("https://test-pic-666.oss-cn-hongkong.aliyuncs.com/0YouCoupon/img/logo.png");
        // url在微信、Facebook等平台中使用
        oks.setUrl("http://easybangbangda.top");
        // 启动分享GUI
        oks.show(MobSDK.getContext());*/
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("加載");
        progressDialog.setMessage("生成圖片中......");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        String userInfoJson = (String) SPUtils.get(getActivity(), "UserInfo", "");
        mUserInfoModel = JSON.parseObject(userInfoJson, UserInfoOutsideModel.DataBean.class);
        String visitorCode = "";
        if (mUserInfoModel != null) {
            if (mUserInfoModel.getInviteCode() != null) {
                visitorCode = mUserInfoModel.getInviteCode();
            } else {
                visitorCode = "WQKIRV";
            }
        } else {
            visitorCode = "WQKIRV";
        }
        final String content="http://118.190.1.209:8083/?visitorCode=" + visitorCode;
        /*Bitmap qrCodeBitmap = QrCodeUtil.createQRCode(content, 54);
        Bitmap bgBitmap = ((BitmapDrawable)getResources().getDrawable(R.mipmap.share1)).getBitmap();
        final Bitmap shareBitmap = YouCommonUtils.mergeBitmap(bgBitmap,qrCodeBitmap, 57,177);*/
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_SHARE_HAIBAO + visitorCode);
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                final CommonModel model =  JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 0) {
                    new DownImage(content, progressDialog).execute(model.getData());;
                } else {
                    if (progressDialog != null) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                    Toast.makeText(getActivity(), String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), "获取分享海报异常，请重试！", Toast.LENGTH_SHORT).show();
                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
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

    class DownImage extends AsyncTask {
        private String content;
        private ProgressDialog progressDialog;
        public DownImage(String content, ProgressDialog progressDialog) {
            this.content = content;
            this.progressDialog = progressDialog;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String url = objects[0].toString();
            Bitmap bitmap = null;
            try {
                //加载一个网络图片
                InputStream is = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (progressDialog != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
            openShareDialog((Bitmap) o, content);
        }
    }

    private void openShareDialog(final Bitmap shareBitmap, final String content) {
        if (shareBitmap != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            final View v = inflater.inflate(R.layout.dialog_app_share, null);
            v.setBackgroundResource(R.color.colorAlpha);
            ImageView dialog_app_wechat_iv = (ImageView) v.findViewById(R.id.dialog_app_wechat_iv);
            LinearLayout wechat = (LinearLayout) v.findViewById(R.id.dialog_app_wechat);
            LinearLayout whatsapp = (LinearLayout) v.findViewById(R.id.dialog_app_whatsapp);
            LinearLayout fb = (LinearLayout) v.findViewById(R.id.dialog_app_fb);
            //TextView shareTv = (TextView) v.findViewById(R.id.dialog_share_tv);
            ImageView haibaoIv = v.findViewById(R.id.dialog_app_iv);
            ImageView cancelIv = v.findViewById(R.id.dialog_app_cancel);
            final Dialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
            //dialog.setCancelable(true);
            cancelIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            wechat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String filePath = YouCommonUtils.saveToLocal(shareBitmap, "shareHaiBao", getActivity());
                    Platform.ShareParams sp = new Platform.ShareParams();
                    sp.setTitle("优券商城-全网返利最高");
                    sp.setText("优券商城-全网返利最高");
                    //sp.setImagePath("https://test-pic-666.oss-cn-hongkong.aliyuncs.com/0YouCoupon/img/logo.png");
                    sp.setImagePath(filePath);
                    sp.setImageUrl(content);
                    sp.setShareType(Platform.SHARE_WEBPAGE);
                    //3.获取平台对象
                    Platform wechatPF = ShareSDK.getPlatform(Wechat.NAME);
                    //4.设置结果回调
                    wechatPF.setPlatformActionListener(new PlatformActionListener() {

                        @Override
                        public void onError(Platform arg0, int arg1, Throwable arg2) {
                            //操作失败啦，打印提供的错误，方便调试
                            arg2.printStackTrace();
                            Toast.makeText(getActivity(), "分享失败！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                            //操作成功，在这里可以做后续的步骤
                            //这里需要说明的一个参数就是HashMap<String, Object> arg2
                            //这个参数在你进行登录操作的时候里面会保存有用户的数据，例如用户名之类的。
                            //Toast.makeText(getActivity(), "分享成功！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel(Platform arg0, int arg1) {
                            //用户取消操作会调用这里
                            Toast.makeText(getActivity(), "您已取消分享！", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //5.执行分享
                    wechatPF.share(sp);
                    dialog.dismiss();
                }
            });
            whatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    YouCommonUtils.saveToLocal(shareBitmap, "shareHaiBao", getActivity());
                    dialog.dismiss();
                }
            });
            fb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //YouCommonUtils.saveToLocal(shareBitmap, "shareHaiBao", getActivity());
                    //String path = BitmapHelper.downloadBitmap(MobSDK.getContext(), "http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
                    //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.kehu, null);
                    Platform.ShareParams shareParams = new Platform.ShareParams();
                    //shareParams.setImagePath(path);
                    //或者使用inmagedata参数
                    shareParams.setImageData(shareBitmap);
                    shareParams.setShareType(Platform.SHARE_IMAGE);
                    Platform platform = ShareSDK.getPlatform(Facebook.NAME);
                    // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
                    platform.setPlatformActionListener(new PlatformActionListener() {
                        @Override
                        public void onError(Platform arg0, int arg1, Throwable arg2) {
                            //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                            Log.d("ShareSDK", "onError ---->  分享失败" + arg2.toString());
                            Log.d("ShareSDK","ThreadID -----> : "+Thread.currentThread().getId());
                        }

                        @Override
                        public void onComplete(Platform arg0, int arg1, HashMap arg2) {
                            //分享成功的回调
                            Log.d("ShareSDK", "onError ---->  分享cg" + arg2.toString());
                        }

                        @Override
                        public void onCancel(Platform arg0, int arg1) {
                            //取消分享的回调
                            Log.d("ShareSDK", "onError ---->  分享xq" );
                        }
                    });
                    platform.share(shareParams);
                    dialog.dismiss();
                }
            });
            haibaoIv.setImageBitmap(shareBitmap);
        } else {
            Toast.makeText(getActivity(), "获取分享海报失败，请重试！", Toast.LENGTH_SHORT).show();
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

            //头像
            x.image().bind(headIv, mUserInfoModel.getHead_img(), options);

            // 检查是否为合作者
            checkIsPartner();
            // 获取余额
            callNetGetBalance();
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
            getHasPayPw();
        } else {
            x.image().bind(headIv, "", options);
            nickTv.setText("立即登录");
            invitationCodeTv.setVisibility(View.INVISIBLE);
            performanceThisMonthTv.setText("0.0元\n本月预估");
            performanceLastMonthTv.setText("0.0元\n上月预估");
            balanceTv.setText("0.0元\n余额");
            Toast.makeText(getActivity(), "您暂未等，请先登录！", Toast.LENGTH_SHORT).show();
        }

        mRefreshLayout.finishRefresh(1000);
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
                PerformanceOutModel model =  JSON.parseObject(result.toString(), PerformanceOutModel.class);
                if (model.getStatus() == 0) {
                    String performanceThisMonth =  model.getData().getCommisonAmount().toString();
                    performanceThisMonthTv.setText(performanceThisMonth + "元\n本月预估");
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                performanceThisMonthTv.setText("0" + "元\n本月预估");
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                performanceThisMonthTv.setText("0" + "元\n本月预估");
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
                PerformanceOutModel model =  JSON.parseObject(result.toString(), PerformanceOutModel.class);
                if (model.getStatus() == 0) {
                    String performanceLastMonth = model.getData().getCommisonAmount().toString();
                    performanceLastMonthTv.setText(performanceLastMonth + "元\n上月预估");
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                performanceLastMonthTv.setText("0" + "元\n上月预估");
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                performanceLastMonthTv.setText("0" + "元\n上月预估");
            }
            @Override
            public void onFinished() {
            }
        });
    }

    /**
     * 判断是否有支付密码
     */
    private void getHasPayPw() {
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
     * 获取余额
     */
    private void callNetGetBalance() {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_BALANCE);
        params.addQueryStringParameter("token", String.valueOf(SPUtils.get(getActivity(), "UserToken", "")));
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model = JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 0) {
                    balanceTv.setText(model.getData().toString() + "元\n余额");
                } else {
                    Toast.makeText(getActivity(), getString(R.string.balance_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.request_error), Toast.LENGTH_SHORT).show();
                }
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch(requestCode) {
                case 10001:
                    callNetchechIsPartner();
                    break;
                case 10002:
                    break;
                case 10003:
                    String userName = data.getStringExtra("userName");
                    String passWord = data.getStringExtra("passWord");
                    callNetLogin(userName, passWord);
                    break;
                default:
                    break;
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

                    String userInfoJson = JSON.toJSONString(userInfoModel);
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
        refreshUI();
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
