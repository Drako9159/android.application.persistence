package com.example.mypersistence.entity;

public class StoreEntity {
    private String name;
    private String address;
    private int _id;

    public StoreEntity(int _id, String name, String address) {
        this.name = name;
        this.address = address;
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
