package com.examples.groupurchase;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.examples.groupurchase.bean.GridviewBean;
import com.examples.groupurchase.bean.ShopInfo;
import com.examples.groupurchase.util.CommonAdapter;
import com.examples.groupurchase.util.CommonViewHolder;
import com.examples.groupurchase.util.Util;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String JSON_URL = "http://www.imooc.com/api/shopping?type=11";
    private GridView gridView;
    private List<GridviewBean> gridviewList;
    private ListView listView;
    private List<ShopInfo.DataBean> dataBeanListView;
    private CommonAdapter commonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        initData();
        gridView = findViewById(R.id.id_gridview);
        listView = findViewById(R.id.id_listview);
        gridView.setAdapter(new CommonAdapter<GridviewBean>(this, gridviewList, R.layout.item_gridview) {
            @Override
            public void convert(CommonViewHolder holder, GridviewBean gridviewBean) {
                holder.setImageViewResouce(R.id.id_gridview_img, gridviewBean.getGridview_image());
                holder.setText(R.id.id_gridview_text, gridviewBean.getGridview_text());
            }
        });


    }

    private void initData() {
        new ImageLoadAsyncTask().execute();
        gridviewList = new ArrayList<>();
        gridviewList.add(new GridviewBean(R.drawable.fly1, "飞机"));
        gridviewList.add(new GridviewBean(R.drawable.car, "车票"));
        gridviewList.add(new GridviewBean(R.drawable.autombile1, "汽车"));
        gridviewList.add(new GridviewBean(R.drawable.cake, "蛋糕"));
        gridviewList.add(new GridviewBean(R.drawable.food, "美食"));
        gridviewList.add(new GridviewBean(R.drawable.watch, "手表"));
        gridviewList.add(new GridviewBean(R.drawable.cp, "电脑"));
        gridviewList.add(new GridviewBean(R.drawable.phone, "手机"));
    }

    /**
     * 使用第三方加载框架Gson,解析json
     *
     * @return List<GroupPurchaseJsonData.CommodityInfo>
     */
    private List<ShopInfo.DataBean> getShopInfoListGson(String jsonDataString) {
        Gson gson = new Gson();
        //参数1：要解析的json字符串 参数2：要解析成的实体类（GsonFormat可以生成）
        ShopInfo shopInfo = gson.fromJson(jsonDataString, ShopInfo.class);
        return shopInfo.getData();
    }

    /**
     * 普通解析
     */
    private List<ShopInfo.DataBean> JsonDataAnalysis(String dataJson) {
        ShopInfo shopInfo = new ShopInfo();
        List<ShopInfo.DataBean> dataArraysList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(dataJson);
            int status = jsonObject.getInt("status");
            String msg = jsonObject.getString("msg");
            shopInfo.setMsg(msg);
            shopInfo.setStatus(status);
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
                    ShopInfo.DataBean mdataArray = new ShopInfo.DataBean();
                    mdataArray.setId(id);
                    mdataArray.setName(name);
                    mdataArray.setImg(img);
                    mdataArray.setCount(count);
                    mdataArray.setPrice(price);
                    mdataArray.setDescription(description);
                    mdataArray.setAction(action);
                    dataArraysList.add(mdataArray);
                }
                shopInfo.setData(dataArraysList);
                return dataArraysList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataArraysList;
    }

    public class ImageLoadAsyncTask extends AsyncTask<Void, Void, List<ShopInfo.DataBean>> {
        @Override
        protected List<ShopInfo.DataBean> doInBackground(Void... voids) {
            dataBeanListView = JsonDataAnalysis(Util.getJsonDataString(JSON_URL));
            //使用图片加载框架
            commonAdapter = new CommonAdapter<ShopInfo.DataBean>(MainActivity.this, dataBeanListView, R.layout.item_listview) {

                @Override
                public void convert(CommonViewHolder holder, ShopInfo.DataBean dataBean) {
                    //使用图片加载框架
                    holder.setImageViewGlide(R.id.id_imageView_shop_ls, dataBean.getImg(), R.mipmap.ic_launcher);
                    holder.setText(R.id.id_shop_name_ls, dataBean.getName());
                    holder.setText(R.id.id_shop_ins, dataBean.getDescription());
                    holder.setText(R.id.id_shop_price_ls, dataBean.getPrice());
                    holder.setText(R.id.id_shop_new_cus_ins_ls, dataBean.getAction());
                    holder.setText(R.id.id_shop_count_ls, dataBean.getCount() + "");
                }
            };
            return dataBeanListView;
        }

        @Override
        protected void onPostExecute(List<ShopInfo.DataBean> list) {
            super.onPostExecute(list);
            listView.setAdapter(commonAdapter);
        }
    }
}
