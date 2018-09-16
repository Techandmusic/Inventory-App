package com.example.android.inventoryapp;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import butterknife.BindView;

public class ViewProductDetails extends Fragment {

    //Class constructor method, takes cursor as argument?

    //Bind Column results from list item to views in
    //product_details.xml

    //TextView for book title
    @BindView(R.id.bookTitle) TextView bookTitle;
    //TextView for book author
    @BindView(R.id.bookAuthor) TextView bookAuthor;
    //TextView for book price
    @BindView(R.id.bookPrice) TextView bookPrice;
    //TextView for book quantity
    @BindView(R.id.bookQuantity) TextView bookQuantity;
    //TextView for book supplier name
    @BindView(R.id.bookSupplier) TextView bookSupplier;


}
