package com.example.handesen.sqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class test extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editFelh,editSuly,editDatum,editText_ID;
    Button btnAddData,btnviewAll,btnUpdate,btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        myDb = new DatabaseHelper(this);

        editFelh =(EditText)findViewById(R.id.felhaszn);
        editSuly =(EditText)findViewById(R.id.suly);
        editDatum =(EditText)findViewById(R.id.datum);
        editText_ID =(EditText)findViewById(R.id.editText_ID);
        btnAddData =(Button)findViewById(R.id.button_add);
        btnviewAll =(Button)findViewById(R.id.button_view);
        btnUpdate =(Button)findViewById(R.id.button_update);
        btnDelete =(Button)findViewById(R.id.button_del);

        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    public void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deleteRows = myDb.deleteData(editText_ID.getText().toString());
                        if(deleteRows > 0)
                            Toast.makeText(test.this,"Data Deleted!",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(test.this,"Data NOTTT Deleted!!",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void UpdateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editText_ID.getText().toString(),editFelh.getText().toString(),editSuly.getText().toString(),editDatum.getText().toString());

                        if(isUpdate == true)
                            Toast.makeText(test.this,"Data Updated!",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(test.this,"Data NOTTT Updated!!",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                    }
                }
        );
    }

    public void viewAll(){
        btnviewAll.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Cursor res= myDb.getAllData();
                        if(res.getCount() == 0){
                            showMessage("Error","Üres tábla");
                            return;
                        }


                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("ID: "+res.getString(0)+"\n");
                            buffer.append("FELH: "+res.getString(1)+"\n");
                            buffer.append("SULY: "+res.getString(2)+"\n");
                            buffer.append("DATUM: "+res.getString(3)+"\n\n");
                        }

                        showMessage("Adatok",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
