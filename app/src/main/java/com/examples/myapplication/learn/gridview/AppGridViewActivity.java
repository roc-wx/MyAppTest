package com.examples.myapplication.learn.gridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.adapter.ShowAppGridViewAdapter;
import com.examples.myapplication.learn.model.AppInfo;
import com.examples.myapplication.learn.util.Util;
import com.examples.myapplication.learn.view.AppGridView;

import java.util.List;

public class AppGridViewActivity extends AppCompatActivity {


    private AppGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        initView();
        showAppGrid();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        gridView = findViewById(R.id.app_grid_view);
    }

    /**
     * 设置适配器，显示应用
     */
    private void showAppGrid() {
        gridView.setAdapter(new ShowAppGridViewAdapter(this, getAppInfoList()));
//        gridView.setOnScrollBottomListener(this);
    }

    /**
     * 获取手机应用
     *
     * @return List
     */
    public List<AppInfo> getAppInfoList() {
        return Util.getAppInfoList(this);
    }


//    /**
//     * GridView基础测试方法
//     */
//    private void testGridView() {
//        List<String> strList = new ArrayList<>();
//        for (int i = 0; i < 9; i++) {
//            strList.add("慕课网" + i);
//        }
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.item_gridview_textview, strList);
//        gridView.setAdapter(arrayAdapter);
//    }

}
