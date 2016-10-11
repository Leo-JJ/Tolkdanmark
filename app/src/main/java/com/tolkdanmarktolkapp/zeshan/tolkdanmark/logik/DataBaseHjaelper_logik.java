package com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DataBaseHjaelper_logik extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "KhataDb.db";
    public static final String CONTACTS_TABLE_NAME = "UserLogintbl1";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_USERNAME = "Username";
    public static final String CONTACTS_COLUMN_PASSWORD = "Password";
    public static final String CONTACTS_COLUMN_MKEY = "mkey";
    public static final String CONTACTS_COLUMN_Name = "Name1";
    private HashMap hp;

    public DataBaseHjaelper_logik(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table contacts " +
                        "(id integer primary key, name text,phone text,email text, street text,place text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS UserLogintbl1");
        onCreate(db);
    }

    public long insertUser(String un, String ps, String mkey, String Userid)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_USERNAME, un);
        contentValues.put(CONTACTS_COLUMN_PASSWORD, ps);
        contentValues.put(CONTACTS_COLUMN_MKEY, mkey);
        contentValues.put(CONTACTS_COLUMN_Name, Userid);

        return db.insert("UserLogintbl1", null, contentValues);
    }

    public Cursor CreateTable(String un, String ps){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS UserLogintbl1(Username VARCHAR,Password VARCHAR,Name1 VARCHAR," + CONTACTS_COLUMN_MKEY + " varchar, Userid VARCHAR );");
        Cursor res =  db.rawQuery("select * from UserLogintbl1 where " + CONTACTS_COLUMN_USERNAME + "='" + un + "' and " + CONTACTS_COLUMN_PASSWORD + "='" + ps + "'", null);
        return res;
    }
    public Cursor GetUserBymkey(String mkey) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery(" select * from UserLogintbl1 where mkey=?", new String[] {mkey} );
        return res;
    }
    public Cursor getAllUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from UserLogintbl1", null );
        return res;
    }
    public Cursor getData(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+CONTACTS_COLUMN_Name+" from UserLogintbl1 where " + CONTACTS_COLUMN_USERNAME + "='" + username + "'", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public void deleteContact (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " +CONTACTS_TABLE_NAME + " WHERE " + CONTACTS_COLUMN_Name + "=\""+ name+"\";");
    }

    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from UserLogintbl1", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_Name)));
            res.moveToNext();
        }
        return array_list;
    }
}