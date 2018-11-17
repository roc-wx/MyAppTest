package com.examples.myapplication.learn.cardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.adapter.CardViewAdapter;
import com.examples.myapplication.learn.date.DataHelper;

public class CardViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        ListView msgList = findViewById(R.id.app_list_view);
        CardViewAdapter cardViewAdapter = new CardViewAdapter(this, DataHelper.initGenerateMockList());
        msgList.setAdapter(cardViewAdapter);
    }
}
