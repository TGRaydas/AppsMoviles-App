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

    private OnMyTablesFragmentInteractionListener mListener;

    public MyTablesFragment() {
        // Required empty public constructor
    }


    public static MyTablesFragment newInstance() {
        MyTablesFragment fragment = new MyTablesFragment();
        ;
        return fragment;
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMyTablesFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMyTablesFragmentInteractionListener) {
            this.mListener = (OnMyTablesFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnMyTablesFragmentInteractionListener {
        void onMyTablesFragmentInteraction(Uri uri);
    }

    public void populateMyTables(List<Desk> tables){
        //ListAdapter listAdapter = new TablesListAdapter(this, context, tables);
        //ListView tablesListView = getView().findViewById(R.id.tables_list_view);
        //tablesListView.setAdapter(listAdapter);
    }

}
