package com.bhz.android.caiyoubang.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.db.DBConfig;
import com.bhz.android.caiyoubang.db.DBOperator;
import com.bhz.android.caiyoubang.db.MyDbHelper;
import com.bhz.android.caiyoubang.domian.UserData;

/**
 * Created by Administrator on 2016/4/30 0030.
 */
// 缺少的内容  1.存储用户账号密码的输入库方法
//             2.返回主页面的My的跳转
public class UserRegisterActivity extends Activity {

    DBOperator operator;               //数据库帮助操作类的对象
    MyDbHelper helper;                  //创建操作数据库的对象
    SQLiteDatabase database;
    String phone;


    TextView tvTest;                      //账号检测功能
    TextView tvBack;                      //账号密码注册页面的返回
    TextView tvLogin;                     //账号密码注册页面的注册
    EditText etPhoneNumber;              //账号密码注册页面的手机号输入
    EditText etPassWord;                  //账号密码注册页面的密码输入
    EditText etPassWordAgain;         //账号密码注册页面的密码输入确认

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register_layout);
        initData();
        tvBack.setOnClickListener(click);
        tvLogin.setOnClickListener(click);
        tvTest.setOnClickListener(click);
    }

    private void initData() {
        operator = new DBOperator(this);
        helper = new MyDbHelper(this);
        Intent intent =getIntent();
        phone = intent.getStringExtra("phone");
        tvTest = (TextView) findViewById(R.id.tv_user_register_test);
        tvBack = (TextView) findViewById(R.id.tv_user_register_back);
        tvLogin = (TextView) findViewById(R.id.tv_user_register_login);
        etPhoneNumber = (EditText) findViewById(R.id.et_user_register_phone_number);
        etPassWord = (EditText) findViewById(R.id.et_user_register_password);
        etPassWordAgain = (EditText) findViewById(R.id.et_user_register_password_again);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_user_register_back:                          // 返回点击事件
                    Intent intentBack = new Intent(UserRegisterActivity.this, UserPhoneRegisterActivity.class);
                    startActivity(intentBack);
                    break;
                case R.id.tv_user_register_test:
                    testUserId();
                    break;
                case R.id.tv_user_register_login:                         // 注册点击事件
                    String registerUserId = etPhoneNumber.getText().toString();
                    String registerUserPassword = etPassWord.getText().toString();                //从密码框获取用户注册的密码 并保存到数据库中
                    String registerUserPasswordAgain = etPassWordAgain.getText().toString();     //从密码框获取用户注册的密码 确认两次密码一致
                    if (registerUserId.equals("") || registerUserPassword.equals("") || registerUserPasswordAgain.equals("")) {
                        Toast.makeText(UserRegisterActivity.this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    testUserIdTwo();                                                                  //判断用户名是否存在
                    if (!testUserIdTwo()) {
                        break;
                    }
                    if (registerUserPassword.equals(registerUserPasswordAgain)) {
                        saveData();
                        Toast.makeText(UserRegisterActivity.this, "账号注册成功", Toast.LENGTH_SHORT).show();
                        Intent intentLogin = new Intent(UserRegisterActivity.this, UserLoginPageActivity.class);  // ！！！！！！！！！！！！！！！！需要修改成跳转主页面MY
                        startActivity(intentLogin);
                    } else {
                        Toast.makeText(UserRegisterActivity.this, "您输入的两次密码不一致，请检测重新输入。", Toast.LENGTH_SHORT).show();   //判断密码输出是否一致
                    }
                    break;
            }
        }
    };

    //  从输入框中输入内容到库中
    private void saveData() {
        UserData data = new UserData();
        ContentValues contentValues = new ContentValues();
        data.setUserId(etPhoneNumber.getText().toString());          //从id框获取用户注册的id 并保存到数据库中
        data.setUserPassword(etPassWord.getText().toString());                //从密码框获取用户注册的密码 并保存到数据库中
        data.setUserName("用户"+Math.random()*10000);
        data.setUserSex("待修改");
        data.setUserBirthday("待修改");
        data.setUserArea("待修改");
        data.setUserQMD("这家伙很懒，什么都没有留下");                  
        data.setUserPhone(phone);
        contentValues.put(DBConfig.USER_ID, data.getUserId());
        contentValues.put(DBConfig.USER_PASSWORD, data.getUserPassword());
        contentValues.put(DBConfig.USER_NAME,data.getUserName());
        contentValues.put(DBConfig.USER_SEX,data.getUserSex());
        contentValues.put(DBConfig.USER_BIRTHDAY,data.getUserBirthday());
        contentValues.put(DBConfig.USER_AREA,data.getUserArea());
        contentValues.put(DBConfig.USER_PHONE,data.getUserPhone());
        contentValues.put(DBConfig.USER_QMD,data.getUserQMD());
        database.insert(DBConfig.DB_NAME, null, contentValues);
    }

    // 注册的账号验证方法
    private boolean testUserId() {
        String registerUserId = etPhoneNumber.getText().toString();
        String[] str = {registerUserId};
        Cursor cursor = database.query(DBConfig.DB_NAME, null, DBConfig.USER_ID + "=?", str, null, null, null);
        int count = cursor.getCount();
        if (count > 0) {
            Toast.makeText(UserRegisterActivity.this, "你输入的用户名已经存在", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(UserRegisterActivity.this, "你输入的用户名可以使用", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    //另外一种检测账号的 注册方法
    private boolean testUserIdTwo() {
        String registerUserId = etPhoneNumber.getText().toString();
        String[] str = {registerUserId};
        Cursor cursor = database.query(DBConfig.DB_NAME, null, DBConfig.USER_ID + "=?", str, null, null, null);
        int count = cursor.getCount();
        if (count > 0) {
            Toast.makeText(UserRegisterActivity.this, "你输入的用户名已经存在", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        database = helper.getWritableDatabase();
        database = helper.getReadableDatabase();
    }

    @Override
    protected void onStop() {
        super.onStop();
        database.close();
    }
}
