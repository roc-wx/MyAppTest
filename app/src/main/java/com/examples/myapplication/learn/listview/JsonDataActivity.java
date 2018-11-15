package com.examples.myapplication.learn.listview;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.adapter.SetJsonDataAdapter;
import com.examples.myapplication.learn.model.DataArrays;
import com.examples.myapplication.learn.model.JsonData;

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

public class JsonDataActivity extends AppCompatActivity {

    public static final String JSON_URL = "http://www.imooc.com/api/teacher?type=2&page=1";
    private ListView listView;
    private String jsonData;
    private JsonData jsonDataAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initView();//初始化
    }

    private void initView() {
        listView = findViewById(R.id.app_list_view);
        //执行后台下载
        GetJsonDataAsyncTask getJsonDataAsyncTask = new GetJsonDataAsyncTask();
        getJsonDataAsyncTask.execute(JSON_URL);
    }

    /**
     * 异步下载
     */
    private class GetJsonDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return getJsonData(strings[0]);
        }

        @Override
        protected void onPostExecute(String dataJson) {
            super.onPostExecute(dataJson);
            jsonDataAll = new JsonData();
            List<DataArrays> dataArraysList = new ArrayList<>();
            try {
                //解析json数据
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
                        DataArrays mdataArray = new DataArrays();
                        mdataArray.setId(id);
                        mdataArray.setLearner(learner);
                        mdataArray.setName(name);
                        mdataArray.setPicSmall(smallPic);
                        mdataArray.setPicBig(bigPic);
                        mdataArray.setDescription(description);
                        dataArraysList.add(mdataArray);
                    }
                    jsonDataAll.setData(dataArraysList);
                    //为listview设置适配器
                    listView.setAdapter(new SetJsonDataAdapter(JsonDataActivity.this, dataArraysList));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取数据
     *
     * @param jsonUrl
     * @return
     */
    private String getJsonData(String jsonUrl) {

        try {
            URL url = new URL(jsonUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5 * 1000);
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
                return jsonData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

}
