package com.example.android.inventoryapp;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.EditText;
import com.example.android.inventoryapp.data.BookContract.BookEntry;
import com.example.android.inventoryapp.data.BookDbHelper;

import butterknife.BindView;

public class EditProductDetails extends Fragment {

    //Database helper instance
    private BookDbHelper mDbHelper;
    //EditText field to add book title
    @BindView(R.id.addTitle) EditText title;
    //EditText field to add book author
    @BindView(R.id.addAuthor) EditText author;
    //EditText field to add book price
    @BindView(R.id.addPrice) EditText price;
    //EditText field to add quantity of book in stock
    @BindView(R.id.addQuantity) EditText quantity;
    //EditText field to add name of supplier for book
    @BindView(R.id.addSupplierName) EditText supplierName;
    //EditText field to add supplier's phone number
    @BindView(R.id.addSupplierNo) EditText supplierNo;

    public EditProductDetails() {

    }

    private void addBook() {
        //Variable for the writable database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        //Get values from EditText fields
        String titleString = title.getText().toString().trim();
        String authorString = author.getText().toString().trim();
        String priceDouble = price.getText().toString().trim();
        String quantityInt = quantity.getText().toString().trim();
        String supNameString = supplierName.getText().toString().trim();
        String supPhoneString = supplierNo.getText().toString().trim();

        //Parse numerical values accordingly
        Double itemPrice = 0.00;
        if (!TextUtils.isEmpty(priceDouble)) {
            itemPrice = Double.parseDouble(priceDouble);
        }
        int itemQuantity = 0;
        if (!TextUtils.isEmpty(quantityInt)) {
            itemQuantity = Integer.parseInt(quantityInt);
        }


    }

}
