package com.example.handesen.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class FuelingActivity extends AppCompatActivity {
    ArrayList<String> cars;

    DatabaseHelper mydb;
    ListView fuellist;
    Button btn_fueladd,btn_fueldelete;
    TextView tv_valassz;
    boolean delete = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fueling);

        btn_fueladd = (Button) findViewById(R.id.btn_fueladd);
        btn_fueldelete = (Button) findViewById(R.id.btn_fueldelete);
        tv_valassz = (TextView) findViewById(R.id.tv_valassz);
        mydb = new DatabaseHelper(this);
        cars = new ArrayList();
        fuellist = (ListView) findViewById(R.id.fuelingListView);
        int userid = mydb.getUserID(LoginActivity.username);
        int carid = CarChangeActivity.carid;
        mydb = new DatabaseHelper(this);

        Cursor c = mydb.getAllFueling(carid,userid);
        final String[] cars = new String[] {"priceall","created_at","fueled_amount"};
        int[] toViewId = new int[] {R.id.part_name,R.id.created_at,R.id.part_prince};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(),R.layout.parts_list, c , cars, toViewId , 0);
        fuellist.setAdapter(myCursorAdapter);
        fuellist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
                if (delete) {
                    // TODO Auto-generated method stub
                    mydb.deleteFuelingRow(cursor.getInt(cursor.getColumnIndex("_id")));
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }

                else{



                }
            }

        });


        btn_fueladd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent caradd = new Intent(FuelingActivity.this, AddService.class);
                FuelingActivity.this.startActivity(caradd);

            }
        });
        btn_fueldelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tv_valassz.setText("Töröld a tankolást!");
                tv_valassz.setTextColor(RED);
                delete= true;
            }
        });




    }
    public void addService(View v){
        Intent inti = new Intent(FuelingActivity.this,AddService.class);
        startActivity(inti);
    }
}
