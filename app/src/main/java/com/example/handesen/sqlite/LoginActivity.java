package com.example.handesen.sqlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class LoginActivity extends AppCompatActivity {

    public static String username;
    public static int userid;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button bRegister = (Button) findViewById(R.id.btn_register);
        final Button bLogin = (Button) findViewById(R.id.btn_login);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new BackgroundTask().execute();


            }
        });
    }

    class BackgroundTask extends AsyncTask<Void,Void,String>{

        EditText et_username = (EditText) findViewById(R.id.et_username);
        EditText et_password = (EditText) findViewById(R.id.et_password);
        String json_url,password;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://szakdolgozat.comxa.com/login.php";
            username =  et_username.getText().toString();
            password = et_password.getText().toString();

        }

        @Override
        protected String doInBackground(Void... params) {


            StringBuilder stringBuilder = new StringBuilder();
            try {

                StringBuilder tokenUri=new StringBuilder("username=");
                tokenUri.append(URLEncoder.encode(username,"UTF-8"));
                tokenUri.append("&password=");
                tokenUri.append(URLEncoder.encode(password,"UTF-8"));


                URL obj = new URL(json_url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");

                con.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
                outputStreamWriter.write(tokenUri.toString());
                outputStreamWriter.flush();

                int responseCode = con.getResponseCode();
                System.out.println("POST Response Code :: " + responseCode);


                // For POST only - END
                if (responseCode == HttpURLConnection.HTTP_OK) { //success
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // print result
                    System.out.println(response.toString());
                    return response.toString().trim();

                } else {
                    System.out.println("POST request not worked");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            valami(result);
        }
    }
    public void valami (String input){
        StringBuffer buff = new StringBuffer();
        try {
            JSONObject js = new JSONObject(input);
            JSONArray ja = js.getJSONArray("result");
            int count = 0;
            while(count<ja.length()){
                JSONObject jo = ja.getJSONObject(count);

                boolean succes = jo.getBoolean("succes");
                if (succes){
                    SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    mydb = new DatabaseHelper(this);
                    userid = mydb.getUserID(username);


                    String remember_token = jo.getString("remember_token");


                    String restoredText = prefs.getString("username", null);
                    int lastcarid = prefs.getInt("_id",-1);
                    if (restoredText != null)
                    {
                        if (restoredText.equals(username) && lastcarid !=-1){
                            Intent mainintent = new Intent(LoginActivity.this, MainActivity.class);
                            mainintent.putExtra("remember_token",remember_token);
                            LoginActivity.this.startActivity(mainintent);

                        }
                        else{
                            editor.putString("username", username);
                            editor.commit();
                            Intent mainintent = new Intent(LoginActivity.this, CarChangeActivity.class);
                            mainintent.putExtra("remember_token",remember_token);
                            LoginActivity.this.startActivity(mainintent);


                        }

                    }
                    else{

                        editor.putString("username", username);
                        editor.commit();
                        Intent mainintent = new Intent(LoginActivity.this, CarChangeActivity.class);
                        mainintent.putExtra("remember_token",remember_token);
                        LoginActivity.this.startActivity(mainintent);
                    }



                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Bejelentkezés sikertelen!")
                            .setNegativeButton("Újra", null)
                            .create()
                            .show();
                }
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  textView.setText(buff.toString());

    }

}