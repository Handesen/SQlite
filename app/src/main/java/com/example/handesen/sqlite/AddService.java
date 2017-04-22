package com.example.handesen.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AddService extends AppCompatActivity {

    Spinner sp_parts;
    List<String> databaseRows;
    List<String> databaseRowsId;
    EditText et_szhelye,et_aktkm,et_csalkar;
    Button btn_add,btn_partsadd;
    DatabaseHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        initialize();

        Cursor c = mydb.getAllPartsType();

        String[] columns = new String[] { "part_name" };
        int[] to = new int[] { android.R.id.text1 };

        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, columns, to);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spnClients = (Spinner) findViewById(R.id.spinner_parts);
        spnClients.setAdapter(mAdapter);




        btn_partsadd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent inti = new Intent(AddService.this,PartsAddActivity.class);
                startActivity(inti);

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String szervizhely;
                int aktkm,cseralkar,cseralktip;
                aktkm = Integer.parseInt(et_aktkm.getText().toString());
                cseralkar = Integer.parseInt(et_csalkar.getText().toString());
                szervizhely = et_szhelye.getText().toString();

                Cursor cursor = (Cursor) sp_parts.getSelectedItem();
                int parts_id = cursor.getInt(cursor.getColumnIndex("_id"));

                int carid = CarChangeActivity.carid;
                int uid = LoginActivity.userid;
                boolean result =  mydb.serviceAdd(uid,carid,parts_id,szervizhely,aktkm,cseralkar);
                if (result){
                    Intent inti = new Intent(AddService.this,ServiceActivity.class);
                    startActivity(inti);
                }
                else{
                    Toast.makeText(AddService.this,"Sikertelen hozzáadás",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void initialize(){
        et_szhelye =(EditText) findViewById(R.id.et_location);
        et_aktkm = (EditText) findViewById(R.id.et_sz_km);
        et_csalkar = (EditText) findViewById(R.id.et_sz_ar);
        btn_add = (Button) findViewById(R.id.btn_sz_add);
        btn_partsadd = (Button) findViewById(R.id.btn_partsadd);
        sp_parts = (Spinner) findViewById(R.id.spinner_parts);
        mydb = new DatabaseHelper(this);

    }
}
