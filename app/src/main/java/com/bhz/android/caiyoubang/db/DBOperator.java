package com.bhz.android.caiyoubang.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.bhz.android.caiyoubang.activity.UserEditDataActivity;
import com.bhz.android.caiyoubang.domian.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/3 0003.
 */
public class DBOperator {
    MyDbHelper helper;
    SQLiteDatabase db;
    Context context;

    public DBOperator(Context context) {
        this.context=context;
        helper = new MyDbHelper(context);
        db = helper.getReadableDatabase();
    }

    // 查询库中内容
    public void queryALLDATA(String strUserId,EditText etName, EditText etSex, EditText etArea, TextView etBirthday) {
        Cursor cursor = db.query(DBConfig.DB_NAME, null,DBConfig.USER_ID + "=?", new String[] {strUserId}, null, null,null);
        while (cursor.moveToNext()) {
            UserData data = buildUserdataFromCursor(cursor);
            etName.setText(data.getUserName());            // 资料编辑页面的昵称输入框
            etSex.setText(data.getUserSex());             // 资料编辑页面的性别输入框
            etArea.setText(data.getUserArea());            // 资料编辑页面的地区输入框
            etBirthday.setText(data.getUserBirthday());    // 资料编辑页面的生日输入框
        }
        cursor.close();
    }

    public void queryALLDATAFromQQ(String strUserName,EditText etName, EditText etSex, EditText etArea, TextView etBirthday,EditText etQmd) {
        Cursor cursor = db.query(DBConfig.DB_NAME, null,DBConfig.USER_NAME + "=?", new String[] {strUserName}, null, null,null);
        while (cursor.moveToNext()) {
            UserData data = buildUserdataFromCursor(cursor);
            etName.setText(data.getUserName());            // 资料编辑页面的昵称输入框
            etSex.setText(data.getUserSex());             // 资料编辑页面的性别输入框
            etArea.setText(data.getUserArea());            // 资料编辑页面的地区输入框
            etBirthday.setText(data.getUserBirthday());    // 资料编辑页面的生日输入框
            etQmd.setText(data.getUserQMD());             //  资料编辑页面的心情输入框
        }
        cursor.close();
    }

    public void queryepsodeFromQQ(String strUserName,EditText etepsode) {
        Cursor cursor = db.query(DBConfig.DB_NAME, null,DBConfig.USER_NAME + "=?", new String[] {strUserName}, null, null,null);
        while (cursor.moveToNext()) {
            UserData data = buildUserdataFromCursor(cursor);
            etepsode.setText(data.getUserQMD());            // 资料编辑页面的昵称输入框
        }
        cursor.close();
    }

    private UserData buildUserdataFromCursor(Cursor cursor) {
        UserData data = new UserData();
        data.setUserId(cursor.getString(cursor.getColumnIndex(DBConfig.USER_ID)));
        data.setUserPassword(cursor.getString(cursor.getColumnIndex(DBConfig.USER_PASSWORD)));
        data.setUserName(cursor.getString(cursor.getColumnIndex(DBConfig.USER_NAME)));
        data.setUserQMD(cursor.getString(cursor.getColumnIndex(DBConfig.USER_QMD)));
        data.setUserSex(cursor.getString(cursor.getColumnIndex(DBConfig.USER_SEX)));
        data.setUserBirthday(cursor.getString(cursor.getColumnIndex(DBConfig.USER_BIRTHDAY)));
        data.setUserArea(cursor.getString(cursor.getColumnIndex(DBConfig.USER_AREA)));
        return data;
    }

    // 修改库中内容
    public void UpdateNote(ContentValues values, String strUserId) {
        if (values == null) {
            Log.i("result", "---------------------------");
            Toast.makeText(context, "内容为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
             int count = db.update(DBConfig.DB_NAME, values, DBConfig.USER_ID + "=?",new String[] {strUserId});
            if (count > 0) {
                Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // 通过扣扣昵称修改哭的内容
    public void UpdateNoteFromQQ(ContentValues values, String strUserName) {
        if (values == null) {
            Log.i("result", "---------------------------");
            Toast.makeText(context, "内容为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            int count = db.update(DBConfig.DB_NAME, values, DBConfig.USER_NAME + "=?",new String[] {strUserName});
            if (count > 0) {
                Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private ContentValues buildUserdataContentValue(UserData data) {
        ContentValues values = new ContentValues();
        values.put(DBConfig.USER_ID, data.getUserId());
        values.put(DBConfig.USER_PASSWORD, data.getUserPassword());
        values.put(DBConfig.USER_NAME, data.getUserName());
        values.put(DBConfig.USER_QMD, data.getUserQMD());
        return values;
    }

    // 删除库中内容
    public void deleteData(int id) {
        db.delete(DBConfig.DB_NAME, DBConfig.USER_ID + "=" + id, null);
    }
}
