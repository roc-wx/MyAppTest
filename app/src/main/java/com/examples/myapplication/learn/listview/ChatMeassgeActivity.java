package com.examples.myapplication.learn.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.adapter.ChatMessageAdapter;
import com.examples.myapplication.learn.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatMeassgeActivity extends AppCompatActivity {

    protected List<ChatMessage> chatMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initView();

    }

    private void initView() {
        ListView listView = findViewById(R.id.app_list_view);
        listView.setAdapter(new ChatMessageAdapter(this, initData()));
    }

    private List<ChatMessage> initData() {
        chatMessageList = new ArrayList<>();
        chatMessageList.add(new ChatMessage(1, 2, "刘小明", "8:20", "你好吗", "", "", true));
        chatMessageList.add(new ChatMessage(2, 1, "小军", "8:21", "我很好", "", "", false));
        chatMessageList.add(new ChatMessage(1, 2, "刘小明", "8:22", "今天天气怎么样", "", "", true));
        chatMessageList.add(new ChatMessage(2, 1, "小军", "8:23", "热成狗了", "", "", false));
        return chatMessageList;
    }


}
