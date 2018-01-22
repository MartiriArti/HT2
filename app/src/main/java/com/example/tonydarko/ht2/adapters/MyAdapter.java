package com.example.tonydarko.ht2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.tonydarko.ht2.R;
import com.example.tonydarko.ht2.model.Product;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    LinkedList<Product> items;
    Context context;

    public MyAdapter(LinkedList<Product> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context)
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder  extends  RecyclerView.ViewHolder{
        @BindView(R.id.tv_product)
         TextView product;
        @BindView(R.id.tv_count)
         TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Product item) {
            product.setText(item.getTitle());
            count.setText(Integer.toString(item.getCount()));
        }
    }

}
