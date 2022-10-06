package com.example.groceryapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class MainModel {
    private String url_Image, title, price, size, category, highlight, info, unit;

    MainModel(){

    }

    public MainModel(String url_Image, String title, String price, String size, String category, String highlight, String info, String unit) {
        this.url_Image = url_Image;
        this.title = title;
        this.price = price;
        this.size = size;
        this.category = category;
        this.highlight = highlight;
        this.info = info;
        this.unit = unit;
    }

    public String getUrl_Image() {
        return url_Image;
    }

    public void setUrl_Image(String url_Image) {
        this.url_Image = url_Image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
