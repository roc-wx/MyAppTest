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

import java.util.List;

public class ShowAppListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<ResolveInfo> resolveInfos;

    public ShowAppListViewAdapter(Context mContext, List<ResolveInfo> resolveInfos) {
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
        AppListViewHolder viewHolder = new AppListViewHolder();
        if (convertView == null) {
            // LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = View.inflate(mContext, R.layout.item_listview_app, null);
            //获取控件
            viewHolder.imageView = convertView.findViewById(R.id.app_imageView);
            viewHolder.textView = convertView.findViewById(R.id.app_textView);
            //设置标签
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AppListViewHolder) convertView.getTag();
        }
        //设置显示数据
        viewHolder.textView.setText(resolveInfos.get(position).activityInfo.loadLabel(mContext.getPackageManager()));
        viewHolder.imageView.setImageDrawable(resolveInfos.get(position).activityInfo.loadIcon(mContext.getPackageManager()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResolveInfo resolveInfo = resolveInfos.get(position);
                //获取包名，类名
                String packageName = resolveInfo.activityInfo.packageName;
                String className = resolveInfo.activityInfo.name;
                //跳转
                Log.d("roc-wx", "pan:  " + packageName + "  c:" + className);
                ComponentName componentName = new ComponentName(packageName, className);
                mContext.startActivity(new Intent().setComponent(componentName));
            }
        });
        return convertView;
    }

    private class AppListViewHolder {
        private ImageView imageView;
        private TextView textView;
    }
}
