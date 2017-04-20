package com.example.handesen.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Handesen on 2017. 02. 21..
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = MainActivity.username+".db";
    public static final String GARAGE_TABLE ="garage";
    public static final String SERVICE_TABLE = "service";
    public static final String FUELING_TABLE = "fueling";
    public static final String COST_TABLE = "cost";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ GARAGE_TABLE +"(_id INTEGER PRIMARY KEY AUTOINCREMENT, make VARCHAR(50), model VARCHAR(30), license_plate VARCHAR(10), fuel_type int, kilometers int, avarage_fuel int, created_at VARCHAR(50), updated_at VARCHAR(50) )");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ SERVICE_TABLE +"(_id INTEGER PRIMARY KEY AUTOINCREMENT, car_id int, service_part_id int, location VARCHAR(50), actual_km int, part_prince int, created_at VARCHAR(50) )");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ FUELING_TABLE +"(_id INTEGER PRIMARY KEY AUTOINCREMENT, car_id int, fuel_type int, gas_station_id int, actual_km int, miss_prefuel boolean, priceall double, price_per_l double, fueled_amount double, avarage_fuel double )");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ COST_TABLE +"(_id INTEGER PRIMARY KEY AUTOINCREMENT, car_id int, cost_type int, price int, created_at varchar(50) ) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+SERVICE_TABLE);

        onCreate(db);

    }

    public void delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+SERVICE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+GARAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+FUELING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+COST_TABLE);
        onCreate(db);

    }

    public Cursor getAllService(int carid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+SERVICE_TABLE,null);
        return res;

    }

    public HashMap<String, String> serviceList(){
        HashMap<String,String> k = new HashMap<>();
        return k;
    }

    public boolean insertDataGarage(String make,String model,String license_plate, int fuel_type, int kilometers, double avarage_fuel ){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("make",make);
        contentValues.put("model",model);
        contentValues.put("license_plate",license_plate);
        contentValues.put("fuel_type",fuel_type);
        contentValues.put("kilometers",kilometers);
        contentValues.put("avarage_fuel",avarage_fuel);
        contentValues.put("created_at",strDate);
        contentValues.put("updated_at","s");
        long result = db.insert(GARAGE_TABLE,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;


    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+GARAGE_TABLE,null);
        return res;
    }


    public boolean updateData(String id,String felh,String suly,String datum){

        return true;

    }

    public Integer deleteData (String id){

        return 1;
    }
}
