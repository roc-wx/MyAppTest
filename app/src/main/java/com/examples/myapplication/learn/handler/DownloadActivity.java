package com.examples.myapplication.learn.handler;

import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.examples.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int DOWNLOAD_CODE_1 = 1;
    public static final String TAG = "roc-wx";
    private ProgressBar progressBar_download;
    private ImageView imageView_download;
    private MyDownloadHandler myDownloadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("DownloadHandler");
        setContentView(R.layout.activity_download);
        //插件：https://blog.csdn.net/hzy670800844/article/details/80335156
        initViewSetClick();
        myDownloadHandler = new MyDownloadHandler(this);
    }

    private void initViewSetClick() {
        progressBar_download = findViewById(R.id.download_progress);
        imageView_download = findViewById(R.id.download_imageView);
        Button button_download = findViewById(R.id.download_button);
        button_download.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download_button:
                Toast.makeText(this, "ok", Toast.LENGTH_LONG).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        downloadImage();
                    }
                }).start();
                break;
        }
    }

    private void downloadImage() {
        Log.i(TAG, "-----downloadImage----");
        try {
            URL url = new URL("https://img2.mukewang.com/5adfee7f0001cbb906000338-240-135.jpg");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(100 * 1000);
            int code = httpURLConnection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                Log.i("roc_wxsss", "ssssssbbbb");
                int contentLength = httpURLConnection.getContentLength();
                int dowloadLength = 0;
                int length;
                byte[] bytes = new byte[1024];
                String downloadFolderName = Environment.getExternalStorageDirectory() + File.separator + "immoc" + File.separator;
                File file = new File(downloadFolderName);
                if (!file.exists()) {
                    final boolean mkdir = file.mkdir();
                    if (mkdir) {
                        Log.i(TAG, "downloadImage: ");
                    }
                }
                String fileName = downloadFolderName + "imooc.jpg";

                File apkFile = new File(fileName);

                if (apkFile.exists()) {
                    final boolean delete = apkFile.delete();
                    if (delete) {
                        Log.i("roc-wx", "delete: success");
                    }
                }
                InputStream inputStream = httpURLConnection.getInputStream();
                OutputStream outputStream = new FileOutputStream(fileName);
                //Drawable bitmap=new BitmapDrawable(BitmapFactory.decodeStream(inputStream));
                while ((length = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, length);
                    dowloadLength += length;
                    Thread.sleep(100);
                    Message message = Message.obtain();
                    message.what = DOWNLOAD_CODE_1;
                    message.arg1 = dowloadLength * 100 / contentLength;
                    message.obj = fileName;
                    myDownloadHandler.sendMessage(message);
                    Log.i("roc_wxsss", "bbbbbbb");
                }
                inputStream.close();
                outputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //static
    public static class MyDownloadHandler extends Handler {
        WeakReference<DownloadActivity> mWeakReference;

        MyDownloadHandler(DownloadActivity downloadActivity) {
            mWeakReference = new WeakReference<>(downloadActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DownloadActivity downloadActivity = mWeakReference.get();
            switch (msg.what) {
                case DOWNLOAD_CODE_1:
                    Log.i("roc_wxsss", "ssssss");
                    downloadActivity.progressBar_download.setProgress(msg.arg1);
                    break;
            }

            if (msg.arg1 == 100) {
                //显示本地图片
                downloadActivity.imageView_download.setImageURI(Uri.fromFile(new File((String) msg.obj)));
            }
        }
    }
}
