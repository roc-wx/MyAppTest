package com.examples.myapplication.learn.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.examples.myapplication.learn.model.AppInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Util {

    /**
     * 获取手机当前应用列表方法一（推荐使用）
     *
     * @return List<ResolveInfo>
     */
    public static List<ResolveInfo> getResolveInfoList(Context context) {
        return context.getPackageManager().queryIntentActivities(new Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER), 0);
    }

    /**
     * 获取手机当前应用列表方法二，AppInfo为自定义应用实体类
     *
     * @return List<AppInfo>
     */

    public static List<AppInfo> getAppInfoList(Context context) {
        //创建一个集合用于存放应用信息
        List<AppInfo> appInfoList = new ArrayList<>();
        //获取包管理对象
        PackageManager pm = context.getPackageManager();
        //从包管理对象中获取以安装应用
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        //循环获取每一个应用
        for (int i = 0; i < packageInfos.size(); i++) {
            //实例化一个应用信息对象
            AppInfo appInfo = new AppInfo();
            //获取应用的包信息
            PackageInfo packageInfo = packageInfos.get(i);
            //图标
            appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(pm));
            //名称
            appInfo.setAppName(packageInfo.applicationInfo.loadLabel(pm) + "");
            //类名----》此处无法正确获取应用类名*********
            //appInfo.setClassName(packageInfo.applicationInfo.name);//*****无法正确获取应用类名
            //包名
            appInfo.setPackageName(packageInfo.applicationInfo.packageName);
            //版本名
            appInfo.setVersionName(packageInfo.versionName);
            //版本号
            appInfo.setVersionCode(packageInfo.versionCode);
            //应用第一次安装的时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            long appDate1 = packageInfo.firstInstallTime;
            String appDate = String.valueOf(dateFormat.format(appDate1));
            Log.i("TAG", "APK第一次安装的时间: " + appDate);
            appInfo.setAppDate(appDate);
            //获取APK文件的路径
            String publicSourceDir = packageInfo.applicationInfo.publicSourceDir;
            appInfo.setPackagePath(publicSourceDir);
            Log.i("TAG", "APK文件的路径: " + publicSourceDir);
            /*
             * 判断(applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)的值，
             * 该值大于0时，表示获取的应用为系统预装的应用，反之则为手动安装的应用。
             * >=0 全部显示
             */
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) >= 0) {
                //将获取到的应用信息存入集合中
                appInfoList.add(appInfo);
            }
        }
        return appInfoList;
    }

    /**
     * 根据提供的json Url获取json数据，返回json字符串
     *
     * @return String
     */
    public static String getJsonDataString(String jsonUrl) {
        String jsonDataString = "";
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
                jsonDataString = new String(byteArrayOutputStream.toByteArray());
                return jsonDataString;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonDataString;
    }
}
