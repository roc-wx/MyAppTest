package com.examples.myapplication.learn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.model.ChatMessage;

import java.util.List;

public class ChatMessageAdapter extends BaseAdapter {
    Context context;
    List<ChatMessage> messageList;
    LayoutInflater inflater;

    public ChatMessageAdapter(Context context, List<ChatMessage> messageList) {
        inflater = LayoutInflater.from(context);
        this.messageList = messageList;
    }

    public interface IMessageViewType {
        int COM_MESSAGE = 0;
        int TO_MESSAGE = 1;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage entity = messageList.get(position);
        if (entity.getMsgType()) {
            return IMessageViewType.COM_MESSAGE;
        } else {
            return IMessageViewType.TO_MESSAGE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessageViewHolder viewHolder;

        final ChatMessage entity = messageList.get(position);
        boolean isComMsg = entity.getMsgType();
        if (convertView == null) {
            if (getItemViewType(position) == IMessageViewType.COM_MESSAGE) {
                convertView = inflater.inflate(R.layout.item_listview_chatting_msg_text_left, null);
            } else {
                convertView = inflater.inflate(R.layout.item_listview_chatting_msg_text_right, null);
            }
            viewHolder = new ChatMessageViewHolder();
            viewHolder.mSendTime = (TextView) convertView.findViewById(R.id.tv_send_time);
            viewHolder.mUserName = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.mContent = (TextView) convertView.findViewById(R.id.tv_chat_content);
            viewHolder.mTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.mUserAvatar = (ImageView) convertView.findViewById(R.id.iv_user_head);
            viewHolder.mIsComMessage = isComMsg;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChatMessageViewHolder) convertView.getTag();
        }

        viewHolder.mSendTime.setText(entity.getDate());
        viewHolder.mContent.setText(entity.getContent());
        viewHolder.mContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        viewHolder.mTime.setText("");
        viewHolder.mUserName.setText(entity.getName());
        if (getItemViewType(position) == IMessageViewType.COM_MESSAGE) {
            viewHolder.mUserAvatar.setImageResource(R.drawable.avatar);
        } else {
            viewHolder.mUserAvatar.setImageResource(R.mipmap.ic_launcher);
//                ImageLoader.getInstance().displayImage(entity.getAvatarUrl(), viewHolder.mUserAvatar);
        }
        return convertView;
    }

    static class ChatMessageViewHolder {
        public TextView mSendTime;
        public TextView mUserName;
        public TextView mContent;
        public TextView mTime;
        public ImageView mUserAvatar;
        public boolean mIsComMessage = true;
    }
}
