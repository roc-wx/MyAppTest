package com.imooc.gridview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyonline on 16/8/30.
 */
public class ExampleActivity extends Activity {

    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example1);
        gridView= (GridView) findViewById(R.id.gridview);
        List<String> strList=new ArrayList<String>();
        for(int i=0;i<9;i++)
        {
            strList.add("慕课网"+i);
        }
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(this,R.layout.item_gridview1,strList);
        gridView.setAdapter(arrayAdapter);
    }
}
