package com.example.tgraydas.billsmanager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProductInSplitAdapter extends ArrayAdapter<Product> {
    private final Context mContext;
    private ArrayList<Product> myObjs;
    private LayoutInflater mInflater;
    private int mResource;
    // 1
    public ProductInSplitAdapter(Context context, int textViewResourceId, ArrayList<Product> products) {
        super(context, textViewResourceId, products);
        this.mContext = context;
        this.myObjs = products;
        this.mInflater = LayoutInflater.from(context);
        this.mResource = textViewResourceId;
    }

    public int getCount(){
        return myObjs.size();
    }

    public Product getItem(int position){
        return myObjs.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = mInflater.inflate(mResource, parent, false);
        View data_view = mInflater.inflate(R.layout.product_in_splited_view, null);
        TextView tw1 = data_view.findViewById(R.id.name);
        TextView tw2 = data_view.findViewById(R.id.price);
        tw1.setText(getItem(position).name);
        tw2.setText(Integer.toString(getItem(position).price));
        return view;
    }
    public View getCustomView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);

        LayoutInflater inflater = mInflater;
        View row = inflater.inflate(R.layout.product_in_splited_view, parent, false);
        TextView label=(TextView)row.findViewById(R.id.name);
        label.setText(getItem(position).name);
        TextView price = (TextView) row.findViewById(R.id.price);
        price.setText("Price: $" + Integer.toString(getItem(position).price));
        return row;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

}

