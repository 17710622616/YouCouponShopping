package com.youcoupon.john_li.youcouponshopping.YouAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteItemOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.SearchOutModel;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 主页的产品列表适配器
 * Created by John_Li on 30/5/2018.
 */

public class SearchItemAdapter extends BaseAdapter {
    private List<SearchOutModel.DataBean.SearchModel> list;
    private Context mContext;
    private LayoutInflater mInflater;

    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setLoadingDrawableId(R.mipmap.img_loading).setFailureDrawableId(R.mipmap.load_img_fail).build();
    public SearchItemAdapter(List<SearchOutModel.DataBean.SearchModel> list, Context context) {
        this.list = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_main_merchandise_list, null);
            holder = new ViewHolder();
            holder.merchandise_iv = convertView.findViewById(R.id.item_main_merchandise_iv);
            holder.merchandise_title = convertView.findViewById(R.id.item_main_merchandise_title);
            holder.merchandise_original_price = convertView.findViewById(R.id.item_main_merchandise_original_price);
            holder.merchandise_after_discoun = convertView.findViewById(R.id.item_main_merchandise_after_discount);
            holder.merchandise_price_after_discount = convertView.findViewById(R.id.item_main_merchandise_price_after_discount);
            holder.merchandise_original_coupon_value = convertView.findViewById(R.id.item_main_merchandise_original_coupon_value);
            holder.item_main_merchandise_volume = convertView.findViewById(R.id.item_main_merchandise_volume);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        x.image().bind(holder.merchandise_iv, list.get(position).getPictUrl(), options);
        holder.merchandise_title.setText(list.get(position).getTitle());
        holder.merchandise_original_price.setText("原價：¥" + list.get(position).getZkFinalPrice());
        holder.merchandise_after_discoun.setText("券后價：");
        holder.merchandise_price_after_discount.setText("¥" + list.get(position).getZkFinalPrice());
        if (list.get(position).getCouponInfo() != null) {
            holder.merchandise_original_coupon_value.setText(list.get(position).getCouponInfo());
            // 按指定模式在字符串查找
            Pattern p=Pattern.compile("\\d+");
            Matcher m=p.matcher(list.get(position).getCouponInfo());
            while(m.find()) {
                if (Double.parseDouble(list.get(position).getZkFinalPrice()) > Double.parseDouble(m.group())) {
                    if (m.find()) {
                        holder.merchandise_price_after_discount.setText("¥" + (Double.parseDouble(list.get(position).getZkFinalPrice()) - Double.parseDouble(m.group())));
                    }

                    break;
                }
            }
        } else {
            holder.merchandise_original_coupon_value.setVisibility(View.GONE);
        }
        holder.item_main_merchandise_volume.setText("月销：" + list.get(position).getVolume());
        return convertView;
    }

    class ViewHolder {
        public ImageView merchandise_iv;
        public TextView merchandise_title, merchandise_original_price, merchandise_after_discoun, merchandise_price_after_discount, merchandise_original_coupon_value,item_main_merchandise_volume;
    }
}
