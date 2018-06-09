package com.example.tgraydas.billsmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;


public class MyTablesFragment extends Fragment {
    public static final String FRAGMENT = "mytables";
    private Context context;

    private String mTableNumber;
    private takeMyTable takeTableListener;


    public MyTablesFragment() {
        // Required empty public constructor
    }



    public static MyTablesFragment newInstance() {
        MyTablesFragment fragment = new MyTablesFragment();
        return fragment;
    }
    public takeMyTable getTakeMyTableListener() {
        return takeTableListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_tables, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof  takeMyTable){
            takeTableListener = (takeMyTable) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }




    public void populateMyTables(List<Desk> tables){
        ListAdapter listAdapter = new MyTablesListAdapter(this, context, tables);
        ListView tablesListView = getView().findViewById(R.id.my_tables_list_view);
        tablesListView.setAdapter(listAdapter);
    }

    /* Take Table Interface */
    public interface takeMyTable{
        void takeMyTableListener(Desk desk);
    }

}
