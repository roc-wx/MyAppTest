package com.imooc.demo.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.imooc.demo.R;
import com.imooc.demo.adapter.MainListAdapter;
import com.imooc.demo.app.listview.ChatActivity;
import com.imooc.demo.app.listview.AppListActivity;
import com.imooc.demo.app.listview.RequestDataActivity;
import com.imooc.demo.model.ActivityItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list_view_demo);
        ArrayList<ActivityItem> activityItems = new ArrayList<>();
        activityItems.add(new ActivityItem("1. 应用列表", AppListActivity.class));
        activityItems.add(new ActivityItem("2. 异步请求数据列表", RequestDataActivity.class));
        activityItems.add(new ActivityItem("3. 模仿IM聊天布局", ChatActivity.class));
        mListView.setAdapter(new MainListAdapter(MainActivity.this,activityItems ));
    }
}
