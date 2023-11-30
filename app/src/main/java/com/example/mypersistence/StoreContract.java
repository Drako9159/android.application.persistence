package com.example.mypersistence;

import android.provider.BaseColumns;

public class StoreContract {
    private StoreContract() {
    }

    public static class StoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "store";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADDRESS = "address";

        public static final String SQL_CREATE_STORE_ENTRY = "" +
                "CREATE TABLE " + StoreEntry.TABLE_NAME + " (" +
                StoreEntry._ID + " INTEGER PRIMARY KEY," +
                StoreEntry.COLUMN_NAME + " TEXT," +
                StoreEntry.COLUMN_ADDRESS + " TEXT)";
    }

    public static class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "product";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_STORE_ID = "store_id";
        public static final String SQL_CREATE_PRODUCT_ENTRY = "" +
                "CREATE TABLE " + ProductEntry.TABLE_NAME + " (" +
                ProductEntry._ID + " INTEGER PRIMARY KEY," +
                ProductEntry.COLUMN_NAME + " TEXT," +
                ProductEntry.COLUMN_PRICE + " TEXT," +
                ProductEntry.COLUMN_DESCRIPTION + " TEXT," +
                ProductEntry.COLUMN_STORE_ID + " INTEGER," +
                "FOREIGN KEY(" + ProductEntry.COLUMN_STORE_ID + ") REFERENCES " +
                StoreEntry.TABLE_NAME + "(" + StoreEntry._ID + "))";
    }
}
