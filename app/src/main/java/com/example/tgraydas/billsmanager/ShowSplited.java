package com.example.tgraydas.billsmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowSplited extends AppCompatActivity {

    private NetworkManager networkManager;
    public Map<Integer, ArrayList<Product>> splitedBill;
    private SplitedViewAdapter splitedViewAdapter;
    private int Desk_id;
    public int totalPrice = 0;
    public int arePaying = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_splited);
        Intent intent = getIntent();
        Desk_id = intent.getExtras().getInt("desk_id");
        networkManager = NetworkManager.getInstance(this);
        splitedBill = (Map<Integer, ArrayList<Product>>) intent.getSerializableExtra("Data");
        for (Map.Entry<Integer, ArrayList<Product>> entry : splitedBill.entrySet()){
            ArrayList<Product> products = entry.getValue();
            for (int i = 0; i < products.size(); i++){
                arePaying += products.get(i).price;
            }
        }
        GridView userProducts = findViewById(R.id.splited_view_grid);
        splitedViewAdapter = new SplitedViewAdapter(this, splitedBill);
        loadProductsIfHave(findViewById(R.id.messege));
        userProducts.setAdapter(splitedViewAdapter);
        splitedViewAdapter.notifyDataSetChanged();
        Button button = findViewById(R.id.finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payed();
            }
        });
    }

    public void payed(){
        try{
            networkManager.killBill(new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }}, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, Desk_id);
        } catch (JSONException e){

        }
    }

    public void loadProductsIfHave(final View view){
        networkManager.getBill(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.optJSONArray("0");
                    for (int i = 0; i < data.length(); i++){
                        int price = data.getJSONObject(i).optInt("price");
                        totalPrice += price;
                    }

                } catch (JSONException e) {

                }
                TextView textView = (TextView) view;
                if (totalPrice == arePaying){
                    textView.setText("Perfecto, te estan pagando todo!");
                    textView.setTextColor(Color.parseColor("#167c06"));
                }
                else {
                    textView.setText("Cuidado, te deberían pagar " + Integer.toString(totalPrice) + " y te estan pagando " + Integer.toString(arePaying));
                    textView.setTextColor(Color.parseColor("#c60303"));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, Desk_id);
    }
}
