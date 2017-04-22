package com.example.handesen.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class FuelAddActivity extends AppCompatActivity {
    Button btn_fueladd,btn_stationadd;
    CheckBox cb_prefuel;
    EditText et_allfuel,et_fuel_l,et_allcost,et_fuelkm;
    Spinner sp_station;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_add);
        initialize();
        Cursor c = mydb.getAllStation();

        String[] columns = new String[] { "name" };
        int[] to = new int[] { android.R.id.text1 };

        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, columns, to);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spnClients = (Spinner) findViewById(R.id.sp_station);
        spnClients.setAdapter(mAdapter);




        btn_stationadd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent inti = new Intent(FuelAddActivity.this,PartsAddActivity.class);
                startActivity(inti);

            }
        });

        btn_fueladd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean elotank;
                int aktkm,tankoltmenny,perl,osszkltsg;
                aktkm = Integer.parseInt(et_fuelkm.getText().toString());
                tankoltmenny = Integer.parseInt(et_allfuel.getText().toString());
                perl = Integer.parseInt(et_fuel_l.getText().toString());
                osszkltsg = Integer.parseInt(et_allcost.getText().toString());
                elotank = cb_prefuel.isChecked();

                Cursor cursor = (Cursor) sp_station.getSelectedItem();
                int station_id = cursor.getInt(cursor.getColumnIndex("_id"));

                int carid = CarChangeActivity.carid;
                int uid = LoginActivity.userid;
                boolean result =  mydb.fuelAdd(uid,carid,station_id,aktkm,elotank,osszkltsg,perl,tankoltmenny);
                if (result){
                    Intent inti = new Intent(FuelAddActivity.this,FuelingActivity.class);
                    startActivity(inti);
                }
                else{
                    Toast.makeText(FuelAddActivity.this,"Sikertelen hozzáadás",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void initialize(){
        cb_prefuel = (CheckBox) findViewById(R.id.cb_prefuel);
        et_allfuel = (EditText) findViewById(R.id.et_tankmeny);
        et_fuel_l = (EditText) findViewById(R.id.et_uzemanyagar);
        et_allcost = (EditText) findViewById(R.id.et_osszkoltseg);
        et_fuelkm = (EditText) findViewById(R.id.et_fuel_km);
        sp_station = (Spinner) findViewById(R.id.sp_station);
        btn_stationadd = (Button) findViewById(R.id.btn_newstation);
        btn_fueladd = (Button) findViewById(R.id.btn_fueladd);
        mydb = new DatabaseHelper(this);
    }
}
