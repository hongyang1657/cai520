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
    private static final String SQL_CREAT_MENU = "create table if not exists menu(id integer primary key autoincrement,content text,image blob)";
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
