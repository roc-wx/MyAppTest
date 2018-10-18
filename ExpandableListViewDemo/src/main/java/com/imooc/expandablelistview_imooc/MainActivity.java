package com.imooc.expandablelistview_imooc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.imooc.expandablelistview_imooc.bean.Chapter;
import com.imooc.expandablelistview_imooc.bean.ChapterLab;
import com.imooc.expandablelistview_imooc.biz.ChapterBiz;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mBtnRefresh;

    private ExpandableListView mExpandableListView;
    private ChapterAdapter mAdapter;
    private List<Chapter> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mExpandableListView = findViewById(R.id.id_expandable_listview);
        mBtnRefresh = findViewById(R.id.id_btn_refresh);

//        mDatas = ChapterLab.generateDatas();
        mAdapter = new ChapterAdapter(this, mDatas);
        mExpandableListView.setAdapter(mAdapter);

        initEvents();
        loadDatas(true);


    }

    private ChapterBiz mChapterBiz = new ChapterBiz();

    private void loadDatas(boolean useCache) {
        mChapterBiz.loadDatas(this, new ChapterBiz.CallBack() {
            @Override
            public void loadSuccess(List<Chapter> chapterList) {
                Log.e("zhy", "loadSuccess  ");

                mDatas.clear();
                mDatas.addAll(chapterList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void loadFailed(Exception ex) {
                ex.printStackTrace();
                Log.e("zhy", "loadFailed ex= " + ex.getMessage());
            }
        }, useCache);
    }

    private void initEvents() {

        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatas(false);
            }
        });


        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("zhy", "onGroupClick groupPosition = " + groupPosition);
                return false;
            }
        });


        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.d("zhy", "onChildClick groupPosition = "
                        + groupPosition + " , childPosition = " + childPosition + " , id = " + id);

                return false;
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            // 收回
            @Override
            public void onGroupCollapse(int groupPosition) {
                Log.d("zhy", "onGroupCollapse groupPosition = " + groupPosition);

            }
        });

        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            // 展开
            @Override
            public void onGroupExpand(int groupPosition) {
                Log.d("zhy", "onGroupExpand groupPosition = " + groupPosition);

            }
        });

        mExpandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("zhy", "onItemClick position = " + position);

            }
        });


    }
}
