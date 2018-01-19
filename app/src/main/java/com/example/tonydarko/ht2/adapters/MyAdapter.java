package com.example.tonydarko.ht2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.tonydarko.ht2.R;
import com.example.tonydarko.ht2.model.Product;

import java.util.LinkedList;

public class MyAdapter extends ArrayAdapter<Product> {
    public MyAdapter(Context context, LinkedList<Product> objects) {
        super(context, R.layout.item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item, parent, false);
            holder = new ViewHolder();
            holder.count = rowView.findViewById(R.id.count);
            holder.product =  rowView.findViewById(R.id.product);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        Product product = getItem(position);
        holder.product.setText(product.getTitle());
        holder.count.setText(String.valueOf(product.getCount()));

        return rowView;
    }

    class ViewHolder {
         TextView product;
         TextView count;
    }
}
