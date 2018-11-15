package com.examples.myapplication.learn.gridview;

import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.adapter.ShowAppGridViewAdapter;
import com.examples.myapplication.learn.listview.AppListActivity;
import com.examples.myapplication.learn.model.AppInfo;
import com.examples.myapplication.learn.util.Util;

import java.util.ArrayList;
import java.util.List;

public class AppGridViewActivity extends AppCompatActivity {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        initView();
    }

    private void initView() {
        gridView = findViewById(R.id.app_grid_view);
        gridView.setAdapter(new ShowAppGridViewAdapter(this, getAppInfoList()));
    }

    /**
     * 获取手机应用
     *
     * @return
     */
    public List<AppInfo> getAppInfoList() {
        return Util.getAppInfoList(this);
    }

    /**
     * GridView基础测试方法
     */
    private void TestGridView() {
        List<String> strList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            strList.add("慕课网" + i);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.item_gridview_textview, strList);
        gridView.setAdapter(arrayAdapter);
    }
}
