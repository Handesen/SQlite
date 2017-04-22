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

    public static final String DATABASE_NAME = "database.db";
    public static final String USER_TABLE = "user";
    public static final String GARAGE_TABLE ="garage";
    public static final String SERVICE_TABLE = "service";
    public static final String SERVICE_PARTS_TABLE = "service_parts";
    public static final String FUELING_TABLE = "fueling";
    public static final String COST_TABLE = "cost";
    public static final String STATION_TABLE = "fuel_station";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ USER_TABLE +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,mysql_id int, username VARCHAR(50) UNIQUE, first_name VARCHAR(50), last_name VARCHAR(50), date_of_birth VARCHAR(50), driving_lic_year VARCHAR(50))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ GARAGE_TABLE +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id int, make VARCHAR(50), model VARCHAR(30), license_plate VARCHAR(10) UNIQUE, fuel_type int, kilometers int, avarage_fuel int, created_at VARCHAR(50), updated_at VARCHAR(50) )");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ SERVICE_TABLE +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id int, car_id int, service_part_id int, location VARCHAR(50), actual_km int, part_prince int, created_at VARCHAR(50) )");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ FUELING_TABLE +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id int, car_id int, gas_station_id int, actual_km int, miss_prefuel boolean, priceall double, price_per_l double, fueled_amount double, created_at VARCHAR(50) )");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ COST_TABLE +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id int, car_id int, cost_type int, price int, created_at varchar(50) ) ");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ SERVICE_PARTS_TABLE +"(_id INTEGER PRIMARY KEY AUTOINCREMENT, part_name VARCHAR(50) UNIQUE)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ STATION_TABLE +"(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50) UNIQUE");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+SERVICE_TABLE);

        onCreate(db);

    }


    public boolean stationAdd(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        long result = db.insert(STATION_TABLE,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean serviceAdd(int userid,int carid, int part_id, String location, int actualkm, int part_prince){
        SQLiteDatabase db = this.getWritableDatabase();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id",userid);
        contentValues.put("car_id",carid);
        contentValues.put("service_part_id",part_id);
        contentValues.put("location",location);
        contentValues.put("actual_km",actualkm);
        contentValues.put("part_prince",part_prince);
        contentValues.put("created_at",strDate);
        long result = db.insert(SERVICE_TABLE,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public void deleteServiceRow(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+SERVICE_TABLE+" WHERE _id = "+id);

        onCreate(db);

    }

    public Cursor getAllFueling(int carid,int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+FUELING_TABLE+ " WHERE user_id ="+userid+" and car_id ="+carid,null);
        return res;
    }
    public Cursor getAllStation(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+STATION_TABLE,null);
        return res;
    }
    public boolean fuelAdd(int user_id,int car_id,int gas_station_id, int actual_km,boolean prefuel, double priceall,double price_l,double allfuel){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id",user_id);
        contentValues.put("car_id",car_id);
        contentValues.put("gas_station_id",gas_station_id);
        contentValues.put("actual_km",actual_km);
        contentValues.put("miss_prefuel",prefuel);
        contentValues.put("priceall",priceall);
        contentValues.put("price_per_l",price_l);
        contentValues.put("fueled_amount",allfuel);
        contentValues.put("created_at",strDate);

        long result = db.insert(FUELING_TABLE,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public void deleteFuelingRow(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+FUELING_TABLE+" WHERE _id = "+id);

        onCreate(db);

    }

    public boolean partsAdd(String parts_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("part_name",parts_name);
        long result = db.insert(SERVICE_PARTS_TABLE,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllPartsType(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+SERVICE_PARTS_TABLE,null);
        return res;
    }
    public void deleterow(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+GARAGE_TABLE+" WHERE _id = "+id);

        onCreate(db);

    }

    public int getUserID(String username){
        int id=-1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+USER_TABLE + " WHERE username='"+username+"'",null);
        if (res.moveToFirst())
        {
            id = res.getInt(res.getColumnIndex("_id"));

        }
        return id;
    }

    public boolean insertUserDetails(String username, int mysql_id,String first_name,String last_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("mysql_id",mysql_id);
        contentValues.put("first_name",first_name);
        contentValues.put("last_name",last_name);
        long result = db.insert(USER_TABLE,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;    }

    public Cursor getAllService(int carid,int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+SERVICE_TABLE+".* ,"+SERVICE_PARTS_TABLE+".part_name from "+SERVICE_TABLE+ " INNER JOIN "+SERVICE_PARTS_TABLE+" ON "+SERVICE_TABLE+".service_part_id = "+SERVICE_PARTS_TABLE+"._id WHERE user_id ="+userid+" and car_id ="+carid,null);
        return res;
    }

    public int getLastCarid(String license_plate){
        int id=-1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select _id from "+GARAGE_TABLE + " WHERE license_plate='"+license_plate+"'",null);
        if (res.moveToFirst())
        {
            id = res.getInt(res.getColumnIndex("_id"));

        }
        return id;
    }

    public boolean insertDataGarage(String make,String model,String license_plate, int fuel_type, int kilometers, double avarage_fuel ){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id",LoginActivity.userid);
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

    public Cursor getAllData(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+GARAGE_TABLE +" WHERE user_id = "+userid,null);
        return  res;

    }

    public Cursor getCarDetails(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+GARAGE_TABLE+ " WHERE _id = "+id,null);
        return  res;
    }
    public Cursor getServiceDetails(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+SERVICE_TABLE+ " WHERE _id = "+id,null);
        return  res;
    }

    public boolean updateServiceDetails(int _id, String location, int actualkm, int part_prince){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("location",location);
        contentValues.put("actual_km",actualkm);
        contentValues.put("part_prince",part_prince);
        long result = db.update(SERVICE_TABLE,contentValues,"_id ="+_id,null);
        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean updateCarDetails(int _id,String make,String model,int fuel_type, String license_plate, int kilometers,int avarage_fuel){
        SQLiteDatabase db = this.getWritableDatabase();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put("make",make);
        contentValues.put("model",model);
        contentValues.put("license_plate",license_plate);
        contentValues.put("fuel_type",fuel_type);
        contentValues.put("kilometers",kilometers);
        contentValues.put("avarage_fuel",avarage_fuel);
        contentValues.put("updated_at",strDate);
        long result = db.update(GARAGE_TABLE,contentValues,"_id ="+_id,null);
        if (result == -1)
            return false;
        else
            return true;
    }
}
