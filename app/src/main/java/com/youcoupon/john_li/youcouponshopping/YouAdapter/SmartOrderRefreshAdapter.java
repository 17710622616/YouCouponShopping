package com.youcoupon.john_li.youcouponshopping.YouAdapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.UserOrderOutModel;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by John_Li on 28/02/2020.
 */

public class SmartOrderRefreshAdapter extends RecyclerView.Adapter<SmartOrderRefreshAdapter.SmartRefreshViewHolder> implements View.OnClickListener {
    private List<UserOrderOutModel.DataBean.OrderListBean> list;
    private final Context mContext;
    private LayoutInflater mInflater;
    private LruCache<String ,BitmapDrawable> mMemoryCache;
    private OnItemClickListener mOnitemClickListener = null;
    private InnerItemOnclickListener mListener;
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setLoadingDrawableId(R.mipmap.img_loading).setFailureDrawableId(R.mipmap.load_img_fail).build();

    public SmartOrderRefreshAdapter(Context context, List<UserOrderOutModel.DataBean.OrderListBean> list) {
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
        View view = mInflater.inflate(R.layout.item_order_info, parent, false);
        SmartRefreshViewHolder vh = new SmartRefreshViewHolder(view);
        vh.item_order_iv = (ImageView) view.findViewById(R.id.item_order_iv);
        vh.item_order_name = (TextView) view.findViewById(R.id.item_order_name);
        vh.item_order_shop_name = (TextView) view.findViewById(R.id.item_order_shop_name);
        vh.item_order_status = (TextView) view.findViewById(R.id.item_order_status);
        vh.item_create_time = (TextView) view.findViewById(R.id.item_create_time);
        vh.item_order_no = (TextView) view.findViewById(R.id.item_order_no);
        vh.item_pay_money = (TextView) view.findViewById(R.id.item_pay_money);
        vh.item_order_income = (TextView) view.findViewById(R.id.item_order_income);
        return vh;
    }

    @Override
    public void onBindViewHolder(SmartRefreshViewHolder holder, int position) {
        String imgUrl = list.get(position).getItem_img().contains("http") ? list.get(position).getItem_img() : "http:" + list.get(position).getItem_img();
        x.image().bind(holder.item_order_iv, imgUrl, options);
        holder.item_order_name.setText(list.get(position).getItem_title());
        holder.item_order_shop_name.setText(list.get(position).getSeller_shop_title());
        holder.item_order_no.setText("订单编号:" + list.get(position).getTrade_id());
        holder.item_create_time.setText("创建时间:" + list.get(position).getTk_create_time());
        holder.item_pay_money.setText("付款金额\n" + list.get(position).getPay_price());
        holder.item_order_income.setText("预估收入\n" + list.get(position).getIncome_rate());
        switch (list.get(position).getTk_status()) {
            case 3:
                holder.item_order_status.setText("待结算");
                holder.item_order_status.setBackground(mContext.getResources().getDrawable(R.drawable.shape_order_status));
                break;
            case 12:
                holder.item_order_status.setText("订单付款");
                holder.item_order_status.setBackground(mContext.getResources().getDrawable(R.drawable.shape_order_status));
                break;
            case 13:
                holder.item_order_status.setText("订单失效");
                holder.item_order_status.setBackground(mContext.getResources().getDrawable(R.drawable.shape_order_status_gray));
                break;
            case 14:
                holder.item_order_status.setText("订单成功");
                holder.item_order_status.setBackground(mContext.getResources().getDrawable(R.drawable.shape_order_status));
                break;
            case 20:
                holder.item_order_status.setText("结算成功");
                holder.item_order_status.setBackground(mContext.getResources().getDrawable(R.drawable.shape_order_status_green));
                break;
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
        public ImageView item_order_iv;
        public TextView item_order_name;
        public TextView item_order_shop_name;
        public TextView item_order_status;
        public TextView item_order_no;
        public TextView item_create_time;
        public TextView item_pay_money;
        public TextView item_order_income;
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

    public void refreshListView(List<UserOrderOutModel.DataBean.OrderListBean> newList) {
        list = newList;
        notifyDataSetChanged();
    }
}
