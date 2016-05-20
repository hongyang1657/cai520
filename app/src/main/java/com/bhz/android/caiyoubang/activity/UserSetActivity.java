package com.bhz.android.caiyoubang.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bhz.android.caiyoubang.MainActivity;
import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.db.DBConfig;
import com.bhz.android.caiyoubang.db.DBOperator;
import com.bhz.android.caiyoubang.db.MyDbHelper;
import com.bhz.android.caiyoubang.domian.UserData;

/**
 * Created by Administrator on 2016/4/26 0026.
 */
// 设置界面缺少的内容   1. 跳转 主页面的设置
//                      2. switch的设置完全没有完成
//                      3. 如何清理软件缓存
//                      4. 缺少两个界面 一个关于我们 一个帮助
public class UserSetActivity extends Activity {

    Button btnBack;     // 设置界面的返回按钮
    Switch swiVoice;   // 设置界面的声音开关
    Switch swiShock;   // 设置界面的震动开关
    TextView tvClean;  // 设置界面的清理缓存设置
    TextView tvVersions;// 设置界面的版本号
    TextView tvAboutUs;// 设置界面的关于我们按钮
    TextView tvHelp;   // 设置界面的帮助按钮
    TextView tvOut;    // 设置界面的退出账号按钮

    SharedPreferences sharedPreferences;

    MyDbHelper myDbHelper;
    SQLiteDatabase database;
    DBOperator operator;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_set_layout);
        initData();
        btnBack.setOnClickListener(click);
        tvClean.setOnClickListener(click);
        tvAboutUs.setOnClickListener(click);
        tvHelp.setOnClickListener(click);
        tvOut.setOnClickListener(click);
    }

    private void initData(){
        myDbHelper = new MyDbHelper(this);
        operator = new DBOperator(this);
        sharedPreferences = getSharedPreferences("app",MODE_PRIVATE);
        btnBack= (Button) findViewById(R.id.btn_user_set_back);
        swiVoice= (Switch) findViewById(R.id.swit_user_set_voice);
        swiShock= (Switch) findViewById(R.id.swit_user_set_shock);
        tvClean = (TextView) findViewById(R.id.tv_user_set_clean_cache);
        tvVersions= (TextView) findViewById(R.id.tv_user_set_versions);
        tvAboutUs= (TextView) findViewById(R.id.tv_user_set_about_us_btn);
        tvHelp= (TextView) findViewById(R.id.tv_user_set_help_btn);
        tvOut= (TextView) findViewById(R.id.tv_user_set_out_id);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_user_set_back:
                    Intent intentBack = new Intent(UserSetActivity.this,MainActivity.class);    // ！！！！！！！！！！！！！！！！返回按钮跳转到主界面的我的页面
                    startActivity(intentBack);
                    break;
                case R.id.tv_user_set_clean_cache:                                              // !！！！！！！！！！！！！！！ 没有处理缓存机制
                     break;
                case R.id.tv_user_set_about_us_btn:
                    Intent intentAboutUs = new Intent(UserSetActivity.this,MainActivity.class); // ！！！！！！！！！！！！！！！缺少关于我们的页面
                    startActivity(intentAboutUs);
                    break;
                case R.id.tv_user_set_help_btn:
                    Intent intentHelp = new Intent(UserSetActivity.this,MainActivity.class);    // !！！！！！！！！！！！！！！ 缺少帮助的页面
                    startActivity(intentHelp);
                    break;
                case R.id.tv_user_set_out_id:
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("LoginCode",0);
                    editor.putInt("LoginType",3);
                    editor.putString("USERID","0");
                    editor.commit();
                    int a = sharedPreferences.getInt("LoginCode",12);
                    Intent intentOut = new Intent(UserSetActivity.this,MainActivity.class);
                    startActivity(intentOut);
                    break;
            }
        }
    };



    @Override
    protected void onStart() {
        super.onStart();
        database=myDbHelper.getReadableDatabase();
        database=myDbHelper.getWritableDatabase();
    }

    @Override
    protected void onStop() {
        super.onStop();
        database.close();
    }
}
