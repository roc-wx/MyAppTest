package com.imooc.demo.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Function:
 * Create date on 16/8/9.
 *
 * @author Conquer
 * @version 1.0
 */
public abstract class BindableAdapter<T> extends BaseListAdapter<T> {

    public BindableAdapter(Context context) {
        super(context);
    }

    public BindableAdapter(Context context, List<T> list) {
        super(context, list);
    }

    @Override
    public final View getDropDownView(int position, View view, ViewGroup container) {
        if (view == null) {
            view = newDropDownView(LayoutInflater.from(getContext()), position, container);
            if (view == null) {
                throw new IllegalStateException("newDropDownView result must not be null.");
            }
        }
        bindDropDownView(getItem(position), position, view, container);
        return view;
    }

    /**
     * Create a new instance of a drop-down view for the specified position.
     */
    public View newDropDownView(LayoutInflater inflater, int position, ViewGroup container) {
        return onNewItemView(inflater, container, position);
    }

    /**
     * Bind the data for the specified {@code position} to the drop-down view.
     */
    public void bindDropDownView(T item, int position, View view, ViewGroup container) {
        onBindItemView(view, item, position, container);
    }
}
