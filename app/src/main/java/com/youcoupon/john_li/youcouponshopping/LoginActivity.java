package com.youcoupon.john_li.youcouponshopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.core.model.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.gyf.immersionbar.ImmersionBar;
import com.youcoupon.john_li.youcouponshopping.YouActivity.BaseActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.ForgetPwdActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.RegisterActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.WebH5Activity;
import com.youcoupon.john_li.youcouponshopping.YouModel.CommonModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.SmsOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.UserInfoOutsideModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.SPUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by John_Li on 2019/10/30.
 */

public class LoginActivity extends BaseActivity {
    private TextView registerTv, forgetTv, userAggrementTV, secretServiceTV;
    private Button loginBtn;
    private EditText phoneEt, pwEt;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initImmersionBar();
        initView();
        setListener();
        initData();
    }

    public  void initImmersionBar() {
        ImmersionBar.with(this).titleBar(R.id.login_toolbar).keyboardEnable(true).init();
    }

    @Override
    public void initView() {
        loginBtn = findViewById(R.id.btn_login);
        registerTv = findViewById(R.id.tv_register);
        forgetTv = findViewById(R.id.tv_forget_password);
        phoneEt = findViewById(R.id.login_et_phone);
        pwEt = findViewById(R.id.login_et_password);
        userAggrementTV = findViewById(R.id.login_secret_service_tv);
        secretServiceTV = findViewById(R.id.login_user_aggrement_tv);
    }

    @Override
    public void setListener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneEt.getText().toString();
                String pw = pwEt.getText().toString();
                if (phone != null && pw != null) {
                    callNetLogin(phone, pw, "");
                } else {
                    Toast.makeText(LoginActivity.this, "请填写账户密码！", Toast.LENGTH_LONG).show();
                }
            }
        });

        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), 10001);
            }
        });

        forgetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, ForgetPwdActivity.class), 10002);
            }
        });
        userAggrementTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, WebH5Activity.class);
                intent.putExtra("title", "隐私服务协议");
                intent.putExtra("webUrl", "https://test-pic-666.oss-cn-hongkong.aliyuncs.com/0html/YouCoupon/secret_service.html");
                startActivity(intent);
            }
        });
        secretServiceTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, WebH5Activity.class);
                intent.putExtra("title", "用户服务协议");
                intent.putExtra("webUrl", "https://test-pic-666.oss-cn-hongkong.aliyuncs.com/0html/YouCoupon/user_agreement.html");
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {

    }

    private void callNetLogin(String phone, String pw, final String verifica) {
        dialog = new ProgressDialog(this);
        dialog.setTitle("提示");
        dialog.setMessage("正在登錄中......");
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
                    SPUtils.put(LoginActivity.this, "UserToken", model.getData().toString());
                    if (!verifica.equals("")) {
                        // 绑定上下级关系
                        callSubmitInvitationCode(verifica,  model.getData().toString());
                    } else {
                        // 获取用户信息
                        getUserInfo(verifica, model.getData().toString());
                    }
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_fail) + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dialog.dismiss();
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(LoginActivity.this, R.string.callnet_timeout, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.login_fail, Toast.LENGTH_SHORT).show();
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

    /**
     * 獲取用戶信息
     * @param verifica
     * @param token
     */
    private void getUserInfo(final String verifica, final String token) {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.GET_USER_INFO + token);
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UserInfoOutsideModel model = JSON.parseObject(result.toString(), UserInfoOutsideModel.class);
                if (model.getStatus() == 0) {
                    if (model.getData().getNick_name() == null) {
                        model.getData().setNick_name("用戶");
                    }
                    if (model.getData().getAddress() == null) {
                        model.getData().setAddress("");
                    }
                    if (model.getData().getReal_name() == null) {
                        model.getData().setReal_name("");
                    }
                    if (model.getData().getAddress() == null) {
                        model.getData().setAddress("");
                    }
                    if (model.getData().getDescx() == null) {
                        model.getData().setDescx("");
                    }
                    if (model.getData().getHead_img() == null) {
                        model.getData().setHead_img("");
                    }
                    if (model.getData().getBirth_day() == null) {
                        model.getData().setBirth_day("");
                    }
                    if (model.getData().getInviteCode() == null) {
                        model.getData().setBirth_day("");
                    }
                    String userInfoJson = JSON.toJSONString(model.getData());
                    SPUtils.put(LoginActivity.this, "UserInfo", userInfoJson);
                    Log.d("getUserURI", "獲取用戶信息成功");

                    // 註冊極光別名
                    String alias = "user" + model.getData().getMobile();
                    //给极光推送设置标签和别名
                    JPushInterface.setAlias(LoginActivity.this, alias, tagAliasCallback);
                    //Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                    //setResult(YouConfigor.LOGIN_FOR_RESULT);
                    EventBus.getDefault().post("LOGIN");
                    finish();
                } else {
                    Log.d("getUserURI", "獲取用戶信息失敗");
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(LoginActivity.this, R.string.get_userinfo_timeout, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.get_userinfo_fail, Toast.LENGTH_SHORT).show();
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

    //极光服务器设置别名是否成功的回调
    private final TagAliasCallback tagAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tagSet) {
            switch (code) {
                case 0:
                    //Toast.makeText(LoginActivity.this, "设置别名成功", Toast.LENGTH_SHORT).show();
                    Log.d("EasyLaundry", "设置别名成功");
                    break;
                default:
                    //Toast.makeText(LoginActivity.this, "设置别名失败", Toast.LENGTH_SHORT).show();
                    Log.d("EasyLaundry", "设置别名失败");
                    break;
            }
        }
    };

    /**
     * 绑定上下级关系
     */
    private void callSubmitInvitationCode(final String verifica, final String token) {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.SUBMIT_INVITATION_CODE);
        params.addQueryStringParameter("invitationCode", verifica);
        params.addQueryStringParameter("token", token);
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SmsOutModel model = JSON.parseObject(result.toString(), SmsOutModel.class);
                if (model.getStatus() == 0) {
                    Toast.makeText(LoginActivity.this, "绑定上下级关系成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "绑定上下级关系失败，请手动绑定！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(LoginActivity.this, "绑定上下级关系失败，请手动绑定！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                // 获取用户信息
                getUserInfo(verifica, token);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10001) {
                String userName = data.getStringExtra("userName");
                String passWord = data.getStringExtra("passWord");
                String verifica = data.getStringExtra("verifica");
                if (!verifica.equals("")) {
                    callNetLogin(userName, passWord, verifica);
                } else {
                    callNetLogin(userName, passWord, "");
                }
            } else if (requestCode == 10002) {
                String userName = data.getStringExtra("userName");
                String passWord = data.getStringExtra("passWord");
                callNetLogin(userName, passWord, "");
            }
        }
    }
}
