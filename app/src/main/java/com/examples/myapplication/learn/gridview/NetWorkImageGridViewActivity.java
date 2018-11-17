package com.examples.myapplication.learn.gridview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.adapter.ShowImageGridViewAdapter;
import com.examples.myapplication.learn.model.GroupPurchaseJsonData;
import com.examples.myapplication.learn.util.Util;
import com.examples.myapplication.learn.view.AppGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NetWorkImageGridViewActivity extends AppCompatActivity {


    public static final String JSON_URL = "http://www.imooc.com/api/shopping?type=11";
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
            showImageGridViewAdapter = new ShowImageGridViewAdapter(NetWorkImageGridViewActivity.this, getCommodityInfoList(Util.getJsonDataString(JSON_URL)));
            /**********************************************************************
             * 使用第三方图片加载Glide框架此处需要省略
             */
            for (int i = 0; i < showImageGridViewAdapter.getCount(); i++) {
                GroupPurchaseJsonData.CommodityInfo commodityInfo = (GroupPurchaseJsonData.CommodityInfo) showImageGridViewAdapter.getItem(i);
                commodityInfo.setImg(Util.getImageFromNetWork(commodityInfo.getImgUrl()));
                publishProgress();
            }
            /**********************************************************************/
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
            /**********************************************************************
             * 使用第三方图片加载Glide框架此处需要省略
             */
            showImageGridViewAdapter.notifyDataSetChanged();
            /**********************************************************************/
        }

    }

    /**
     * 解析json数据
     *
     * @return List<GroupPurchaseJsonData.CommodityInfo>
     */
    private List<GroupPurchaseJsonData.CommodityInfo> getCommodityInfoList(String jsonDataString) {
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
                    int price = jsonArrayObject.getInt("price");
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
