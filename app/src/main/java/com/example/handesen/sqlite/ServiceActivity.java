package com.example.handesen.sqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class ServiceActivity extends AppCompatActivity {
    ArrayList<String> cars;
    DatabaseHelper mydb;
    ListView servicelist;
    Button btn_seradd,btn_serdelete,btn_sermod;
    TextView tv_valassz;
    boolean delete = false;
    boolean modify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        btn_seradd = (Button) findViewById(R.id.btn_seradd);
        btn_serdelete = (Button) findViewById(R.id.btn_serdelete);
        btn_sermod = (Button) findViewById(R.id.btn_sermod);
        tv_valassz = (TextView) findViewById(R.id.tv_valassz);
        mydb = new DatabaseHelper(this);
        cars = new ArrayList();
        servicelist = (ListView) findViewById(R.id.serviceListView);
        int userid = mydb.getUserID(LoginActivity.username);
        int carid = CarChangeActivity.carid;
        mydb = new DatabaseHelper(this);

        Cursor c = mydb.getAllService(carid,userid);
        final String[] cars = new String[] {"part_name","created_at","part_prince"};
        int[] toViewId = new int[] {R.id.part_name,R.id.created_at,R.id.part_prince};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(),R.layout.parts_list, c , cars, toViewId , 0);
        servicelist.setAdapter(myCursorAdapter);
        servicelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
                if (delete) {
                    // TODO Auto-generated method stub
                    mydb.deleteServiceRow(cursor.getInt(cursor.getColumnIndex("_id")));
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }
                else if(modify){
                    Intent carmod = new Intent(ServiceActivity.this, ModifyService.class);
                    carmod.putExtra("serv_id",cursor.getInt(cursor.getColumnIndex("_id")));
                    ServiceActivity.this.startActivity(carmod);
                }
                else{



                }
            }

        });


        btn_seradd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent caradd = new Intent(ServiceActivity.this, AddService.class);
                ServiceActivity.this.startActivity(caradd);

            }
        });
        btn_serdelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tv_valassz.setText("Töröld az autót!");
                tv_valassz.setTextColor(RED);
                delete= true;
            }
        });
        btn_sermod.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tv_valassz.setText("Módosítsd az autót!");
                tv_valassz.setTextColor(GREEN);
                modify = true;



            }
        });


    }
    public void addService(View v){
        Intent inti = new Intent(ServiceActivity.this,AddService.class);
        startActivity(inti);
    }
}
