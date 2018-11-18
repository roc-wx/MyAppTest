package com.examples.myapplication.learn.storagedemo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.examples.myapplication.R;

public class SharePreferenceActivity extends AppCompatActivity implements View.OnClickListener {

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
        initData();
    }

    private void initData() {
        //创建sharedPreference对象,参数一：文件名，参数二：模式
        SharedPreferences sharedPreferences_get = getSharedPreferences("user_register_info", MODE_PRIVATE);
        String username_get = sharedPreferences_get.getString("username", "没有注册信息");
        String password_get = sharedPreferences_get.getString("password", "没有信息");
        username.setText(username_get);
        password.setText(password_get);
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
        switch (v.getId()) {
            case R.id.register_button:
                username_input = username.getText().toString();
                password_input = password.getText().toString();
                //创建sharedPreference对象,参数一：文件名，参数二：模式
                SharedPreferences sharedPreferences = getSharedPreferences("user_register_info", MODE_PRIVATE);
                //获取编辑器对象
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //使用编辑器对象存贮数据
                Log.i(TAG, "username_input: " + username_input);
                editor.putString("username", username_input);
                editor.putString("password", password_input);
                //提交数据
                editor.commit();
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.analysis_button:
                //创建sharedPreference对象,参数一：文件名，参数二：模式
                SharedPreferences sharedPreferences_get = getSharedPreferences("user_register_info", MODE_PRIVATE);
                String username_get = sharedPreferences_get.getString("username", "没有信息");
                String password_get = sharedPreferences_get.getString("password", "没有信息");
                Toast.makeText(this, "输入用户名：" + username_get + " 密码：" + password_get, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
