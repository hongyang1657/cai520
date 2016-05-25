package com.bhz.android.caiyoubang;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioGroup;


import com.bhz.android.caiyoubang.activity.CreateMenuActivity;
import com.bhz.android.caiyoubang.activity.GuideActivity;
import com.bhz.android.caiyoubang.data.EventSummary;
import com.bhz.android.caiyoubang.fragment.DiscoveryFragment;
import com.bhz.android.caiyoubang.fragment.HomeFragment;
import com.bhz.android.caiyoubang.fragment.MyFragment;
import com.bhz.android.caiyoubang.fragment.MyLogoutFragment;
import com.bhz.android.caiyoubang.fragment.ShareFragment;
import com.bhz.android.caiyoubang.utils.EventDataPass;

import java.util.ArrayList;
import java.util.List;


//hehedgdhf1111111111111111
public class MainActivity extends Activity implements EventDataPass {//利用借口实现和fragment的数据互通
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    RadioGroup group_mainpage;
    int loginjudge = 0;
    //判断是否第一次进入程序以及是否打开引导页
    SharedPreferences sp;
    MyFragment myfragment;
    ImageView iv_sendMenu;
    boolean judge = false;
    List<EventSummary> list;


    //for the kingfdsafdsa
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //判断是否需要进入引导页
        sp = getSharedPreferences("app", Context.MODE_PRIVATE);  //获取SHaredPreferences服务
        whethertoguidepage();
        loginjudge = sp.getInt("LoginCode", 0);
        //初始化
        inti();
    }

    private void whethertoguidepage() {
        if (sp.getBoolean("firstlogin", true)) {  //通过SharedPreferences判断是否第一次进入app
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
            finish();
        }
    }
/////
    private void setDefaultFragment() {       //设置默认页面
        if (judge == false) {
            transaction = fragmentManager.beginTransaction();
            HomeFragment homefrag = new HomeFragment();
            homefrag.setActivity(this);
            transaction.replace(R.id.mainpage_main_fragment, homefrag);
            transaction.commit();
        } else if (judge == true) {
            transaction = fragmentManager.beginTransaction();
            Fragment discoveryfrag = new DiscoveryFragment();
            transaction.replace(R.id.mainpage_main_fragment, discoveryfrag);
            transaction.commit();
        }
    }

    private void inti() {
        list = new ArrayList<>();
        iv_sendMenu = (ImageView) findViewById(R.id.iv_send_menu);
        iv_sendMenu.setOnClickListener(toSendMenu);
        fragmentManager = getFragmentManager();
        setDefaultFragment();
        group_mainpage = (RadioGroup) findViewById(R.id.mainpage_menu_group);
        group_mainpage.setOnCheckedChangeListener(onchecked);
    }


    RadioGroup.OnCheckedChangeListener onchecked = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                case R.id.mainpage_menu_home:
                    setfragment(1);
                    break;
                case R.id.mainpage_menu_share:
                    setfragment(2);
                    break;
                case R.id.mainpage_menu_discover:
                    setfragment(3);
                    break;
                case R.id.mainpage_menu_my:
                    if (loginjudge == 0) {
                        setfragment(5);  //为了调试直接调出登录页面，需改回
                    } else {
                        setfragment(4);
                    }
                    break;
            }
        }
    };

    //设定显示内容
    private void setfragment(int s) {
        switch (s) {
            case 1:
                HomeFragment homefragment = new HomeFragment();
                homefragment.setActivity(this);
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.mainpage_main_fragment, homefragment);
                transaction.commit();

                break;
            case 2:
                transaction = fragmentManager.beginTransaction();
                Fragment sharefragment = new ShareFragment();
                transaction.replace(R.id.mainpage_main_fragment, sharefragment);
                transaction.commit();
                break;
            case 3:
                transaction = fragmentManager.beginTransaction();
                Fragment discoveryfragment = new DiscoveryFragment();
                transaction.replace(R.id.mainpage_main_fragment, discoveryfragment);
                transaction.commit();
                break;
            case 4:
                transaction = fragmentManager.beginTransaction();
                Fragment myfragment = new MyFragment();
                transaction.replace(R.id.mainpage_main_fragment, myfragment, "myfragment");
                transaction.commit();
                Fragment fragment = fragmentManager.findFragmentByTag("myfragment");


                break;
            case 5:
                transaction = fragmentManager.beginTransaction();
                Fragment nonloginfragment = new MyLogoutFragment();
                transaction.replace(R.id.mainpage_main_fragment, nonloginfragment);
                transaction.commit();
        }
    }

    //
    View.OnClickListener toSendMenu = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, CreateMenuActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void dataPass(List<EventSummary> list, boolean judge) {
        this.list = list;
        this.judge = judge;
    }


}
