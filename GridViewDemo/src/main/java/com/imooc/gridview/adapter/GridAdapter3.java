package com.imooc.gridview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.imooc.gridview.R;
import com.imooc.gridview.model.AppInfo;
import com.imooc.gridview.model.ImageInfo;

import java.util.List;

/**
 * Created by dyonline on 16/8/30.
 */
public class GridAdapter3 extends BaseAdapter {

    private Context context;
    private List<ImageInfo> imageInfoList;

    public GridAdapter3(Context context, List<ImageInfo> imageInfoList) {
        this.imageInfoList = imageInfoList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_gridview2, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_appIcon);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_appName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageInfo imageInfo = imageInfoList.get(position);
        holder.textView.setText(imageInfo.getText());


        Glide.with(context).load(imageInfo.getImagePath()).placeholder(R.mipmap.ic_launcher).centerCrop().into(holder.imageView);

//        if(imageInfo.getBitmap()==null)
//        {
//          holder.imageView.setImageResource(R.mipmap.ic_launcher);
//        }
//        else
//        {
//            holder.imageView.setImageBitmap(imageInfo.getBitmap());
//        }

        return convertView;
    }


    public class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
