package com.example.tgraydas.billsmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowSplited extends AppCompatActivity {
    Map<Integer, ArrayList<Product>> splitedBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_splited);
        Intent intent = getIntent();
        splitedBill = (Map<Integer, ArrayList<Product>>) intent.getSerializableExtra("Data");
        System.out.println(splitedBill);
    }
}
