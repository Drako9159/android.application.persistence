package com.example.mypersistence;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class StoreActivity extends AppCompatActivity {

    private EditText editTextName, editTextAddress;
    private Bundle extras;
    private Button btnCreate, btnUpdate, btnDelete;
    private int storeSelectedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        this.editTextName = (EditText) findViewById(R.id.editTextName);
        this.editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        this.btnCreate = (Button) findViewById(R.id.btnCreate);
        this.btnUpdate = (Button) findViewById(R.id.btnUpdate);
        this.btnDelete = (Button) findViewById(R.id.btnDelete);

        btnUpdate.setVisibility(View.GONE);
        this.btnDelete.setVisibility(View.GONE);

        extras = getIntent().getExtras();
        if (getIntent() != null && getIntent().getExtras() != null) {
            this.btnUpdate.setVisibility(View.VISIBLE);
            this.btnDelete.setVisibility(View.VISIBLE);
            this.btnCreate.setVisibility(View.GONE);

            this.editTextName.setText(extras.getString("name"));
            this.editTextAddress.setText(extras.getString("address"));
            this.storeSelectedId = extras.getInt("_id", 0);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tienda");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCreate(View view) {
        StoreDBHelper storeDBHelper = new StoreDBHelper(this);
        SQLiteDatabase sqLiteDatabase = storeDBHelper.getWritableDatabase();

        String name = editTextName.getText().toString();
        String address = editTextAddress.getText().toString();

        if (!name.isEmpty() && !address.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("address", address);

            long newRowId = sqLiteDatabase.insert(
                    StoreContract.StoreEntry.TABLE_NAME,
                    null,
                    contentValues
            );

            if (newRowId != -1) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Tienda registrada correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error, itenta otra vez", Toast.LENGTH_SHORT).show();
            }
            sqLiteDatabase.close();
        } else {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
        }
    }


    public void onUpdate(View view) {
        StoreDBHelper storeDBHelper = new StoreDBHelper(this);
        SQLiteDatabase sqLiteDatabase = storeDBHelper.getWritableDatabase();

        String name = editTextName.getText().toString();
        String address = editTextAddress.getText().toString();

        if (!name.isEmpty() && !address.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("address", address);

            int count = sqLiteDatabase.update(
                    StoreContract.StoreEntry.TABLE_NAME,
                    contentValues,
                    "_id=" + this.storeSelectedId,
                    null
            );
            if (count != 0) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Tienda actualizada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error, itenta otra vez", Toast.LENGTH_SHORT).show();
            }
            sqLiteDatabase.close();
        } else {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
        }

    }

    public void onDelete(View view) {
        StoreDBHelper storeDBHelper = new StoreDBHelper(this);
        SQLiteDatabase sqLiteDatabase = storeDBHelper.getWritableDatabase();

        int count = sqLiteDatabase.delete(
                StoreContract.StoreEntry.TABLE_NAME,
                "_id=" + this.storeSelectedId,
                null
        );

        if (count != 0) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Tienda eliminada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error, itenta otra vez", Toast.LENGTH_SHORT).show();

        }
        sqLiteDatabase.close();

    }


}