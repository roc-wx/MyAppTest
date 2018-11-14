package com.examples.myapplication.network.listview;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.examples.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewShowAppActivity extends AppCompatActivity {

    private ListView listView;
    private LayoutInflater layoutInflater;
    /**
     * 不可以在这里获取
     * private LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     *
     * java.lang.RuntimeException: Unable to instantiate activity ComponentInfo{
     * com.examples.myapplication/com.examples.myapplication.network.listview.ListViewShowAppActivity}:
     * java.lang.IllegalStateException: System services not available to Activities before onCreate()
     * at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2720)
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        setTitle("加载手机应用");
        initView();
    }

    private void initView() {
        listView = findViewById(R.id.app_list_view);
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.header_list_demo,null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListViewShowAppActivity.this,"click",Toast.LENGTH_LONG).show();
            }
        });
        listView.addHeaderView(view);
        listView.addFooterView(view);
        listView.setAdapter(new ShowAppListViewAdapter(this, getResolveInfo()));
    }

    /**
     * 获取手机应用
     *
     * @return
     */
    public List<ResolveInfo> getResolveInfo() {
        return getPackageManager().queryIntentActivities(new Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER), 0);
    }

    public class ShowAppListViewAdapter extends BaseAdapter {
        private Context mContext;
        private List<ResolveInfo> resolveInfos = new ArrayList<>();

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
            AppListViewHolder viewHolder;

            if (convertView == null) {
                viewHolder = new AppListViewHolder();
                convertView = layoutInflater.inflate(R.layout.app_list_view, null);
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
                    ComponentName componentName = new ComponentName(packageName, className);
                    startActivity(new Intent().setComponent(componentName));
                }
            });
            return convertView;
        }

        public class AppListViewHolder {
            private ImageView imageView;
            private TextView textView;
        }
    }

}
