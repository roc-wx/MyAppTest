package com.examples.myapplication.learn.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.model.DataArrays;

import java.util.List;

/**
 * 配置适配器
 */
public class SetJsonDataAdapter extends BaseAdapter {
    protected Context context;
    private List<DataArrays> dataArraysListView;

    public SetJsonDataAdapter(Context context, List<DataArrays> dataArraysListView) {
        this.context = context;
        this.dataArraysListView = dataArraysListView;
    }

    @Override
    public int getCount() {
        return dataArraysListView.size();
    }

    @Override
    public Object getItem(int position) {
        return dataArraysListView.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        JsonDataViewHolder jsonDataViewHolder;
//        LayoutInflater inflater;
        if (convertView == null) {
            jsonDataViewHolder = new JsonDataViewHolder();
//            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = View.inflate(context, R.layout.item_listview_textview, null);
            jsonDataViewHolder.textView = convertView.findViewById(R.id.item_main_title);
            convertView.setTag(jsonDataViewHolder);
        } else {
            jsonDataViewHolder = (JsonDataViewHolder) convertView.getTag();
        }
        jsonDataViewHolder.textView.setText(dataArraysListView.get(position).getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, dataArraysListView.get(position).getDescription(), Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }

    private class JsonDataViewHolder {
        TextView textView;
    }
}
