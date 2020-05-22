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
import com.youcoupon.john_li.youcouponshopping.YouModel.FavoriteItemOutModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.MainClassifyOutModel;
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

public class ClassifyItemAdapter extends BaseAdapter {
    private List<MainClassifyOutModel.DataBean.ResultsBean.ChildItemBean> list;
    private Context mContext;
    private LayoutInflater mInflater;

    private ImageOptions options;
    public ClassifyItemAdapter(List<MainClassifyOutModel.DataBean.ResultsBean.ChildItemBean> list, Context context) {
        this.list = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        int screemWith = YouCommonUtils.getDeviceWitdh(context);
        int imgWitdh = screemWith / 2 - 5;
        options = new ImageOptions.Builder().setSize(imgWitdh, imgWitdh).setImageScaleType(ImageView.ScaleType.CENTER_INSIDE).setLoadingDrawableId(R.mipmap.img_loading).setFailureDrawableId(R.mipmap.load_img_fail).build();
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
            convertView = mInflater.inflate(R.layout.item_classify, null);
            holder = new ViewHolder();
            holder.item_classify_iv = convertView.findViewById(R.id.item_classify_iv);
            holder.item_classify_tv = convertView.findViewById(R.id.item_classify_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        x.image().bind(holder.item_classify_iv, list.get(position).getImg_url(), options);
        holder.item_classify_tv.setText(list.get(position).getChild_activity_title());
        return convertView;
    }

    class ViewHolder {
        public ImageView item_classify_iv;
        public TextView item_classify_tv;
    }
}
