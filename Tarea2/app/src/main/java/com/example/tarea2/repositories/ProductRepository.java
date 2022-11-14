package com.example.tarea2.repositories;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.tarea2.daos.ProductDao;
import com.example.tarea2.database.ProductDB;
import com.example.tarea2.models.Product;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.ExecutionException;

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

    public void update(Product product){
        new updateAsyncTask(productDao).execute(product);
    }
    public void delete(Product product){ new deleteAsyncTask(productDao).execute(product); }

    //get img from firebase here add a download ASyncTask
    public Product getProduct(Integer product) throws ExecutionException, InterruptedException {
        return new findProductAsyncTask(productDao).execute(product).get();
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
    private static class updateAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao asyncProdDao;

        updateAsyncTask(ProductDao asyncProdDao){
            this.asyncProdDao = asyncProdDao;
        }

        @Override
        protected Void doInBackground(final Product... products) {
            Product aux = asyncProdDao.findProductById(products[0].getId());

            asyncProdDao.updateProduct(products[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao asyncProdDao;

        deleteAsyncTask(ProductDao asyncProdDao){
            this.asyncProdDao = asyncProdDao;
        }

        @Override
        protected Void doInBackground(final Product... products) {
            Product aux = asyncProdDao.findProductById(products[0].getId());

            asyncProdDao.deleteProduct(products[0]);
            return null;
        }
    }

    private static class findProductAsyncTask extends AsyncTask<Integer, Void, Product>{
        private ProductDao asyncProdDao;

        findProductAsyncTask(ProductDao asyncProdDao){ this.asyncProdDao = asyncProdDao; }
        @Override
        protected Product doInBackground(Integer... integers) {
            Product product = asyncProdDao.findProductById(integers[0]);
            return product;
        }
    }
}
