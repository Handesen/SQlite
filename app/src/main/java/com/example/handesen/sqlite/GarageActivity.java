package com.example.handesen.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class GarageActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    ListView aListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);



//        mydb = new DatabaseHelper(this);
//        aListView = (ListView) findViewById(R.id.carList);
//        Cursor c = mydb.getAllData();
//        String[] cars = new String[] {"make","license_plate"};
//        int[] toViewId = new int[] {R.id.make,R.id.license_plate};
//        SimpleCursorAdapter myCursorAdapter;
//        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(),R.layout.list_item, c , cars, toViewId , 0);
//        aListView.setAdapter(myCursorAdapter);

    }



}
