package com.example.tgraydas.billsmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowSplited extends AppCompatActivity {
    public Map<Integer, ArrayList<Product>> splitedBill;
    private SplitedViewAdapter splitedViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_splited);
        Intent intent = getIntent();
        splitedBill = (Map<Integer, ArrayList<Product>>) intent.getSerializableExtra("Data");
        GridView userProducts = findViewById(R.id.splited_view_grid);
        splitedViewAdapter = new SplitedViewAdapter(this, splitedBill);
        userProducts.setAdapter(splitedViewAdapter);
        splitedViewAdapter.notifyDataSetChanged();

    }
}
