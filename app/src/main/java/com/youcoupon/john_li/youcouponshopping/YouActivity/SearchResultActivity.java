package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youcoupon.john_li.youcouponshopping.MerchandiseDetialActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.MaterialItemAdapter;
import com.youcoupon.john_li.youcouponshopping.YouModel.MaterialClassifyItemOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.StatusBarUtil;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;
import com.youcoupon.john_li.youcouponshopping.YouView.FlowLayout;
import com.youcoupon.john_li.youcouponshopping.YouView.NoScrollGridView;

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

public class SearchResultActivity extends AppCompatActivity {
    private SearchView mSearchView;
    private ImageView backIv, searchIv;
    private Toolbar mToolbar;
    private RadioGroup mRg;
    private NoScrollGridView mGv;
    private RefreshLayout mRefreshLayout;
    private RadioGroup keywordRg;
    private CheckBox mHasCouponCb;
    private FlowLayout mFlowLayout;
    private LinearLayout mSearchIntroLL;

    private List<MaterialClassifyItemOutModel.DataBean.MaterialItemModel> searchModelList;
    private String str;
    private String sort = "tk_total_sales";
    private long pageNo = 1;
    private String isDefault = "true";
    private long pageSize = 10;
    private MaterialItemAdapter mMaterialItemAdapter;

    String[] titles = new String[]{"眉笔", "时尚连衣裙", "爽肤水", "积木", "男士内裤",
            "Iphone", "扫地机器人", "毛绒玩具", "名片设计", "打印机"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
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
        mSearchIntroLL = findViewById(R.id.search_intro_ll);
        mSearchView = (SearchView) findViewById(R.id.search_sv);
        backIv = (ImageView) findViewById(R.id.search_back_iv);
        searchIv = (ImageView) findViewById(R.id.search_search_iv);
        keywordRg = findViewById(R.id.search_keyword);
        mHasCouponCb = findViewById(R.id.search_hascoupon);
        mGv = (NoScrollGridView) findViewById(R.id.search_gv);
        mFlowLayout = findViewById(R.id.search_flow_layout);
        mRefreshLayout = (RefreshLayout) findViewById(R.id.search_srl);
        mRefreshLayout.setEnableAutoLoadmore(false);//是否启用列表惯性滑动到底部时自动加载更多
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作

        ImageView icon = mSearchView.findViewById(androidx.appcompat.R.id.search_button);
        icon.setColorFilter(Color.WHITE);
    }

    private void setListener() {
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mHasCouponCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mRefreshLayout.autoRefresh();
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
                if (isDefault.equals("true")){
                    Toast.makeText(SearchResultActivity.this, "没有更多数据了~", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(SearchResultActivity.this, MerchandiseDetialActivity.class);
                intent.putExtra("MerchandiseModel", JSON.toJSONString(searchModelList.get(position)));
                startActivity(intent);
            }
        });
        keywordRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.search_des:
                        sort = "_des";
                        mRefreshLayout.autoRefresh();
                        break;
                    case R.id.search_asc:
                        sort = "_asc";
                        mRefreshLayout.autoRefresh();
                        break;
                    case R.id.search_total_sales:
                        sort = "total_sales";
                        mRefreshLayout.autoRefresh();
                        break;
                    case R.id.search_tk_rate:
                        sort = "tk_rate";
                        mRefreshLayout.autoRefresh();
                        break;
                    case R.id.search_tk_total_sales:
                        sort = "tk_total_sales";
                        mRefreshLayout.autoRefresh();
                        break;
                    case R.id.search_price:
                        sort = "price";
                        mRefreshLayout.autoRefresh();
                        break;
                }
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
                mRefreshLayout.autoRefresh();
                mSearchIntroLL.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                str = newText;
                return false;
            }
        });

        mMaterialItemAdapter = new MaterialItemAdapter(searchModelList, this);
        mGv.setAdapter(mMaterialItemAdapter);

        for (String text : titles) {
            TextView textView = buildLabel(text);
            mFlowLayout.addView(textView);
        }
    }

    /**
     * 请求网络查询商品
     *  @param str
     */
    private void callNetSearchItem(String str) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageno", String.valueOf(pageNo));
        paramsMap.put("pagesize", String.valueOf(pageSize));
        paramsMap.put("q", str);
        paramsMap.put("sort", sort);
        if (mHasCouponCb.isChecked()) {
            paramsMap.put("hasCoupon", String.valueOf(0));
        } else {
            paramsMap.put("hasCoupon", String.valueOf(1));
        }
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.SEARCH_MATERIAL + YouCommonUtils.createLinkStringByGet(paramsMap));
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MaterialClassifyItemOutModel model = JSON.parseObject(result, MaterialClassifyItemOutModel.class);
                if (model.getStatus() == 0) {
                    searchModelList.addAll(model.getData().getMap_data());
                    mMaterialItemAdapter.notifyDataSetChanged();
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


    /**
     * 獲取熱門關鍵詞
     */
    private void callGetHotWord() {
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.SEARCH_HOT_WORD);
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MaterialClassifyItemOutModel model = JSON.parseObject(result, MaterialClassifyItemOutModel.class);
                if (model.getStatus() == 0) {
                    searchModelList.addAll(model.getData().getMap_data());
                    mMaterialItemAdapter.notifyDataSetChanged();
                } else {
                    //Toast.makeText(getApplicationContext(), "获取查询列表失敗！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //Toast.makeText(getApplicationContext(), "获取查询列表失敗！", Toast.LENGTH_SHORT).show();
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

    private TextView buildLabel(final String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setPadding((int)dpToPx(10), (int)dpToPx(3), (int)dpToPx(10), (int)dpToPx(3));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.shape_label_bg);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchView.setQuery(text, true);
            }
        });
        return textView;
    }


    //dp转换成px
    private float dpToPx(float dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
