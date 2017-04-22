package com.example.handesen.sqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String remember_token ="";
    public static String username="";
    boolean vanid = false;
    public static int carid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());
        String restoredText = prefs.getString("username", null);
        String license = prefs.getString("license_plate", null);

        if (restoredText != null &&license != null)
        {
            username=restoredText;
            carid = prefs.getInt("carid", -1);
            if (carid !=-1) {
                vanid = true;
            }

        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        remember_token = intent.getStringExtra("remember_token");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (vanid){

            View headerView = navigationView.getHeaderView(0);
            TextView tv = (TextView) headerView.findViewById(R.id.header_tv1);
            TextView tv2 = (TextView) headerView.findViewById(R.id.header_tv2);
            tv.setText("Kiválasztott autó ID: "+ String.valueOf(carid));
            tv2.setText(license);
        }



        navigationView.setNavigationItemSelectedListener(this);








    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_garage) {
            Intent garageintent = new Intent(MainActivity.this, CarChangeActivity.class);
            MainActivity.this.startActivity(garageintent);
        } else if (id == R.id.nav_service) {
            Intent garageintent = new Intent(MainActivity.this, ServiceActivity.class);
            MainActivity.this.startActivity(garageintent);

        } else if (id == R.id.nav_serviceadd) {
            Intent garageintent = new Intent(MainActivity.this, CarChangeActivity.class);
            MainActivity.this.startActivity(garageintent);

        } else if (id == R.id.nav_caradd) {
            Intent caraddintent = new Intent(MainActivity.this, NewCar.class);
            MainActivity.this.startActivity(caraddintent);

        } else if (id == R.id.nav_share) {
            Intent carchange = new Intent(MainActivity.this, CarChangeActivity.class);
            MainActivity.this.startActivity(carchange);

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
