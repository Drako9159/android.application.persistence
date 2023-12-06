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

import com.example.mypersistence.adapter.RecyclerViewStoreAdapter;
import com.example.mypersistence.entity.ProductEntity;
import com.example.mypersistence.entity.StoreEntity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<StoreEntity> storeEntityArrayList;
    private RecyclerViewStoreAdapter adapter;

    private int recyclerViewItemSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.buildRecyclerView();
        // getSupportActionBar().setTitle("asd");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Stores");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void startStoreActivity() {
        Intent intent = new Intent(this, StoreActivity.class);
        startActivity(intent);
    }

    public void buildRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewStore);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        storeEntityArrayList = new ArrayList<>();

        StoreDBHelper storeDBHelper = new StoreDBHelper(this);
        SQLiteDatabase sqLiteDatabase = storeDBHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + StoreContract.StoreEntry.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(StoreContract.StoreEntry.COLUMN_NAME));
            String address = cursor.getString(cursor.getColumnIndex(StoreContract.StoreEntry.COLUMN_ADDRESS));
            int _id = cursor.getInt(cursor.getColumnIndex(StoreContract.StoreEntry._ID));
            storeEntityArrayList.add(new StoreEntity(_id, name, address));
        }

        sqLiteDatabase.close();

        adapter = new RecyclerViewStoreAdapter(this, storeEntityArrayList);
        recyclerView.setAdapter(adapter);
        adapter.setOnLongItemCustomListener((v, position) -> {
            recyclerViewItemSelected = position;
            v.showContextMenu();
        });
        /*
        adapter.setOnLongItemCustomListener(new RecyclerViewStoreAdapter.OnLongItemCustomListener() {
            @Override
            public void itemLongClicked(View v, int position) {
                recyclerViewItemSelected = position;
                v.showContextMenu();
            }
        });*/
        registerForContextMenu(recyclerView);
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
            Intent intent = new Intent(this, StoreActivity.class);
            //intent.putExtra("store_id", this.store_id);
            //intent.putExtra("store_name", this.store_name);
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
            //ProductEntity product = productEntityArrayList.get(this.recyclerViewItemSelected);
            /*intent.putExtra("name", product.getName());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("description", product.getDescription());
            intent.putExtra("_id", product.get_id());
            intent.putExtra("store_id", this.store_id);
            intent.putExtra("store_name", this.store_name);*/
            startActivity(intent);
            return true;
        }
        return super.onContextItemSelected(item);
    }



















}