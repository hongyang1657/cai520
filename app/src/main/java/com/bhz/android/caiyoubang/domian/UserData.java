package com.bhz.android.caiyoubang.domian;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/3 0003.
 */
public class UserData implements Parcelable {

    int id;                // 表自增的id
    String UserId;         // 用户ID
    String UserPassword;  // 用户密码
    String UserName;       // 用户昵称
    String UserQMD;        // 用户签名
    String UserSex;        // 用户性别
    String UserBirthday;  // 用户生日
    String UserArea;       // 用户地区
    String UserPhone;      // 用户电话

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserQMD() {
        return UserQMD;
    }

    public void setUserQMD(String userQMD) {
        UserQMD = userQMD;
    }

    public String getUserSex() {
        return UserSex;
    }

    public void setUserSex(String userSex) {
        UserSex = userSex;
    }

    public String getUserBirthday() {
        return UserBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        UserBirthday = userBirthday;
    }

    public String getUserArea() {
        return UserArea;
    }

    public void setUserArea(String userArea) {
        UserArea = userArea;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(UserId);
        dest.writeString(UserPassword);
        dest.writeString(UserName);
        dest.writeString(UserQMD);
        dest.writeString(UserSex);
        dest.writeString(UserBirthday);
        dest.writeString(UserArea);
        dest.writeString(UserPhone);
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {

        @Override
        public UserData createFromParcel(Parcel source) {
            UserData data = new UserData();
            data.setId(source.readInt());
            data.setUserId(source.readString());
            data.setUserPassword(source.readString());
            data.setUserName(source.readString());
            data.setUserQMD(source.readString());
            data.setUserSex(source.readString());
            data.setUserBirthday(source.readString());
            data.setUserArea(source.readString());
            data.setUserPhone(source.readString());
            return data;
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[0];
        }
    };

}
