package com.example.tarea2.models;

import android.net.Uri;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter @NoArgsConstructor
@Entity(tableName = "products")
public class Product {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;
    @NotNull
    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "brand")
    String brand;

    @ColumnInfo(name = "price")
    @NonNull
    Double price;
    @ColumnInfo(name = "uuid")
    String uuid;

    //Uri uri;

    public Product(String name, String brand, Double price){
        this.name = name;
        this.brand = brand;
        this.price = price;
    }
}
