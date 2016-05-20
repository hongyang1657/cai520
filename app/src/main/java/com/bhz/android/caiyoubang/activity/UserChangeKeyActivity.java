package com.bhz.android.caiyoubang.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.db.DBOperator;
import com.bhz.android.caiyoubang.db.MyDbHelper;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class UserChangeKeyActivity extends Activity {

    MyDbHelper helper;          //用来操作数据库的类
    SQLiteDatabase database;   // 操作库的对象
    DBOperator operator;       // 数据库操作类

    EditText etFirst;
    EditText etSecond;
    TextView tvChange;

    String userId;            //  保存你是修改谁的账号密码

    SharedPreferences sharedPreferences;   //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_key_layout);
        initData();
    }

    private void initData() {
        etFirst= (EditText) findViewById(R.id.et_change_key_first);
        etSecond= (EditText) findViewById(R.id.et_change_key_second);
        tvChange= (TextView) findViewById(R.id.et_change_key_first);
    }
}
