package com.youcoupon.john_li.youcouponshopping;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
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
import com.youcoupon.john_li.youcouponshopping.YouModel.SearchOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
