package com.examples.myapplication.learn.storagedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.examples.myapplication.R;

public class StorageMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_main);
    }

    public void onClicks(View v) {
        switch (v.getId()) {
            case R.id.shardpreference_button:
                startActivity(new Intent().setClass(this, SharePreferenceActivity.class));
                break;
            case R.id.internal_bt:
                startActivity(new Intent().setClass(this, InternalActivity.class));
                break;
        }
    }
}
