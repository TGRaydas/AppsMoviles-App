package com.example.tgraydas.billsmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.makeText;

public class LoginActivity extends AppCompatActivity {

    private NetworkManager networkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Context context = getApplicationContext();
        SharedPreferences sharedPreferences;

        final EditText loginEmail = (EditText) findViewById(R.id.login_email);
        final EditText loginPassword = (EditText) findViewById(R.id.login_password);
        final Button loginButton = (Button) findViewById(R.id.login_button);

        sharedPreferences = context.getSharedPreferences(
          "loginpreferences", context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", String.valueOf(loginEmail));
        editor.putString("password", String.valueOf(loginPassword));
        editor.commit();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkManager = NetworkManager.getInstance(getApplicationContext());
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                try {

                    networkManager.login(new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast initialized_message =
                                    Toast.makeText(getApplicationContext(),
                                            "Has iniciado secion :D", Toast.LENGTH_SHORT);

                            initialized_message.show();
                            Intent userAreaIntent = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(userAreaIntent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast initialized_message =
                                    Toast.makeText(getApplicationContext(),
                                            "VOLLEY ERROR: " + error, Toast.LENGTH_SHORT);

                            initialized_message.show();
                            System.out.println(error);
                        }
                    });
                } catch (JSONException e){
                    Toast initialized_message =
                            Toast.makeText(getApplicationContext(),
                                    "ERROR: JSON Exception ->" + e, Toast.LENGTH_SHORT);

                    initialized_message.show();
                    e.printStackTrace();
                }
            }
        });

    }

    public void onLoginClick(View view) {
        try {
            networkManager.login(new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    getProducts();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error);

                }
            });
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void getProducts(){
        networkManager.getProducts(new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                System.out.println(response);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                // TODO: Handle error
                System.out.println(error);
            }
        });
    }
}


