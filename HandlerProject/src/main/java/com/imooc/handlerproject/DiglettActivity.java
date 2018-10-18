package com.imooc.handlerproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * Function:
 * Create date on 16/9/16.
 *
 * @author Conquer
 * @version 1.0
 */
public class DiglettActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    public static final int CODE = 123;
    private TextView mResultTextView;
    private ImageView mDiglettImageView;
    private Button mStartButton;

    public int[][] mPosition = new int[][]{
            {342, 180},{432, 880},
            {521, 256},{429, 780},
            {456, 976},{145, 665},
            {123, 678},{564, 567},
    };

    private int mTotalCount;
    private int mSuccessCount;

    public static final int MAX_COUNT = 10;

    private DiglettHandler mHandler = new DiglettHandler(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diglett);

        initView();

        setTitle("打地鼠");

    }

    private void initView() {
        mResultTextView = (TextView) findViewById(R.id.text_view);
        mDiglettImageView = (ImageView) findViewById(R.id.image_view);
        mStartButton = (Button) findViewById(R.id.start_button);

        mStartButton.setOnClickListener(this);
        mDiglettImageView.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:
                start();
                break;
        }

    }

    private void start() {
        // 发送消息 handler.sendmessagedelayer
        mResultTextView.setText("开始啦");
        mStartButton.setText("游戏中……");
        mStartButton.setEnabled(false);

        next(0);
    }

    private void next(int delayTime){
        int position = new Random().nextInt(mPosition.length);

        Message message = Message.obtain();
        message.what = CODE;
        message.arg1 = position;

        mHandler.sendMessageDelayed(message, delayTime);
        mTotalCount ++ ;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.setVisibility(View.GONE);
        mSuccessCount ++ ;
        mResultTextView.setText("打到了"+ mSuccessCount +"只,共" + MAX_COUNT +"只.");
        return false;
    }

    public static class DiglettHandler extends Handler{
        public static final int RANDOM_NUMBER = 500;
        public final WeakReference<DiglettActivity> mWeakReference;

        public DiglettHandler(DiglettActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DiglettActivity activity = mWeakReference.get();

            switch (msg.what){
                case CODE:
                    if(activity.mTotalCount > MAX_COUNT){
                        activity.clear();
                        Toast.makeText(activity, "地鼠打完了!", Toast.LENGTH_LONG ).show();
                        return;
                    }

                    int position = msg.arg1;
                    activity.mDiglettImageView.setX(activity.mPosition[position][0]);
                    activity.mDiglettImageView.setY(activity.mPosition[position][1]);
                    activity.mDiglettImageView.setVisibility(View.VISIBLE);

                    int randomTime = new Random().nextInt(RANDOM_NUMBER) + RANDOM_NUMBER;

                    activity.next(randomTime);
                    break;
            }

        }
    }

    private void clear() {
        mTotalCount = 0;
        mSuccessCount = 0;
        mDiglettImageView.setVisibility(View.GONE);
        mStartButton.setText("点击开始");
        mStartButton.setEnabled(true);
    }
}
