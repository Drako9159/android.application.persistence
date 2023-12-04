package com.example.mypersistence;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypersistence.adapter.RecyclerViewStoreAdapter;
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
        getSupportActionBar().setTitle("asd");

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
}