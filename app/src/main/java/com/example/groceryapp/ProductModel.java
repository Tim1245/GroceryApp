package com.example.groceryapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ProductModel {
    private String GroceryName, StoreName, desc, price;

    public ProductModel(String groceryName, String storeName, String desc, String price) {
        GroceryName = groceryName;
        StoreName = storeName;
        this.desc = desc;
        this.price = price;
    }

    public String getGroceryName() {
        return GroceryName;
    }

    public void setGroceryName(String groceryName) {
        GroceryName = groceryName;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }}


