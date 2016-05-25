package com.bhz.android.caiyoubang.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.adapter.MenuBaseAdapter;
import com.bhz.android.caiyoubang.db.MyDbHelper;
import com.bhz.android.caiyoubang.utils.MyOKHttpUtils;
import com.bhz.android.caiyoubang.view.ScrollListView;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * 显示菜单的页面
 * Created by Administrator on 2016/4/18.
 */
public class MenuActivity extends Activity implements MyOKHttpUtils.OKHttpHelper{
    MenuBaseAdapter adapter;
    ScrollListView methodList;
    ImageView imageHead;
    TextView tvMenuName;
    TextView tvMenuAbstract;
    TextView tvMenuStuff;
    TextView tvMenuTips;
    String searchname;
    String MenuName;
    String MenuAbstract;
    String MenuStuff;
    String MenuTips;
    String MenuimageHead;//主图片
    String[] imgList;
    String[] stepList;
    int CDKey;
    MyDbHelper helper;
    MyOKHttpUtils utils;

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
        }else if(CDKey==3){
            searchname=intent.getStringExtra("content");
            utils=MyOKHttpUtils.getinit();
            try {
                String titleurl= URLEncoder.encode(searchname,"UTF-8");
                String url="http://apis.juhe.cn/cook/query.php";
                utils.doSearch(url,titleurl);
                utils.search(this);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
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

    @Override
    public void OnFailure(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnResponse(String message) {

    }

    @Override
    public void getDrawable(Drawable drawable) {

    }

    @Override
    public void getdetail(String menuname, String menuabstract, String menustuff, String menutips, String menuimagehead,
                          String[] imalist,String[] detailist) {
        MenuName=menuname;
        MenuAbstract=menuabstract;
        MenuStuff=menustuff;
        MenuTips=menutips;
        MenuimageHead=menuimagehead;
        imgList=imalist;
        stepList=detailist;
        tvMenuName.setText(MenuName);
        tvMenuAbstract.setText(MenuAbstract);
        tvMenuStuff.setText(MenuStuff);
        tvMenuTips.setText(MenuTips);
        Picasso.with(MenuActivity.this).load(MenuimageHead).into(imageHead);
        adapter = new MenuBaseAdapter(this,imgList,stepList);
        methodList.setAdapter(adapter);
    }
}
