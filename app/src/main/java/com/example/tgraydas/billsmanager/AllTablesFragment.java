package com.example.tgraydas.billsmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.tgraydas.billsmanager.dummy.DummyContent;
import com.example.tgraydas.billsmanager.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

public class AllTablesFragment extends Fragment{

    private Context context;

    private OnListFragmentInteractionListener mListener;

    public AllTablesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tables, container, false);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyItem item);
    }

    public void populateAllTables(ArrayList<Desk> tables){
        ListAdapter listAdapter = new TablesListAdapter(this, context, tables);
        ListView tablesListView = getView().findViewById(R.id.tables_list_view);
        tablesListView.setAdapter(listAdapter);
    }
}
