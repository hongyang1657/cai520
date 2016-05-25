package com.bhz.android.caiyoubang.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/4/18.
 */
public class MyDbHelper extends SQLiteOpenHelper {

    Context mContext;
    public static final int MENU_DB_VERSION = 1;
    private static final String SQL_CREAT_MENU = "create table if not exists "
            +"menu(id integer primary key autoincrement,"
            + DBConfig.IMG_ADDRESS+" text,"
            + DBConfig.MENU_NAME+" varchar(20),"
            + DBConfig.BRIEF+" varchar(150),"
            + DBConfig.MATERIAL+" text,"
            + DBConfig.TIPS+" text,"
            + DBConfig.STEP_IMG1+" text,"
            + DBConfig.STEP_TEXT1+" text,"
            + DBConfig.STEP_IMG2+" text,"
            + DBConfig.STEP_TEXT2+" text,"
            + DBConfig.STEP_IMG3+" text,"
            + DBConfig.STEP_TEXT3+" text,"
            + DBConfig.STEP_IMG4+" text,"
            + DBConfig.STEP_TEXT4+" text,"
            + DBConfig.STEP_IMG5+" text,"
            + DBConfig.STEP_TEXT5+" text,"
            + DBConfig.STEP_IMG6+" text,"
            + DBConfig.STEP_TEXT6+" text,"
            + DBConfig.STEP_IMG7+" text,"
            + DBConfig.STEP_TEXT7+" text,"
            + DBConfig.STEP_IMG8+" text,"
            + DBConfig.STEP_TEXT8+" text,"
            + DBConfig.STEP_IMG9+" text,"
            + DBConfig.STEP_TEXT9+" text,"
            + DBConfig.STEP_IMG10+" text,"
            + DBConfig.STEP_TEXT10+" text)";


    private static final String CREATE_TB_NOTE = "create table if not exists "
            + DBConfig.DB_NAME + "(_id integer primary key autoincrement,"
            + DBConfig.USER_PHONE + " integer,"
            + DBConfig.USER_NAME + " varchar(20),"
            + DBConfig.USER_ID + " varchar(20),"
            + DBConfig.USER_PASSWORD + " varchar(50),"
            + DBConfig.USER_QMD + " varchar(150),"
            + DBConfig.USER_SEX + " varchar(20),"
            + DBConfig.USER_AREA + " varchar(50),"
            + DBConfig.USER_BIRTHDAY + " text(50))";
////
    public MyDbHelper(Context context) {
        super(context, DBConfig.DB_NAME, null, MENU_DB_VERSION);
        this.mContext = context;
    }

    public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, MENU_DB_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAT_MENU);
        db.execSQL(CREATE_TB_NOTE);
        Toast.makeText(mContext, "创建成功", Toast.LENGTH_SHORT).show();   // 建表解释，后期和删除
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
