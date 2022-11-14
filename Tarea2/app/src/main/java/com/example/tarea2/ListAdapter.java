package com.example.tarea2;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea2.models.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    FirebaseStorage storage;
    StorageReference storageReference;
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
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false),
                recyclerViewInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        final long ONE_MB = 3024*3024;
        if (data != null) {
            StorageReference path = storageReference.child("images/"+data.get(position).getUuid());
            path.getBytes(ONE_MB).addOnSuccessListener(bytes -> {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.img.setImageBitmap(bm);
            });
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
        ImageView img;
        public ViewHolder (@NonNull View itemView, RecyclerViewInterface recyclerViewInterface){
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            desc = itemView.findViewById(R.id.productDescription);
            price = itemView.findViewById(R.id.productPrice);
            img = itemView.findViewById(R.id.productImage);

            //img.setImageBitmap();

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
