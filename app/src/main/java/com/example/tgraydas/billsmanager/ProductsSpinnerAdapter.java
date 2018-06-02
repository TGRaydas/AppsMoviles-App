package com.example.tgraydas.billsmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProductsSpinnerAdapter extends ArrayAdapter<Product> {
    private Context context;
    private ArrayList<Product> myObjs;
    private LayoutInflater mInflater;
    private int mResource;
    public ProductsSpinnerAdapter(Context context, int textViewResourceId,
                                  ArrayList<Product> myObjs) {
        super(context, textViewResourceId, myObjs);
        this.context = context;
        this.myObjs = myObjs;
        this.mInflater = LayoutInflater.from(context);
        mResource = textViewResourceId;
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
        View data_view = mInflater.inflate(R.layout.spinner_product_info, null);
        TextView tw1 = data_view.findViewById(R.id.name_spinner_product);
        TextView tw2 = data_view.findViewById(R.id.id_spinner_product);
        tw1.setText(getItem(position).name);
        tw2.setText(getItem(position).id);
        return view;
    }
    public View getCustomView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);

        LayoutInflater inflater = mInflater;
        View row = inflater.inflate(R.layout.spinner_product_info, parent, false);
        TextView label=(TextView)row.findViewById(R.id.name_spinner_product);
        label.setText(getItem(position).name);
        label = row.findViewById(R.id.id_spinner_product);
        return row;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}

