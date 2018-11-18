package com.examples.myapplication.learn.storagedemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.examples.myapplication.R;
import com.examples.myapplication.learn.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class InternalActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "roc-wx";
    private EditText username;
    private EditText password;
    private Button rigster;
    private Button analysis_button;
    private String username_input;
    private String password_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_preference);
        initView();
    }

    private void initView() {
        username = findViewById(R.id.tv_username);
        password = findViewById(R.id.text_input_password);
        rigster = findViewById(R.id.register_button);
        analysis_button = findViewById(R.id.analysis_button);
        analysis_button.setOnClickListener(this);
        rigster.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /*
         * App专属文件
         * 这类文件应该是随着app删除而一起删除的，它们可以被存储在两个地方：internal storage 和 external storage，
         * internal storage很小，所以应该很正确的使用它，因为SD卡有可能会被用户卸下，换成新的，所以SD卡不是任何时间都可用的，
         * 因此我们必须将一些重要的数据库文件以及一些用户配置文件存放在internal storage中。
         * 将一些大的图片或文件等缓存放到external storage中。

         * 内部存贮（internal storage）
         * app私有的目录，你的shared preference文件，数据库文件，都存储在这里。
         * 访问方法为：
         * context.getFilesDir()  data/data/包名/files/。。。
         * context.getCacheDir()  data/data/包名/cache/。。。缓存路径
         *
         * 外部存贮（external storage ）
         * 应该随着App的删除而一起删除的文件不应该存在SD卡的根目录下。
         * 例如一种格式的电子书，只有该app才可以打开，如果用户删除了该app，
         * 那么留下来的电子书就成为了一种无法打开的垃圾文件，所以应该随着该app一起删除掉。
         * 获得这个路径的方法：
         * context.getExternalFilesDir()  sdcard/Android/data/包名/files/。。。
         * context.getExternalCacheDir()  sdcard/Android/data/包名/cache/。。。缓存路径
         * 判断是否使用内外部存贮的标准是是否有sd卡
         *
         *App独立文件
         * 这类文件当我们删除应用之后，还应该保留在手机上的，例如拍照的照片，不应该随着删除应用而被删除掉。
         * 对于这类文件，Android给我们提供了特定的目录，这些目录都是以DIRECTORY开头的，
         * 例如：DIRECTORY_MUSIC , DIRECTORY_PICTURES.
         *访问这些文件夹有两种方式：
         * 第一种：
         * File sdCard = Environment.getExternalStorageDirectory();
         * 这个sdCard的路径为mnt/sdcard/ 即为SD卡根路径，我们可以指定访问的文件夹名
         * File sdCard = Environment.getExternalStorageDirectory();
         * File directory_pictures = new File(sdCard, "Pictures");
         * Log.i(TAG,"directory_pictures="+directory_pictures);
         * 第二种：
         * File directory_pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
         * Log.e(TAG, "directory_pictures="+directory_pictures);
         * 得到的路径如下：
         * 第二种方法是一个更加方便的访问Android给我们提供好的一些公共目录的方法，第一种方式更加灵活，可以自己指定目录。
         * getAbsoluteFile() 绝对路径
         * 参考资料：https://blog.csdn.net/nugongahou110/article/details/48154859
         */

        File file = new File(getFilesDir(), "user_info.txt");
        switch (v.getId()) {

            case R.id.register_button:
//                final String externalStorageDirectory = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/lear_roc_wx/";
//                Log.i(TAG, "externalStorageDirectory :" + externalStorageDirectory);
                username_input = username.getText().toString();
                password_input = password.getText().toString();
                String registerInfo = "username:" + username_input + "password:" + password_input;
                try {
                    if (!file.exists()) {
                        boolean isCreate = file.createNewFile();
                        if (!isCreate) {
                            Toast.makeText(this, "文件创建失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    OutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(registerInfo.getBytes());
                    outputStream.flush();
                    outputStream.close();
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Util.isExistSDCard()) {
                    Toast.makeText(this, "有SD卡"+Util.getSDAllSize(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "无SD卡", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.analysis_button:
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    byte[] bytes = new byte[1024];
                    int len = fileInputStream.read(bytes);
                    String input = new String(bytes, 0, len);
                    Toast.makeText(this, input, Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
}
