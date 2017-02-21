package com.example.handesen.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editFelh,editSuly,editDatum;
    Button btnAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editFelh =(EditText)findViewById(R.id.felhaszn);
        editSuly =(EditText)findViewById(R.id.suly);
        editDatum =(EditText)findViewById(R.id.datum);
        btnAddData =(Button)findViewById(R.id.button_add);
        AddData();
    }

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editFelh.getText().toString(),
                                editSuly.getText().toString(),
                                editDatum.getText().toString() );
                        if(isInserted)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data NOTTT Inserted",Toast.LENGTH_LONG).show();


                    }
                }
        );
    }
}
