package com.examples.myapplication.learn.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.model.AppInfo;

import java.util.List;

public class ShowAppGridViewAdapter extends BaseAdapter {
    protected Context context;
    private List<AppInfo> appInfoList;

    public ShowAppGridViewAdapter() {

    }

    public ShowAppGridViewAdapter(Context context, List<AppInfo> appInfoList) {
        this.context = context;
        this.appInfoList = appInfoList;
    }

    @Override
    public int getCount() {
        return appInfoList != null ? appInfoList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return appInfoList != null ? appInfoList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return appInfoList != null ? position : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppGridViewViewHolder viewViewHolder = new AppGridViewViewHolder();
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_gridview_app, null);
            viewViewHolder.imageView = convertView.findViewById(R.id.item_gridView_app_imageView);
            viewViewHolder.textView = convertView.findViewById(R.id.item_gridView_app_textView);
            convertView.setTag(viewViewHolder);
        } else {
            viewViewHolder = (AppGridViewViewHolder) convertView.getTag();
        }
        final AppInfo appInfo = appInfoList.get(position);
        viewViewHolder.imageView.setImageDrawable(appInfo.getAppIcon());
        viewViewHolder.textView.setText(appInfo.getAppName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("roc-wx", "pan:  " + appInfo.getPackageName());
                //在无法获取类名的情况下，仅使用包名进行跳转（不可用于系统服务的跳转）
                context.startActivity(context.getPackageManager().getLaunchIntentForPackage(appInfo.getPackageName()));
            }
        });
        return convertView;
    }

    public class AppGridViewViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
