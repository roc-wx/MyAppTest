package com.examples.myapplication.learn.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.util.CommonViewHolder;

import java.util.List;

public class ShowAppWithCommonViewHolderAdapter extends BaseAdapter {
    private Context mContext;
    private List<ResolveInfo> resolveInfos;

    /**
     * 简化后的适配器
     * @param mContext
     * @param resolveInfos
     */
    public ShowAppWithCommonViewHolderAdapter(Context mContext, List<ResolveInfo> resolveInfos) {
        this.mContext = mContext;
        this.resolveInfos = resolveInfos;
    }

    @Override
    public int getCount() {
        return resolveInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return resolveInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = CommonViewHolder.getViewHolder(mContext, convertView, parent, R.layout.item_listview_app, position);
        ImageView imageView = viewHolder.getView(R.id.app_imageView);
        TextView textView = viewHolder.getView(R.id.app_textView);
        //设置显示数据
        textView.setText(resolveInfos.get(position).activityInfo.loadLabel(mContext.getPackageManager()));
        imageView.setImageDrawable(resolveInfos.get(position).activityInfo.loadIcon(mContext.getPackageManager()));
        viewHolder.getmConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResolveInfo resolveInfo = resolveInfos.get(position);
                //获取包名，类名
                String packageName = resolveInfo.activityInfo.packageName;
                String className = resolveInfo.activityInfo.name;
                //跳转
                Log.d("roc-wx", "pn:  " + packageName + "  cn:" + className);
                ComponentName componentName = new ComponentName(packageName, className);
                mContext.startActivity(new Intent().setComponent(componentName));
            }
        });
        return viewHolder.getmConvertView();
    }


}
