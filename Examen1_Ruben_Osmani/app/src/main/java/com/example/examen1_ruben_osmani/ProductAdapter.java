package com.example.examen1_ruben_osmani;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    ArrayList<Product> products;

    public ProductAdapter(ArrayList<Product> products){
        this.products = products;
    }



    @NonNull
    @NotNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_blob, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.getpName().setText(product.getName());
        holder.getpDescription().setText(product.getDescription());
        holder.getpPrice().setText(product.getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
