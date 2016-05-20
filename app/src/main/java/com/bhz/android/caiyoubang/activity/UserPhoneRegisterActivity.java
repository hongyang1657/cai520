package com.bhz.android.caiyoubang.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.db.DBConfig;
import com.bhz.android.caiyoubang.db.MyDbHelper;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.mob.tools.utils.R.getStringRes;

/**
 * Created by Administrator on 2016/4/30 0030.
 */
// 缺少的内容  1.获取手机验证码服务 发送
//             2.缺少一个方法，验证输入的验证码和该手机号码的验证码是否一致;
//             3.缺少一个食疗服务条款的页面跳转
public class UserPhoneRegisterActivity extends Activity {

    TextView tvBack;           //页面的返回上一步按钮
    TextView tvNext;           //页面的下一步按钮
    TextView tvGetCode;       //页面的获取验证码按钮
    TextView tvServe;         //页面的查看服务的按钮
    TextView tvTimeHint;     //页面的时间提示框
    EditText etPhone;         //页面的输入手机号码框
    EditText etAuthCode;     //页面的输入验证码的框

    int time = 60;           //提示信息的秒数;
    String phone;            //保存手机号的字段
    String code;                 //保存手机验证码的的字段
    private boolean flag = true;

    SQLiteDatabase database ;  // 创建操作数据库的对象
    MyDbHelper helper;

    SharedPreferences sharedPreferences ;
    int loseKey;         //判断是否忘记密码;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_phone_register_layout);
        initData();
        EventHandler eh = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
        tvBack.setOnClickListener(click);
        tvNext.setOnClickListener(click);
        tvGetCode.setOnClickListener(click);
    }

    private void initData() {
        sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE);
        loseKey = sharedPreferences.getInt("LoseKey",0);
        SMSSDK.initSDK(this, "1261341259852", "32f48f874bbda490d94ec29699404379");
        helper = new MyDbHelper(this);
        tvNext = (TextView) findViewById(R.id.tv_user_phone_register_next);
        tvGetCode = (TextView) findViewById(R.id.tv_user_phone_register_get_authcode);
        tvServe = (TextView) findViewById(R.id.tv_user_phone_register_serve);
        tvBack = (TextView) findViewById(R.id.tv_user_phone_register_back);
        etPhone = (EditText) findViewById(R.id.et_user_phone_register_phone_number);
        etAuthCode = (EditText) findViewById(R.id.et_user_phone_register_verification_code);
        tvTimeHint = (TextView) findViewById(R.id.tv_user_phone_register_time_hint);

    }

    //手机注册页面的两个按钮  一个用来获取验证码，一个跳转到登录页面；
    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_user_phone_register_back:                       // 返回按钮
                    Intent intentBack = new Intent(UserPhoneRegisterActivity.this, UserLoginPageActivity.class);  //  !!!!!!!!!!!!!!!!!!!!!该页面跳转需要改成跳到主页面而不是这个页面
                    startActivity(intentBack);
                    break;
                case R.id.tv_user_phone_register_next:                       // 注册的下一步按钮
                    if(!TextUtils.isEmpty(etAuthCode.getText().toString().trim())){
                        if(etAuthCode.getText().toString().trim().length()==4){
                            code = etAuthCode.getText().toString().trim();
                            SMSSDK.submitVerificationCode("86", phone, code);
                            flag = false;
                        }else{
                            Toast.makeText(UserPhoneRegisterActivity.this, "请输入完整验证码", Toast.LENGTH_LONG).show();
                            etAuthCode.requestFocus();
                        }
                    }else{
                        Toast.makeText(UserPhoneRegisterActivity.this, "请输入验证码", Toast.LENGTH_LONG).show();
                        etAuthCode.requestFocus();
                    }
                    break;
                case R.id.tv_user_phone_register_get_authcode:             // 获取验证码的按钮
                    if(!TextUtils.isEmpty(etPhone.getText().toString().trim())){
                        if(etPhone.getText().toString().trim().length()==11){
                            phone = etPhone.getText().toString().trim();
                            switch (loseKey){
                                case 1:
                                    if(testPhone()){
                                        testPhone();
                                        break;
                                    }
                                case 2:
                                    if(testPhoneTwo()){
                                        testPhone();
                                        break;
                                    }
                            }
                            SMSSDK.getVerificationCode("86",phone);
                            etAuthCode.requestFocus();
                            tvGetCode.setVisibility(View.GONE);
                        }else{
                            Toast.makeText(UserPhoneRegisterActivity.this, "请输入完整电话号码", Toast.LENGTH_LONG).show();
                            etPhone.requestFocus();
                        }
                    }else{
                        Toast.makeText(UserPhoneRegisterActivity.this, "请输入您的电话号码", Toast.LENGTH_LONG).show();
                        etPhone.requestFocus();
                    }
                    break;

                case R.id.tv_user_phone_register_serve:                     // 食疗的服务检查按钮
                    break;
            }
        }
    };

    //验证码送成功后提示文字
    private void reminderText() {
        tvTimeHint.setVisibility(View.VISIBLE);
        handlerText.sendEmptyMessageDelayed(1, 1000);
    }

    Handler handlerText =new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what==1){
                if(time>0){
                    tvTimeHint.setText("验证码已发送"+time+"秒");
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                }else{
                    tvTimeHint.setText("提示信息");
                    time = 60;
                    tvTimeHint.setVisibility(View.GONE);
                    tvGetCode.setVisibility(View.VISIBLE);
                }
            }else{
                etAuthCode.setText("");
                tvTimeHint.setText("提示信息");
                time = 60;
                tvTimeHint.setVisibility(View.GONE);
                etAuthCode.setVisibility(View.VISIBLE);
            }
        };
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功,验证通过
                    Toast.makeText(getApplicationContext(), "验证码校验成功", Toast.LENGTH_SHORT).show();
                    Intent intentNext = new Intent(UserPhoneRegisterActivity.this, UserRegisterActivity.class);               //跳转条件没有完成
                    startActivity(intentNext);
                    finish();
                    handlerText.sendEmptyMessage(2);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){//服务器验证码发送成功
                    reminderText();
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//返回支持发送验证码的国家列表
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(flag){
                    tvGetCode.setVisibility(View.VISIBLE);
                    Toast.makeText(UserPhoneRegisterActivity.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                    etPhone.requestFocus();
                }else{
                    ((Throwable) data).printStackTrace();
                    int resId = getStringRes(UserPhoneRegisterActivity.this, "smssdk_network_error");
                    Toast.makeText(UserPhoneRegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    etAuthCode.selectAll();
                    if (resId > 0) {
                        Toast.makeText(UserPhoneRegisterActivity.this, resId, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
    };

    private boolean testPhone(){
        String myPhone = etPhone.getText().toString();
        Cursor cursor = database.query(DBConfig.DB_NAME,null, DBConfig.USER_PHONE + "=?",new String[] {myPhone},null,null,null);
        int count = cursor.getCount();
        if (count > 0){
            Toast.makeText(UserPhoneRegisterActivity.this, "你的手机号已经注册", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean testPhoneTwo(){
        String myPhone = etPhone.getText().toString();
        Cursor cursor = database.query(DBConfig.DB_NAME,null, DBConfig.USER_PHONE + "=?",new String[] {myPhone},null,null,null);
        int count = cursor.getCount();
        if (count < 0){
            Toast.makeText(UserPhoneRegisterActivity.this, "你的账号不存在", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        database = helper.getReadableDatabase();
        database = helper.getWritableDatabase();
    }

    @Override
    protected void onStop() {
        super.onStop();
        database.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
