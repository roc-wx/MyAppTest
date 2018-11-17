package com.examples.myapplication.learn;


import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


import com.examples.myapplication.R;
import com.examples.myapplication.learn.adapter.MainListAdapter;
import com.examples.myapplication.learn.asynctask.AsyncTaskDownloadActivity;
import com.examples.myapplication.learn.cardview.CardViewActivity;
import com.examples.myapplication.learn.gridview.AppGridViewActivity;
import com.examples.myapplication.learn.gridview.NetWorkImageGridViewActivity;
import com.examples.myapplication.learn.handler.DownloadActivity;
import com.examples.myapplication.learn.listview.AppListActivity;
import com.examples.myapplication.learn.listview.ChatMeassgeActivity;
import com.examples.myapplication.learn.listview.JsonDataActivity;
import com.examples.myapplication.learn.model.ActivityItem;
import com.examples.myapplication.learn.network.NetworkActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 11112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        requestPermission();

        ListView mListView = findViewById(R.id.app_list_view);
        ArrayList<ActivityItem> activityItems = new ArrayList<>();
        activityItems.add(new ActivityItem("handler实现网络图片加载", DownloadActivity.class));
        activityItems.add(new ActivityItem("Json数据获取与解析", NetworkActivity.class));
        activityItems.add(new ActivityItem("AsyncTask获取网络图片", AsyncTaskDownloadActivity.class));
        activityItems.add(new ActivityItem("ListView加载手机应用", AppListActivity.class));
        activityItems.add(new ActivityItem("ListView网络加载Json数据", JsonDataActivity.class));
        activityItems.add(new ActivityItem("ListView网络显示聊天数据", ChatMeassgeActivity.class));
        activityItems.add(new ActivityItem("ListView加载本地图片CardView显示效果", CardViewActivity.class));
        activityItems.add(new ActivityItem("GridView加载手机应用", AppGridViewActivity.class));
        activityItems.add(new ActivityItem("GridView加载网络图片", NetWorkImageGridViewActivity.class));
        mListView.setAdapter(new MainListAdapter(MainActivity.this, activityItems));
    }

    /**
     * 向用户请求权限
     */
    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }
}
