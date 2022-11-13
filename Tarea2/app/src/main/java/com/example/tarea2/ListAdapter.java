package com.example.tarea2;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea2.models.Product;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    ArrayList<Product> data;

    public ListAdapter(ArrayList<Product> data) {
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.desc.setText(data.get(position).getDescription());
        holder.price.setText("$ "+data.get(position).getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // aquí se cambia el image shit
        TextView name, desc, price;
        public ViewHolder (@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            desc = itemView.findViewById(R.id.productDescription);
            price = itemView.findViewById(R.id.productPrice);
        }
    }
}
