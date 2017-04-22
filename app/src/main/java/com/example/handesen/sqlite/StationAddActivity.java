package com.example.handesen.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class StationAddActivity extends AppCompatActivity {
    EditText et_stationname;
    DatabaseHelper mydb;
    Button btn_stationadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_add);

        et_stationname = (EditText) findViewById(R.id.et_partsname);
        btn_stationadd = (Button) findViewById(R.id.btn_partsadd);
        mydb = new DatabaseHelper(this);

        btn_stationadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = mydb.partsAdd(et_stationname.getText().toString());
                if (result){
                    Intent Servceact = new Intent(StationAddActivity.this, FuelingActivity.class);
                    StationAddActivity.this.startActivity(Servceact);
                }
                else{
                    Toast.makeText(StationAddActivity.this, "Sikertelen hozzáadás", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
