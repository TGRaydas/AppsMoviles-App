package com.example.tgraydas.billsmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private NetworkManager networkManager;
    private SharedPreferences sharedPreferences;
    private String[] mDrawerMenu;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkManager = NetworkManager.getInstance(this);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        /* Navigation Drawer */
        mDrawerMenu = getResources().getStringArray(R.array.drawer_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerMenu));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        /*  Login */
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

        /* Logout */
        Button logoutButton = (Button) findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("token");
                editor.commit();
                Intent goToFormsIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(goToFormsIntent);
            }
        });

    }

    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = new OrderTableFragment();
        Bundle args = new Bundle();
        args.putInt(OrderTableFragment.ARG_TABLE_NUMBER, position);
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        //FragmentManager fragmentManager = getFragmentManager();
        //fragmentManager.beginTransaction()
        //        .replace(R.id.content_frame, fragment)
        //        .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mDrawerMenu[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        //mTitle = title;
        //getActionBar().setTitle(mTitle);
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

    public void getProducts()
    {
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
}
