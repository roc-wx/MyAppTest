package com.examples.myapplication.learn.gridview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.adapter.ShowImageGridViewAdapter;
import com.examples.myapplication.learn.model.GroupPurchaseJsonData;
import com.examples.myapplication.learn.util.Util;
import com.examples.myapplication.learn.view.AppGridView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NetWorkImageGridViewActivity extends AppCompatActivity {


    public static final String JSON_URL = "http://www.imooc.com/api/shopping?type=11";
    public static final String TAG = "roc-wx";
    private AppGridView gridView;
    private ShowImageGridViewAdapter showImageGridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        initView();
        showAppGrid();
    }


    /**
     * 初始化布局
     */
    private void initView() {
        gridView = findViewById(R.id.app_grid_view);
    }

    /**
     * 设置适配器，显示应用
     */
    private void showAppGrid() {
        ImageLoadAsyncTask imageLoadAsyncTask = new ImageLoadAsyncTask();
        imageLoadAsyncTask.execute();
    }


    public class ImageLoadAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            //jsonObject解析
            //List<GroupPurchaseJsonData.CommodityInfo> commodityInfos = getCommodityInfoListJsonObj(jsonDataString);
            //Gson解析
             String jsonDataString = Util.getJsonDataString(JSON_URL);
            List<GroupPurchaseJsonData.CommodityInfo> commodityInfos = getCommodityInfoListGson(jsonDataString);
            //设置适配器
            showImageGridViewAdapter = new ShowImageGridViewAdapter(NetWorkImageGridViewActivity.this, commodityInfos);

            //********************使用第三方图片加载Glide框架此处需要省略----start
            for (int i = 0; i < showImageGridViewAdapter.getCount(); i++) {
                GroupPurchaseJsonData.CommodityInfo commodityInfo = (GroupPurchaseJsonData.CommodityInfo) showImageGridViewAdapter.getItem(i);
                commodityInfo.setDowloadImg(Util.getImageFromNetWork(commodityInfo.getImg()));
                publishProgress();
            }
            //*******************************************************************end
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            gridView.setAdapter(showImageGridViewAdapter);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            /**********使用第三方图片加载Glide框架此处可以省略*****/
            showImageGridViewAdapter.notifyDataSetChanged();
            /******************************************************/
        }

    }

    /**
     * 使用第三方加载框架Gson,解析json
     *
     * @return List<GroupPurchaseJsonData.CommodityInfo>
     */
    private List<GroupPurchaseJsonData.CommodityInfo> getCommodityInfoListGson(String jsonDataString) {

        //注意：要使用GsonFormat工具生成实体类，其中的名字不可以更改否则不能解析信息
        //            Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
        //                @Override
        //                public boolean shouldSkipField(FieldAttributes f) {
        //                    //不解析img的属性
        //                    return f.getName().equals("img");
        //                }
        //
        //                @Override
        //                public boolean shouldSkipClass(Class<?> clazz) {
        //                    return false;
        //                }
        //            }).create();

        Gson gson = new Gson();
        //参数1：要解析的json字符串 参数2：要解析成的实体类（GsonFormat可以生成）
        GroupPurchaseJsonData groupPurchaseJsonDataGson = gson.fromJson(jsonDataString, GroupPurchaseJsonData.class);
        return groupPurchaseJsonDataGson.getData();
    }

    /**
     * JSONObject解析json数据,使用Gson框架解析可省略
     *
     * @return List<GroupPurchaseJsonData.CommodityInfo>
     */
    private List<GroupPurchaseJsonData.CommodityInfo> getCommodityInfoListJsonObj(String jsonDataString) {
        GroupPurchaseJsonData groupPurchaseJsonData = new GroupPurchaseJsonData();
        List<GroupPurchaseJsonData.CommodityInfo> commodityInfoList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonDataString);
            int status = jsonObject.getInt("status");
            String msg = jsonObject.getString("msg");
            groupPurchaseJsonData.setMsg(msg);
            groupPurchaseJsonData.setStatus(status);
            JSONArray dataArrays = jsonObject.getJSONArray("data");
            if (dataArrays != null && dataArrays.length() > 0) {
                for (int i = 0; i < dataArrays.length(); i++) {
                    JSONObject jsonArrayObject = (JSONObject) dataArrays.get(i);
                    int id = jsonArrayObject.getInt("id");
                    String name = jsonArrayObject.getString("name");
                    String img = jsonArrayObject.getString("img");
                    int count = jsonArrayObject.getInt("count");
                    String price = jsonArrayObject.getString("price");
                    String description = jsonArrayObject.getString("description");
                    String action = jsonArrayObject.getString("action");
                    GroupPurchaseJsonData.CommodityInfo commodityInfo = new GroupPurchaseJsonData.CommodityInfo();
                    commodityInfo.setId(id);
                    commodityInfo.setName(name);
                    commodityInfo.setImgUrl(img);
                    commodityInfo.setCount(count);
                    commodityInfo.setPrice(price);
                    commodityInfo.setDescription(description);
                    commodityInfo.setAction(action);
                    commodityInfoList.add(commodityInfo);
                }
                groupPurchaseJsonData.setData(commodityInfoList);
                return commodityInfoList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commodityInfoList;
    }

}
