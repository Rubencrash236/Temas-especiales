package com.example.examen1_ruben_osmani;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.examen1_ruben_osmani.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Product> products;
    private ActivityMainBinding binding;
    private ProductAdapter productAdapter;
    Button registerBtn, deleteBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(products == null){
            products = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                Product product = new Product("Product" + i,"This is a description", (i+1)*200d);
                products.add(product);
            }
        }
        registerBtn = findViewById(R.id.createBtn);
        registerBtn.setOnClickListener(this::registerClick);




        RecyclerView recyclerView = binding.productsViewer;
        recyclerView.setAdapter(new ProductAdapter(products));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        productAdapter = (ProductAdapter) recyclerView.getAdapter();
    }

    public void registerClick(View view){
        Intent intent = new Intent(this, RegisterProduct.class);
        startActivity(intent);
    }

    public void delete(){

    }
}