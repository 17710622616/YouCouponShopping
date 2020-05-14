package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.OrderFgAdapter;
import com.youcoupon.john_li.youcouponshopping.YouFragment.FirstLevelUnderLineFragment;
import com.youcoupon.john_li.youcouponshopping.YouFragment.MyOrderFragment;
import com.youcoupon.john_li.youcouponshopping.YouFragment.SecondLevelUnderLineFragment;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderListActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    public String [] sTitle = null;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String orderType ="myOrder";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        //,getResources().getString(R.string.giving)
        sTitle = new String[]{"我的订单","Ⅰ级下线订单","Ⅱ级下线订单"};
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        headView = findViewById(R.id.order_head);
        mViewPager = (ViewPager) findViewById(R.id.order_view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.order_tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[2]));
    }

    @Override
    public void setListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        headView.setTitle("訂單列表");
        headView.setLeft(this);

        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[2]));
        mTabLayout.setupWithViewPager(mViewPager);
        List<Fragment> fgList = new ArrayList<>();
        fgList.add(new MyOrderFragment());
        fgList.add(new FirstLevelUnderLineFragment());
        fgList.add(new SecondLevelUnderLineFragment());

        OrderFgAdapter adapter = new OrderFgAdapter(getSupportFragmentManager(),fgList, Arrays.asList(sTitle));
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(adapter);

        orderType = getIntent().getStringExtra("orderType");
        switch (orderType) {
            case "myOrder":
                mViewPager.setCurrentItem(0);
                break;
            case "firstLevel":
                mViewPager.setCurrentItem(1);
                break;
            case "secondLevel":
                mViewPager.setCurrentItem(2);
                break;
            default:
                mViewPager.setCurrentItem(0);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.head_left:
                finish();
                break;
        }
    }
}
