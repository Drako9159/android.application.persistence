package com.example.mypersistence;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

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