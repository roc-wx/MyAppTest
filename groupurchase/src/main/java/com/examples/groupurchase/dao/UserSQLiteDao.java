package com.examples.groupurchase.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.examples.groupurchase.bean.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserSQLiteDao {
    private static final String TAG = "roc-wx";
    private SQLiteDatabase sqLiteDatabase;

    public UserSQLiteDao(Context context) {
        //获取应用的内部存贮路径
        String dataBaseDir = context.getFilesDir() + File.separator + "/user_info.db";
        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(context, dataBaseDir, null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String sql = "create table user_info(_id integer primary key autoincrement," +
                        "username varchar(20)," +
                        "password varchar(20)," +
                        "user_issue varchar(20)," +
                        "password_security vachar(20))";
                db.execSQL(sql);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
    }

    /**
     * 添加用户信息
     *
     * @return Long
     */
    public Long addUserInfo(User user) {
        //方法一
        //String sql = "insert into user_info (username,password) values(?,?)";
        //sqLiteDatabase.execSQL(sql, new Object[]{user.getUser_issue(), user.getPassword()});
        //方法二
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        return sqLiteDatabase.insert("user_info", null, values);
    }

    /**
     * 查询用户信息
     *
     * @return List<User>
     */
    private List<User> queryUserInfo(User user) {
        List<User> userList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("user_info",
                new String[]{"username", "password"},
                "username=?",
                new String[]{user.getUsername()},
                null, null, null);
        while (cursor.moveToNext()) {
            User users1 = new User();
            String username = cursor.getString(0);
            String password = cursor.getString(1);
            Log.i(TAG, "queryUserInfo: username: " + username + " password:" + password);
            users1.setUsername(username);
            users1.setPassword(password);
            userList.add(users1);
        }
        cursor.close();
        return userList;
    }

    /**
     * 判断用户输入是否重复
     *
     * @return boolean
     */
    public boolean isRepeat(User user) {
        List<User> userList = queryUserInfo(user);
        for (int i = 0; i < userList.size(); i++) {
            User users2 = userList.get(i);
            if (users2.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否登录
     *
     * @return boolean
     */
    public boolean isLogin(User user) {
        List<User> userList = queryUserInfo(user);
        for (int i = 0; i < userList.size(); i++) {
            User users2 = userList.get(i);
            if (users2.getUsername().equals(user.getUsername()) && users2.getPassword().equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

}
