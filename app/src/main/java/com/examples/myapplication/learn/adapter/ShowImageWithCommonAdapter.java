package com.examples.myapplication.learn.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.model.GroupPurchaseJsonData;
import com.examples.myapplication.learn.util.CommonAdapter;
import com.examples.myapplication.learn.util.CommonViewHolder;

import java.util.List;

public class ShowImageWithCommonAdapter extends CommonAdapter<GroupPurchaseJsonData.CommodityInfo> {
    public static final String TAG = "roc-wx";
    protected Context context;
    private List<GroupPurchaseJsonData.CommodityInfo> commodityInfoList;
    protected int layoutId;


    public ShowImageWithCommonAdapter(Context context, List<GroupPurchaseJsonData.CommodityInfo> commodityInfoList, int layoutId) {
        super(context, commodityInfoList, layoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, GroupPurchaseJsonData.CommodityInfo commodityInfo) {
        holder.setImageViewGlide(R.id.item_gridView_app_imageView, commodityInfo.getImg(), R.mipmap.ic_launcher);
    }


}
