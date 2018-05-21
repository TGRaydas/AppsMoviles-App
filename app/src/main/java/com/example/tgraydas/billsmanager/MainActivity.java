package com.example.tgraydas.billsmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    NetworkManager networkManager;
    SharedPreferences sharedPreferences;
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

        sharedPreferences = getApplicationContext().getSharedPreferences(
                getString(R.string.loginpreferences), Context.MODE_PRIVATE);

        String token = sharedPreferences.getString("token", "");
        System.out.println(token);

        if(Objects.equals(token, "")) {
            Intent goToLoginIntent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(goToLoginIntent);
            MainActivity.this.finish();
        }

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
