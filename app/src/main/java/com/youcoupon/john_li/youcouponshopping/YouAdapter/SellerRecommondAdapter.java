package com.youcoupon.john_li.youcouponshopping.YouAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.SellerOutModel;
import com.youcoupon.john_li.youcouponshopping.YouView.NoScrollGridView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by John_Li on 8/4/2019.
 */

public class SellerRecommondAdapter extends BaseAdapter {
    private List<SellerOutModel.DataBean.SellerModel> list;
    private int[] iconArr = {R.mipmap.exemption_from_postage99,R.mipmap.time_limit,R.mipmap.boutique,R.mipmap.guide,R.mipmap.head_boy};
    private Context mContext;
    private LayoutInflater mInflater;
    static int heightTextView=0;
    static int numTextView=0;
    private NoScrollGridView mGv;

    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setLoadingDrawableId(R.mipmap.img_loading).setFailureDrawableId(R.mipmap.load_img_fail).build();
    public SellerRecommondAdapter(List<SellerOutModel.DataBean.SellerModel> list, Context context, NoScrollGridView mGv) {
        this.list = list;
        this.mGv = mGv;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (list.size() > 3) {
            return 3;
        }

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
        SellerOutModel.DataBean.SellerModel fruit = (SellerOutModel.DataBean.SellerModel) getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_seller_recommond, null);
            holder = new ViewHolder();
            holder.item_seller_recon_iv = convertView.findViewById(R.id.item_seller_recon_iv);
            holder.item_seller_recon_tv = convertView.findViewById(R.id.item_seller_recon_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        x.image().bind(holder.item_seller_recon_iv, list.get(position).getPictUrl(), options);
        holder.item_seller_recon_tv.setText(list.get(position).getShopTitle());
        return convertView;
    }

    class ViewHolder {
        public ImageView item_seller_recon_iv;
        public TextView item_seller_recon_tv;
    }

    public void refreshData(List<SellerOutModel.DataBean.SellerModel> newList) {
        list = newList;
        notifyDataSetChanged();
    }
}
