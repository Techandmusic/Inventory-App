package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.BookContract;

public class BookCursorAdapter extends CursorAdapter {


    //Context variable
    private Context mContext;

    //Initialize TextViews


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
        mContext = context;
        //Declare and initialize TextViews
        TextView titleTextView = view.findViewById(R.id.productName);
        TextView priceTextView = view.findViewById(R.id.productPrice);
        final TextView quantityTextView = view.findViewById(R.id.productQuantity);
        //Create int variables for column indices
        int idColumnIndex = cursor.getColumnIndex(BookContract.BookEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_QUANTITY);
        //Extract column indices to appropriate variables
        final String id = cursor.getString(idColumnIndex);
        String bookTitle = cursor.getString(nameColumnIndex);
        Double bookPrice = cursor.getDouble(priceColumnIndex);
        final int bookQuantity = cursor.getInt(quantityColumnIndex);

        //Set variables to TextViews
        titleTextView.setText(bookTitle);
        priceTextView.setText(Double.toString(bookPrice));
        quantityTextView.setText(Integer.toString(bookQuantity));

        Button saleButton = (Button) view.findViewById(R.id.sale);
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookQuantity == 0) {
                    Toast.makeText(mContext, (R.string.zero_error), Toast.LENGTH_SHORT).show();
                } else {
                    int newQuantity = bookQuantity - 1;
                    ContentValues values = new ContentValues();
                    values.put(BookContract.BookEntry.COLUMN_QUANTITY, newQuantity);
                    quantityTextView.setText(Integer.toString(newQuantity));
                    Uri currentUri = Uri.withAppendedPath(BookContract.BookEntry.CONTENT_URI, id);
                    mContext.getContentResolver().update(currentUri, values, null, null);


                }
            }
        });


    }


}
