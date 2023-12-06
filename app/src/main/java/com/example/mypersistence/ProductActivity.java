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
import androidx.appcompat.widget.Toolbar;

public class ProductActivity extends AppCompatActivity {
    private Bundle extras;
    private int store_id = -1;
    private String store_name = "";
    private EditText editTextProductName, editTextProductPrice, editTextProductDescription;
    private Button btnProductCreate, btnProductUpdate, btnProductDelete;
    private int productSelectedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);



        editTextProductName = (EditText) findViewById(R.id.editTextProductName);
        editTextProductPrice = (EditText) findViewById(R.id.editTextProductPrice);
        editTextProductDescription = (EditText) findViewById(R.id.editTextProductDescription);

        btnProductCreate = (Button) findViewById(R.id.btnProductCreate);
        btnProductUpdate = (Button) findViewById(R.id.btnProductUpdate);
        btnProductDelete = (Button) findViewById(R.id.btnProductDelete);

        btnProductUpdate.setVisibility(View.GONE);
        btnProductDelete.setVisibility(View.GONE);

        extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("name") != null) {
                btnProductUpdate.setVisibility(View.VISIBLE);
                btnProductDelete.setVisibility(View.VISIBLE);
                btnProductCreate.setVisibility(View.GONE);
            }
            editTextProductName.setText(extras.getString("name"));
            editTextProductPrice.setText("" + extras.getDouble("price", 0.0));
            editTextProductDescription.setText(extras.getString("description"));
            productSelectedId = extras.getInt("_id", 0);
            store_id = extras.getInt("store_id", 0);
            store_name = extras.getString("store_name");
        }
        //getSupportActionBar().setTitle("Producto de:" + this.store_name);
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

        String name = this.editTextProductName.getText().toString();
        double price = Double.parseDouble(this.editTextProductPrice.getText().toString());
        String description = this.editTextProductDescription.getText().toString();

        if (!name.isEmpty() && !description.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("price", price);
            contentValues.put("description", description);
            contentValues.put("store_id", this.store_id);

            long newRowId = sqLiteDatabase.insert(
                    StoreContract.ProductEntry.TABLE_NAME,
                    null,
                    contentValues
            );
            if (newRowId != -1) {
                Intent intent = new Intent(this, ProductListActivity.class);
                intent.putExtra("store_id", this.store_id);
                intent.putExtra("store_name", this.store_name);
                startActivity(intent);
                Toast.makeText(this, "Producto registrado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error, intenta otra vez", Toast.LENGTH_SHORT).show();
            }
            sqLiteDatabase.close();
        } else {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
        }

    }

    public void onUpdate(View view) {
        StoreDBHelper storeDBHelper = new StoreDBHelper(this);
        SQLiteDatabase sqLiteDatabase = storeDBHelper.getWritableDatabase();

        String name = this.editTextProductName.getText().toString();
        double price = Double.parseDouble(this.editTextProductPrice.getText().toString());
        String description = this.editTextProductDescription.getText().toString();

        if (!name.isEmpty() && !description.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("price", price);
            contentValues.put("description", description);

            long count = sqLiteDatabase.update(
                    StoreContract.ProductEntry.TABLE_NAME,
                    contentValues, "_id+" + this.productSelectedId, null
            );


            if (count != 0) {
                Intent intent = new Intent(this, ProductListActivity.class);
                intent.putExtra("store_id", this.store_id);
                intent.putExtra("store_name", this.store_name);
                startActivity(intent);
                Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error, intenta otra vez", Toast.LENGTH_SHORT).show();
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
                StoreContract.ProductEntry.TABLE_NAME,
                "_id" + this.productSelectedId,
                null
        );
        if (count != 0) {
            Intent intent = new Intent(this, ProductListActivity.class);
            intent.putExtra("store_id", this.store_id);
            intent.putExtra("store_name", this.store_name);
            startActivity(intent);
            Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Error, intenta otra vez", Toast.LENGTH_SHORT).show();

        }

    }


}