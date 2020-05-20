package com.youcoupon.john_li.youcouponshopping;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.fastjson.JSON;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OnKeyboardListener;
import com.youcoupon.john_li.youcouponshopping.YouActivity.BaseActivity;
import com.youcoupon.john_li.youcouponshopping.YouFragment.MainFragment;
import com.youcoupon.john_li.youcouponshopping.YouFragment.MineFragment;
import com.youcoupon.john_li.youcouponshopping.YouFragment.ShopCartFragment;
import com.youcoupon.john_li.youcouponshopping.YouModel.CommonModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.SearchOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioButton park_rb,forum_rb,mine_rb;
    private RadioGroup bottom_group;
    private FragmentManager fm;
    private Fragment cacheFragment;

    private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
    private Map<String, String> exParams;//yhhpass参数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        initView();
        setListener();
        initData();
        checkAPPVersion();

        /*alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
        AlibcTradeSDK.setForceH5(false);
        couponTestTv = (TextView) findViewById(R.id.taobao_coupon_test);
        couponTestTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlibcTrade.show(MainActivity.this, new AlibcPage("https://uland.taobao.com/coupon/edetail?e=IFYm0w6Tqb0GQASttHIRqa0KuqDKtdl7mOe3Zy3ORIrSJ8DRB+FABT4G6p9xPaetXP616cnJp9+s6qWhAtAEiAas4jbyTDVDDfqEFBOhTcxNQ0HDGNWn0sKx7sA6hYu1TG+QkQpnVyCA+Lj8OlpHEGPfrr0N2WBehkvjNmX0iiHk92+M7h46cxOTXEDgQqt83bbMXRH/cNI=&traceId=0bfaef8215272314603452398"), alibcShowParams, null, exParams , new AlibcTradeCallback() {
                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(MainActivity.this, "打开领券界面失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        Toast.makeText(MainActivity.this, "打开领券界面成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/
    }

    @Override
    public void initView() {
        bottom_group = (RadioGroup)findViewById(R.id.bottom_main_group);
        park_rb = (RadioButton) findViewById(R.id.bottom_main);
        forum_rb = (RadioButton) findViewById(R.id.bottom_main_recommend);
        mine_rb = (RadioButton) findViewById(R.id.bottom_main_mine);

        //iv = findViewById(R.id.order_icon);
    }

    @Override
    public void setListener() {
        bottom_group.setOnCheckedChangeListener(this);
    }

    @Override
    public void initData() {
        fm = getSupportFragmentManager();
        FragmentTransaction traslation = fm.beginTransaction();
        cacheFragment = new MainFragment();
        traslation.add(R.id.main_containor,cacheFragment,MainFragment.TAG);
        traslation.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //获取剪切板
        /*ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData data = cm.getPrimaryClip();
        if(data != null) {
            ClipData.Item item = data.getItemAt(0);
            if(item != null) {
                CharSequence content = item.getText();
                if (content != null) {
                    //对剪贴板文字的操作
                    callNetSearchItem(content.toString());
                }
            }
        }*/
    }

    /**
     * 请求网络查询
     * @param str
     */
    private void callNetSearchItem(String str) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageno", String.valueOf(1));
        paramsMap.put("pagesize", String.valueOf(1));
        paramsMap.put("q", str);
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.SEARCH_MATERIAL + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                final SearchOutModel model = JSON.parseObject(result, SearchOutModel.class);
                if (model.getStatus() == 0) {
                    if (model.getData().getResults().size() > 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("检查到您正在寻找");
                        builder.setMessage(model.getData().getResults().get(0).getTitle());
                        builder.setIcon(R.mipmap.ic_launcher_round);
                        //点击对话框以外的区域是否让对话框消失
                        builder.setCancelable(true);
                        //设置正面按钮
                        builder.setPositiveButton("去看看", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, MerchandiseDetialActivity.class);
                                intent.putExtra("MerchandiseModel", JSON.toJSONString(model.getData().getResults().get(0)));
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        //设置反面按钮
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "你点击了不是", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        //显示对话框
                        dialog.show();
                    } else {
                        Toast.makeText(getApplicationContext(), "暂未找到查询列表！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "获取查询列表失敗！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "获取查询列表失敗！", Toast.LENGTH_SHORT).show();
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
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i){
            case R.id.bottom_main:
                park_rb.setTextColor(getResources().getColor(R.color.colorPrimary));
                forum_rb.setTextColor(getResources().getColor(R.color.colorFottor));
                mine_rb.setTextColor(getResources().getColor(R.color.colorFottor));
                switchPages(MainFragment.class,MainFragment.TAG);
                break;
            case R.id.bottom_main_recommend:
                park_rb.setTextColor(getResources().getColor(R.color.colorFottor));
                forum_rb.setTextColor(getResources().getColor(R.color.colorPrimary));
                mine_rb.setTextColor(getResources().getColor(R.color.colorFottor));
                switchPages(ShopCartFragment.class, ShopCartFragment.TAG);
                break;
            case R.id.bottom_main_mine:
                park_rb.setTextColor(getResources().getColor(R.color.colorFottor));
                forum_rb.setTextColor(getResources().getColor(R.color.colorFottor));
                mine_rb.setTextColor(getResources().getColor(R.color.colorPrimary));
                switchPages(MineFragment.class,MineFragment.TAG);
                break;
        }
    }

    private void switchPages(Class<?> cls, String tag){
        FragmentTransaction transaction = fm.beginTransaction();
        if (cacheFragment != null){
            transaction.hide(cacheFragment);
        }
        cacheFragment = fm.findFragmentByTag(tag);
        if (cacheFragment != null){
            transaction.show(cacheFragment);
        } else {
            try{
                cacheFragment = (Fragment) cls.getConstructor().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
            transaction.add(R.id.main_containor, cacheFragment, tag);
        }
        transaction.commit();
    }

    /********************************************版本更新***************************************************/
    private String m_newVerCode; //最新版的版本号
    private String m_appNameStr; //下载到本地要给这个APP命的名字
    private Callback.Cancelable cancelable;// 短點續傳的回調
    private ProgressDialog m_progressDlg;
    private static String m_newApkUrl;//新的apk下载地址
    /**
     * 从服务器获取当前最新版本号
     */
    private void checkAPPVersion() {
        m_progressDlg = new ProgressDialog(MainActivity.this);
        m_progressDlg.setTitle("提示");
        m_progressDlg.setMessage("檢查版本號中......");
        m_progressDlg.setCancelable(false);
        m_progressDlg.setCanceledOnTouchOutside(false);
        //m_progressDlg.show();
        m_appNameStr = "YouCoupon.apk";
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.CHECK_VERSION);
        params.addQueryStringParameter("apkname", m_appNameStr);
        String url = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonModel model = JSON.parseObject(result.toString(), CommonModel.class);
                if (model.getStatus() == 0) {
                    String verCode = model.getData().toString();
                    if (verCode != null) {
                        m_newVerCode = verCode;
                    } else {
                        m_newVerCode = "-1";
                        Toast.makeText(MainActivity.this, "獲取版本號失敗！" + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    m_newVerCode = "-1";
                    Toast.makeText(MainActivity.this, "獲取版本號失敗！" + String.valueOf(model.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                m_newVerCode = "-1";
            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                //m_progressDlg.dismiss();
                int vercode = YouCommonUtils.getVerCode(MainActivity.this.getApplicationContext());
                if (Integer.parseInt(m_newVerCode) > vercode) {
                    doNewVersionUpdate(); // 更新新版本
                } else {
                    notNewVersionDlgShow(); // 提示当前为最新版本
                }
            }
        });
    }

    private void doNewVersionUpdate() {
        int verCode = YouCommonUtils.getVerCode(MainActivity.this.getApplicationContext());
        String verName = YouCommonUtils.getVerName(MainActivity.this.getApplicationContext());

        String str= "當前版本："+verName+" Code:"+verCode+" ,發現新版本："+
                " Code:"+m_newVerCode+" ,是否更新？";
        Dialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("軟件更新").setMessage(str)
                // 设置内容
                .setPositiveButton("更新",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                m_progressDlg.setTitle("正在下載");
                                m_progressDlg.setMessage("請稍後...");
                                downFile(m_newApkUrl);  //开始下载

                                m_progressDlg.setTitle("正在下載");
                                m_progressDlg.setMessage("請稍後...");
                                Map<String, String> paramsMap = new HashMap<>();
                                paramsMap.put("apkname", m_appNameStr);
                                m_newApkUrl = YouConfigor.BASE_URL + YouConfigor.GET_NEW_APK + YouCommonUtils.createLinkStringByGet(paramsMap);
                                downFile(m_newApkUrl);  //开始下载
                            }
                        })
                .setNegativeButton("暫不更新",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 点击"取消"按钮之后退出程序
                                //System.exit(0);
                            }
                        }).create();// 创建

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        // 显示对话框
        dialog.show();
    }

    /**
     *  提示当前为最新版本
     */
    private void notNewVersionDlgShow() {
        int verCode = YouCommonUtils.getVerCode(MainActivity.this.getApplicationContext());
        String verName = YouCommonUtils.getVerName(MainActivity.this.getApplicationContext());
        if(m_newVerCode.equals("-1")) {
            //Toast.makeText(MainActivity.this, "獲取版本號失敗，請重試！", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(MainActivity.this, "當前版本:"+verName+" Code:"+verCode+",已經是最新版本!", Toast.LENGTH_SHORT).show();
        }
    }

    private void downFile(String m_newApkUrl) {
        initProgressDialog();
        // 開始下載
        //设置请求参数
        RequestParams params = new RequestParams(m_newApkUrl);
        params.setAutoResume(true);//设置是否在下载是自动断点续传
        params.setAutoRename(false);//设置是否根据头信息自动命名文件
        params.setSaveFilePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/YouPictures/" + m_appNameStr);
        params.setExecutor(new PriorityExecutor(2, true));//自定义线程池,有效的值范围[1, 3], 设置为3时, 可能阻塞图片加载.
        params.setCancelFast(true);//是否可以被立即停止.
        //下面的回调都是在主线程中运行的,这里设置的带进度的回调
        /**
         * 可取消的任务
         */
        cancelable = x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onCancelled(CancelledException arg0) {
                Log.i("tag", "取消"+Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                Log.i("tag", "onError: 失败"+Thread.currentThread().getName() + "------Throwable:" + arg0.getMessage());
                m_progressDlg.dismiss();
            }

            @Override
            public void onFinished() {
                Log.i("tag", "完成,每次取消下载也会执行该方法"+Thread.currentThread().getName());
                m_progressDlg.dismiss();
            }

            @RequiresApi(api = 26)
            @Override
            public void onSuccess(File arg0) {
                Log.i("tag", "下载成功的时候执行"+Thread.currentThread().getName());
                // 下載完成
                down();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                if (isDownloading) {
                    m_progressDlg.setProgress((int) (current*100/total));
                    Log.i("tag", "下载中,会不断的进行回调:"+Thread.currentThread().getName());
                }
            }

            @Override
            public void onStarted() {
                Log.i("tag", "开始下载的时候执行"+Thread.currentThread().getName());
                m_progressDlg.show();
            }

            @Override
            public void onWaiting() {
                Log.i("tag", "等待,在onStarted方法之前执行"+Thread.currentThread().getName());
            }
        });
    }

    /**
     * 下載完成關閉進度條
     */
    @RequiresApi(api = 26)
    private void down() {
        m_progressDlg.dismiss();
        //update();
        openAPKFile(MainActivity.this, new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/YouPictures", m_appNameStr).getPath());
    }

    /**
     * 打开安装包
     *
     * @param mContext
     * @param fileUri
     */
    @RequiresApi(api = 26)
    public void openAPKFile(Activity mContext, String fileUri) {
        //DataEmbeddingUtil.dataEmbeddingAPPUpdate(fileUri);
        // 核心是下面几句代码
        if (null != fileUri) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                File apkFile = new File(fileUri);
                //兼容7.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(MainActivity.this, "com.youcoupon.john_li.youcouponshopping" + ".fileprovider", apkFile);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                    //兼容8.0
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        boolean hasInstallPermission = MainActivity.this.getPackageManager().canRequestPackageInstalls();
                        if (!hasInstallPermission) {
                            Toast.makeText(MainActivity.this, "hasInstallPermission=" + hasInstallPermission, Toast.LENGTH_LONG);
                            startInstallPermissionMainActivity();
                            return;
                        }
                    }
                } else {
                    intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                if (mContext.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                    mContext.startActivity(intent);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                //DataEmbeddingUtil.dataEmbeddingAPPUpdate(e.toString());
                //YouCommonUtils.makeEventToast(MyApplication.getContext(), MyApplication.getContext().getString(R.string.download_hint), false);
                Toast.makeText(MainActivity.this, "版本更新失敗，您亦可以嘗試去Google Play搜索(优券商城)更新最新版，謝謝！", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionMainActivity() {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 更新APP
     */
    private void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/YouPictures", m_appNameStr)), "application/vnd.android.package-archive");
        this.startActivity(intent);
    }

    /*初始化短點續傳的对话框*/
    private void initProgressDialog() {
        //创建进度条对话框
        m_progressDlg = new ProgressDialog(MainActivity.this);
        //设置标题
        m_progressDlg.setTitle("下載安裝包");
        //设置信息
        m_progressDlg.setMessage("玩命下載中...");
        //设置显示的格式
        m_progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //设置按钮
        m_progressDlg.setButton(ProgressDialog.BUTTON_NEGATIVE, "暫停",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //点击取消正在下载的操作
                //cancelable.cancel();
            }});

        m_progressDlg.show();
    }

}
