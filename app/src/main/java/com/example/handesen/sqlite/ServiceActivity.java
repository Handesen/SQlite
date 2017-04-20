package com.example.handesen.sqlite;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity {
    ArrayList<String> cars;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        myDb = new DatabaseHelper(this);
        cars = new ArrayList();
        Cursor cursor = myDb.getAllService(1);
        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    String partname,location,created_at;
                    int actual_km,part_prince;
                    partname = cursor.getString(cursor.getColumnIndex("partname"));
                    location = cursor.getString(cursor.getColumnIndex("location"));
                    created_at = cursor.getString(cursor.getColumnIndex("created_at"));
                    actual_km = cursor.getInt(cursor.getColumnIndex("actual_km"));
                    part_prince = cursor.getInt(cursor.getColumnIndex("part_prince"));


                }while (cursor.moveToNext());
            }
        }

    }
}
