package com.youcoupon.john_li.youcouponshopping.YouAdapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.MainClassifyOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.TeamMemberOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

public class ClassifyItemRecycleAdapter extends RecyclerView.Adapter<ClassifyItemRecycleAdapter.ClassifyViewHolder> implements View.OnClickListener {
    private List<MainClassifyOutModel.DataBean.ResultsBean.ChildItemBean> list;
    private Context mContext;
    private LayoutInflater mInflater;
    private ImageOptions options;
    private ClassifyItemRecycleAdapter.OnItemClickListener mOnitemClickListener = null;

    public ClassifyItemRecycleAdapter(List<MainClassifyOutModel.DataBean.ResultsBean.ChildItemBean> list, Context context) {
        this.list = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        int screemWith = YouCommonUtils.getDeviceWitdh(context);
        int imgWitdh = screemWith / 2 - 5;
        options = new ImageOptions.Builder().setSize(imgWitdh, imgWitdh).setImageScaleType(ImageView.ScaleType.CENTER_INSIDE).setLoadingDrawableId(R.mipmap.img_loading).setFailureDrawableId(R.mipmap.load_img_fail).build();
    }
    @Override
    public ClassifyItemRecycleAdapter.ClassifyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_classify, parent, false);
        ClassifyItemRecycleAdapter.ClassifyViewHolder vh = new ClassifyItemRecycleAdapter.ClassifyViewHolder(view);
        vh.item_classify_iv = (ImageView) view.findViewById(R.id.item_classify_iv);
        vh.item_classify_tv = (TextView) view.findViewById(R.id.item_classify_tv);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ClassifyItemRecycleAdapter.ClassifyViewHolder holder, int position) {
        x.image().bind(holder.item_classify_iv, list.get(position).getImg_url(), options);
        holder.item_classify_tv.setText(list.get(position).getChild_activity_title());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ClassifyViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_classify_iv;
        public TextView item_classify_tv;
        public ClassifyViewHolder(View view){
            super(view);
        }
    }
    @Override
    public void onClick(View v) {
        if (mOnitemClickListener != null) {
            mOnitemClickListener.onItemClick(v, (int)v.getTag());
        }
    }

    public void setOnItemClickListenr(ClassifyItemRecycleAdapter.OnItemClickListener onItemClickListener) {
        this.mOnitemClickListener = onItemClickListener;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
