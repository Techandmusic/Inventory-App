package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.BookContract;
import com.example.android.inventoryapp.data.BookContract.BookEntry;
import com.example.android.inventoryapp.data.BookDbHelper;

public class MainActivity extends AppCompatActivity {

    private BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new BookDbHelper(this);
        Button button = findViewById(R.id.testButton1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        Button button1 = findViewById(R.id.displayButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryData();
            }
        });

    }



    private void insertData() {
        //Variable for the writable database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        //Content values object
        ContentValues values = new ContentValues();
        //Columns to be added to ContentValues
        values.put(BookEntry.COLUMN_PRODUCT_NAME, "Head First Android Development");
        values.put(BookEntry.COLUMN_PRICE, 45.99);
        values.put(BookEntry.COLUMN_QUANTITY, 2);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, "BookWorld Distribution");
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE, "(850)555-1234");
        long newRowID = db.insert(BookEntry.TABLE_NAME, null, values);
    }

    @Override
    protected void onStart(){
        super.onStart();
       // insertData();
       // queryData();

    }

    private void queryData() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_PRODUCT_NAME,
                BookEntry.COLUMN_PRICE,
                BookEntry.COLUMN_QUANTITY,
                BookEntry.COLUMN_SUPPLIER_NAME,
                BookEntry.COLUMN_SUPPLIER_PHONE };

        Cursor cursor;
        cursor = db.query(
                BookEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        TextView dataView = findViewById(R.id.dataDisplay);

        try {
            //Get the name of each index column
            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int productColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);
            int supplierNameIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_PHONE);
            //Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()){
                int currentID = cursor.getInt(idColumnIndex);
                String currentProduct = cursor.getString(productColumnIndex);
                Double currentPrice = cursor.getDouble(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneIndex);
                //Display the rows in the TextView
                dataView.append("\n" + currentID + ", " + currentProduct + ", " + currentPrice + ", "
                + currentQuantity + ", " + currentSupplierName + ", " + currentSupplierPhone);
            }
        } finally {
            cursor.close();
        }
    }
}

// TODO Write queryData method that uses a Cursor to read data from the database DONE
// TODO Write insertData method to add items to the database DONE
// TODO Write Contract Class that defines name of table and constants DONE
// TODO Inside Contract Class add inner class for each table created DONE
// TODO Write DbHelper Class which extends SQLIteOpenHelper class and overrides onCreate and onUpgrade DONE
// TODO Add some code to test that SQLite commands are working properly DONE