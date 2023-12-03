package com.example.mypersistence.entity;

public class ProductEntity {
    private String name = "";
    private double price = 0.0;
    private String description = "";
    private int store_id = 0;
    private int _id = 0;

    public ProductEntity(int _id, String name, double price, String description, int store_id) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.store_id = store_id;
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
