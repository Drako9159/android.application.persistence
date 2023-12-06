package com.example.mypersistence;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypersistence.adapter.RecyclerViewProductAdapter;
import com.example.mypersistence.entity.ProductEntity;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {
    private RecyclerView recyclerViewProduct;
    private ArrayList<ProductEntity> productEntityArrayList;
    private int recyclerViewItemSelected;
    private int store_id;
    private String store_name;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        this.extras = getIntent().getExtras();
        if (this.extras != null) {
            this.store_name = extras.getString("store_name");
            this.store_id = extras.getInt("store_id", 0);
            //getSupportActionBar().setTitle("Productos de:" + store_name);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Products  List");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

        }
        this.buildRecyclerView();
    }

    public void buildRecyclerView() {
        recyclerViewProduct = (RecyclerView) findViewById(R.id.recyclerViewProduct);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        productEntityArrayList = new ArrayList<>();

        StoreDBHelper storeDBHelper = new StoreDBHelper(this);
        SQLiteDatabase sqLiteDatabase = storeDBHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + StoreContract.ProductEntry.TABLE_NAME + " WHERE store_id=" + this.store_id;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Double price = cursor.getDouble(cursor.getColumnIndex("price"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            int store_id = cursor.getInt(cursor.getColumnIndex("store_id"));
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            productEntityArrayList.add(new ProductEntity(_id, name, price, description, store_id));

        }
        sqLiteDatabase.close();

        RecyclerViewProductAdapter adapter = new RecyclerViewProductAdapter(this, productEntityArrayList);
        recyclerViewProduct.setAdapter(adapter);

        adapter.setOnLongItemCustomListener(new RecyclerViewProductAdapter.OnLongItemCustomListener() {
            @Override
            public void itemLongClicked(View v, int position) {
                recyclerViewItemSelected = position;
                v.showContextMenu();
            }
        });
        registerForContextMenu(recyclerViewProduct);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_options_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return false;
        }
        if (item.getItemId() == R.id.newItem) {
            Intent intent = new Intent(this, ProductActivity.class);
            intent.putExtra("store_id", this.store_id);
            intent.putExtra("store_name", this.store_name);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_options_bar, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.editItem) {
            Intent intent = new Intent(this, ProductEntity.class);
            ProductEntity product = productEntityArrayList.get(this.recyclerViewItemSelected);
            intent.putExtra("name", product.getName());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("description", product.getDescription());
            intent.putExtra("_id", product.get_id());
            intent.putExtra("store_id", this.store_id);
            intent.putExtra("store_name", this.store_name);
            startActivity(intent);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}