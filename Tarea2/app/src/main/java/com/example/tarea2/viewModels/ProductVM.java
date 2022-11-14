package com.example.tarea2.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.tarea2.models.Product;
import com.example.tarea2.repositories.ProductRepository;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductVM extends AndroidViewModel {

    private ProductRepository productRepository;
    private LiveData<List<Product>> products;


    public ProductVM(@NonNull @NotNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        products = productRepository.getProducts();
    }

    public LiveData<List<Product>> getAllProducts(){
        return products;
    }

    public void insert(Product product){
        productRepository.insert(product);
    }
    public void update(Product product){
        productRepository.update(product);
    }
    public void delete(Product product) { productRepository.delete(product); }
    public Product findById(Integer product) throws ExecutionException, InterruptedException {
        return productRepository.getProduct(product);
    }
}
