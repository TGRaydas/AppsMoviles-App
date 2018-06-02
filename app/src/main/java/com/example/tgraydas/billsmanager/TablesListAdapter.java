package com.example.tgraydas.billsmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class TablesListAdapter extends ArrayAdapter<Desk> {

    private  AllTablesFragment allTablesFragment;

    public TablesListAdapter(@NonNull AllTablesFragment allTablesFragment, Context context, @NonNull List<Desk> tables) {
        super(context, R.layout.table_line, tables);
        this.allTablesFragment = allTablesFragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder") View tableLineView = inflater.inflate(R.layout.table_line,
                parent, false);

        final Desk desk = getItem(position);
        TextView name = (TextView) tableLineView.findViewById(R.id.name_table_line_tv);
        Button takeOrder = (Button) tableLineView.findViewById(R.id.take_order_table_line_button);

        assert desk != null;
        name.setText(String.format("Mesa %s", ((Integer) desk.getNumber()).toString()));

        takeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* la funcion del fragment por medio de una interfaz */
                Toast initialized_message =
                        Toast.makeText(getContext(),
                                "Initialize TakeOrderActivity", Toast.LENGTH_SHORT);
                initialized_message.show();
                allTablesFragment.getTakeTableListener().takeTableListener(desk);

            }
        });

        return tableLineView;
    }


}

