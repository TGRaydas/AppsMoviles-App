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

import com.example.tgraydas.billsmanager.dummy.DummyContent;
import com.example.tgraydas.billsmanager.dummy.DummyContent.DummyItem;

public class AllTablesFragment extends Fragment{

    private OnListFragmentInteractionListener mListener;
    public AllTablesFragment() {

    }

    @SuppressWarnings("unused")
    public static AllTablesFragment newInstance(int columnCount) {
        AllTablesFragment fragment = new AllTablesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_tables_list, container, false);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyItem item);
    }
}
