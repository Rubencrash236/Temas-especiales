package com.example.tarea2.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.tarea2.daos.ProductDao;
import com.example.tarea2.models.Product;

@Database(entities = {Product.class}, version = 3, exportSchema = false)
public abstract class ProductDB extends RoomDatabase {

    public abstract ProductDao productDao();

    private static volatile ProductDB INSTANCE;

    public static ProductDB getInstance(Context ctx){
        if(INSTANCE == null){
            synchronized (ProductDB.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(), ProductDB.class, "tarea2")
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
