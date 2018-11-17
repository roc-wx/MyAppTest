package com.examples.myapplication.learn.network;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.model.JsonData;
import com.examples.myapplication.learn.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NetworkActivity extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_network);
        setTitle("NetWork");
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
                        if (Util.isOnline(NetworkActivity.this)) {
                            JsonDataGet();
//                        JsonDataPost();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(NetworkActivity.this, "网络掉啦", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).start();
                break;
            case R.id.analysis_button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (jsonData != null) {
                            JsonDataAnalysis(jsonData);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(NetworkActivity.this, "请先获取数据", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).start();
                break;
        }
    }

/*    private void JsonDataPost() {
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
            String data = "username=" + Util.getStringEncodeToUTF8("imooc") + "&number=" + Util.getStringEncodeToUTF8("150088886666");
            OutputStream outputStream = urlConnection.getOutputStream();

            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
//            int responseCode = urlConnection.getResponseCode();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = urlConnection.getInputStream();
                jsonData = Util.streamToString(stream);
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
    }*/


    private void JsonDataGet() {
        try {
            URL url = new URL(inputUrl.getText().toString());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3 * 1000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                //将流转换为字符
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int len;
                while ((len = inputStream.read(bytes)) != -1) {
                    byteArrayOutputStream.write(bytes, 0, len);
                }
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
                inputStream.close();
                jsonData = new String(byteArrayOutputStream.toByteArray());
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
/*    private void JsonDataGet() {
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
    }*/

    private void JsonDataAnalysis(String dataJson) {
        jsonDataAll = new JsonData();
        List<JsonData.DataArrays> dataArraysList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(dataJson);
            int status = jsonObject.getInt("status");
            String msg = jsonObject.getString("msg");
            jsonDataAll.setMsg(msg);
            jsonDataAll.setStatus(status);
            JSONArray dataArrays = jsonObject.getJSONArray("data");
            if (dataArrays != null && dataArrays.length() > 0) {
                for (int i = 0; i < dataArrays.length(); i++) {
                    JSONObject jsonArrayObject = (JSONObject) dataArrays.get(i);
                    int id = jsonArrayObject.getInt("id");
                    int learner = jsonArrayObject.getInt("learner");
                    String name = jsonArrayObject.getString("name");
                    String smallPic = jsonArrayObject.getString("picSmall");
                    String bigPic = jsonArrayObject.getString("picBig");
                    String description = jsonArrayObject.getString("description");
                    JsonData.DataArrays mdataArray = new JsonData.DataArrays();
                    mdataArray.setId(id);
                    mdataArray.setLearner(learner);
                    mdataArray.setName(name);
                    mdataArray.setPicSmall(smallPic);
                    mdataArray.setPicBig(bigPic);
                    mdataArray.setDescription(description);
                    dataArraysList.add(mdataArray);
                }
                jsonDataAll.setData(dataArraysList);
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
    /*private void JsonDataAnalysis(String dataJson) {
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
    }*/

}
