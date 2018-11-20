package com.examples.groupurchase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.examples.groupurchase.bean.User;
import com.examples.groupurchase.dao.UserSQLiteDao;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_CODE = 1001;
    public static final String TAG = "roc-wx";
    public static final int RESULT_CODE = 110210;
    private EditText login_userName;
    private EditText login_password;
    protected Button login_button;
    protected TextView retrieve_password;
    protected TextView register_tv;
    protected String userName_in;
    protected String password_in;
    private User user;
    private UserSQLiteDao userSQLiteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        userSQLiteDao = new UserSQLiteDao(this);
    }

    private void initView() {
        login_userName = findViewById(R.id.id_login_username_et);
        login_password = findViewById(R.id.id_login_password_et);
        login_button = findViewById(R.id.id_login_button);
        retrieve_password = findViewById(R.id.id_login_retrieve_password_tvbt);
        register_tv = findViewById(R.id.id_login_register_tvbt);
        login_button.setOnClickListener(this);
        retrieve_password.setOnClickListener(this);
        register_tv.setOnClickListener(this);

    }

    /**
     * 获取用户数据
     */
    private void getUserLoginInput() {
        user = new User();
        userName_in = login_userName.getText().toString();
        password_in = login_password.getText().toString();
        user.setUsername(userName_in);
        user.setPassword(password_in);
        /**
         * 在Android里面可以通过setOnEditorActionListener监听回车达到屏蔽回车按键的目的。
         * 它在API的TextView(EditText的父类)，
         * 谷歌大意这样描述它：它是一个特殊的监听器，用于监听一个Enter键，如果设备具有物理键盘，
         * 点击Enter建不会插入回车符，不过按着alt键可以修复。
         */
        login_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //当actionId == XX_SEND 或者 XX_DONE时都触发
                //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    isLogin();
                }
                return false;
            }
        });
    }

    private void isLogin() {
        if (userSQLiteDao.isLogin(user)) {
            startActivity(new Intent().setClass(this, MainActivity.class));
        } else {
            Toast.makeText(this, "用户名或密码错误，请重新输入", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        getUserLoginInput();
        switch (v.getId()) {
            case R.id.id_login_button:
                isLogin();
                break;
            case R.id.id_login_retrieve_password_tvbt:
                Toast.makeText(this, "攻城狮正在日夜不停的赶工中...", Toast.LENGTH_LONG).show();
                startActivity(new Intent().setClass(this, MainActivity.class));
                break;
            case R.id.id_login_register_tvbt:
                //往下个页面传值
                Intent intent = new Intent(this, RegisterActivity.class);
                if (!userName_in.isEmpty() && !password_in.isEmpty()) {
                    //方法一，直接传
                    intent.putExtra("username", userName_in);
                    intent.putExtra("password", password_in);
                    //方法二，放到bundle中在传
                    Bundle bundle = new Bundle();
                    bundle.putString("username_bundle", userName_in);
                    bundle.putString("password_bundle", password_in);
                    //为intent设置bundle
                    intent.putExtra("login_bundle", bundle);
                    Log.i(TAG, "onClick: Login发送数据 username：" + userName_in + " password: " + password_in);
                }
                //跳转，设置为可以返回结果的方式
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            //从返回结果中获取bundle
            Bundle bundle = data.getBundleExtra("bundle");
            String username_bundle = bundle.getString("username");
            String password_bundle = bundle.getString("password");
            //从返回结果中直接获取数据
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");
            login_userName.setText(username_bundle);
            //login_password.setText(password_bundle);
            Log.i(TAG, "onActivityResult: 直接获取 username: " + username + " password: " + password + " requestCode:" + requestCode + " resultCode: " + resultCode);
        }
    }

}
