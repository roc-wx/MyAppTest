package com.imooc.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Function:
 *
 * @author Conquer
 * @version 1.0
 */
public class NetworkActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView;
    private Button mButton;
    private static final String TAG = "NetworkActivity";
    private String mResult;
    private Button mParseDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        findViews();
        setListeners();

    }

    private void findViews() {
        mTextView = findViewById(R.id.textView);
        mButton = findViewById(R.id.getButton);
        mParseDataButton = findViewById(R.id.parseDataButton);
    }

    private void setListeners() {
        mButton.setOnClickListener(this);
        mParseDataButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getButton:
                //在子线程中进行操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestDataByGet();
                    }
                }).start();
                break;

            case R.id.parseDataButton:
                handleJSONData(mResult);
                break;
        }
    }

    private void handleJSONData(String result) {
        try {

            LessonResult lessonResult = new LessonResult();

            JSONObject jsonObject = new JSONObject(result);
            List<LessonResult.Lesson> lessonList = new ArrayList<>();
            int status = jsonObject.getInt("status");
            JSONArray lessons = jsonObject.getJSONArray("data");

            lessonResult.setStatus(status);

            if(lessons != null && lessons.length() > 0){
                for (int index = 0; index < lessons.length(); index++) {
                    JSONObject lesson = (JSONObject) lessons.get(index);
                    int id = lesson.getInt("id");
                    int learner = lesson.getInt("learner");
                    String name = lesson.getString("name");
                    String smallPic = lesson.getString("picSmall");
                    String bigPic = lesson.getString("picBig");
                    String description = lesson.getString("description");

                    LessonResult.Lesson lessonItem = new LessonResult.Lesson();
                    lessonItem.setID(id);
                    lessonItem.setName(name);
                    lessonItem.setSmallPictureUrl(smallPic);
                    lessonItem.setBigPictureUrl(bigPic);
                    lessonItem.setDescription(description);
                    lessonItem.setLearnerNumber(learner);
                    lessonList.add(lessonItem);
                }
                lessonResult.setLessons(lessonList);
            }

            mTextView.setText(lessonResult.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void requestDataByGet() {
        try {
            URL url = new URL("http://www.imooc.com/api/teacher?type=2&page=1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30*1000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.connect(); // 发起连接

            int responseCode = connection.getResponseCode();
            String reponseMessage = connection.getResponseMessage();

            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                mResult = streamToString(inputStream);
                //通过调用runOnUiThread来更新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mResult = decode(mResult);
                        mTextView.setText(mResult);
                    }
                });
            } else {
                // TODO: error fail
                Log.e(TAG, "run: error code:" +responseCode +", message:" + reponseMessage);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestDataByPost() {
        try {
            // ?type=2&page=1
            URL url = new URL("http://www.imooc.com/api/teacher");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30*1000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Accept-Charset", "UTF-8");

            connection.setDoOutput(true);
            connection.setDoInput(true);
            //设置是否可缓存
            connection.setUseCaches(false);
            connection.connect(); // 发起连接

            String data = "username=" + getEncodeValue("imooc") + "&number=" + getEncodeValue("150088886666");

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            String reponseMessage = connection.getResponseMessage();

            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                mResult = streamToString(inputStream);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mResult = decode(mResult);
                        mTextView.setText(mResult);
                    }
                });


            } else {
                // TODO: error fail

                Log.e(TAG, "run: error code:" +responseCode +", message:" + reponseMessage);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private String getEncodeValue(String imooc) {

        String encode = null;
        try {
            encode = URLEncoder.encode(imooc, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }


    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return 字符串
     */
    public String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    /**
     * 将Unicode字符转换为UTF-8类型字符串
     */
    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuilder retBuf = new StringBuilder();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5)
                        && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
                        .charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else {
                    retBuf.append(unicodeStr.charAt(i));
                }
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }


}
