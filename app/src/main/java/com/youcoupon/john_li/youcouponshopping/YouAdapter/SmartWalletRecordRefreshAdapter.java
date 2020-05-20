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
import com.youcoupon.john_li.youcouponshopping.YouModel.UserOrderOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.WithDrawalDetialModel;

import org.w3c.dom.Text;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by John_Li on 28/02/2020.
 */

public class SmartWalletRecordRefreshAdapter extends RecyclerView.Adapter<SmartWalletRecordRefreshAdapter.SmartRefreshViewHolder> implements View.OnClickListener {
    private List<WithDrawalDetialModel.DataBean.WalletRecordListBean> list;
    private final Context mContext;
    private LayoutInflater mInflater;
    private LruCache<String ,BitmapDrawable> mMemoryCache;
    private OnItemClickListener mOnitemClickListener = null;
    private InnerItemOnclickListener mListener;
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setLoadingDrawableId(R.mipmap.img_loading).setFailureDrawableId(R.mipmap.load_img_fail).build();

    public SmartWalletRecordRefreshAdapter(Context context, List<WithDrawalDetialModel.DataBean.WalletRecordListBean> list) {
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
        View view = mInflater.inflate(R.layout.item_wallet_record, parent, false);
        SmartRefreshViewHolder vh = new SmartRefreshViewHolder(view);
        vh.item_wr_remark = (TextView) view.findViewById(R.id.item_wr_remark);
        vh.item_wr_time = (TextView) view.findViewById(R.id.item_wr_time);
        vh.item_wr_no = (TextView) view.findViewById(R.id.item_wr_no);
        vh.item_wr_num = (TextView) view.findViewById(R.id.item_wr_num);
        vh.item_wr_MOP = (TextView) view.findViewById(R.id.item_wr_MOP);
        vh.item_wr_type = (TextView) view.findViewById(R.id.item_wr_type);
        return vh;
    }

    @Override
    public void onBindViewHolder(SmartRefreshViewHolder holder, int position) {
        holder.item_wr_remark.setText(list.get(position).getDes());
        holder.item_wr_time.setText("创建时间:" + list.get(position).getCreateTime());
        holder.item_wr_no.setText("流水编号:" + list.get(position).getId());
        holder.item_wr_MOP.setText("RMB:");
        holder.item_wr_num.setText(String.valueOf(list.get(position).getAmount()));
        if (list.get(position).getFlag() == 0) {
            holder.item_wr_type.setText("+");
        } else {
            holder.item_wr_type.setText("-");
        }
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
        public TextView item_wr_remark;
        public TextView item_wr_time;
        public TextView item_wr_no;
        public TextView item_wr_num;
        public TextView item_wr_MOP;
        public TextView item_wr_type;
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

    public void refreshListView(List<WithDrawalDetialModel.DataBean.WalletRecordListBean> newList) {
        list = newList;
        notifyDataSetChanged();
    }
}
