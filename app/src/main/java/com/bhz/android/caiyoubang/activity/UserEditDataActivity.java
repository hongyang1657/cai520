package com.bhz.android.caiyoubang.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhz.android.caiyoubang.MainActivity;
import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.db.DBConfig;
import com.bhz.android.caiyoubang.db.DBOperator;
import com.bhz.android.caiyoubang.db.MyDbHelper;
import com.bhz.android.caiyoubang.domian.UserData;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/4/26 0026.
 */

//  本页面缺少的内容   1.日期设置器
//                     2.往数据库传输内容
public class UserEditDataActivity extends Activity {

    MyDbHelper helper;          //用来操作数据库的类
    SQLiteDatabase database;   // 操作库的对象
    DBOperator operator;       // 数据库操作类

    TextView tvBack;            // 资料编辑页面的返回按钮
    TextView tvSave;            // 资料编辑页面的保存按钮
    EditText etName;            // 资料编辑页面的昵称输入框
    EditText etSex;             // 资料编辑页面的性别输入框
    EditText etArea;            // 资料编辑页面的地区输入框
    TextView tvBirthday;       // 资料编辑页面的用户生日
    EditText etQmd;             // 资料编辑页面的用户签名
    ImageView imgUserPhoto;   //  编辑资料的用户头像

    String userId;

    SharedPreferences sharedPreferences;
    int loginType;
    String userName;

    Calendar cal;
    int year;
    int month;
    int day;

    /*
    *  flag = 0;修改状态 ； flag = 1 保存状态 ；
    * */

    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit_data_layout);
        initData();
        sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE);     // sharedPerences 放在oncreate中初始化
        loginType = sharedPreferences.getInt("LoginType", 0);
        userName = sharedPreferences.getString("QQNAME", null);
        userId = sharedPreferences.getString("USERID", null);
        tvBack.setOnClickListener(click);
        tvSave.setOnClickListener(click);
        tvBirthday.setOnClickListener(click);
    }

    private void initData() {
        helper = new MyDbHelper(this);
        operator = new DBOperator(this);
        tvBack = (TextView) findViewById(R.id.tv_user_edit_data_back);
        tvSave = (TextView) findViewById(R.id.tv_user_edit_data_save);
        etName = (EditText) findViewById(R.id.et_user_edit_data_name);
        etSex = (EditText) findViewById(R.id.et_user_edit_data_sex);
        etArea = (EditText) findViewById(R.id.et_user_edit_data_area);
        etQmd = (EditText) findViewById(R.id.et_user_edit_data_QMD);
        tvBirthday = (TextView) findViewById(R.id.et_user_edit_data_birthday);
        imgUserPhoto = (ImageView) findViewById(R.id.img_user_edit_data_photo);
        tvSave.setText("修改");
        etName.setEnabled(false);
        etSex.setEnabled(false);
        etArea.setEnabled(false);
        etQmd.setEnabled(false);
        cal = Calendar.getInstance();
        dateUtil();
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_user_edit_data_back:                                               // 用户点击了返回的事件
                    Intent intentBack = new Intent(UserEditDataActivity.this, MainActivity.class);
                    startActivity(intentBack);
                    break;
                case R.id.tv_user_edit_data_save:                                               // 用户点击了保存的事件
                    switch (flag) {
                        case 0:
                            tvSave.setText("保存");
                            etName.setEnabled(true);
                            etSex.setEnabled(true);
                            etArea.setEnabled(true);
                            etQmd.setEnabled(true);
                            flag = 1;
                            break;
                        case 1:
                            saveUserData();
                            Intent intentSave = new Intent(UserEditDataActivity.this, MainActivity.class);
                            startActivity(intentSave);
                            finish();
                            break;
                        case 2:
                            Toast.makeText(UserEditDataActivity.this, "请登录您的账号", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    break;
                case R.id.et_user_edit_data_birthday:
                    new DatePickerDialog(UserEditDataActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {
                            tvBirthday.setText(year1+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                            year=year1;
                            month=monthOfYear+1;
                            day=dayOfMonth;
                        }
                    }, year, cal.get(Calendar.MONTH), day).show();
                    break;
            }
        }
    };




    private void saveUserData() {
        String userNewName = etName.getText().toString();
        String userId = sharedPreferences.getString("USERID", null);
        UserData data = new UserData();
        ContentValues values = new ContentValues();
        data.setUserName(etName.getText().toString());          //获取用户输入的昵称
        data.setUserSex(etSex.getText().toString());            //获取用户输入的性别
        data.setUserArea(etArea.getText().toString());          //获取用户输入的地域
        data.setUserBirthday(year+"-"+month+"-"+day);  //获取用户的生日信息
        data.setUserQMD(etQmd.getText().toString());            // 获取用户的签名
        values.put(DBConfig.USER_NAME, data.getUserName());               //用户的昵称填入数据
        values.put(DBConfig.USER_SEX, data.getUserSex());                 //用户的性别数据
        values.put(DBConfig.USER_AREA, data.getUserArea());               //用户的地域数据
        values.put(DBConfig.USER_BIRTHDAY, data.getUserBirthday());      //用户的生日数据
        values.put(DBConfig.USER_QMD, data.getUserQMD());
        switch (loginType) {
            case 1:
                operator.UpdateNote(values, userId);
                break;
            case 2:
                operator.UpdateNoteFromQQ(values, userName);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("QQNAME", userNewName);
                editor.commit();
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        database = helper.getWritableDatabase();
        database = helper.getReadableDatabase();
        switch (loginType) {
            case 1:
                operator.queryALLDATA(userId, etName, etSex, etArea, tvBirthday);
                break;
            case 2:
                String imgQQPhotUrl = sharedPreferences.getString("QQIcon", null);
                Picasso.with(this).load(imgQQPhotUrl).into(imgUserPhoto);
                operator.queryALLDATAFromQQ(userName, etName, etSex, etArea, tvBirthday, etQmd);
                break;
            case 3:
                operator.queryALLDATA(userId, etName, etSex, etArea, tvBirthday);
                flag = 2;
        }
    }

    private void dateUtil() {
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void onStop() {
        super.onStop();
        database.close();
    }
}
