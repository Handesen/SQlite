package com.example.handesen.sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PartsAddActivity extends AppCompatActivity {
    EditText et_partname;
    DatabaseHelper mydb;
    Button btn_partsadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts_add);
        et_partname = (EditText) findViewById(R.id.et_partsname);
        btn_partsadd = (Button) findViewById(R.id.btn_partsadd);
        mydb = new DatabaseHelper(this);

        btn_partsadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = mydb.partsAdd(et_partname.getText().toString());
                if (result){
                    Intent Servceact = new Intent(PartsAddActivity.this, ServiceActivity.class);
                    PartsAddActivity.this.startActivity(Servceact);
                }
                else{
                    Toast.makeText(PartsAddActivity.this, "Sikertelen hozzáadás", Toast.LENGTH_LONG).show();
                }
            }
        });




    }
}
