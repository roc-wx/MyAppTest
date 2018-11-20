package com.examples.groupurchase.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CommonViewHolder {
    private SparseArray<View> mViews;
    protected int mPosition;
    private View mConvertView;
    protected Context context;

    public CommonViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        this.context = context;
        mConvertView = LayoutInflater.from(context).inflate(layoutId, null, false);
        mConvertView.setTag(this);
    }

    public View getmConvertView() {
        return mConvertView;
    }

    public static CommonViewHolder getViewHolder(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new CommonViewHolder(context, parent, layoutId, position);
        } else {
            CommonViewHolder viewHolder = (CommonViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
            return viewHolder;
        }
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public int getmPosition() {
        return mPosition;
    }

    /**
     * 设置TextView
     *
     * @param viewId
     * @param text
     * @return
     */
    public CommonViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    /**
     * 设置ImageView Resouce
     *
     * @param viewId
     * @return
     */
    public CommonViewHolder setImageViewResouce(int viewId, int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    /**
     * 设置ImageView Drawable
     *
     * @param viewId
     * @return
     */
    public CommonViewHolder setImageViewDrawable(int viewId, Drawable drawable) {
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }


    /**
     * 设置ImageView Bitmap
     *
     * @param viewId
     * @return
     */
    public CommonViewHolder setImageViewBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置ImageView Glide
     *
     * @param viewId
     * @return
     */
    public CommonViewHolder setImageViewGlide(int viewId, String imgUrl, int placeholder) {
        ImageView imageView = getView(viewId);
        Glide.with(context).load(imgUrl).placeholder(placeholder).centerCrop().into(imageView);
        return this;
    }

}
