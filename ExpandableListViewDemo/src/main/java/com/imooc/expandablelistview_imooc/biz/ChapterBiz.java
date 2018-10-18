package com.imooc.expandablelistview_imooc.biz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.imooc.expandablelistview_imooc.bean.Chapter;
import com.imooc.expandablelistview_imooc.bean.ChapterItem;
import com.imooc.expandablelistview_imooc.dao.ChapterDao;
import com.imooc.expandablelistview_imooc.db.ChapterDbHelper;
import com.imooc.expandablelistview_imooc.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

/**
 * Created by baidu on 2018/6/10.
 */

public class ChapterBiz {

    private ChapterDao chapterDao = new ChapterDao();

    public void loadDatas(final Context context, final CallBack callBack,
                          final boolean useCache) {
        AsyncTask<Boolean, Void, List<Chapter>> asyncTask
                = new AsyncTask<Boolean, Void, List<Chapter>>() {

            private Exception ex;

            @Override
            protected void onPostExecute(List<Chapter> chapters) {
                if (ex != null) {
                    callBack.loadFailed(ex);
                } else {
                    callBack.loadSuccess(chapters);
                }

            }

            @Override
            protected List<Chapter> doInBackground(Boolean... booleans) {
                final List<Chapter> chapters = new ArrayList<>();

                try {
                    // 从缓存中取
                    if (booleans[0]) {
                        chapters.addAll(chapterDao.loadFromDb(context));
                    }
                    Log.d("zhy", "loadFromDb -> " + chapters);

                    if (chapters.isEmpty()) {
                        // 从网络获取
                        final List<Chapter> chaptersFromNet = loadFromNet(context);
                        // 缓存在数据库
                        chapterDao.insertToDb(context, chaptersFromNet);
                        Log.d("zhy", "loadFromNet -> " + chaptersFromNet);
                        chapters.addAll(chaptersFromNet);
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    ex = e;
                }
                return chapters;
            }
        };
        asyncTask.execute(useCache);
    }

    public static interface CallBack {
        void loadSuccess(List<Chapter> chapterList);

        void loadFailed(Exception ex);
    }

    public List<Chapter> loadFromNet(Context context) {

        final String url = "http://www.wanandroid.com/tools/mockapi/2/mooc-expandablelistview";

        String content = HttpUtils.doGet(url);
        final List<Chapter> chapterList = parseContent(content);
        // 缓存到数据库
        chapterDao.insertToDb(context, chapterList);

        return chapterList;
    }

    private List<Chapter> parseContent(String content) {

        List<Chapter> chapters = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(content);
            int errorCode = jsonObject.optInt("errorCode");
            if (errorCode == 0) {
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject chapterJson = jsonArray.getJSONObject(i);
                    int id = chapterJson.optInt("id");
                    String name = chapterJson.optString("name");
                    Chapter chapter = new Chapter(id, name);
                    chapters.add(chapter);

                    JSONArray chapterItems = chapterJson.optJSONArray("children");
                    for (int j = 0; j < chapterItems.length(); j++) {
                        JSONObject chapterItemJson = chapterItems.getJSONObject(j);
                        id = chapterItemJson.optInt("id");
                        name = chapterItemJson.optString("name");
                        ChapterItem chapterItem = new ChapterItem(id, name);
                        chapter.addChild(chapterItem);
                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chapters;
    }

}
