package com.youcoupon.john_li.youcouponshopping.YouAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.MainClassifyOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.MerchandiseOutModel;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by John_Li on 8/4/2019.
 */

public class MainClassifyAdapter extends RecyclerView.Adapter<MainClassifyAdapter.MainClassifyViewHolder> implements View.OnClickListener {
    private List<MainClassifyOutModel.DataBean.ResultsBean> list;
    private int[] iconArr = {R.mipmap.exemption_from_postage99,R.mipmap.time_limit,R.mipmap.boutique,R.mipmap.guide,R.mipmap.head_boy};
    private Context mContext;
    private LayoutInflater mInflater;
    private MainClassifyAdapter.OnItemClickListener mOnitemClickListener = null;

    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setImageScaleType(ImageView.ScaleType.FIT_XY).setLoadingDrawableId(R.mipmap.img_loading).setFailureDrawableId(R.mipmap.load_img_fail).build();
    public MainClassifyAdapter(List<MainClassifyOutModel.DataBean.ResultsBean> list, Context context) {
        this.list = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MainClassifyAdapter.MainClassifyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_main_classify, parent, false);
        MainClassifyAdapter.MainClassifyViewHolder vh = new MainClassifyAdapter.MainClassifyViewHolder(view);
        vh.item_main_classify_iv = (ImageView) view.findViewById(R.id.item_main_classify_iv);
        vh.item_main_classify_tv = (TextView) view.findViewById(R.id.item_main_classify_tv);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(MainClassifyAdapter.MainClassifyViewHolder holder, int position) {
        /*if (iconArr.length - 1 < position) {
            holder.item_main_classify_iv.setImageResource(R.mipmap.loading);
        } else {
            holder.item_main_classify_iv.setImageResource(iconArr[position]);
        }*/
        x.image().bind(holder.item_main_classify_iv, list.get(position).getImg_url(), options);
        holder.item_main_classify_tv.setText(list.get(position).getActivity_title());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        /*if (list.size() > 5) {
            return 5;
        }*/

        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnitemClickListener != null) {
            mOnitemClickListener.onItemClick(v, (int)v.getTag());
        }
    }

    public void setOnItemClickListenr(MainClassifyAdapter.OnItemClickListener onItemClickListener) {
        this.mOnitemClickListener = onItemClickListener;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MainClassifyViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_main_classify_iv;
        public TextView item_main_classify_tv;
        public MainClassifyViewHolder (View view){
            super(view);
        }
    }
}
