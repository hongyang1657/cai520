package com.bhz.android.caiyoubang.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bhz.android.caiyoubang.MainActivity;
import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.db.DBConfig;
import com.bhz.android.caiyoubang.db.DBOperator;
import com.bhz.android.caiyoubang.db.MyDbHelper;

import com.bhz.android.caiyoubang.domian.UserData;
import com.bhz.android.caiyoubang.util.WeiBoAccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;


/**
 * Created by Administrator on 2016/4/18 0018.
 */
// 登录页面缺少的内容  1. 三方登录的内容
//                     2. 缺少一个忘记密码的页面
public class UserLoginPageActivity extends Activity {

    MyDbHelper helper;
    SQLiteDatabase database;    // 库对象
    DBOperator operator;     // 库操作
    Button btnLogin;       // 登录页面的用户登录按钮

    EditText etUserName;       // 登录页面的用户名输入框
    EditText etUserPassword;        // 登录页面的用户密码输入框

    TextView tvBack;             // 登录页面的返回按钮
    TextView tvRegister;        // 登录页面的注册按钮
    TextView tvQQ;               // 登录页面的QQ登录按钮
    TextView tvWeiXin;          // 登录页面的微信登录按钮
    TextView tvWeiBo;           // 登录页面的微博登录按钮
    TextView tvForgetPassword;// 登录页面的忘记密码按钮

    private Tencent mTencent;  // 创建的扣扣三方登陆的对象
    public static String nicknameString;                // 获取的用户的昵称
    public static String QQPhotoString;                 //  获取的用户的头像
    public static String QQSex;                         // 获取的用户的性别
    private static String TAG = "QQLife";

   /* private AuthInfo mAuthInfo;      //微博授权类对象
    private String nickname ;      //获取微博账户昵称的对象
    private String WeiBoPhotoString;  //获取的微博账户的头像
    private Oauth2AccessToken mAccessToken; // 存储从微博返回的请求吗的对象
    private SsoHandler mSsoHandler;*/

