package com.examples.myapplication.learn.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.GridView;

/**
 * 滚动到底部自动刷新
 *
 * @author yinghui.hong
 *
 */
public class AppGridView extends GridView implements AbsListView.OnScrollListener {

    public AppGridView(Context context) {
        super(context);
    }

    public AppGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    /**
     * 列表视图滚动
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case OnScrollListener.SCROLL_STATE_IDLE:
                Log.v("onScrollStateChanged", "已经停止：SCROLL_STATE_IDLE");
                // 判断滚动到底部
                if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                    Log.v("onScrollStateChanged", "到底部啦。可以请求刷新咯~~~~~~");
                    if (listener != null) {
                        listener.onScrollBottom();
                    }
                }
                break;
            case OnScrollListener.SCROLL_STATE_FLING:
                Log.v("onScrollStateChanged", "开始滚动：SCROLL_STATE_FLING");
                break;
            case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                Log.v("onScrollStateChanged", "正在滚动：SCROLL_STATE_TOUCH_SCROLL");
                break;
        }

    }

    private OnScrollBottomListener listener;

    public void setOnScrollBottomListener(OnScrollBottomListener listener) {
        this.setOnScrollListener(this);
        this.listener = listener;
    }

    public void removeOnScrollBottomListener() {
        listener = null;
        System.out.println("removeOnScrollBottomListener");
    }

    /**
     * 列表视图滚动到底部监听器
     *
     * @author yinghui.hong
     *
     */
    public interface OnScrollBottomListener {
        /**
         * 列表视图滚动到底部时响应
         */
        public void onScrollBottom();
    }
}
