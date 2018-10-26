package com.examples.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "roc-wx";
    private Button getButton;
    private Button analysisButton;
    private TextView showText;
    private String jsonData;
    private EditText inputUrl;
    private JsonData jsonDataAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //https://www.cnblogs.com/imqsl/p/6763133.html
        setContentView(R.layout.activity_main);
        initView();
        setClick();
    }

    private void setClick() {
        getButton.setOnClickListener(this);
        analysisButton.setOnClickListener(this);
    }

    private void initView() {
        inputUrl = findViewById(R.id.input_url);
        getButton = findViewById(R.id.get_button);
        analysisButton = findViewById(R.id.analysis_button);
        showText = findViewById(R.id.show_text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        JsonDataGet();
                        JsonDataPost();
                    }
                }).start();
                break;
            case R.id.analysis_button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JsonDataAnalysis(jsonData);
                    }
                }).start();
                break;
        }
    }

    private void JsonDataPost() {
        try {
            URL url = new URL(inputUrl.getText().toString());
            //打开链接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3 * 1000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            //设置是否可缓存
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();//发起连接
            String data = "username=" + getEncodeValue("imooc") + "&number=" + getEncodeValue("150088886666");
            OutputStream outputStream = urlConnection.getOutputStream();

            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
//            int responseCode = urlConnection.getResponseCode();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = urlConnection.getInputStream();
                jsonData = streamToString(stream);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showText.setText(jsonData);
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getEncodeValue(String imooc) {
        String encode = null;
        try {
            encode = URLEncoder.encode(imooc, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }

    private void JsonDataGet() {
        try {
            URL url = new URL(inputUrl.getText().toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3 * 1000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
//            int responseCode = urlConnection.getResponseCode();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = urlConnection.getInputStream();
                jsonData = streamToString(stream);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showText.setText(jsonData);
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String streamToString(InputStream stream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len;
        try {
            while ((len = stream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();
            outputStream.close();
            stream.close();
            byte[] byteArrays = outputStream.toByteArray();
            return new String(byteArrays);

        } catch (IOException ex) {
            Log.e(TAG, ex.toString());
            ex.printStackTrace();
            return null;
        }
    }

    private void JsonDataAnalysis(String dataJson) {
        //Toast.makeText(this,"aaaa",Toast.LENGTH_LONG).show();
        try {
            jsonDataAll = new JsonData();
            List<DataArrays> dataArrayList = new ArrayList();
            JSONObject jsonObject = new JSONObject(dataJson);   //    注意放入：new JSONObject(dataJson);
            int status = jsonObject.getInt("status");
            String msg = jsonObject.getString("msg");
            jsonDataAll.setStatus(status);
            jsonDataAll.setMsg(msg);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int index = 0; index < jsonArray.length(); index++) {
                    JSONObject jsonArrayObject = (JSONObject) jsonArray.get(index);
                    int id = jsonArrayObject.getInt("id");
                    int learner = jsonArrayObject.getInt("learner");
                    String name = jsonArrayObject.getString("name");
                    String smallPic = jsonArrayObject.getString("picSmall");
                    String bigPic = jsonArrayObject.getString("picBig");
                    String description = jsonArrayObject.getString("description");

                    DataArrays dataArrays = new DataArrays();
                    dataArrays.setId(id);
                    dataArrays.setLearner(learner);
                    dataArrays.setName(name);
                    dataArrays.setPicSmall(smallPic);
                    dataArrays.setPicBig(bigPic);
                    dataArrays.setDescription(description);
                    dataArrayList.add(dataArrays);
                }
                jsonDataAll.setData(dataArrayList);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showText.setText(jsonDataAll.toString());
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
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
