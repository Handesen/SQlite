package com.example.handesen.sqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class ModifyCar extends AppCompatActivity {
    DatabaseHelper mydb;
    Spinner sp_uzemanyag;
    EditText et_modellname,et_modelltype,et_license,et_km,et_avgfuel;
    Button btn_mod;
    public static int carid;
    boolean vanid = false;
    String etmodel,ettype,etlice,created_at;
    int km,avgfuel,spuzem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);
        initialize();
        Intent intent = getIntent();

        carid = intent.getIntExtra("carid",-1);
        if (carid == -1){
            //#TODO Lekezelni ha nem kap id-t
        }
        Cursor c = mydb.getCarDetails(carid);

        if (c.moveToFirst()){

                etmodel = c.getString(c.getColumnIndex("make"));
                ettype = c.getString(c.getColumnIndex("model"));
                etlice = c.getString(c.getColumnIndex("license_plate"));
                spuzem = c.getInt(c.getColumnIndex("fuel_type"));
                km  = c.getInt(c.getColumnIndex("fuel_type"));
                avgfuel  = c.getInt(c.getColumnIndex("fuel_type"));
                created_at = c.getString(c.getColumnIndex("created_at"));


        }
        c.close();


        btn_mod.setText("Módosít");
        et_modellname.setText(etmodel);
        et_modelltype.setText(ettype);
        et_license.setText(etlice);
        et_km.setText(String.valueOf(km));
        et_avgfuel.setText(String.valueOf(avgfuel));
        sp_uzemanyag.setSelection(spuzem);

        btn_mod.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                etmodel = et_modellname.getText().toString();
                ettype = et_modelltype.getText().toString();
                etlice = et_license.getText().toString();
                km = Integer.parseInt(et_km.getText().toString());
                avgfuel = Integer.parseInt(et_avgfuel.getText().toString());
                spuzem = sp_uzemanyag.getSelectedItemPosition();

                boolean ucar = mydb.updateCarDetails(carid,etmodel,ettype,spuzem,etlice,km,avgfuel);
                if (ucar) {
                    Intent caradd = new Intent(ModifyCar.this, CarChangeActivity.class);
                    ModifyCar.this.startActivity(caradd);
                }

            }
        });
    }

    public void initialize(){
        mydb = new DatabaseHelper(this);
        et_modellname = (EditText) findViewById(R.id.et_modell_name);
        et_modelltype = (EditText) findViewById(R.id.et_modell_type);
        et_license = (EditText) findViewById(R.id.et_licence_plate);

        et_km = (EditText) findViewById(R.id.et_km);
        et_avgfuel = (EditText) findViewById(R.id.et_avg_fuel);

        sp_uzemanyag = (Spinner) findViewById(R.id.sp_fueltype);

        btn_mod = (Button) findViewById(R.id.btn_add);


    }
}
