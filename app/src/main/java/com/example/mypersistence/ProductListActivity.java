package com.example.mypersistence;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
            getSupportActionBar().setTitle("Productos de:" + store_name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.buildRecyclerView();
    }

    public void buildRecyclerView() {
        recyclerViewProduct = (RecyclerView) findViewById(R.id.recyclerViewProduct);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        productEntityArrayList = new ArrayList<>();

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
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.newItem:
                Intent intent = new Intent(this, ProductEntity.class);
                intent.putExtra("store_id", this.store_id);
                intent.putExtra("store_name", this.store_name);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_context_list, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.editItem:
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
            default:
                return super.onContextItemSelected(item);
        }
    }
}