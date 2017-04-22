package com.example.handesen.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ModifyService extends AppCompatActivity {
    Spinner sp_parts;
    EditText et_szhelye,et_aktkm,et_csalkar;
    TextView tv_csalk;
    Button btn_add,btn_partsadd;
    DatabaseHelper mydb;
    String loc;
    int pprince,km;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service); initialize();
        sp_parts.setVisibility(View.INVISIBLE);
        btn_partsadd.setVisibility(View.INVISIBLE);
        tv_csalk.setVisibility(View.INVISIBLE);


        Cursor c = mydb.getAllPartsType();

        String[] columns = new String[] { "part_name" };
        int[] to = new int[] { android.R.id.text1 };

        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, columns, to);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spnClients = (Spinner) findViewById(R.id.spinner_parts);
        spnClients.setAdapter(mAdapter);
        Intent intent = getIntent();
        int selected = intent.getIntExtra("serv_id",-1);
        btn_add.setText("Módosít");
        Cursor cc = mydb.getServiceDetails(selected);

        if (cc.moveToFirst()){

            loc = cc.getString(cc.getColumnIndex("location"));
            pprince = cc.getInt(cc.getColumnIndex("part_prince"));
            km = cc.getInt(cc.getColumnIndex("actual_km"));
        }

        et_szhelye.setText(""+loc);
        et_aktkm.setText(""+km);
        et_csalkar.setText(""+pprince);
        cc.close();


        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String szervizhely;
                int aktkm,cseralkar;
                aktkm = Integer.parseInt(et_aktkm.getText().toString());
                cseralkar = Integer.parseInt(et_csalkar.getText().toString());
                szervizhely = et_szhelye.getText().toString();

                Intent intent = getIntent();
                int selected = intent.getIntExtra("serv_id",-1);
                boolean result =  mydb.updateServiceDetails(selected,szervizhely,aktkm,cseralkar);
                if (result){
                    Intent inti = new Intent(ModifyService.this,ServiceActivity.class);
                    startActivity(inti);
                }
                else{
                    Toast.makeText(ModifyService.this,"Sikertelen hozzáadás",Toast.LENGTH_SHORT).show();
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
        tv_csalk = (TextView) findViewById(R.id.tv_csalk);

    }
}
