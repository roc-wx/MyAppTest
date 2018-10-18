package com.example.administrator.storagedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void operate(View v){
        Intent it=null;
        switch (v.getId()){
            case R.id.share_btn:
                it = new Intent(this,ShareActivity.class);
                break;
            case R.id.external_btn:
                it = new Intent(this,ExternalActivity.class);
                break;
            default:
                it = new Intent(this,InternalActivity.class);
                break;
        }
        startActivity(it);
    }
}
