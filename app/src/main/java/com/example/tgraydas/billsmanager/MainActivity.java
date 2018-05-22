package com.example.tgraydas.billsmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static com.example.tgraydas.billsmanager.R.string.navigation_drawer_close;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener{
    private NetworkManager networkManager;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        networkManager = NetworkManager.getInstance(this);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        /* NAVIGATION DRAWER */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /*  LOGIN */
        sharedPreferences = getApplicationContext().getSharedPreferences(
                getString(R.string.loginpreferences), Context.MODE_PRIVATE);

        String token = sharedPreferences.getString("token", "");
        System.out.println(token);

        if(Objects.equals(token, "")) {
            /* descomentar cuando este listo el login */
            Intent goToLoginIntent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(goToLoginIntent);
            MainActivity.this.finish();
            Toast initialized_message =
                    Toast.makeText(getApplicationContext(),
                            "Debes Iniciar Sesion!" + ("\ud83d\ude01"), Toast.LENGTH_SHORT);

            initialized_message.show();
        }
    }

    public void login(){
        try{
            networkManager.login(new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }catch (JSONException e){

        }
    }

    public void getProducts() {
        networkManager.getProducts(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        Fragment goToFragment = null;
        boolean selectedFragment = false;

        if(id == R.id.nav_all_tables){
            goToFragment = new TablesFragment();
            selectedFragment = true;
        } else if (id == R.id.nav_waiter_tables){
            goToFragment = new OrderTableFragment();
            selectedFragment = true;
        } else if (id == R.id.nav_logout){
            /* LOGOUT */
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("token");
            editor.commit();
            Intent goToLoginIntent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(goToLoginIntent);
            finish();
        }

        if (selectedFragment){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, goToFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
