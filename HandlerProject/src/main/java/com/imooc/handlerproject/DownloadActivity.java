package com.imooc.handlerproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Function:
 * Create date on 16/9/16.
 *
 * @author Conquer
 * @version 1.0
 */
public class DownloadActivity extends Activity {

    public static final int DOWNLOAD_MESSAGE_CODE = 100001;
    private static final int DOWNLOAD_MESSAGE_FAIL_CODE = 100002;
    public static final String APP_URL = "http://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk";
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);


        /**
         * 主线程  -- > start
         * 点击按键 |
         * 发起下载 |
         * 开启子线程做下载 |
         * 下载过程中通知主线程 |  -- > 主线程更新进度条
         */

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        download(APP_URL);
                    }
                }).start();

            }
        });

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case DOWNLOAD_MESSAGE_CODE:
                        progressBar.setProgress((Integer) msg.obj);
                        break;
                    case DOWNLOAD_MESSAGE_FAIL_CODE:

                }
            }
        };

    }

    private void download(String appUrl) {
        try {
            URL url = new URL(appUrl);
            URLConnection urlConnection = url.openConnection();

            InputStream inputStream = urlConnection.getInputStream();

            /**
             * 获取文件的总长度
             */

            int contentLength = urlConnection.getContentLength();

            String downloadFolderName = Environment.getExternalStorageDirectory()
                    + File.separator + "imooc" + File.separator;

            File file = new File(downloadFolderName);
            if (!file.exists()) {
                file.mkdir();
            }

            String fileName = downloadFolderName + "imooc.apk";

            File apkFile = new File(fileName);

            if(apkFile.exists()){
                apkFile.delete();
            }

            int downloadSize = 0;
            byte[] bytes = new byte[1024];

            int length;

            OutputStream outputStream = new FileOutputStream(fileName);
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
                downloadSize += length;
                /**
                 * update UI
                 */

                Message message = Message.obtain();
                message.obj = downloadSize * 100 / contentLength;
                message.what = DOWNLOAD_MESSAGE_CODE;
                mHandler.sendMessage(message);

            }
            inputStream.close();
            outputStream.close();


        } catch (MalformedURLException e) {
            notifyDownloadFaild();
            e.printStackTrace();
        } catch (IOException e) {
            notifyDownloadFaild();
            e.printStackTrace();
        }
    }

    private void notifyDownloadFaild() {
        Message message = Message.obtain();
        message.what = DOWNLOAD_MESSAGE_FAIL_CODE;
        mHandler.sendMessage(message);
    }
}
