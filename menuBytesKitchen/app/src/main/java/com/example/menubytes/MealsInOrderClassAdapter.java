package com.example.menubytes;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.*;
        import java.util.*;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;

public class MealsInOrderClassAdapter extends ArrayAdapter<MealsInOrderClassList> {

    private Context mContext;
    private int mResource;

    public MealsInOrderClassAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MealsInOrderClassList> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource,parent,false);

        TextView txtViewMealQty = convertView.findViewById(R.id.txtMealQty);
        txtViewMealQty.setText(getItem(position).getOrderQty());
        TextView txtViewMealName = convertView.findViewById(R.id.txtMealName);
        txtViewMealName.setText(getItem(position).getOrderName());
        TextView txtAddOns = convertView.findViewById(R.id.txtAddOns);
        if(getItem(position).isHas_addons()){
            txtAddOns.setText("Shawarma All Meat");
        }else{
            if(getItem(position).getFlavors()!=null){
                txtAddOns.setText(getItem(position).getFlavors());
            }
            else {
                txtAddOns.setText("");
            }
        }
        return convertView;
    }
}
