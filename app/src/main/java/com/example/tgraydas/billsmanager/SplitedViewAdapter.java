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
import android.widget.ImageView;
import android.widget.ListView;
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
        mContext = context;
        splitedBill = splitedBill;
    }

    // 2
    @Override
    public int getCount() {
        return 0;
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
        View listItem = convertView;
        if(listItem == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItem = layoutInflater.inflate(R.layout.client_products_splited, null);
        }
        ListView listView = (ListView) listItem.findViewById(R.id.product_client_list);
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, R.layout.product_in_splited_view, R.id.name,);
        TextView textView = (TextView) listItem.findViewById(R.id.user_number2);
        TextView price = (TextView) listItem.findViewById(R.id.price_total);
        textView.setText("Usuario " + splitedBill.get(position));
        int total = 0;
        for(int i = 0; i < splitedBill.get(position).size(); i++){
            total += splitedBill.get(position).get(i).price;
        }
        price.setText("$ " + Integer.toString(total));

        return listItem;

    }

}