    String userId;                                       // 通过账号密码登陆保存的账号密码信息
    SharedPreferences sharedPreferences;               //  用来保存用户登陆的账号密码信息


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_loginpage_layout);
        operator = new DBOperator(this);
        initData();
       // initweibologin();
        sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE);       // sharedPerences 放在oncreate中初始化
        tvBack.setOnClickListener(click);
        tvRegister.setOnClickListener(click);
        btnLogin.setOnClickListener(click);
        tvQQ.setOnClickListener(click);
        tvWeiBo.setOnClickListener(click);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener());
        // 自动重新登录这个微博登录的账号信息
       /* if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }*/
    }

    // 初始化所有控件;
    private void initData() {
        helper = new MyDbHelper(this);
        tvBack = (TextView) findViewById(R.id.tv_user_login_back);                             // 登录界面的返回按钮
        tvRegister = (TextView) findViewById(R.id.tv_user_login_login);                    // 登录界面的注册按钮
        tvForgetPassword = (TextView) findViewById(R.id.btn_user_login);     // 登录界面的忘记密码按钮
        tvQQ = (TextView) findViewById(R.id.tv_user_QQ_login);
        tvWeiXin = (TextView) findViewById(R.id.tv_user_weixin_login);
        tvWeiBo = (TextView) findViewById(R.id.tv_user_weibo_login);
        btnLogin = (Button) findViewById(R.id.btn_user_login);                                  // 登录界面的登录按钮
        etUserName = (EditText) findViewById(R.id.et_user_name);                                 // 用户id输入框
        etUserPassword = (EditText) findViewById(R.id.et_user_key);                                  // 用户密码输入框
        //mAccessToken = WeiBoAccessTokenKeeper.readAccessToken(this);
    }

    /**
     * 进行微博授权初始化操作
     */
    /*private void initweibologin() {
        // TODO Auto-generated method stub
        // 初始化授权类对象，将应用的信息保存
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(UserLoginPageActivity.this, mAuthInfo);
    }*/


    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_user_login_back:                   // 用户的返回事件
                    Intent intent = new Intent(UserLoginPageActivity.this, MainActivity.class);   // ！！！！！！！！！！！！！！！！！！！！返回的是主页面的my界面
                    startActivity(intent);
                    break;
                case R.id.tv_user_login_login:                 // 用户的注册事件
                    Intent intentTwo = new Intent(UserLoginPageActivity.this, UserPhoneRegisterActivity.class);
                    SharedPreferences.Editor editor1 = sharedPreferences.edit();
                    editor1.putInt("LoseKey",1);                             // 账号密码登录的key；
                    editor1.commit();
                    startActivity(intentTwo);
                    break;
                case R.id.btn_user_login:                       // 用户的登录事件
                    compare();
                    break;
                case R.id.tv_user_login_forget:
                    Intent intentFour = new Intent(UserLoginPageActivity.this, UserPhoneRegisterActivity.class);  //！！！！！！！！！！！！！！！！！缺少一个忘记密码的页面
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("LoseKey",2);                             // 账号密码登录的key；
                    editor.commit();
                    startActivity(intentFour);
                    break;
                case R.id.tv_user_QQ_login:
                    loginQQ();
                    break;
                case R.id.tv_user_weibo_login:
                    // SSO 授权, ALL IN ONE
                    // 如果手机安装了微博客户端则使用客户端授权,没有则进行网页授权
                  //  mSsoHandler.authorize(new AuthListener());
                    break;
            }
        }
    };

    // 比较库中的内容是否和输入的一样。
    private void compare() {
        userId = etUserName.getText().toString();
        String userPassword = etUserPassword.getText().toString();
        // 将用户登录使用的账号信息存入到sharedPerence中去。
        if (userId == null || "".equals(userId)) {
            Toast.makeText(UserLoginPageActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();

        } else if (userPassword == null || "".equals(userPassword)) {
            Toast.makeText(UserLoginPageActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();

        } else {
            getDate();
        }
    }

    // 对比数据库中的资料
    private void getDate() {
        String userId = etUserName.getText().toString();                     //记录输入的账号
        String userPassword = etUserPassword.getText().toString();          //记录输入的密码
        String[] str = {userId, userPassword};
        Cursor cursor = database.query(DBConfig.DB_NAME, null, DBConfig.USER_ID + "=?and " + DBConfig.USER_PASSWORD + "=?", str, null, null, null);
        int count = cursor.getCount();
        if (count > 0) {
            Toast.makeText(UserLoginPageActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("LoginType", 1);                             // 账号密码登录的key；
            editor.putString("USERID", userId);
            editor.putInt("LoginCode",1);
            editor.commit();
            Toast.makeText(UserLoginPageActivity.this, "" + sharedPreferences.getString("USERID", null), Toast.LENGTH_SHORT).show();
            Intent intentThree = new Intent(UserLoginPageActivity.this, MainActivity.class);  // ！！！！！！！！！！！！！！！！登陆完毕后跳转我的页面查看自己的信息.
            startActivity(intentThree);
        } else {
            Toast.makeText(UserLoginPageActivity.this, "你输入的账号或者密码有误", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    // 采用扣扣第三发登录的方法;
    public void loginQQ() {
        mTencent = Tencent.createInstance("1105397478", this.getApplicationContext());
        mTencent.login(UserLoginPageActivity.this, "all", new BaseUiListener());
    }

    // 自动以的监听器实现UIListener接口
    public class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
            try {
                String userId = ((JSONObject) response).getString("openid");                       //  获取的第一次的返回值
                String accessToken = ((JSONObject) response).getString("access_token");           //  获取用户信息的第二次验证码
                String expiresIn = ((JSONObject) response).getString("expires_in");

                mTencent.setOpenId(userId);
                mTencent.setAccessToken(accessToken, expiresIn);
                getUserInfoInThread();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
        }

        @Override
        public void onCancel() {
        }
    }

    public void getUserInfoInThread() {                                                //  登陆成功之后点登录按钮，获取到用户信息

        QQToken qqToken = mTencent.getQQToken();                                       //
        UserInfo info = new UserInfo(getApplicationContext(), qqToken);
        info.getUserInfo(new IUiListener() {

            public void onComplete(final Object response) {
                // TODO Auto-generated method stub

                try {
                    nicknameString = ((JSONObject) response).getString("nickname");
                    QQPhotoString = ((JSONObject) response).getString("figureurl_qq_2");
                    QQSex = ((JSONObject) response).getString("gender");
                    saveData(nicknameString, QQSex);
                    User(nicknameString, QQPhotoString);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            public void onCancel() {
                Log.i(TAG, "--------------onCancel");
                // TODO Auto-generated method stub
            }

            public void onError(UiError arg0) {
                // TODO Auto-generated method stub
                Log.i(TAG, "---------------onError" + ":" + arg0);
            }
        });
    }


    // QQ真正实现跳转的方法;
    private void User(String name, String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("QQNAME", name);
        Log.i("TAG", "User: " + name);
        editor.putString("QQIcon", url);
        editor.putInt("LoginType", 2);                              //  登录模式二 扣扣登录
        editor.putInt("LoginCode",1);
        editor.commit();
        Intent intent = new Intent(UserLoginPageActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // QQd登录保存QQ昵称作为唯一辨识
    private void saveData(String qqName, String qqSex) {
        UserData data = new UserData();
        ContentValues contentValues = new ContentValues();
        data.setUserName(qqName);
        data.setUserSex(qqSex);
        data.setUserBirthday("待修改");
        data.setUserArea("待修改");
        data.setUserQMD("这家伙很懒，什么都没有留下");
        contentValues.put(DBConfig.USER_NAME, data.getUserName());
        contentValues.put(DBConfig.USER_SEX, data.getUserSex());
        contentValues.put(DBConfig.USER_BIRTHDAY, data.getUserBirthday());
        contentValues.put(DBConfig.USER_AREA, data.getUserArea());
        contentValues.put(DBConfig.USER_QMD, data.getUserQMD());
        database.insert(DBConfig.DB_NAME, null, contentValues);
    }

    // 微博登录所需要的 app 和url 还有权限
   /* public class Constants {
        public static final String APP_KEY = "3448493831";           // 应用的APP_KEY
        public static final String REDIRECT_URL = "http://www.sina.com";// 应用的回调页
        public static final String SCOPE =                               // 应用申请的高级权限
                "email,direct_messages_read,direct_messages_write,"
                        + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                        + "follow_app_official_microblog," + "invitation_write";
    }*/

    // 微博实现登录的接口
    /*public class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                nickname = "用户名:" + String.valueOf(values.get("com.sina.weibo.intent.extra.NICK_NAME"));
                WeiBoPhotoString= "用户名:" + String.valueOf(values.get("com.sina.weibo.intent.extra.USER_ICON"));
                UserWeiBo(nickname,WeiBoPhotoString);
                saveDataWeiBo(nickname);
                WeiBoAccessTokenKeeper.writeAccessToken(UserLoginPageActivity.this, mAccessToken);
                Toast.makeText(UserLoginPageActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            }
        }

        // 授权失败
        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(UserLoginPageActivity.this, "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // 取消授权
        @Override
        public void onCancel() {
            Toast.makeText(UserLoginPageActivity.this, "取消授权", Toast.LENGTH_LONG).show();
        }
    }*/

    // WeiBo真正实现跳转的方法;
    /*private void UserWeiBo(String name, String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("WeiBoName", name);
        editor.putString("WeiBoIcon", url);
        editor.putInt("LoginType", 3);                              //  登录模式二 扣扣登录
        editor.commit();
        Intent intent = new Intent(UserLoginPageActivity.this, UserMyActivity.class);
        startActivity(intent);
        finish();
    }*/

    // WeiBo登录保存QQ昵称作为唯一辨识
    /*private void saveDataWeiBo(String WeiBoName) {
        UserData data = new UserData();
        ContentValues contentValues = new ContentValues();
        data.setUserName(WeiBoName);
        data.setUserSex("待修改");
        data.setUserBirthday("待修改");
        data.setUserArea("待修改");
        data.setUserQMD("这家伙很懒，什么都没有留下");
        contentValues.put(DBConfig.USER_NAME, data.getUserName());
        contentValues.put(DBConfig.USER_SEX, data.getUserSex());
        contentValues.put(DBConfig.USER_BIRTHDAY, data.getUserBirthday());
        contentValues.put(DBConfig.USER_AREA, data.getUserArea());
        contentValues.put(DBConfig.USER_QMD, data.getUserQMD());
        database.insert(DBConfig.DB_NAME, null, contentValues);
    }*/

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
}
