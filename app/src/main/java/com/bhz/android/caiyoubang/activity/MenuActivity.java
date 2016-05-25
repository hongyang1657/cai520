package com.bhz.android.caiyoubang.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.adapter.MenuBaseAdapter;
import com.bhz.android.caiyoubang.db.MyDbHelper;
import com.bhz.android.caiyoubang.view.ScrollListView;
import com.squareup.picasso.Picasso;


/**
 * 显示菜单的页面
 * Created by Administrator on 2016/4/18.
 */
public class MenuActivity extends Activity{
    MenuBaseAdapter adapter;
    ScrollListView methodList;
    ImageView imageHead;
    TextView tvMenuName;
    TextView tvMenuAbstract;
    TextView tvMenuStuff;
    TextView tvMenuTips;

    String MenuName;
    String MenuAbstract;
    String MenuStuff;
    String MenuTips;
    String MenuimageHead;//主图片
    String[] imgList;
    String[] stepList;
    int CDKey;
    MyDbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        getintentInfo();
        init();
        setInfo();
    }

    //获取点击上一个Activity的不同item解析得到的数据
    private void getintentInfo(){
        Intent intent = getIntent();
        CDKey = intent.getIntExtra("CDKey",0);
        if (CDKey==1) {
            MenuName = intent.getStringExtra("MenuName");
            MenuAbstract = intent.getStringExtra("MenuAbstract");
            MenuStuff = intent.getStringExtra("MenuStuff");
            MenuTips = intent.getStringExtra("MenuTips");
            MenuimageHead = intent.getStringExtra("MenuimageHead");
            imgList = intent.getStringArrayExtra("imgList");
            stepList = intent.getStringArrayExtra("stepList");
        }else if (CDKey==2){
            helper = new MyDbHelper(this);
            SQLiteDatabase database = helper.getReadableDatabase();
            //database.query("menu",null,"menu_name",);
        }
    }

    private void init(){
        //初始化控件
        imageHead = (ImageView) findViewById(R.id.image_head);
        tvMenuName = (TextView) findViewById(R.id.menu_name);
        tvMenuAbstract = (TextView) findViewById(R.id.menu_abstract);
        tvMenuStuff = (TextView) findViewById(R.id.tv_menu_stuff);
        tvMenuTips = (TextView) findViewById(R.id.menu_tips_content);
        methodList = (ScrollListView) findViewById(R.id.list_menu_method);

        adapter = new MenuBaseAdapter(this,imgList,stepList);
        methodList.setAdapter(adapter);
    }

    //给各个控件设置数据
    private void setInfo(){
        tvMenuName.setText(MenuName);
        tvMenuAbstract.setText(MenuAbstract);
        tvMenuStuff.setText(MenuStuff);
        tvMenuTips.setText(MenuTips);
        Picasso.with(MenuActivity.this).load(MenuimageHead).into(imageHead);
    }
}
