package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.youcoupon.john_li.youcouponshopping.LoginActivity;
import com.youcoupon.john_li.youcouponshopping.MainActivity;
import com.youcoupon.john_li.youcouponshopping.MerchandiseDetialActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

public class XinrenH5Activity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    private BridgeWebView mWebView;
    private LinearLayout web_h5_tv;

    private String title;
    private String webUrl;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 123) {
                web_h5_tv.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinren_h5);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        headView = findViewById(R.id.xinren_h5_head);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            headView.setHeadHight();
        }

        web_h5_tv = findViewById(R.id.xinren_h5_tv);
        mWebView = findViewById(R.id.xinren_h5_wv);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        webUrl = intent.getStringExtra("webUrl");
        headView.setTitle(title);
        headView.setLeft(this);
        headView.setRightText("规则", this);
        //支持javascript
        WebSettings webSettings = mWebView.getSettings();
        //设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        //支持javascript
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        mWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mHandler.sendEmptyMessage(123);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        mWebView.loadUrl("http://118.190.1.209:8082/xinrenmiandan/index.html");
        mWebView.registerHandler("onClickNewcomeItem", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(XinrenH5Activity.this, MerchandiseDetialActivity.class);
                intent.putExtra("MerchandiseModel", data);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_left:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.head_right_tv:
                Intent intent = new Intent(XinrenH5Activity.this, WebH5Activity.class);
                intent.putExtra("title", "新人免单规则");
                intent.putExtra("webUrl", "https://test-pic-666.oss-cn-hongkong.aliyuncs.com/0html/YouCoupon/newcome_free.html");
                startActivity(intent);
                break;
        }
    }
}
