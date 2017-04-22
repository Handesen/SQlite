package com.example.handesen.sqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class CarChangeActivity extends AppCompatActivity {
    DatabaseHelper mydb;
    ListView aListView;
    Cursor c;
    Button btn_caradd,btn_cardelete,btn_carmod;
    TextView tv_valassz;
    boolean delete = false;
    boolean modify = false;
    public static int carid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_change);

        btn_caradd = (Button) findViewById(R.id.btn_newcaradd);
        btn_cardelete = (Button) findViewById(R.id.btn_cardelete);
        btn_carmod = (Button) findViewById(R.id.btn_carmod);
        tv_valassz = (TextView) findViewById(R.id.tv_valassz);
        mydb = new DatabaseHelper(this);
        aListView = (ListView) findViewById(R.id.carListView);
        int userid = mydb.getUserID(LoginActivity.username);
        Cursor c = mydb.getAllData(userid);
        final String[] cars = new String[] {"make","license_plate"};
        int[] toViewId = new int[] {R.id.make,R.id.license_plate};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(),R.layout.list_item, c , cars, toViewId , 0);
        aListView.setAdapter(myCursorAdapter);
        aListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
                if (delete) {
                    // TODO Auto-generated method stub
                    mydb.deleterow(cursor.getInt(cursor.getColumnIndex("_id")));
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }
                else if(modify){
                    Intent carmod = new Intent(CarChangeActivity.this, ModifyCar.class);
                    carmod.putExtra("servid",cursor.getInt(cursor.getColumnIndex("_id")));
                    CarChangeActivity.this.startActivity(carmod);
                }
                else{

                    SharedPreferences preferences = getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    carid = cursor.getInt(cursor.getColumnIndex("_id"));
                    editor.putInt("carid", carid);
                    editor.putString("license_plate", cursor.getString(cursor.getColumnIndex("license_plate")));
                    editor.commit();
                    Intent intent = getIntent();
                    String remember_token = intent.getStringExtra("remember_token");
                    Intent mainintent = new Intent(CarChangeActivity.this, MainActivity.class);
                    mainintent.putExtra("remember_token", remember_token);
                    CarChangeActivity.this.startActivity(mainintent);

                }
            }

        });


        btn_caradd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent caradd = new Intent(CarChangeActivity.this, NewCar.class);
                CarChangeActivity.this.startActivity(caradd);

            }
        });
        btn_cardelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tv_valassz.setText("Töröld az autót!");
                tv_valassz.setTextColor(RED);
                delete= true;
            }
        });
        btn_carmod.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tv_valassz.setText("Módosítsd az autót!");
                tv_valassz.setTextColor(GREEN);
                modify = true;



            }
        });


    }
}
