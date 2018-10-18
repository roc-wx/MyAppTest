package com.imooc.demo.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Function:
 * Create date on 16/8/9.
 *
 * @author Conquer
 * @version 1.0
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    private List<T> mList;
    private final Context mContext;

    public BaseListAdapter(Context context) {
        this.mContext = context;
    }

    public BaseListAdapter(Context context, List<T> list) {
        mContext = context;
        mList = list;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mList != null ? mList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return mList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = onNewItemView(LayoutInflater.from(mContext), parent, position);
        }

        onBindItemView(convertView, getItem(position), position, parent);
        return convertView;
    }

    protected abstract View onNewItemView(LayoutInflater inflater, ViewGroup parent, int position);

    protected abstract void onBindItemView(View viewItem, T data, int position, ViewGroup parent);

    public void notifyDataSetChanged(List<T> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
}
