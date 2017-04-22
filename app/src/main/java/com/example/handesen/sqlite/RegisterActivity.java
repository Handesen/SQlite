package com.example.handesen.sqlite;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {


    boolean password=false;
    boolean name=false;
    boolean username=false;
    boolean email=false;
    DatabaseHelper mydb;


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etName = (EditText) findViewById(R.id.et_lastname);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bRegister = (Button) findViewById(R.id.bRegister);


        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (etName.getText().toString().trim()
                            .length() < 6) {

                        etName.setError("A neved túl rövid!");
                        name=false;
                    } else {
                        etName.setError(null);
                        name=true;
                    }
                }
            }
        });
        etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (etUsername.getText().toString().trim()
                            .length() < 6) {

                        etUsername.setError("A Felhasználóneved túl rövid!");
                        username=false;
                    } else {
                        etUsername.setError(null);
                        username=true;
                    }
                }
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (etPassword.getText().toString().trim()
                            .length() < 6) {

                        etPassword.setError("A jelszó túl rövid!");
                        password=false;
                    } else {
                        etPassword.setError(null);
                        password=true;
                    }
                }
            }
        });
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!isEmailValid(etEmail.getText().toString())) {

                        etEmail.setError("Az email cím helytelen!");
                        email=false;
                    } else {
                        etEmail.setError(null);
                        email=true;
                    }
                }
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etEmail.clearFocus();
                etName.clearFocus();
                etUsername.clearFocus();
                etPassword.clearFocus();
                if (name && username && password && email) {
                    bRegister.setError(null);
                    new BackgroundTask().execute();
                }
            }
        });
    }

    class BackgroundTask extends AsyncTask<Void,Void,String> {

        EditText et_username = (EditText) findViewById(R.id.etUsername);
        EditText et_password = (EditText) findViewById(R.id.etPassword);
        EditText et_firstname = (EditText) findViewById(R.id.et_firstname);
        EditText et_lastname = (EditText) findViewById(R.id.et_lastname);
        EditText et_email = (EditText) findViewById(R.id.etEmail);
        String json_url,usernamestring,passwordstring,emailstring,namelast,namefirst,created_atstring;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://szakdolgozat.comxa.com/register.php";
            usernamestring =  et_username.getText().toString();
            passwordstring = et_password.getText().toString();
            emailstring = et_email.getText().toString();
            namelast = et_lastname.getText().toString();
            namefirst = et_firstname.getText().toString();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            created_atstring = sdf.format(c.getTime());

        }

        @Override
        protected String doInBackground(Void... params) {


            try {

                StringBuilder tokenUri=new StringBuilder("username=");
                tokenUri.append(URLEncoder.encode(usernamestring,"UTF-8"));
                tokenUri.append("&password=");
                tokenUri.append(URLEncoder.encode(passwordstring,"UTF-8"));
                tokenUri.append("&email=");
                tokenUri.append(URLEncoder.encode(emailstring,"UTF-8"));
                tokenUri.append("&first_name=");
                tokenUri.append(URLEncoder.encode(namefirst,"UTF-8"));
                tokenUri.append("&last_name=");
                tokenUri.append(URLEncoder.encode(namelast,"UTF-8"));
                tokenUri.append("&created_at=");
                tokenUri.append(URLEncoder.encode(created_atstring,"UTF-8"));


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
          //  String username,email,name,created_at,remember_token;

            while(count<ja.length()){
                JSONObject jo = ja.getJSONObject(count);
                boolean success = jo.getBoolean("success");
                if (success){
                    mydb = new DatabaseHelper(this);
                    int mysqlid = jo.getInt("id");
                    String username = jo.getString("username");
                    String fname = jo.getString("first_name");
                    String lname = jo.getString("last_name");
                    mydb.insertUserDetails(username,mysqlid,fname,lname);


                    Intent loginintent = new Intent(RegisterActivity.this, LoginActivity.class);
                    RegisterActivity.this.startActivity(loginintent);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Sikertelen regisztráció")
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