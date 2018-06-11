package com.example.tgraydas.billsmanager;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SplitBillActivity extends AppCompatActivity {
    int deskId;
    int nClients;
    NetworkManager networkManager;
    ProductsGridAdapterForSplit productsGridAdapter;
    int actualClient;
    Map<Integer, ArrayList<Product>> splitedBill = new HashMap<Integer, ArrayList<Product>>();
    ArrayList<Product> allProduct = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_bill);
        networkManager = NetworkManager.getInstance(this);
        deskId = getIntent().getIntExtra("Desk", -1);
        GridView gridView = findViewById(R.id.gridViewProductsForSplit);
        productsGridAdapter = new ProductsGridAdapterForSplit(this, allProduct);
        gridView.setAdapter(productsGridAdapter);
        loadProductsIfHave();
        Button button = (Button) findViewById(R.id.split_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.numbers_input);
                nClients = Integer.parseInt(String.valueOf(editText.getText()));
                actualClient = 1;
                productsGridAdapter.notifyDataSetChanged();
                changeView();
            }
        });
        Button nextClient = (Button) findViewById(R.id.next_client);
        nextClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView actualPerson = (TextView) findViewById(R.id.actual_person);
                actualPerson.setText("Seleccione los productos para el cliente " + Integer.toString(actualClient));
                readyOnClick();
            }
        });

    }

    public void changeView(){
        Button button = (Button) findViewById(R.id.split_button);
        Button next = (Button) findViewById(R.id.next_client);
        TextView textView = (TextView) findViewById(R.id.text_numbers);
        TextView actualPerson = (TextView) findViewById(R.id.actual_person);
        EditText editText = (EditText) findViewById(R.id.numbers_input);
        GridView gridView = (GridView) findViewById(R.id.gridViewProductsForSplit);
        button.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.INVISIBLE);
        actualPerson.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
    }

    public void loadProductsIfHave(){
        networkManager.getBill(new Response.Listener<JSONObject>() {
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
                        allProduct.add(product);
                    }

                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, deskId);

    }
    public void productAdded(int position){
        Product product = allProduct.get(position);
        ArrayList<Product> products = splitedBill.get(actualClient);
        if (products == null){
            products = new ArrayList<>();
        }
        products.add(product);
        splitedBill.put(actualClient, products);
        allProduct.remove(position);
        productsGridAdapter.notifyDataSetChanged();
    }
    public void readyOnClick(){
        if (nClients == actualClient){
            Intent intent = new Intent(this, ShowSplited.class);
            intent.putExtra("Data", (Serializable) splitedBill);
            intent.putExtra("nClients", nClients);
            startActivity(intent);
        }
        else{
            actualClient += 1;
            TextView actualPerson = (TextView) findViewById(R.id.actual_person);
            actualPerson.setText("Seleccione los productos para el cliente " + Integer.toString(actualClient));
        }
    }
}
