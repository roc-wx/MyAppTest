package com.imooc.gridview;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.GridView;
import com.imooc.gridview.adapter.GridAdapter2;
import com.imooc.gridview.model.AppInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyonline on 16/8/30.
 */
public class ExampleActivity2 extends Activity {




    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);
        gridView= (GridView) findViewById(R.id.gridview);
        GridAdapter2 gridAdapter2=new GridAdapter2(this,getAppList());
        gridView.setAdapter(gridAdapter2);

    }


    public List<AppInfo> getAppList()
    {
        List<AppInfo>appInfoList=new ArrayList<AppInfo>();
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);

        for(int i=0;i<installedPackages.size();i++)
        {
            PackageInfo packageInfo = installedPackages.get(i);
            AppInfo appInfo=new AppInfo();
            appInfo.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
            appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(packageManager));
            appInfo.setPackageName(packageInfo.packageName);
            appInfo.setVersionCode(packageInfo.versionCode);
            appInfo.setVersionName(packageInfo.versionName);
            if((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)==0)
            {
               appInfoList.add(appInfo);
            }
        }

        return appInfoList;
    }
}
