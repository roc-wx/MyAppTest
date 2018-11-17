package com.examples.myapplication.learn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.model.Msg;

import java.util.List;

public class CardViewAdapter extends BaseAdapter {
    protected Context context;
    private List<Msg> msgList;
    private LayoutInflater layoutInflater;

    public CardViewAdapter(Context context, List<Msg> msgList) {
        this.context = context;
        this.msgList = msgList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return msgList.size();
    }

    @Override
    public Object getItem(int position) {
        return msgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new CardViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_card_view, parent, false);
            viewHolder.mIvImg = convertView.findViewById(R.id.id_iv_img);
            viewHolder.mTvTitle = convertView.findViewById(R.id.id_tv_title);
            viewHolder.mTvContent = convertView.findViewById(R.id.id_tv_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder) convertView.getTag();
        }
        Msg msg = msgList.get(position);
        viewHolder.mIvImg.setImageResource(msg.getImgResId());
        viewHolder.mTvTitle.setText(msg.getTitle());
        viewHolder.mTvContent.setText(msg.getContent());
        return convertView;
    }

    private class CardViewHolder {
        private ImageView mIvImg;
        private TextView mTvTitle;
        private TextView mTvContent;
    }
}
