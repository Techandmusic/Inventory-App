package com.example.android.inventoryapp;

import android.support.v4.app.Fragment;
import android.widget.EditText;

import butterknife.BindView;

public class EditProductDetails extends Fragment {
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

}
