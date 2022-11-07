package com.example.menubytes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OrderQueueAdapter extends ArrayAdapter<OrderQueueClassList>{

    private Context mContext;
    private int mResource;

    public OrderQueueAdapter(@NonNull Context context, int resource, @NonNull ArrayList<OrderQueueClassList> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource,parent,false);

        TextView txtViewOrderNum = convertView.findViewById(R.id.txtOrderNumber);
        txtViewOrderNum.setText(getItem(position).getOrderNum());
        TextView txtViewTableNum = convertView.findViewById(R.id.txtTableNumber);
        txtViewTableNum.setText(getItem(position).getTableNum());
        TextView txtViewPendingOrderQty = convertView.findViewById(R.id.txtPendingOrderQty);
        txtViewPendingOrderQty.setText(getItem(position).getOrderQty());
        TextView txtStatus = convertView.findViewById(R.id.txtStatus);
        txtStatus.setText(getItem(position).getOrderStatus());

        return convertView;
    }
}
