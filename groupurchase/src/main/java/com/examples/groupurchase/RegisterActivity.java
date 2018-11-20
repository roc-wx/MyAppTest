package com.examples.groupurchase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.examples.groupurchase.bean.User;
import com.examples.groupurchase.dao.UserSQLiteDao;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RESULT_CODE = 110210;
    public static final String TAG = "roc-wx";
    private EditText register_userName;
    private EditText register_password;
    protected Button register_button;
    protected String userName_in_register;
    protected String password_in_register;
    private UserSQLiteDao userSQLiteDao;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
    }

    private void initView() {
        register_userName = findViewById(R.id.id_register_username_et);
        register_password = findViewById(R.id.id_register_password_et);
        register_button = findViewById(R.id.id_register_button);
        register_button.setOnClickListener(this);
    }

    private void initData() {
        //获取生成数据库
        userSQLiteDao = new UserSQLiteDao(this);
        //获取从上个界面传过来的值
        Intent getIntent = getIntent();
        //直接获取从intent传过来的值
        String login_username = getIntent.getStringExtra("username");
        String login_password = getIntent.getStringExtra("password");
        //先获取从intent传过来的bundle
        Bundle login_bundle = getIntent.getBundleExtra("login_bundle");
        //从bundle获取数据
        if (login_bundle != null) {
            String bundle_username = login_bundle.getString("username_bundle");
            String bundle_password = login_bundle.getString("password_bundle");
            Log.i(TAG, "initData:Register包装获取 bundle_username: " + bundle_username + "bundle_password: " + bundle_password);
        }
        //设置控件数据
        register_userName.setText(login_username);
        register_password.setText(login_password);
    }

    /**
     * 获取用户输入的值
     */
    private void getUserInput() {
        //实例化user对象
        user = new User();
        userName_in_register = register_userName.getText().toString();
        password_in_register = register_password.getText().toString();

        //将数据存入user对象
        user.setUsername(userName_in_register);
        user.setPassword(password_in_register);
    }

    @Override
    public void onClick(View v) {
        getUserInput();
        switch (v.getId()) {
            case R.id.id_register_button:
                //将获取到的数据保存到数据库
                //判断是否有重复数据，有则不更新数据库，并通知客户
                if (!userSQLiteDao.isRepeat(user) && !userName_in_register.isEmpty() && !password_in_register.isEmpty()) {
                    long id = userSQLiteDao.addUserInfo(user);
                    Toast.makeText(this, "注册成功，ID：" + id, Toast.LENGTH_SHORT).show();
                    //当前activity销毁时将数据返回的方法
                    Intent intent = new Intent();
                    //方法一，直接从intent传值
                    intent.putExtra("username", userName_in_register);
                    intent.putExtra("password", password_in_register);
                    //方法二，用bundle包装后再传
                    Bundle bundle = new Bundle();
                    bundle.putString("username", userName_in_register);
                    bundle.putString("password", password_in_register);
                    Log.i(TAG, "onClick: Register发送数据 username：" + userName_in_register + " password: " + password_in_register);

                    //为intent设置bundle
                    intent.putExtra("bundle", bundle);
                    //设置返回结果，使用此方法的前提是跳转到此界面的方法为startActivityForResult();
                    setResult(RESULT_CODE, intent);
                    //关闭当前界面
                    finish();
                } else {
                    Toast.makeText(this, "数据为空或用户已存在，请重新输入", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
