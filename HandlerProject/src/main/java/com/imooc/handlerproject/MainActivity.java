package com.imooc.handlerproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final int CODE = 10001;
    private TextView mTextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextview = (TextView) findViewById(R.id.textView);

        final Handler handler = new TestHandler(this);

        Message message = Message.obtain();
        message.arg1 = 10000;
        message.what = CODE;

        handler.sendMessageDelayed(message, 1000);

    }

    public static class TestHandler extends Handler{

        public final WeakReference<MainActivity> mWeakReference;
        public TestHandler(MainActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = mWeakReference.get();
            if(msg.what == CODE){
                int time = msg.arg1;
                activity.mTextview.setText(String.valueOf(time/1000));
                Message message = Message.obtain();
                message.what = CODE;
                message.arg1  = time - 1000;

                if (time > 0) {
                    sendMessageDelayed(message, 1000);
                }
            }

        }
    }

}






