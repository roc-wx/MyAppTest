package com.examples.myapplication.learn.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> mDatas;
    protected LayoutInflater layoutInflater;
    protected int layoutId;

    public CommonAdapter(Context context, List<T> mDatas, int layoutId) {
        this.context = context;
        this.mDatas = mDatas;
        this.layoutId = layoutId;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = CommonViewHolder.getViewHolder(context, convertView, parent, layoutId, position);
        convert(viewHolder, getItem(position));
        return viewHolder.getmConvertView();
    }

    public abstract void convert(CommonViewHolder holder, T t);
}
