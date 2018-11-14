package com.examples.myapplication.learn;


import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


import com.examples.myapplication.R;
import com.examples.myapplication.learn.adapter.MainListAdapter;
import com.examples.myapplication.learn.asynctask.AsyncTaskActivity;
import com.examples.myapplication.learn.handler.DownloadActivity;
import com.examples.myapplication.learn.listview.AppListActivity;
import com.examples.myapplication.learn.listview.JsonDataActivity;
import com.examples.myapplication.learn.model.ActivityItem;
import com.examples.myapplication.learn.network.NetworkActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 11112;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        requestPermission();

        mListView = findViewById(R.id.app_list_view);
        ArrayList<ActivityItem> activityItems = new ArrayList<>();
        activityItems.add(new ActivityItem("1. handler实现网络图片加载", DownloadActivity.class));
        activityItems.add(new ActivityItem("2. Json数据获取与解析", NetworkActivity.class));
        activityItems.add(new ActivityItem("3. AsyncTask获取网络图片", AsyncTaskActivity.class));
        activityItems.add(new ActivityItem("4. ListView加载手机应用", AppListActivity.class));
        activityItems.add(new ActivityItem("4. ListView网络加载Json数据", JsonDataActivity.class));
        mListView.setAdapter(new MainListAdapter(MainActivity.this, activityItems));
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }
   /* public static final int REQUEST_CODE = 100;
    private Button json_button;
    private Button handler_download_button;
    private Button async_task_button;
    private Button list_view_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //https://www.cnblogs.com/imqsl/p/6763133.html
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        json_button = findViewById(R.id.json_get_button);
        handler_download_button = findViewById(R.id.handler_download_button);
        json_button.setOnClickListener(this);
        handler_download_button.setOnClickListener(this);
        async_task_button = findViewById(R.id.Async_task_download_button);
        async_task_button.setOnClickListener(this);
        list_view_button = findViewById(R.id.list_view_button);
        list_view_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.json_get_button:
                startActivity(new Intent(this, NetworkActivity.class));
                break;
            case R.id.handler_download_button:
                startActivity(new Intent(this, DownloadActivity.class));
                break;
            case R.id.Async_task_download_button:
                startActivity(new Intent(this, AsyncTaskActivity.class));
                break;
            case R.id.list_view_button:
                startActivity(new Intent(this, AppListActivity.class));
                break;
        }
    }*/


}
