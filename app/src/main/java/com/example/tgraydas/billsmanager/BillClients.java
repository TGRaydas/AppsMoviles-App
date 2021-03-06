package com.example.tgraydas.billsmanager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BillClients extends AppCompatActivity {
    private static final String TAG = "Y EL NUMERO ES.....!!!";
    NetworkManager networkManager;
    ProductsGridAdapter productsGridAdapter;
    ProductsSpinnerAdapter productsSpinnerAdapter;
    ArrayList<Product> billProductList = new ArrayList<>();
    ArrayList<Product> productList = new ArrayList<>();
    int Desk_id;
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_clients);
        networkManager = NetworkManager.getInstance(this);
        GridView gridView = findViewById(R.id.gridViewProducts);
        final Spinner spinner = findViewById(R.id.spinner);
        getProducts(productList, gridView, spinner);
        productsGridAdapter = new ProductsGridAdapter(this, billProductList);
        gridView.setAdapter(productsGridAdapter);
        Button button = findViewById(R.id.Add_product_to_bill);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToBill(spinner, productsGridAdapter);
            }
        });
        final int extras = getIntent().getIntExtra("Desk", -1);
        Desk_id = getIntent().getIntExtra("Desk", -1);
        Log.d(TAG, "onCreate: " + extras);
        TextView tablesBill = (TextView) findViewById(R.id.table_name_bills_client_tx);
        tablesBill.setText(String.format("Mesa %d", extras));
        loadProductsIfHave();
        button = findViewById(R.id.create_bill_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createBill(billProductList, extras);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        button = findViewById(R.id.lets_pay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                letsSplit();
            }
        });
        button = findViewById(R.id.update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // payed();
                try {
                    updateBill(billProductList, extras, false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getProducts(final ArrayList<Product> productList, final GridView gridView, final Spinner spinner) {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        networkManager.getProducts(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.optJSONArray("0");
                    for (int i = 0; i < data.length(); i++){
                        System.out.println( data.getJSONObject(i));
                        int id = data.getJSONObject(i).optInt("id");
                        String name = data.getJSONObject(i).optString("name");
                        int price = data.getJSONObject(i).optInt("price");
                        String detail = data.getJSONObject(i).optString("detail");
                        URL url;
                        try {
                            url = new URL(new URL(networkManager.BASE_URL), data.getJSONObject(i).optString("img_url"));
                            Product product = new Product(id, price, name, detail, url);
                            productList.add(product);
                        }catch (MalformedURLException e){

                        }
                    }
                    productsSpinnerAdapter = new ProductsSpinnerAdapter(getApplicationContext(), R.id.spinner ,productList);
                    spinner.setAdapter(productsSpinnerAdapter);
                    progress.dismiss();

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

    public void addProductToBill(Spinner spinner, ProductsGridAdapter productsGridAdapter){
        billProductList.add(0, (Product) spinner.getSelectedItem());
        productsGridAdapter.notifyDataSetChanged();

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
                        URL url;
                        try {
                            url = new URL(new URL(networkManager.BASE_URL), data.getJSONObject(i).optString("img_url"));
                            Product product = new Product(id, price, name, detail, url);
                            billProductList.add(product);
                            System.out.println("Se agrego");
                        }catch (MalformedURLException e){

                        }
                    }
                    Button button =  findViewById(R.id.create_bill_button);
                    button.setVisibility(View.INVISIBLE);
                    productsGridAdapter.notifyDataSetChanged();

                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Button button =  findViewById(R.id.lets_pay);
                button.setVisibility(View.INVISIBLE);
            }
        }, Desk_id);

    }

    public void removeProductFromBill(int position){
        billProductList.remove(position);
        productsGridAdapter.notifyDataSetChanged();
    }

    public void createBill(final ArrayList<Product> products, int desk) throws JSONException {
        networkManager.createBill(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, products, desk);
    }

    public void updateBill(final ArrayList<Product> products, int desk, final boolean justCall) throws JSONException {
        networkManager.updateBill(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (!justCall) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), SplitBillActivity.class);
                    intent.putExtra("Desk", Desk_id);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, products, desk);
    }

    public void letsSplit(){
        try {
            final ProgressDialog progress = new ProgressDialog(this);
            updateBill(billProductList, Desk_id, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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


}
