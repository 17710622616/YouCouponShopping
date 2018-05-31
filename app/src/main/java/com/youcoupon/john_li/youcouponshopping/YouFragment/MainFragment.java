package com.youcoupon.john_li.youcouponshopping.YouFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youcoupon.john_li.youcouponshopping.MerchandiseDetialActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.MainMerchandiseAdapter;
import com.youcoupon.john_li.youcouponshopping.YouModel.MerchandiseOutModel;
import com.youcoupon.john_li.youcouponshopping.YouView.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页碎片
 * Created by John_Li on 25/5/2018.
 */

public class MainFragment extends LazyLoadFragment implements View.OnClickListener{
    public static String TAG = MainFragment.class.getName();
    private ImageView taoqianggouIV, juhuasuanIV;
    private TabLayout mTabLayout;
    private NoScrollListView mListView;
    private RefreshLayout mRefreshLayout;

    private List<MerchandiseOutModel.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> mMerchandiseList;
    private MainMerchandiseAdapter mMainMerchandiseAdapter;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_main);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        taoqianggouIV = (ImageView) findViewById(R.id.main_taoqianggou);
        juhuasuanIV = (ImageView) findViewById(R.id.main_juhuasuan);
        mTabLayout = (TabLayout) findViewById(R.id.title_tabLayout);
        mRefreshLayout = (RefreshLayout) findViewById(R.id.main_srl);
        mListView = (NoScrollListView) findViewById(R.id.main_lv);

        mRefreshLayout.setEnableAutoLoadmore(false);//是否启用列表惯性滑动到底部时自动加载更多
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        // 设置header的高度
        // mRefreshLayout.setHeaderHeightPx((int)(BSSMCommonUtils.getDeviceWitdh(getActivity()) / 4.05));//Header标准高度（显示下拉高度>=标准高度 触发刷新）
    }

    private void setListener() {
        taoqianggouIV.setOnClickListener(this);
        juhuasuanIV.setOnClickListener(this);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                callNetGetMerchandiseList();
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mMerchandiseList.clear();
                callNetGetMerchandiseList();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MerchandiseDetialActivity.class);
                intent.putExtra("MerchandiseModel", JSON.toJSONString(mMerchandiseList.get(position)));
                startActivity(intent);
            }
        });
    }


    private void initData() {
        mMerchandiseList = new ArrayList<>();
        mMainMerchandiseAdapter = new MainMerchandiseAdapter(mMerchandiseList, getActivity());
        mListView.setAdapter(mMainMerchandiseAdapter);

        callNetGetTitleList();
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_taoqianggou:
                break;
            case R.id.main_juhuasuan:
                break;
        }
    }

    private void callNetGetTitleList() {
        String [] sTitle = new String[]{"精品推薦","時尚女裝","潮流男裝", "珠寶首飾", "生活用品", "數碼電器", "計生用品"};

        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[2]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[3]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[4]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[5]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[6]));
    }

    private void callNetGetMerchandiseList() {
        String reslut = "{\"tbk_uatm_favorites_item_get_response\":{\"results\":{\"uatm_tbk_item\":[{\"category\":16,\"click_url\":\"https:\\/\\/s.click.taobao.com\\/t?e=m%3D2%26s%3D9eXo1%2B4wb0xw4vFB6t2Z2ueEDrYVVa64XoO8tOebS%2Bfjf2vlNIV67nKx%2F0WMtCwsjGYPrSmetxH9VCSQqTLsa4%2BDpQeWfDJaVpEUn89CVjnJsAA91U3vce%2FBas0tvb4eZz42eVlqqJT6BXppXS5x5z0BDlc5vrJjopr%2FSerwmYPPQZX9jO9tUbYtcWZSGtno5Zhxu%2BbPYAutNnoIVcZmJueY5tUZuWkpxgxdTc00KD8%3D\",\"coupon_click_url\":\"https:\\/\\/uland.taobao.com\\/coupon\\/edetail?e=n7qaPzoNPcAGQASttHIRqQkZPCfQqsO4V1yHWHmoq9WPr5ljijO76C2hIi8KRT8qiiDmULtsMvCJI6EXzi0xFL9fwBwwUiqlCfDTD%2F74gDLJnadaiBmiH%2FSs4zOlZ4se\",\"coupon_end_time\":\"2018-06-30\",\"coupon_info\":\"满100元减10元\",\"coupon_remain_count\":98688,\"coupon_start_time\":\"2018-05-26\",\"coupon_total_count\":100000,\"event_end_time\":\"1970-01-01 00:00:00\",\"event_start_time\":\"1970-01-01 00:00:00\",\"item_url\":\"http:\\/\\/h5.m.taobao.com\\/awp\\/core\\/detail.htm?id=569092747231\",\"nick\":\"du嘟嘟家\",\"num_iid\":569092747231,\"pict_url\":\"https:\\/\\/img.alicdn.com\\/tfscom\\/i4\\/450662946\\/TB2FCOfhsyYBuNkSnfoXXcWgVXa_!!450662946.jpg\",\"provcity\":\"广东 广州\",\"reserve_price\":\"109.80\",\"seller_id\":450662946,\"shop_title\":\"嘟嘟家 酷风来袭\",\"small_images\":{\"string\":[\"https:\\/\\/img.alicdn.com\\/tfscom\\/i2\\/450662946\\/TB2WV.dpL1TBuNjy0FjXXajyXXa_!!450662946.jpg\",\"https:\\/\\/img.alicdn.com\\/tfscom\\/i3\\/450662946\\/TB2toDQpH1YBuNjSszhXXcUsFXa_!!450662946.jpg\",\"https:\\/\\/img.alicdn.com\\/tfscom\\/i4\\/450662946\\/TB2j6T2pQCWBuNjy0FaXXXUlXXa_!!450662946.jpg\",\"https:\\/\\/img.alicdn.com\\/tfscom\\/i2\\/450662946\\/TB2FuGihOOYBuNjSsD4XXbSkFXa_!!450662946.jpg\"]},\"status\":1,\"title\":\"半身牛仔裙女2018新款夏季韩版宽松百搭学生BF风潮ins超火的裙子\",\"tk_rate\":\"5.00\",\"type\":1,\"user_type\":0,\"volume\":89,\"zk_final_price\":\"109.80\",\"zk_final_price_wap\":\"109.80\"},{\"category\":16,\"click_url\":\"https:\\/\\/s.click.taobao.com\\/t?e=m%3D2%26s%3D1pgEMyGO8blw4vFB6t2Z2ueEDrYVVa64XoO8tOebS%2Bfjf2vlNIV67nKx%2F0WMtCwsjGYPrSmetxH9VCSQqTLsa4%2BDpQeWfDJaVpEUn89CVjnJsAA91U3vce%2FBas0tvb4eZz42eVlqqJT6BXppXS5x5z0BDlc5vrJjopr%2FSerwmYM1LTXXCD%2BOUvsQkLppJOqGUgJOt%2FgUtXY4GU9PM8GyritwjvUPA%2BzkcSpj5qSCmbA%3D\",\"coupon_click_url\":\"https:\\/\\/uland.taobao.com\\/coupon\\/edetail?e=axaQcZXDXDAGQASttHIRqelATBH1eoytSuNX%2F0cyFv6Pr5ljijO76C2hIi8KRT8qiiDmULtsMvCJI6EXzi0xFL9fwBwwUiqlCfDTD%2F74gDLJnadaiBmiH%2FSs4zOlZ4se\",\"coupon_end_time\":\"2018-05-30\",\"coupon_info\":\"满200元减30元\",\"coupon_remain_count\":2150,\"coupon_start_time\":\"2018-05-24\",\"coupon_total_count\":5000,\"event_end_time\":\"1970-01-01 00:00:00\",\"event_start_time\":\"1970-01-01 00:00:00\",\"item_url\":\"http:\\/\\/h5.m.taobao.com\\/awp\\/core\\/detail.htm?id=569079538463\",\"nick\":\"yueliang1985415\",\"num_iid\":569079538463,\"pict_url\":\"https:\\/\\/img.alicdn.com\\/tfscom\\/i2\\/TB1M0zRpv9TBuNjy1zbYXFpepXa_M2.SS2\",\"provcity\":\"广东 深圳\",\"reserve_price\":\"538.00\",\"seller_id\":87253698,\"shop_title\":\"s秀名媛馆 高端女装\",\"small_images\":{\"string\":[\"https:\\/\\/img.alicdn.com\\/tfscom\\/i1\\/TB1PfxfpStYBeNjSspkYXHU8VXa_M2.SS2\",\"https:\\/\\/img.alicdn.com\\/tfscom\\/i7\\/TB1ptDRpv9TBuNjy1zbYXFpepXa_M2.SS2\",\"https:\\/\\/img.alicdn.com\\/tfscom\\/i7\\/TB1QQ6XpwmTBuNjy1XbYXGMrVXa_M2.SS2\",\"https:\\/\\/img.alicdn.com\\/tfscom\\/i3\\/TB1e1bQpv5TBuNjSspcYXHnGFXa_M2.SS2\"]},\"status\":1,\"title\":\"s秀2018夏女新款时尚港风ins超火T恤中长款蓬蓬网纱裙套装两件套\",\"tk_rate\":\"15.00\",\"type\":1,\"user_type\":0,\"volume\":9,\"zk_final_price\":\"269.00\",\"zk_final_price_wap\":\"269.00\"},{\"category\":16,\"click_url\":\"https:\\/\\/s.click.taobao.com\\/t?e=m%3D2%26s%3Di%2FBrGRTeS91w4vFB6t2Z2ueEDrYVVa64XoO8tOebS%2Bfjf2vlNIV67nKx%2F0WMtCwsjGYPrSmetxH9VCSQqTLsa4%2BDpQeWfDJaVpEUn89CVjnJsAA91U3vce%2FBas0tvb4eZz42eVlqqJT6BXppXS5x5z0BDlc5vrJjopr%2FSerwmYOsSowblonTuNBI2Mmq9AhlJIVU%2B7iDh6VxwM1syVBzrB9hW2JKu7YsDJbuZDCrHt4%3D\",\"coupon_click_url\":\"https:\\/\\/uland.taobao.com\\/coupon\\/edetail?e=BFcTFUzIkqMGQASttHIRqbWqjL2%2FMu%2BcS85%2BAekcBQyPr5ljijO76C2hIi8KRT8qiiDmULtsMvCJI6EXzi0xFL9fwBwwUiqlCfDTD%2F74gDLJnadaiBmiH%2FSs4zOlZ4se\",\"coupon_end_time\":\"2018-05-31\",\"coupon_info\":\"满100元减10元\",\"coupon_remain_count\":9211,\"coupon_start_time\":\"2018-05-28\",\"coupon_total_count\":10000,\"event_end_time\":\"1970-01-01 00:00:00\",\"event_start_time\":\"1970-01-01 00:00:00\",\"item_url\":\"http:\\/\\/h5.m.taobao.com\\/awp\\/core\\/detail.htm?id=569600285991\",\"nick\":\"ericsha0007\",\"num_iid\":569600285991,\"pict_url\":\"https:\\/\\/img.alicdn.com\\/tfscom\\/i3\\/2904804747\\/TB2xD5afOMnBKNjSZFzXXc_qVXa_!!2904804747.jpg\",\"provcity\":\"北京\",\"reserve_price\":\"219.00\",\"seller_id\":2904804747,\"shop_title\":\"胡月明 AMARIS HOO\",\"small_images\":{\"string\":[\"https:\\/\\/img.alicdn.com\\/tfscom\\/i4\\/2904804747\\/TB2.ieyi98YBeNkSnb4XXaevFXa_!!2904804747.jpg\",\"https:\\/\\/img.alicdn.com\\/tfscom\\/i3\\/2904804747\\/TB2pB6OrbSYBuNjSspfXXcZCpXa_!!2904804747.jpg\",\"https:\\/\\/img.alicdn.com\\/tfscom\\/i3\\/2904804747\\/TB28dnBrb9YBuNjy0FgXXcxcXXa_!!2904804747.jpg\",\"https:\\/\\/img.alicdn.com\\/tfscom\\/i2\\/2904804747\\/TB24R_Wrf1TBuNjy0FjXXajyXXa_!!2904804747.jpg\"]},\"status\":1,\"title\":\"大月月定制 连衣裙女夏2018新款露肩显瘦雪纺中长款ins超火的裙子\",\"tk_rate\":\"5.00\",\"type\":1,\"user_type\":0,\"volume\":124,\"zk_final_price\":\"169.00\",\"zk_final_price_wap\":\"169.00\"},{\"category\":16,\"click_url\":\"https:\\/\\/s.click.taobao.com\\/t?e=m%3D2%26s%3Dy1u4cF%2FNJaVw4vFB6t2Z2ueEDrYVVa64yK8Cckff7TXjf2vlNIV67nKx%2F0WMtCwsjGYPrSmetxH9VCSQqTLsa4%2BDpQeWfDJaVpEUn89CVjnJsAA91U3vce%2FBas0tvb4eZz42eVlqqJT6BXppXS5x5z0BDlc5vrJjopr%2FSerwmYPdauuLewEf2KoSINHC3AbXjMTCuk80ROq0G5roL0JvhR9hW2JKu7YsDJbuZDCrHt4%3D\",\"coupon_click_url\":\"https:\\/\\/uland.taobao.com\\/coupon\\/edetail?e=NwKW9uiRK4cGQASttHIRqfrywtHuKwTXzTlmBU7yF6GPr5ljijO76C2hIi8KRT8qiiDmULtsMvCJI6EXzi0xFL9fwBwwUiqlCfDTD%2F74gDLJnadaiBmiH%2FSs4zOlZ4se\",\"coupon_end_time\":\"2018-06-10\",\"coupon_info\":\"满99元减10元\",\"coupon_remain_count\":99720,\"coupon_start_time\":\"2018-05-29\",\"coupon_total_count\":100000,\"event_end_time\":\"1970-01-01 00:00:00\",\"event_start_time\":\"1970-01-01 00:00:00\",\"item_url\":\"http:\\/\\/h5.m.taobao.com\\/awp\\/core\\/detail.htm?id=564944086098\",\"nick\":\"韩都衣舍敏丽专卖店\",\"num_iid\":564944086098,\"pict_url\":\"https:\\/\\/img.alicdn.com\\/tfscom\\/i4\\/1111900536\\/TB1iBEQt1uSBuNjSsplXXbe8pXa_!!0-item_pic.jpg\",\"provcity\":\"山东 济南\",\"reserve_price\":\"288.00\",\"seller_id\":1111900536,\"shop_title\":\"韩都衣舍敏丽专卖店\",\"small_images\":{\"string\":[\"https:\\/\\/img.alicdn.com\\/tfscom\\/i4\\/263817957\\/TB2hdpbXiQnBKNjSZFmXXcApVXa-263817957.jpg\",\"https:\\/\\/img.alicdn.com\\/tfscom\\/i1\\/263817957\\/TB2jvBbXbZnBKNjSZFKXXcGOVXa-263817957.jpg\",\"https:\\/\\/img.alicdn.com\\/tfscom\\/i1\\/263817957\\/TB2gWlWXiCYBuNkSnaVXXcMsVXa-263817957.jpg\",\"https:\\/\\/img.alicdn.com\\/tfscom\\/i2\\/263817957\\/TB24grdXbSYBuNjSspiXXXNzpXa-263817957.jpg\"]},\"status\":1,\"title\":\"韩都衣舍2018女装夏装条纹收腰气质小清新短款美美的夏夏连衣裙\",\"tk_rate\":\"6.51\",\"type\":1,\"user_type\":1,\"volume\":6,\"zk_final_price\":\"165.00\",\"zk_final_price_wap\":\"165.00\"},{\"category\":16,\"click_url\":\"https:\\/\\/s.click.taobao.com\\/t?e=m%3D2%26s%3Dprj2C0SXqqpw4vFB6t2Z2ueEDrYVVa64XoO8tOebS%2Bfjf2vlNIV67nKx%2F0WMtCwsjGYPrSmetxH9VCSQqTLsa4%2BDpQeWfDJaVpEUn89CVjnJsAA91U3vce%2FBas0tvb4eZz42eVlqqJT6BXppXS5x5z0BDlc5vrJjopr%2FSerwmYOCMHtpFwM8vWOcw86FTGy78rZl6sW6CV5IJ6G0ZTXBaytwjvUPA%2BzkcSpj5qSCmbA%3D\",\"event_end_time\":\"1970-01-01 00:00:00\",\"event_start_time\":\"1970-01-01 00:00:00\",\"item_url\":\"http:\\/\\/h5.m.taobao.com\\/awp\\/core\\/detail.htm?id=565152848615\",\"nick\":\"fengshengj\",\"num_iid\":565152848615,\"pict_url\":\"https:\\/\\/img.alicdn.com\\/tfscom\\/i4\\/90489528\\/TB1_KncbuuSBuNjSsplXXbe8pXa_!!0-item_pic.jpg\",\"provcity\":\"浙江 杭州\",\"reserve_price\":\"299.00\",\"seller_id\":90489528,\"shop_title\":\"日着設計師服飾品牌\",\"small_images\":{\"string\":[\"https:\\/\\/img.alicdn.com\\/tfscom\\/i4\\/90489528\\/TB2mKrcbuuSBuNjSsplXXbe8pXa_!!90489528.jpg\",\"https:\\/\\/img.alicdn.com\\/tfscom\\/i4\\/90489528\\/TB2tOLpbv1TBuNjy0FjXXajyXXa_!!90489528.jpg\",\"https:\\/\\/img.alicdn.com\\/tfscom\\/i3\\/90489528\\/TB2CsPmbpuWBuNjSspnXXX1NVXa_!!90489528.jpg\",\"https:\\/\\/img.alicdn.com\\/tfscom\\/i4\\/90489528\\/TB2l92ebxSYBuNjSsphXXbGvVXa_!!90489528.jpg\"]},\"status\":1,\"title\":\"日着原创设计女装【你是407吗】2018夏季新款宽松不对称解构T恤\",\"tk_rate\":\"5.00\",\"type\":1,\"user_type\":0,\"volume\":98,\"zk_final_price\":\"299.00\",\"zk_final_price_wap\":\"299.00\"}]},\"total_results\":5,\"request_id\":\"10fk4ls6lekjw\"}}";
        MerchandiseOutModel model = JSON.parseObject(reslut, MerchandiseOutModel.class);
        mMerchandiseList.addAll(model.getTbk_uatm_favorites_item_get_response().getResults().getUatm_tbk_item());
        mMainMerchandiseAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh(1000);
        mRefreshLayout.finishLoadmore(1000);
    }
}
