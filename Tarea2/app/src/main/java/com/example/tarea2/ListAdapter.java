package com.example.tarea2;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea2.models.Product;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    List<Product> data;
    private final RecyclerViewInterface recyclerViewInterface;

    public ListAdapter(List<Product> data, RecyclerViewInterface recyclerViewInterface) {
        this.data = data;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false), recyclerViewInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        if (data != null) {
            holder.name.setText(data.get(position).getName());
            holder.desc.setText(data.get(position).getBrand());
            holder.price.setText("$ "+data.get(position).getPrice().toString());
        }
    }

    public void setProducts(List<Product> products){
        data = products;
        notifyDataSetChanged();
    }

    public List<Product> getProducts() {
        return data;
    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // aquí se le pone el image shit
        TextView name, desc, price;
        Button edit;
        public ViewHolder (@NonNull View itemView, RecyclerViewInterface recyclerViewInterface){
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            desc = itemView.findViewById(R.id.productDescription);
            price = itemView.findViewById(R.id.productPrice);

            edit = itemView.findViewById(R.id.editBtn);
            edit.setOnClickListener(view -> {
                if(recyclerViewInterface != null){
                    int pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onEditClick(pos);
                    }
                }
            });

        }
    }
}
