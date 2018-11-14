package com.examples.myapplication.network;


import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.examples.myapplication.R;
import com.examples.myapplication.network.asynctask.AsyncTaskActivity;
import com.examples.myapplication.network.handler.DownloadActivity;
import com.examples.myapplication.network.listview.ListViewActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE = 100;
    private Button json_button;
    private Button handler_download_button;
    private Button async_task_button;
    private Button list_view_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //https://www.cnblogs.com/imqsl/p/6763133.html
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        json_button = findViewById(R.id.json_get_button);
        handler_download_button = findViewById(R.id.handler_download_button);
        json_button.setOnClickListener(this);
        handler_download_button.setOnClickListener(this);
        async_task_button = findViewById(R.id.Async_task_download_button);
        async_task_button.setOnClickListener(this);
        list_view_button = findViewById(R.id.list_view_button);
        list_view_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.json_get_button:
                startActivity(new Intent(this, NetworkActivity.class));
                break;
            case R.id.handler_download_button:
                startActivity(new Intent(this, DownloadActivity.class));
                break;
            case R.id.Async_task_download_button:
                startActivity(new Intent(this, AsyncTaskActivity.class));
                break;
            case R.id.list_view_button:
                startActivity(new Intent(this, ListViewActivity.class));
                break;
        }
    }
}
