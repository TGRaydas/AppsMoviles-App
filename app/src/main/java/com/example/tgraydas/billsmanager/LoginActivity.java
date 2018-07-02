package com.example.tgraydas.billsmanager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.makeText;

public class LoginActivity extends AppCompatActivity {

    private NetworkManager networkManager;
    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginButton;
    private ProgressBar loginProgressBar;
    SharedPreferences sharedPreferences;

    @SuppressLint("ApplySharedPref")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Context context = getApplicationContext();

        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_button);
        loginProgressBar = (ProgressBar) findViewById(R.id.login_progressBar);

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
                final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
                progress.setTitle("Loading");
                progress.setMessage("Wait while loading...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();
                networkManager = NetworkManager.getInstance(getApplicationContext());
                try {
                    networkManager.login(new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("token", response.getString("Authenticate"));
                                editor.commit();
                                Toast initialized_message =
                                        Toast.makeText(getApplicationContext(),
                                                "Has iniciado sesion :D", Toast.LENGTH_SHORT);
                                initialized_message.show();

                                Intent userAreaIntent = new Intent(LoginActivity.this, MainActivity.class);
                                LoginActivity.this.startActivity(userAreaIntent);
                                finish();
                                progress.dismiss();
                            } catch (JSONException e){

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", "");
                            editor.putString("password", "");
                            editor.putString("token", "");
                            editor.commit();
                            Toast initialized_message =
                                    Toast.makeText(getApplicationContext(),
                                            "VOLLEY ERROR: " + error, Toast.LENGTH_SHORT);
                            initialized_message.show();
                            System.out.println(error);
                        }
                    });
                } catch (JSONException e){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", "");
                    editor.putString("password", "");
                    editor.putString("token", "");
                    editor.commit();
                    Toast initialized_message =
                            Toast.makeText(getApplicationContext(),
                                    "ERROR: JSON Exception ->" + e, Toast.LENGTH_SHORT);
                    initialized_message.show();
                    e.printStackTrace();
                    progress.dismiss();
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


