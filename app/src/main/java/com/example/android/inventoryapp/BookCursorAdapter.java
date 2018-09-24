package com.example.android.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventoryapp.data.BookContract;

import butterknife.BindView;

public class BookCursorAdapter extends CursorAdapter {

    //Text view bindings via butterknife
    @BindView(R.id.productName)
    TextView titleTextView;
    @BindView(R.id.productPrice)
    TextView priceTextView;
    @BindView(R.id.productQuantity)
    TextView quantityTextView;


    //Class constructor
    public BookCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    //newView method to inflate layout to list_item.xml
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    //Implementation of bindView method
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //Create int variables for column indices
        int nameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_QUANTITY);
        //Extract column indices to appropriate variables
        String bookTitle = cursor.getString(nameColumnIndex);
        Double bookPrice = cursor.getDouble(priceColumnIndex);
        int bookQuantity = cursor.getInt(quantityColumnIndex);

        //Set variables to TextViews
        titleTextView.setText(bookTitle);
        priceTextView.setText(Double.toString(bookPrice));
        quantityTextView.setText(bookQuantity);
    }
}
