package com.example.uscrecsport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelperRegister extends SQLiteOpenHelper {

    public DBHelperRegister(Context context){
        super(context, "UserLogin.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(username Text primary key, password Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists users");
    }

    public boolean insertd(String un, String pw){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", un);
        cv.put("password", pw);
        boolean userExist = checkusername(un);
        if(userExist){
            return false;
        }
        long res = db.insert("users",null, cv);
        if(res == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkusername(String un){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cs = db.rawQuery("select * from users where username = ?", new String[] {un});
        if(cs.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkusernamepassword(String un, String pw){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cs = db.rawQuery("select * from users where username = ? and password = ? ", new String[] {un,pw});
        if(cs.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }
}
