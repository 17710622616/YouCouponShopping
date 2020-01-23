package com.youcoupon.john_li.youcouponshopping.YouAdapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteItemOutModel;

import org.xutils.cache.LruCache;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by John_Li on 28/11/2017.
 */

public class MainHotRefreshAdapter extends RecyclerView.Adapter<MainHotRefreshAdapter.SmartRefreshViewHolder> implements OnClickListener {
    private List<FavoriteItemOutModel.DataBean.FavoriteItemModel> list;
    private final Context mContext;
    private LayoutInflater mInflater;
    private LruCache<String ,BitmapDrawable> mMemoryCache;
    private OnItemClickListener mOnitemClickListener = null;
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setLoadingDrawableId(R.mipmap.img_loading).setFailureDrawableId(R.mipmap.load_img_fail).build();
    public MainHotRefreshAdapter(Context context, List<FavoriteItemOutModel.DataBean.FavoriteItemModel> list) {
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
        View view = mInflater.inflate(R.layout.item_main_hot, parent, false);
        SmartRefreshViewHolder vh = new SmartRefreshViewHolder(view);
        vh.item_hot_iv = (ImageView) view.findViewById(R.id.item_hot_iv);
        vh.item_hot_price_after_discount = (TextView) view.findViewById(R.id.item_hot_price_after_discount);
        vh.item_hot_original_price = (TextView) view.findViewById(R.id.item_hot_original_price);
        //vh.item_hot_coupon_value = (TextView) view.findViewById(R.id.item_hot_coupon_value);
        vh.item_hot_title = (TextView) view.findViewById(R.id.item_hot_title);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(SmartRefreshViewHolder holder, int position) {
        x.image().bind(holder.item_hot_iv, list.get(position).getPictUrl(), options);
        //holder.item_hot_coupon_value.setText(list.get(position).getCouponInfo());
        if (list.get(position).getCouponInfo() != null) {
            holder.item_hot_price_after_discount.setText(list.get(position).getCouponInfo());
            // 按指定模式在字符串查找
            Pattern p=Pattern.compile("\\d+");
            Matcher m=p.matcher(list.get(position).getCouponInfo());
            while(m.find()) {
                if (Double.parseDouble(list.get(position).getZkFinalPrice()) >= Double.parseDouble(m.group())) {
                    if (m.find()) {
                        String price = String.format("%.2f", (Double.parseDouble(list.get(position).getZkFinalPrice()) - Double.parseDouble(m.group())));
                        holder.item_hot_price_after_discount.setText("¥" + price);
                    }

                    break;
                }
            }
        } else {
            holder.item_hot_price_after_discount.setText("¥" + list.get(position).getZkFinalPriceWap());
            holder.item_hot_original_price.setVisibility(View.GONE);
        }
        holder.item_hot_original_price.setText("¥" + list.get(position).getZkFinalPriceWap());
        holder.item_hot_original_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        holder.item_hot_title.setText(list.get(position).getTitle());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onClick(View v) {
        if (mOnitemClickListener != null) {
            mOnitemClickListener.onItemClick(v, (int)v.getTag());
        }
    }

    public void setOnItemClickListenr(OnItemClickListener onItemClickListener) {
        this.mOnitemClickListener = onItemClickListener;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class SmartRefreshViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_hot_iv;
        public TextView item_hot_price_after_discount;
        public TextView item_hot_original_price;
        //public TextView item_hot_coupon_value;
        public TextView item_hot_title;
        public SmartRefreshViewHolder(View view){
            super(view);
        }
    }

    public void refreshListView(List<FavoriteItemOutModel.DataBean.FavoriteItemModel> newList) {
        list = newList;
        notifyDataSetChanged();
    }
}
