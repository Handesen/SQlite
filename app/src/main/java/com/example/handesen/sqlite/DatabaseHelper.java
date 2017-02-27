package com.example.handesen.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Handesen on 2017. 02. 21..
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="Adatok.db";
    public static final String TABLE_NAME ="napimeres_tabla";
    public static final String COL_1 ="ID";
    public static final String COL_2 ="FELHASZN";
    public static final String COL_3 ="SULY";
    public static final String c="valami";
    public static final String COL_4 ="IDO";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT,FELHASZN TEXT,SULY INTEGER,IDO TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String felh,String suly,String datum){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,felh);
        contentValues.put(COL_3,suly);
        contentValues.put(COL_4,datum);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;


    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }


    public boolean updateData(String id,String felh,String suly,String datum){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",id);
        contentValues.put(COL_2,felh);
        contentValues.put(COL_3,suly);
        contentValues.put(COL_4,datum);
        db.update(TABLE_NAME,contentValues, "ID = ?",new String[] {id });
        return true;

    }

    public Integer deleteData (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?", new String[] {id });

    }
}
