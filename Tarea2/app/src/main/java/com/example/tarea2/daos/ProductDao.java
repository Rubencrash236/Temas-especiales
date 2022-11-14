package com.example.tarea2.daos;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.tarea2.models.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insertProduct(Product product);

    @Query("SELECT * FROM products")
    List<Product> getAllProducts();

    @Query("SELECT * FROM products WHERE id = :id")
    Product findProductById(int id);

    @Delete
    void deleteProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Insert
    void insertMany(List<Product> products);

    @Query("SELECT * FROM products")
    LiveData<List<Product>> findAllProducts();
}
