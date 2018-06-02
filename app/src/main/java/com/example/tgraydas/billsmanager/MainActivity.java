package com.example.tgraydas.billsmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener,
        AllTablesFragment.takeTable
{

    private NetworkManager networkManager;
    private SharedPreferences sharedPreferences;
    private TextView navHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        networkManager = NetworkManager.getInstance(this);
        Button button = findViewById(R.id.button);
        ArrayList<Product> productList = new ArrayList<>();
        getProducts(productList);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BillClients.class);

                startActivity(intent);
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
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null){
            if(savedInstanceState != null){
                return;
            }
        }


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
        setTitle("Bills Manager");


    }
    public void killBill(Bill bill){

    }

    public void createBill(final ArrayList<Product> products, final Desk desk, final ArrayList<Bill> bill) throws JSONException {
        networkManager.createBill(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    Bill bills = new Bill(response.getInt("id"), desk, products);
                    bill.add(bills);
                } catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, products, desk);
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

    public void getProducts(final ArrayList<Product> productList) {
        networkManager.getProducts(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.optJSONArray("0");
                    for (int i = 0; i < data.length(); i++){
                        int id = data.getJSONObject(i).optInt("id");
                        String name = data.getJSONObject(i).optString("name");
                        int price = data.getJSONObject(i).optInt("price");
                        String detail = data.getJSONObject(i).optString("detail");
                        Product product = new Product(id, price, name, detail);
                        productList.add(product);
                    }

                } catch (JSONException e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
    }

    public void getDesks(final ArrayList<Desk> deskList, final AllTablesFragment fragment){
        networkManager.getDesks(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray data = response.optJSONArray("0");
                for (int i = 0; i < data.length(); i++) {
                    try {
                        int id = data.getJSONObject(i).optInt("id");
                        int number = data.getJSONObject(i).optInt("number");
                        Desk desk = new Desk(id, number);
                        deskList.add(desk);
                    } catch (JSONException e) {

                    }
                }
                fragment.populateAllTables(deskList);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(findViewById(R.id.fragment_container) != null){
            if (id == R.id.nav_all_tables) {
                AllTablesFragment allTablesFragment = new AllTablesFragment();
                allTablesFragment.setArguments(getIntent().getExtras());
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment_container, allTablesFragment, allTablesFragment.toString());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                ArrayList<Desk> tables = new ArrayList<>();
                getDesks(tables, allTablesFragment);
            } else if (id == R.id.nav_waiter_tables) {
                MyTablesFragment myTablesFragment = new MyTablesFragment();
                myTablesFragment.setArguments(getIntent().getExtras());
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment_container, myTablesFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else if (id == R.id.nav_logout) {
                    /* LOGOUT */
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("token");
                editor.commit();
                Intent goToLoginIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(goToLoginIntent);
                finish();
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void takeTableListener(Desk desk) {
        Intent goToOrderWithTable = new Intent(getApplicationContext(), BillClients.class);
        goToOrderWithTable.putExtra("Desk", desk.id);
        startActivity(goToOrderWithTable);
    }
}
