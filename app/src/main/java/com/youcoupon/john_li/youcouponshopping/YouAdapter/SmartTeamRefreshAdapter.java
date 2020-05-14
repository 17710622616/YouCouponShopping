package com.youcoupon.john_li.youcouponshopping.YouAdapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.TeamMemberOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.UserOrderOutModel;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by John_Li on 28/02/2020.
 */

public class SmartTeamRefreshAdapter extends RecyclerView.Adapter<SmartTeamRefreshAdapter.SmartRefreshViewHolder> implements View.OnClickListener {
    private List<TeamMemberOutModel.DataBean.DataModelsBean> list;
    private final Context mContext;
    private LayoutInflater mInflater;
    private LruCache<String ,BitmapDrawable> mMemoryCache;
    private OnItemClickListener mOnitemClickListener = null;
    private InnerItemOnclickListener mListener;
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setLoadingDrawableId(R.mipmap.img_loading).setFailureDrawableId(R.mipmap.load_img_fail).build();

    public SmartTeamRefreshAdapter(Context context, List<TeamMemberOutModel.DataBean.DataModelsBean> list) {
        this.list = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        //计算内存，并且给Lrucache 设置缓存大小
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory/6;
        mMemoryCache = new LruCache<String ,BitmapDrawable>(cacheSize){
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                return  value.getBitmap().getByteCount();
            }
        };
    }
    @Override
    public SmartRefreshViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_team_list, parent, false);
        SmartRefreshViewHolder vh = new SmartRefreshViewHolder(view);
        vh.item_team_iv = (ImageView) view.findViewById(R.id.item_team_iv);
        vh.item_team_tv = (TextView) view.findViewById(R.id.item_team_tv);
        return vh;
    }

    @Override
    public void onBindViewHolder(SmartRefreshViewHolder holder, int position) {
        String imgUrl = list.get(position).getHead_img().contains("http") ? list.get(position).getHead_img() : "http:" + list.get(position).getHead_img();
        x.image().bind(holder.item_team_iv, imgUrl, options);
        holder.item_team_tv.setText(list.get(position).getNick_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface InnerItemOnclickListener {
        void itemClick(View v);
    }

    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener) {
        this.mListener = listener;
    }
    @Override
    public void onClick(View v) {
        /*if (mOnitemClickListener != null) {
            mOnitemClickListener.onItemClick(v, (int)v.getTag());
        }*/

        mListener.itemClick(v);
    }

    public class SmartRefreshViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_team_iv;
        public TextView item_team_tv;
        public SmartRefreshViewHolder(View view){
            super(view);
        }
    }

    public void setOnItemClickListenr(OnItemClickListener listenr) {
        this.mOnitemClickListener = listenr;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void refreshListView(List<TeamMemberOutModel.DataBean.DataModelsBean> newList) {
        list = newList;
        notifyDataSetChanged();
    }
}
