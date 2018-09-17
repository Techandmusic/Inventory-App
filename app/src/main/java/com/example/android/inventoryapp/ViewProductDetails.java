package com.example.android.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;

public class ViewProductDetails extends Fragment {

    //Fragment's context variable
    private Context mContext;

    //Class constructor method, takes cursor as argument?
    public ViewProductDetails() {

    }

    //Fragment's built in get context method

    public Context getmContext() {
        mContext = this.getContext();
        return mContext;
    }


    //Bind Column results from list item to views in
    //product_details.xml

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_details, container, false);
        return view;
    }


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
