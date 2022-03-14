package com.example.uscrecsport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelperRegister extends SQLiteOpenHelper {

    public DBHelperRegister(Context context){
        super(context, "recCenter.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(username TEXT PRIMARY KEY, password TEXT NOT NULL)");
        db.execSQL("create table villageGym(time_id INTEGER PRIMARY KEY, time INTEGER NOT NULL)");
        db.execSQL("create table villageGymAppointment(appointment_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "time_id INTEGER NOT NULL, username TEXT NOT NULL, FOREIGN KEY(time_id) REFERENCES villageGym(time_id))");
        insertTime(110, 10);
        insertTime(111, 11);
        insertTime(112, 12);
        insertTime(113, 13);
        insertTime(114, 14);
        insertTime(115, 15);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists users");
    }

    public boolean insertUser(String un, String pw){
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

    private boolean insertTime(Integer time_id, Integer time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("time_id", time_id);
        cv.put("time", time);
        long res = db.insert("villageGym",null, cv);
        if(res == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean insertAppointment(Integer time_id, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        long res = db.insert("villageGym(" + time_id + ")",null, cv);
        if(res == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkAppointment(Integer time_id, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cs = db.rawQuery("select * from villageGynAppointments where username = ?", new String[] {username});
        if(cs.getCount() > 0){
            return true;
        }else{
            return false;
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
