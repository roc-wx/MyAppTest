package com.examples.groupurchase.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;


import com.examples.groupurchase.bean.AppInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Util {

    private static final String TAG = "roc-wx";

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
            //appInfo.setClassName(packageInfo.applicationInfo.className);//*****无法正确获取应用类名
            //Log.i(TAG, "APK ClassName: " + appInfo.getClassName());
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
//            Log.i(TAG, "APK第一次安装的时间: " + appDate);
            appInfo.setAppDate(appDate);
            //获取APK文件的路径
            String publicSourceDir = packageInfo.applicationInfo.publicSourceDir;
            appInfo.setPackagePath(publicSourceDir);
            Log.i(TAG, "APK文件的路径: " + publicSourceDir);
            /*
             * 判断(applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)的值，
             * 该值大于0时，表示获取的应用为系统预装的应用，反之则为手动安装的应用。
             * >=0 全部显示
             */
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
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

    /**
     * 根据提供的url获取网络图片
     *
     * @return Bitmap
     */
    public static Bitmap getImageFromNetWork(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(10 * 1000);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Stream转换String
     *
     * @return String
     */
    public static String streamToString(InputStream stream) {
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

    /**
     * 将Unicode字符转换为UTF-8类型字符串
     *
     * @return String
     */
    public static String unicodeToUTF8(String unicodeStr) {
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

    /**
     * 将字符串编码为UTF-8
     *
     * @return String
     */
    public static String getStringEncodeToUTF8(String imooc) {
        String encode = null;
        try {
            encode = URLEncoder.encode(imooc, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }

    /**
     * 获取当前网络连接状态
     *
     * @return boolean
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        Log.d(TAG, "networkInfo:  " + networkInfo);
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * 判断SD是否存在
     *
     * @return boolean
     */
    public static boolean isExistSDCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取sd卡状态
     *
     * @return String
     */
    public static String getSDCardState() {
        String state;
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            state = "SD卡存在";// 此时SD是可读写的
            Log.i(TAG, "getSDCardState: SD卡存在");
        } else if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            state = "SD存在，但是为只读状态";
            Log.i(TAG, "getSDCardState: SD存在，但是为只读状态");
        } else if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_REMOVED)) {
            state = "SD不存在";
            Log.i(TAG, "getSDCardState: SD不存在");
        } else if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_SHARED)) {
            state = "SD卡存在，但是正与PC等相连接";
            Log.i(TAG, "getSDCardState: SD卡存在，但是正与PC等相连接");
        } else if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_BAD_REMOVAL)) {
            state = "SD卡在挂载状态下被错误取出";
            Log.i(TAG, "getSDCardState: SD卡在挂载状态下被错误取出");
        } else if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_CHECKING)) {
            state = "正在检查SD卡...";
            Log.i(TAG, "getSDCardState: 正在检查SD卡...");
        } else if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_NOFS)) {
            state = "SD卡存在，但其文件系统不被支持";
            Log.i(TAG, "getSDCardState: SD卡存在，但其文件系统不被支持");
        } else if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_UNMOUNTABLE)) {
            state = "SD卡存在，但是无法被挂载";
            Log.i(TAG, "getSDCardState: SD卡存在，但是无法被挂载");
        } else if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_UNMOUNTED)) {
            state = "SD卡存在，但是未被挂载";
            Log.i(TAG, "getSDCardState: SD卡存在，但是未被挂载");
        } else {
            state = "未知状态...";
            Log.i(TAG, "getSDCardState: 未知状态..");
        }
        return state;
    }

    /**
     * 获取SD剩余空间
     *
     * @return long
     */
    public static long getSDFreeSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; //单位MB
    }

    /**
     * 获取SD总容量
     *
     * @return long
     */
    public static long getSDAllSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //获取所有数据块数
        long allBlocks = sf.getBlockCount();
        //返回SD卡大小
        //return allBlocks * blockSize; //单位Byte
        //return (allBlocks * blockSize)/1024; //单位KB
        return (allBlocks * blockSize) / 1024 / 1024; //单位MB
    }
}
