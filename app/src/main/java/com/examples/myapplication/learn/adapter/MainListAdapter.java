package com.examples.myapplication.learn.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.model.ActivityItem;
import com.examples.myapplication.learn.util.BindableAdapter;

import java.util.List;

/**
 * Function:
 * Create date on 16/8/9.
 *
 * @author Conquer
 * @version 1.0
 */
public class MainListAdapter extends BindableAdapter<ActivityItem> {

    private Context mContext;
    public MainListAdapter(Context context, List<ActivityItem> list) {
        super(context, list);
        mContext = context;

    }

    @Override
    protected View onNewItemView(LayoutInflater inflater, ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.item_listview_textview, null);
        ViewHolder holder = new ViewHolder();
        holder.mTitle = (TextView) view.findViewById(R.id.item_main_title);
        view.setTag(holder);
        return view;
    }

    @Override
    protected void onBindItemView(View viewItem, final ActivityItem data, int position, ViewGroup parent) {
        ViewHolder holder = (ViewHolder) viewItem.getTag();
        holder.mTitle.setText(data.getName());
        viewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mContext.startActivity(new Intent(mContext, data.getActivityClass()));
            }
        });
    }

    private class ViewHolder {
        private TextView mTitle;
    }
}
