package com.example.tarea2.repositories;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.tarea2.daos.ProductDao;
import com.example.tarea2.database.ProductDB;
import com.example.tarea2.models.Product;
import lombok.Getter;

import java.util.List;

public class ProductRepository {
    private ProductDao productDao;
    @Getter
    private final LiveData<List<Product>> products;

    public ProductRepository(Application application){
        ProductDB db = ProductDB.getInstance(application);
        productDao = db.productDao();
        products = productDao.findAllProducts();
    }

    public void insert(Product product){
        new insertAsyncTask(productDao).execute(product);
    }

    private static class insertAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao asyncProdDao;

        insertAsyncTask(ProductDao asyncProdDao){
            this.asyncProdDao = asyncProdDao;
        }

        @Override
        protected Void doInBackground(final Product... products) {
            asyncProdDao.insertProduct(products[0]);
            return null;
        }
    }
}
