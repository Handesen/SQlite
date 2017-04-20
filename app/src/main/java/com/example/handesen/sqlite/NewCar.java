package com.example.handesen.sqlite;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewCar extends AppCompatActivity {
    DatabaseHelper myDb;
    Spinner sp_uzemanyag;
    EditText et_modellname,et_modelltype,et_license,et_km,et_avgfuel;
    Button btn_add;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);
        initialize();

        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String modellname,modelltype,license;
                int km,uzemanyag;
                Double avgfuel;
                modellname = et_modellname.getText().toString();
                modelltype = et_modelltype.getText().toString();
                license = et_license.getText().toString();
                km = Integer.parseInt(et_km.getText().toString());
                avgfuel = Double.parseDouble(et_avgfuel.getText().toString());
                uzemanyag = sp_uzemanyag.getSelectedItemPosition();

                boolean isInserted = myDb.insertDataGarage(modellname,modelltype,license,uzemanyag,km,avgfuel);
                if(isInserted) {
                    Intent backmain = new Intent(NewCar.this, MainActivity.class);
                    NewCar.this.startActivity(backmain);
                }
                else {
                    Toast.makeText(NewCar.this, "Data NOTTT Inserted", Toast.LENGTH_LONG).show();
                }

            }
        });

    }



    public void initialize(){
        myDb = new DatabaseHelper(this);
        et_modellname = (EditText) findViewById(R.id.et_modell_name);
        et_modelltype = (EditText) findViewById(R.id.et_modell_type);
        et_license = (EditText) findViewById(R.id.et_licence_plate);

        et_km = (EditText) findViewById(R.id.et_km);
        et_avgfuel = (EditText) findViewById(R.id.et_avg_fuel);

        sp_uzemanyag = (Spinner) findViewById(R.id.sp_fueltype);

        btn_add = (Button) findViewById(R.id.btn_add);


    }
}
