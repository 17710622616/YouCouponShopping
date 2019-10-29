package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youcoupon.john_li.youcouponshopping.MerchandiseDetialActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.FavoritesItemAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.SearchItemAdapter;
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteItemOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.ItemInfoOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.SearchOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.SellerOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.StatusBarUtil;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by John_Li on 17/4/2019.
 */

public class SearchActivity extends AppCompatActivity {
    private SearchView mSearchView;
    private ImageView backIv, searchIv;
    private Toolbar mToolbar;
    private RadioGroup mRg;
    private GridView mGv;
    private RefreshLayout mRefreshLayout;

    private List<SearchOutModel.DataBean.SearchModel> searchModelList;
    private String str;
    private long totalCount = 100;
    private long pageNo = 1;
    private long pageSize = 10;
    private SearchItemAdapter mSearchItemAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorPrimary));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();
        setListener();
        initData();
    }

    private void initView() {
        mSearchView = (SearchView) findViewById(R.id.search_sv);
        backIv = (ImageView) findViewById(R.id.search_back_iv);
        searchIv = (ImageView) findViewById(R.id.search_search_iv);
        mGv = (GridView) findViewById(R.id.search_gv);
        mRefreshLayout = (RefreshLayout) findViewById(R.id.search_srl);
        mRefreshLayout.setEnableAutoLoadmore(false);//是否启用列表惯性滑动到底部时自动加载更多
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
    }

    private void setListener() {
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        searchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str != null) {
                    if (!str.equals("")) {
                        searchModelList.clear();
                        callNetSearchItem(str);
                    }
                }
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //和最大的数据比较
                if (pageSize * (pageNo + 1) > totalCount){
                    Toast.makeText(SearchActivity.this, "没有更多数据了~", Toast.LENGTH_SHORT).show();
                    mRefreshLayout.finishRefresh();
                    mRefreshLayout.finishLoadmore();
                } else {
                    pageNo ++;
                    callNetSearchItem(str);
                }
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNo = 1;
                searchModelList.clear();
                callNetSearchItem(str);
            }
        });
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, MerchandiseDetialActivity.class);
                intent.putExtra("MerchandiseModel", JSON.toJSONString(searchModelList.get(position)));
                startActivity(intent);
            }
        });
    }

    private void initData() {
        // 獲取歷史數據
        searchModelList = new ArrayList<>();

        // searview
        //设置搜索框直接展开显示。左侧有放大镜(在搜索框中) 右侧有叉叉 可以关闭搜索框
        //mSearchView.setIconified(false);
        //设置搜索框直接展开显示。左侧有放大镜(在搜索框外) 右侧无叉叉 有输入内容后有叉叉 不能关闭搜索框
        //mSearchView.setIconifiedByDefault(false);
        //设置搜索框直接展开显示。左侧有无放大镜(在搜索框中) 右侧无叉叉 有输入内容后有叉叉 不能关闭搜索框
        mSearchView.onActionViewExpanded();
        mSearchView.setQueryHint("请输入需要查询的商品名称");

        // 修改搜索框控件间的间隔
        LinearLayout search_edit_frame = mSearchView.findViewById(R.id.search_edit_frame);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) search_edit_frame.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 10;
        search_edit_frame.setLayoutParams(params);

        // searchview字變化的監聽
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                str = query;
                searchModelList.clear();
                callNetSearchItem(str);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                str = newText;
                return false;
            }
        });

        mSearchItemAdapter = new SearchItemAdapter(searchModelList, this);
        mGv.setAdapter(mSearchItemAdapter);
    }

    /**
     * 请求网络查询
     * @param str
     */
    private void callNetSearchItem(String str) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageno", String.valueOf(pageNo));
        paramsMap.put("pagesize", String.valueOf(pageSize));
        paramsMap.put("q", str);
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.COUPON_SEARCH_LIST + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SearchOutModel model = JSON.parseObject(result, SearchOutModel.class);
                if (model.getStatus() == 0) {
                    searchModelList.addAll(model.getData().getResults());
                    mSearchItemAdapter.notifyDataSetChanged();
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
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
            }
        });
    }
}
