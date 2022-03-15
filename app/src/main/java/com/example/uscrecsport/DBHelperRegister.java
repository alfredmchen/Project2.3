package com.example.uscrecsport;

import android.annotation.SuppressLint;
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
        db.execSQL("create table villageGym(appointment_id INTEGER PRIMARY KEY AUTOINCREMENT, month TEXT NOT NULL, " +
                "date TEXT NOT NULL, time TEXT NOT NULL)");
        db.execSQL("create table villageGymAppointment(appointment_id INTEGER, " +
                "username TEXT NOT NULL, FOREIGN KEY(appointment_id) REFERENCES villageGym(appointment_id))");
        db.execSQL("create table villageGymWaitlist(appointment_id INTEGER, " +
                "username TEXT NOT NULL, FOREIGN KEY(appointment_id) REFERENCES villageGym(appointment_id))");
        db.execSQL("create table lyonGym(appointment_id INTEGER PRIMARY KEY AUTOINCREMENT, month TEXT NOT NULL, " +
                "date TEXT NOT NULL, time TEXT NOT NULL)");
        db.execSQL("create table lyonGymAppointment(appointment_id INTEGER, " +
                "username TEXT NOT NULL, FOREIGN KEY(appointment_id) REFERENCES villageGym(appointment_id))");
        db.execSQL("create table lyonGymWaitlist(appointment_id INTEGER, " +
                "username TEXT NOT NULL, FOREIGN KEY(appointment_id) REFERENCES villageGym(appointment_id))");
        for(int i=1; i < 32; i++) {
            for (int j = 8; j < 24; j+=2) {
                db.execSQL("insert into villageGym(month, date, time) values(3," + i + ", " + j + ")");
            }
        }
        for(int i=1; i < 32; i++) {
            for (int j = 8; j < 24; j+=2) {
                db.execSQL("insert into lyonGym(month, date, time) values(3," + i + ", " + j + ")");
            }
        }
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

    public boolean insertTime(String month, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("month", month);
        cv.put("date", date);
        cv.put("time", time);
        long res = db.insert("villageGym",null, cv);
        if(res == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean cancelAppointment(String gym, Integer appointment_id, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "";
        if(gym.equals("village")){
            queryString = "DELETE FROM villageGymAppointment " +
                    "WHERE appointment_id = " + appointment_id + " and username = " + username;
        }else{
            queryString = "DELETE FROM lyonGymAppointment " +
                    "WHERE appointment_id = " + appointment_id + " and username = " + username;
        }

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteWaitlist(String gym, Integer appointment_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "";
        if(gym.equals("village")){
            queryString = "DELETE FROM villageGymWaitlist WHERE appointment_id = " + appointment_id;
        }else{
            queryString = "DELETE FROM lyonGymWaitlist WHERE appointment_id = " + appointment_id;;
        }

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    public boolean insertAppointment(String gym, Integer appointment_id, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("appointment_id", appointment_id);
        cv.put("username", username);
        long res;
        if(gym.equals("village")){
            res = db.insert("villageGymAppointment" ,null, cv);
        }else{
            res = db.insert("lyonGymAppointment" ,null, cv);
        }

        if(res == -1){
            return false;
        }else return true;
    }

    public boolean insertWaitlist(String gym, Integer appointment_id, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("appointment_id", appointment_id);
        cv.put("username", username);
        long res;
        if(gym.equals("village")){
            res = db.insert("villageGymWaitlist" ,null, cv);
        }else{
            res = db.insert("lyonGymWaitlist" ,null, cv);
        }

        if(res == -1){
            return false;
        }else return true;
    }

    public Integer getAppointmentId(String gym, String month, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cs;

        if(gym.equals("village")){
            cs = db.rawQuery("select * from villageGym where month = ? and date = ? and time = ?",
                    new String[] {month, date, time});
        }else{
            cs = db.rawQuery("select * from lyonGym where month = ? and date = ? and time = ?",
                    new String[] {month, date, time});
        }
        if(cs.moveToFirst()){
            return cs.getInt(0);
        }
        return -1;
    }

    public boolean checkAppointmentAvailability(String gym, String month, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cs;
        if(gym.equals("village")){
            cs = db.rawQuery("select * from villageGymAppointment where appointment_id = ?",
                    new String[]{getAppointmentId(gym, month, date, time).toString()});
        }else{
            cs = db.rawQuery("select * from lyonGymAppointment where appointment_id = ?",
                    new String[]{getAppointmentId(gym, month, date, time).toString()});
        }
        if(cs.getCount() > 2){
            return false;
        }else{
            return true;
        }
    }
    public boolean checkAppointment(String gym, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cs;
        if(gym.equals("village")){
            cs = db.rawQuery("select * from villageGymAppointment where username = ?", new String[] {username});
        }else{
            cs = db.rawQuery("select * from lyonGymAppointment where username = ?", new String[] {username});
        }
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

    public String getCurrentAppointments(String un, int currmonth, int currday, int currhour) {
        String result = "Current Appointments: \n";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor lcs = db.rawQuery("select * from lyonGymAppointment where username = ?", new String[]{un});
        if (lcs.getCount() > 0){
            while (lcs.moveToNext()) {
                String time = lcs.getString(0);
                if(isCurrentAppt(currmonth,currday,currhour,time)) {
                    Cursor cs = db.rawQuery("select * from lyonGym where appointment_id = ?", new String[]{time});
                    cs.moveToFirst();
                    String month = cs.getString(cs.getColumnIndexOrThrow("month"));
                    String day = cs.getString(cs.getColumnIndexOrThrow("date"));
                    String hour = cs.getString(cs.getColumnIndexOrThrow("time"));
                    result += ("Lyon center: " + month + " / " + day + " "+ hour + ":00\n");
                }
            }
            lcs.close();
        }else{
            result += "no current appointment for Lyon Center \n";
        }
        Cursor vcs = db.rawQuery("select * from villageGymAppointment where username = ?", new String[]{un});
        if (vcs.getCount() > 0){
            while (vcs.moveToNext()) {
                String time = vcs.getString(0);
                if(isCurrentAppt(currmonth,currday,currhour,time)) {
                    Cursor cs = db.rawQuery("select * from lyonGym where appointment_id = ?", new String[]{time});
                    cs.moveToFirst();
                    String month = cs.getString(cs.getColumnIndexOrThrow("month"));
                    String day = cs.getString(cs.getColumnIndexOrThrow("date"));
                    String hour = cs.getString(cs.getColumnIndexOrThrow("time"));
                    result += ("Village gym: " + month + " / " + day + " "+ hour + ":00\n");
                }
            }
            vcs.close();
        }else{
            result += "no current appointment for village gym \n";
        }

        return result;
    }

    public boolean isCurrentAppt(int cm, int cd, int ch, String timeid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cs = db.rawQuery("select month, date, time from lyonGym where appointment_id = ?", new String[]{timeid});
        cs.moveToFirst();
        if(cs.getCount() == 1) {
            int month = Integer.parseInt(cs.getString(cs.getColumnIndexOrThrow("month")));
            int day = Integer.parseInt(cs.getString(cs.getColumnIndexOrThrow("date")));
            int hour = Integer.parseInt(cs.getString(cs.getColumnIndexOrThrow("time")));
            if(month >= cm){
                if(day > cd){
                    return true;
                }else if (day == cd){
                    if(hour >= ch){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
