package com.examples.myapplication.learn.listview;

import android.annotation.SuppressLint;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.util.CommonAdapter;
import com.examples.myapplication.learn.util.Util;
import com.examples.myapplication.learn.util.CommonViewHolder;

import java.util.List;

public class AppListActivity extends AppCompatActivity {

    public static final String TAG = "roc-wx";

    /**
     * 不可以在这里获取
     * private LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     * <p>
     * java.lang.RuntimeException: Unable to instantiate activity ComponentInfo{
     * com.examples.myapplication/com.examples.myapplication.network.listview.AppListActivity}:
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
        ListView listView = findViewById(R.id.app_list_view);
//        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.item_listview_header_image, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AppListActivity.this, "click", Toast.LENGTH_LONG).show();
            }
        });
        listView.addHeaderView(view);
        listView.addFooterView(view);
//        listView.setAdapter(new ShowAppWithCommonViewHolderAdapter(this, getResolveInfo()));
        //使用万能适配器
        listView.setAdapter(new CommonAdapter<ResolveInfo>(this, getResolveInfo(), R.layout.item_listview_app) {
            @Override
            public void convert(CommonViewHolder holder, ResolveInfo resolveInfo) {
                Log.i(TAG, "convert: ---------------->app___in");
                holder.setImageViewDrawable(R.id.app_imageView, resolveInfo.activityInfo.loadIcon(getPackageManager()));
                holder.setText(R.id.app_textView, resolveInfo.activityInfo.loadLabel(getPackageManager()).toString());
            }
        });
    }

    /**
     * 获取手机应用
     *
     * @return List<ResolveInfo>
     */
    public List<ResolveInfo> getResolveInfo() {
        return Util.getResolveInfoList(AppListActivity.this);
    }

}
