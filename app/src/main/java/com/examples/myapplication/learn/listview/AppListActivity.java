package com.examples.myapplication.learn.listview;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.adapter.ShowAppListViewAdapter;
import com.examples.myapplication.learn.util.Util;

import java.util.List;

public class AppListActivity extends AppCompatActivity {

    private ListView listView;
    private LayoutInflater layoutInflater;

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
        listView = findViewById(R.id.app_list_view);
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_listview_header_image, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AppListActivity.this, "click", Toast.LENGTH_LONG).show();
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
        return Util.getResolveInfoList(AppListActivity.this);
    }

}
