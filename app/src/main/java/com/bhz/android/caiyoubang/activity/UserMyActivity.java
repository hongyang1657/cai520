package com.bhz.android.caiyoubang.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhz.android.caiyoubang.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/4/30 0030.
 */
public class UserMyActivity extends Activity {

    TextView tvBack;
    TextView tvMy;
    TextView tvMyUserName;
    Button btnSet;
    ImageView imgUserQQPhoto;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_my_layout);
        initData();
        initUserData();
        tvMy.setOnClickListener(click);
        btnSet.setOnClickListener(click);
        tvBack.setOnClickListener(click);
    }

    private void initData() {
        tvBack = (TextView) findViewById(R.id.tv_user_my_back);
        tvMy = (TextView) findViewById(R.id.tv_my_informe);
        tvMyUserName= (TextView) findViewById(R.id.tx_user_my_name);
        btnSet = (Button) findViewById(R.id.btn_my_set);
        imgUserQQPhoto = (ImageView) findViewById(R.id.img_user_my_photo);
    }

    private void initUserData(){
        sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE);
        String UserName= sharedPreferences.getString("QQNAME",null);
        String imgQQPhotUrl = sharedPreferences.getString("QQIcon",null);
        tvMyUserName.setText(UserName);
        Picasso.with(this).load(imgQQPhotUrl).into(imgUserQQPhoto);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_my_informe:
                    Intent intentData = new Intent(UserMyActivity.this, UserEditDataActivity.class);
                    startActivity(intentData);
                    finish();
                    break;
                case R.id.btn_my_set:
                    Intent intentSet = new Intent(UserMyActivity.this, UserSetActivity.class);
                    startActivity(intentSet);
                    finish();
                    break;
                case R.id.tv_user_my_back:
                    Intent intentBack = new Intent(UserMyActivity.this,UserLoginPageActivity.class);
                    startActivity(intentBack);
                    finish();
                    break;
            }
        }
    };
}
