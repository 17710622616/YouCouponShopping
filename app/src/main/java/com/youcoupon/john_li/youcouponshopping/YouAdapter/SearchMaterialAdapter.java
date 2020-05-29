package com.youcoupon.john_li.youcouponshopping.YouAdapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.MaterialClassifyItemOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 主页的产品列表适配器
 * Created by John_Li on 30/5/2018.
 */

public class SearchMaterialAdapter extends BaseAdapter {
    private List<MaterialClassifyItemOutModel.DataBean.MaterialItemModel> list;
    private Context mContext;
    private LayoutInflater mInflater;

    private ImageOptions options;
    public SearchMaterialAdapter(List<MaterialClassifyItemOutModel.DataBean.MaterialItemModel> list, Context context) {
        this.list = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        int screemWith = YouCommonUtils.getDeviceWitdh(context);
        int imgWitdh = screemWith / 2 - 5;
        options = new ImageOptions.Builder().setSize(imgWitdh, imgWitdh).setLoadingDrawableId(R.mipmap.img_loading).setFailureDrawableId(R.mipmap.load_img_fail).build();
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
            holder.item_main_merchandise_rebate = convertView.findViewById(R.id.item_main_merchandise_rebate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String imgUrl = list.get(position).getPictUrl().contains("http") ? list.get(position).getPictUrl() : "http:" + list.get(position).getPictUrl();
        x.image().bind(holder.merchandise_iv, imgUrl, options);
        if (list.get(position).getShortTitle() != null) {
            if (!list.get(position).getShortTitle().equals("")) {
                holder.merchandise_title.setText(list.get(position).getShortTitle());
            } else {
                holder.merchandise_title.setText(list.get(position).getTitle());
            }
        } else {
            holder.merchandise_title.setText(list.get(position).getTitle());
        }
        holder.merchandise_original_price.setText("原價：¥" + list.get(position).getZkFinalPrice());
        holder.merchandise_original_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        holder.merchandise_after_discoun.setText("券后價：");
        holder.merchandise_price_after_discount.setText("¥" + list.get(position).getZkFinalPrice());
        /*if (list.get(position).getCouponInfo() != null) {
            if(!list.get(position).getCouponInfo().equals("")) {
                //holder.merchandise_original_coupon_value.setText(list.get(position).getCouponInfo());
                // 按指定模式在字符串查找
                Pattern p=Pattern.compile("\\d+");
                Matcher m=p.matcher(list.get(position).getCouponInfo());
                double afterCoupon = Double.parseDouble(list.get(position).getZkFinalPrice());;
                double couponAmount = 0.001;
                while(m.find()) {
                    // 判断不为0，避开减.00的阶段
                    if(Double.parseDouble(m.group()) != 0.0) {
                        // 当不为0时判断是否是起减金额
                        if (Double.parseDouble(list.get(position).getZkFinalPrice()) >= Double.parseDouble(m.group())) {
                            while (m.find()){
                                if (Double.parseDouble(m.group()) != 0.0) {
                                    String price = list.get(position).getZkFinalPrice();
                                    afterCoupon = Double.parseDouble(list.get(position).getZkFinalPrice()) - Double.parseDouble(m.group());
                                    couponAmount = Double.parseDouble(m.group());
                                    //holder.merchandise_price_after_discount.setText("¥" + String.format("%.2f", (Double.parseDouble(list.get(position).getZkFinalPrice()) - Double.parseDouble(m.group()))));
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }

                holder.merchandise_price_after_discount.setText("¥" + String.format("%.2f", afterCoupon));
                holder.merchandise_original_coupon_value.setText("减" + couponAmount + "元");
                holder.item_main_merchandise_rebate.setText("预计返利：" + String.format("%.2f", (0.78 * (afterCoupon * Double.parseDouble(list.get(position).getCommissionRate()) * 0.01))));
                holder.merchandise_price_after_discount.setText("¥" + String.format("%.2f", (Double.parseDouble(list.get(position).getZkFinalPrice()) - couponAmount)));
                *//*double afterCoupon = 0.01;
                double couponAmount = 0.01;
                while(m.find()) {
                    double price = Double.parseDouble(list.get(position).getZkFinalPrice());
                    double d = Double.parseDouble(m.group());
                    if (Double.parseDouble(list.get(position).getZkFinalPrice()) >= Double.parseDouble(m.group())) {
                        if (m.find()) {
                            if (Double.parseDouble(m.group()) == 0.0) {
                                if (m.find()) {
                                    holder.merchandise_price_after_discount.setText("¥" + String.format("%.2f", (Double.parseDouble(list.get(position).getZkFinalPrice()) - Double.parseDouble(m.group()))));
                                    afterCoupon = Double.parseDouble(list.get(position).getZkFinalPrice()) - Double.parseDouble(m.group());
                                    couponAmount = Double.parseDouble(m.group());
                                }
                            }
                        }

                        holder.merchandise_original_coupon_value.setText("减" + couponAmount + "元");
                        holder.item_main_merchandise_rebate.setText("预计返利：" + String.format("%.2f", (0.78 * (afterCoupon * Double.parseDouble(list.get(position).getCommissionRate()) * 0.01))));
                        break;
                    }
                }*//*
            } else {
                holder.merchandise_original_coupon_value.setVisibility(View.GONE);
                holder.item_main_merchandise_rebate.setText("预计返利：" + String.format("%.2f", (0.78 * (Double.parseDouble(list.get(position).getZkFinalPrice()) * Double.parseDouble(list.get(position).getCommissionRate()) * 0.01))));
            }
        } else {
            holder.merchandise_original_coupon_value.setVisibility(View.GONE);
            holder.item_main_merchandise_rebate.setText("预计返利：" + String.format("%.2f", (0.78 * (Double.parseDouble(list.get(position).getZkFinalPrice()) * Double.parseDouble(list.get(position).getCommissionRate()) * 0.01))));
        }*/
        if (list.get(position).getCouponInfo() != null) {
            if(!list.get(position).getCouponInfo().equals("")) {
                holder.merchandise_price_after_discount.setText("¥" + String.format("%.2f", list.get(position).getPriceAfterDiscount()));
                holder.merchandise_original_coupon_value.setText("减" + String.format("%.2f", list.get(position).getOriginalCouponValue()) + "元");
                holder.item_main_merchandise_rebate.setText("预计返利：" + String.format("%.2f", list.get(position).getRebateMoney()));
            } else {
                holder.merchandise_original_coupon_value.setVisibility(View.GONE);
                holder.item_main_merchandise_rebate.setText("预计返利：" + String.format("%.2f", list.get(position).getRebateMoney()));
            }
        } else {
            holder.merchandise_original_coupon_value.setVisibility(View.GONE);
            holder.item_main_merchandise_rebate.setText("预计返利：" + String.format("%.2f", list.get(position).getRebateMoney()));
        }
        holder.item_main_merchandise_volume.setText("月销：" + list.get(position).getVolume());
        return convertView;
    }

    class ViewHolder {
        public ImageView merchandise_iv;
        public TextView merchandise_title, merchandise_original_price, merchandise_after_discoun, merchandise_price_after_discount, merchandise_original_coupon_value,item_main_merchandise_volume,item_main_merchandise_rebate;
    }
}
