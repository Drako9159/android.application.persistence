package com.example.mypersistence;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<StoreActivity> storeEntityArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startStoreActivity();
    }

    private void startStoreActivity() {
        Intent intent = new Intent(this, StoreActivity.class);
        startActivity(intent);
    }
}