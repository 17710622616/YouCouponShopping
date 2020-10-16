package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.SplashActivity;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.FirstLoadAdapter;

import java.util.ArrayList;
import java.util.List;

public class FirstLoadActivity extends BaseActivity {
    /** 声明viewpager引用 */
    private ViewPager viewPager;
    /** 上一个页面位置 */
    private int lostPositin;
    /** 图片视图集合 */
    private List<View> ivList = new ArrayList<View>();
    /** 圆点图片数组 */
    private LinearLayout linearLayout;
    /** 图片地址数组 */
    private int[] img = {R.mipmap.splash1,R.mipmap.splash2};
    private boolean flag;
    private FirstLoadAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_load);
        ImmersionBar.with(this).navigationBarColor(R.color.colorWhite).init();
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        viewPager = (ViewPager) findViewById(R.id.first_vp);
        linearLayout = (LinearLayout) findViewById(R.id.first_group_ll);
    }

    @Override
    public void setListener() {
        // TODO Auto-generated method stub
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub
                Log.d("vivi", "onPageScrollStateChanged: " + state);
                switch(state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        //拖的时候才进入下一页
                        flag = false;
                        Log.d("vivi", "SCROLL_STATE_DRAGGING: " + ViewPager.SCROLL_STATE_DRAGGING);
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        flag = true;
                        Log.d("vivi", "SCROLL_STATE_SETTLING: " + ViewPager.SCROLL_STATE_SETTLING);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        Log.d("vivi", "SCROLL_STATE_IDLE: " + ViewPager.SCROLL_STATE_IDLE+"  mViewPager.getCurrentItem() "+viewPager.getCurrentItem());
                        /**
                         * 判断是不是最后一页，同是是不是拖的状态
                         */
                        if(viewPager.getCurrentItem() == adapter.getCount() - 1 && !flag) {
                            if (getIntent().getStringExtra("startWay") != null) {
                                if (!getIntent().getStringExtra("startWay").equals("")) {
                                    finish();
                                } else {
                                    Intent intent = new Intent(FirstLoadActivity.this, SplashActivity.class);
                                    intent.putExtra("startWay", "first");
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Intent intent = new Intent(FirstLoadActivity.this, SplashActivity.class);
                                intent.putExtra("startWay", "first");
                                startActivity(intent);
                                finish();
                            }
                        }
                        flag = true;
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                linearLayout.getChildAt(position).setEnabled(true);
                linearLayout.getChildAt(lostPositin).setEnabled(false);

                lostPositin = position;
            }

        });
    }

    @Override
    public void initData() {
        // 初始化三个图片视图
        View iv01 = (View) LayoutInflater.from(this).inflate(R.layout.first_load_activity_01, null);
        View iv02 = (View) LayoutInflater.from(this).inflate(R.layout.first_load_activity_01, null);
        iv02.setBackgroundResource(R.mipmap.splash2);
        ivList.add(iv01);
        ivList.add(iv02);

        for (int i = 0; i < img.length; i++) {
            // 初始化三个图片视图
			/*ImageView iv = new ImageView(this);
			iv.setBackgroundResource(img[i]);

			ivList.add(iv);*/

            // 添加圆点
            ImageView pointIV = new ImageView(this);
            pointIV.setBackgroundResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            params.setMargins(0, 0, 20, 0);
            pointIV.setLayoutParams(params);
            pointIV.setScaleType(ImageView.ScaleType.FIT_XY);

            if (i == 0) {
                pointIV.setEnabled(true);
            } else {
                pointIV.setEnabled(false);
            }
            linearLayout.addView(pointIV);
        }

        adapter = new FirstLoadAdapter(ivList);
        viewPager.setAdapter(adapter);
    }

    /**
     * 开启主页面
     * @param view
     */
    public void startMainActivity(View view) {
        if (getIntent().getStringExtra("startWay") != null) {
            if (!getIntent().getStringExtra("startWay").equals("")) {
                finish();
            } else {
                Intent intent = new Intent(this, SplashActivity.class);
                intent.putExtra("startWay", "first");
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(this, SplashActivity.class);
            intent.putExtra("startWay", "first");
            startActivity(intent);
        }
    }
}
