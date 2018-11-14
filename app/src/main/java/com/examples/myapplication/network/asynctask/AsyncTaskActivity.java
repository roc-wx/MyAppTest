package com.examples.myapplication.network.asynctask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.examples.myapplication.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String IIMAGE_URL = "https://img2.mukewang.com/5adfee7f0001cbb906000338-240-135.jpg";
    public static final int PROGRESS = 0;
    private Button downloadButton;
    private ProgressBar progressBar;
    private ImageView imageView;
    private Bitmap bitmap;
    private static final String TAG = "roc_wxa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_async_task);
        setTitle("DownloadAsyncTask");
        initView();
        setClick();
    }

    private void initView() {
        downloadButton = findViewById(R.id.download_button_async);
        progressBar = findViewById(R.id.download_progress_async);
        imageView = findViewById(R.id.download_imageView_async);
    }

    private void setClick() {
        downloadButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download_button_async:
                Toast.makeText(this, "ok", Toast.LENGTH_LONG).show();
                DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
                downloadAsyncTask.execute(IIMAGE_URL);
                break;
        }
    }

    public class DownloadAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            downloadButton.setText("点击下载.");
            progressBar.setProgress(PROGRESS);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url;
            HttpURLConnection httpURLConnection;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                // 下载地址准备
                String mFilePath = Environment.getExternalStorageDirectory()
                        + File.separator + "immoc.jpg";
                OutputStream outputStream = new FileOutputStream(mFilePath);

                int len;
                int downloadSize = 0;
                int fileSize = httpURLConnection.getContentLength();
                byte[] bytes = new byte[1024];
                while ((len = bufferedInputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                    downloadSize += len;
                    Thread.sleep(500);
                    publishProgress(downloadSize * 100 / fileSize);
                }
                bitmap = BitmapFactory.decodeFile(mFilePath);
                Log.i(TAG, "doInBackground: -----b" + downloadSize * 100 / fileSize);
                inputStream.close();
                outputStream.close();
                bufferedInputStream.close();
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "doInBackground: +fail   ---n");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
            if (bitmap != null) {
                downloadButton.setText("下载完成");
            } else {
                downloadButton.setText("下载失败");
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values != null && values.length > 0) {
                progressBar.setProgress(values[0]);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onCancelled(Bitmap bitmap) {
            super.onCancelled(bitmap);
        }
    }
}
