package com.example.tgraydas.billsmanager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProductsGridAdapter extends BaseAdapter {
    private final Context mContext;
    private ArrayList<Product> products;
    private LayoutInflater layoutInflater;

    // 1
    public ProductsGridAdapter(Context context, ArrayList<Product> products) {
        this.mContext = context;
        this.products = products;
    }

    // 2
    @Override
    public int getCount() {
        return products.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        Product product = products.get(position);
        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = layoutInflater.inflate(R.layout.bill_product_grid, null);
            TextView textView = gridView.findViewById(R.id.text_name);
            textView.setText(product.name);
            textView = gridView.findViewById(R.id.text_detail);
            textView.setText(product.detail);
            textView = gridView.findViewById(R.id.price);
            String price_info = "Price: $" + Integer.toString((product.price));
            textView.setText(price_info);
            return gridView;

        }
        else {
            TextView textView = convertView.findViewById(R.id.text_name);
            textView.setText(product.name);
            textView = convertView.findViewById(R.id.text_detail);
            textView.setText(product.detail);
            return convertView;
        }
    }

}

