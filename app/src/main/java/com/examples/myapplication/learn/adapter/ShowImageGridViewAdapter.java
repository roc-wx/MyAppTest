package com.examples.myapplication.learn.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.examples.myapplication.R;
import com.examples.myapplication.learn.model.GroupPurchaseJsonData;

import java.util.List;

public class ShowImageGridViewAdapter extends BaseAdapter {
    protected Context context;
    private List<GroupPurchaseJsonData.CommodityInfo> commodityInfoList;

    public ShowImageGridViewAdapter(Context context, List<GroupPurchaseJsonData.CommodityInfo> commodityInfoList) {
        this.context = context;
        this.commodityInfoList = commodityInfoList;
    }

    @Override
    public int getCount() {
        return commodityInfoList != null ? commodityInfoList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return commodityInfoList != null ? commodityInfoList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return commodityInfoList != null ? position : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        gridViewViewHolder viewHolder = new gridViewViewHolder();
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_gridview_app, null);
            viewHolder.imageView = convertView.findViewById(R.id.item_gridView_app_imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (gridViewViewHolder) convertView.getTag();
        }
        final GroupPurchaseJsonData.CommodityInfo commodityInfo = commodityInfoList.get(position);
        /********************************************************************************************
         * 使用第三方加载图片框架Glide
         * build.gradle配置： implementation 'com.github.bumptech.glide:glide:3.7.0'
         * with（上下文）
         * load（图片路径）
         * placeholder（预览图）
         * centerCrop图片预览模式
         * into（到具体的控件）
         **********************************************************************************************/
        //Glide.with(context).load(commodityInfo.getImgUrl()).placeholder(R.mipmap.ic_launcher).centerCrop().into(viewHolder.imageView);
        //*********************************************************************************************/

        if (commodityInfo.getImg() == null) {
            viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            viewHolder.imageView.setImageBitmap(commodityInfo.getImg());
        }
        return convertView;
    }

    public class gridViewViewHolder {
        ImageView imageView;
    }
}
