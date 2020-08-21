package com.youcoupon.john_li.youcouponshopping.YouFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.fastjson.JSON;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OnNavigationBarListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youcoupon.john_li.youcouponshopping.MerchandiseDetialActivity;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouActivity.CategoryListingsActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.SearchResultActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.TeamListActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.WebH5Activity;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.ClassifyItemAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.ClassifyItemRecycleAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.MainClassifyAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.MainHotRefreshAdapter;
import com.youcoupon.john_li.youcouponshopping.YouAdapter.MaterialItemAdapter;
import com.youcoupon.john_li.youcouponshopping.YouModel.MainActivityOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.MainClassifyOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.MaterialClassifyItemOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;
import com.youcoupon.john_li.youcouponshopping.YouView.NoScrollGridView;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.GridLayout.HORIZONTAL;

/**
 * 分类碎片
 * Created by John_Li on 25/5/2018.
 */

public class ClassifyFragment extends LazyLoadFragment implements View.OnClickListener{
    public static String TAG = ClassifyFragment.class.getName();
    private YouHeadView headView;
    private RecyclerView mGv;

    private SkeletonScreen skeletonScreen;
    private RecyclerViewSkeletonScreen.Builder builder;
    private ClassifyItemRecycleAdapter mClassifyItemAdapter;
    private List<MainClassifyOutModel.DataBean.ResultsBean> mainAllClassifyList;
    private List<MainClassifyOutModel.DataBean.ResultsBean.ChildItemBean> mainClassifyList;
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setImageScaleType(ImageView.ScaleType.FIT_XY).setLoadingDrawableId(R.mipmap.img_loading).setFailureDrawableId(R.mipmap.img_loading).build();

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_classify);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        ImmersionBar.with(getActivity()).init();
        headView = (YouHeadView) findViewById(R.id.classify_head);
        mGv = (RecyclerView) findViewById(R.id.classify_gv);
    }

    private void setListener() {
        /*mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mainAllClassifyList.size() > 0) {
                    Intent intent = new Intent(getActivity(), CategoryListingsActivity.class);
                    intent.putExtra("ClassifyModel", JSON.toJSONString(mainAllClassifyList.get(0)));
                    intent.putExtra("Positon", position);
                    startActivity(intent);
                }
            }
        });*/
    }


    private void initData() {
        headView.setTitle("好券推荐");
        mainAllClassifyList = new ArrayList<>();
        mainClassifyList = new ArrayList<>();

        mClassifyItemAdapter = new ClassifyItemRecycleAdapter(mainClassifyList, getActivity());
        mGv.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL,false));
        builder = Skeleton.bind(mGv);
        skeletonScreen = builder
                .adapter(mClassifyItemAdapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(3300)
                .color(R.color.colorLoadingGray)
                .count(6)
                .load(R.layout.item_classify)
                .show();
        //mGv.setAdapter(mClassifyItemAdapter);
        //添加Android自带的分割线
        //mGv.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        callNetGetTitleList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_search_ll:
                startActivity(new Intent(getActivity(), SearchResultActivity.class));
                break;
        }
    }

    /**
     * 分类标题
     */
    private void callNetGetTitleList() {
        Map<String, String> paramsMap = new HashMap<>();
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.CLASSIFY_TITLE);
        params.setConnectTimeout(30 * 1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MainClassifyOutModel model = JSON.parseObject(result, MainClassifyOutModel.class);
                if (model.getStatus() == 0) {
                    mainClassifyList.addAll(model.getData().getResults().get(0).getChildItem());
                    mainAllClassifyList.addAll(model.getData().getResults());
                    mClassifyItemAdapter.notifyDataSetChanged();
                    skeletonScreen.hide();
                } else {
                    Toast.makeText(getApplicationContext(), "获取分类标题列表失败！", Toast.LENGTH_SHORT).show();
                    skeletonScreen.hide();
                    builder.shimmer(false).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "获取分类标题列表失败！", Toast.LENGTH_SHORT).show();
                skeletonScreen.hide();
                builder.shimmer(false).show();
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
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
