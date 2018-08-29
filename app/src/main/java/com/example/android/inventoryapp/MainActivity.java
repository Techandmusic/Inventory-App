package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.inventoryapp.data.BookContract;
import com.example.android.inventoryapp.data.BookContract.BookEntry;
import com.example.android.inventoryapp.data.BookDbHelper;

public class MainActivity extends AppCompatActivity {

    private BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickBtn(View v) {
        insertData();
        queryData();
    }

    private void insertData() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_PRODUCT_NAME, "Head First Android Development");
        values.put(BookEntry.COLUMN_PRICE, 45.99);
        values.put(BookEntry.COLUMN_QUANTITY, 2);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, "BookWorld Distribution");
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE, "(850)555-1234");
        long newRowID = db.insert(BookEntry.TABLE_NAME, null, values);
    }

    private void queryData() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] Projection = {
                BookEntry._ID,
                BookEntry.COLUMN_PRODUCT_NAME,
                BookEntry.COLUMN_PRICE,
                BookEntry.COLUMN_QUANTITY,
                BookEntry.COLUMN_SUPPLIER_NAME,
                BookEntry.COLUMN_SUPPLIER_PHONE};
        Cursor cursor = db.query(BookEntry.TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null);

        try {

            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int productColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);
            int supplierNameIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_PHONE);
            while (cursor.moveToNext()){
                int currentID = cursor.getInt(idColumnIndex);
                String currentProduct = cursor.getString(productColumnIndex);
                Double currentPrice = cursor.getDouble(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneIndex);
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