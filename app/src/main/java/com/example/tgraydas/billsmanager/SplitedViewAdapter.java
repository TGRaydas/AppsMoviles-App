package com.example.tgraydas.billsmanager;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tgraydas.billsmanager.Product;
import com.example.tgraydas.billsmanager.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SplitedViewAdapter extends BaseAdapter {
    private final Context mContext;
    private Map<Integer, ArrayList<Product>> splitedBill;
    private LayoutInflater layoutInflater;

    // 1
    public SplitedViewAdapter(Context context, Map<Integer, ArrayList<Product>> splitedBill) {
        this.mContext = context;
        this.splitedBill = splitedBill;
    }

    // 2
    @Override
    public int getCount() {
        return splitedBill.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = layoutInflater.inflate(R.layout.client_products_splited, null);
            TextView textView = gridView.findViewById(R.id.user_number2);
            textView.setText("Usuario " + Integer.toString(position + 1));
            textView = gridView.findViewById(R.id.price_total);
            int total_price = 0;
            for (int i = 0; i < splitedBill.get(position + 1).size(); i++){
                total_price += splitedBill.get(position + 1).get(i).price;
            }
            String price_info = "Price: $" + Integer.toString(total_price);
            textView.setText(price_info);
            Spinner productsList = (Spinner) gridView.findViewById(R.id.products_list_in_splited);
            ProductInSplitAdapter productInSplitAdapter = new ProductInSplitAdapter(mContext, R.id.products_list_in_splited, splitedBill.get(position + 1));
            productsList.setAdapter(productInSplitAdapter);
            return gridView;

        }
        else {
            TextView textView = gridView.findViewById(R.id.user_number2);
            textView.setText("Usuario " + Integer.toString(position + 1));
            textView = gridView.findViewById(R.id.price_total);
            int total_price = 0;
            for (int i = 0; i < splitedBill.get(position + 1).size(); i++){
                total_price += splitedBill.get(position + 1).get(i).price;
            }
            String price_info = "Price: $" + Integer.toString(total_price);
            textView.setText(price_info);
            return convertView;
        }
    }
}

