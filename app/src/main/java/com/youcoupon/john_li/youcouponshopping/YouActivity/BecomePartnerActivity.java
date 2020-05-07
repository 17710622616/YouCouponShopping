package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.gyf.immersionbar.ImmersionBar;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class BecomePartnerActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    //private SubsamplingScaleImageView mIv;
    private WebView mWebView;
    ProgressBar progressBar;
    private String rtag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_partner);
        ImmersionBar.with(this).init();
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        headView = findViewById(R.id.about_us_head);
        mWebView = findViewById(R.id.about_us_wb);
        progressBar = findViewById(R.id.about_us_pb);
        /*mIv = findViewById(R.id.about_us_tv);
        mIv.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        mIv.setMinScale(1.0F);*/
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {
        //callNetGetVerCode();
        headView.setLeft(this);
        headView.setTitle("成为合作者");

        rtag = getIntent().getStringExtra("rtag");
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        //注入接口
        //js_java_interaction.html文件中的toastMessage(),sumToJava()方法中的window后面的字段
        mWebView.addJavascriptInterface(new JsInteration(), "control");
        mWebView.setWebChromeClient(new WebChromeClient() {});
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        mWebView.loadUrl("https://mos.m.taobao.com/inviter/register?inviterCode=WQKIRV&src=pub&app=common&rtag=" + rtag);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_left:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    //返回结果的处理
    public class JsInteration {

        @JavascriptInterface
        public void toastMessage(String message) {

        }
        //		onSumResult字段和 window.control.onSumResult(number1 + number2)中的onSumResult一样
        @JavascriptInterface
        public void onSumResult(int result) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            setResult(RESULT_OK);
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
