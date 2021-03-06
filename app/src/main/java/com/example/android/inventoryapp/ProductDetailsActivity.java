package com.example.android.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.BookContract.BookEntry;
import com.example.android.inventoryapp.data.BookDbHelper;



public class ProductDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    //Loader ID for Details Activity
    private static final int DETAILS_LOADER = 0;

    //Current uri
    private Uri mCurrentBookUri;
    //Database Helper Instance
    private BookDbHelper mDbHelper;
    //TextView for book title
    TextView bookTitle = findViewById(R.id.bookTitle);
    //TextView for book author
    TextView bookAuthor = findViewById(R.id.bookAuthor);
    //TextView for book price
    TextView bookPrice = findViewById(R.id.bookPrice);
    //TextView for book quantity
    TextView bookQuantity = findViewById(R.id.bookQuantity);
    //TextView for book supplier name
    TextView bookSupplier = findViewById(R.id.bookSupplier);
    //TextView for book supplier phone number
    TextView bookSupPhone = findViewById(R.id.bookSupPhone);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        //Get intent with book data to display
        Intent intent = getIntent();
        mCurrentBookUri = intent.getData();


        Button increaseQuantity = (Button) findViewById(R.id.increase);
        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuantity();
                updateBookInfo();
            }
        });

        Button decreaseQuantity = (Button) findViewById(R.id.decrease);
        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtractQuantity();
                updateBookInfo();
            }
        });


        Button order = (Button) findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String supplierCall = bookSupPhone.getText().toString();
                Intent orderIntent = new Intent(Intent.ACTION_DIAL);
                orderIntent.setData(mCurrentBookUri.parse("tel:" + supplierCall));
                startActivity(orderIntent);
            }
        });


        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });


        //Initialize loader
        getLoaderManager().initLoader(DETAILS_LOADER, null, this);


    }

    public void addQuantity() {
        //Get TextView text as a String variable
        String quantityText = bookQuantity.getText().toString();
        //Convert String to Integer variable
        int quantityNumber = Integer.parseInt(quantityText);
        //Add 1 to current TextView Text
        int newQuantity = quantityNumber + 1;
        //Set the updated quantity text to the TextView
        bookQuantity.setText(Integer.toString(newQuantity));
        //Update the database with the new value
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_QUANTITY, newQuantity);
        this.getContentResolver().update(mCurrentBookUri, values, null, null);
    }

    public void subtractQuantity() {
        //Get TextView text as a String variable
        String quantityText = bookQuantity.getText().toString();
        //Convert String to Integer variable
        int quantityNumber = Integer.parseInt(quantityText);
        //Add 1 to current TextView Text
        int newQuantity = quantityNumber - 1;
        if (newQuantity < 0) {
            newQuantity = 0;
        }
        //Set the updated quantity text to the TextView
        bookQuantity.setText(Integer.toString(newQuantity));
        //Update database with new value
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_QUANTITY, newQuantity);
        this.getContentResolver().update(mCurrentBookUri, values, null, null);

    }

    public void deleteBook() {
        if (mCurrentBookUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentBookUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.delete_successful), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void updateBookInfo() {
        String quantityText = bookQuantity.getText().toString();
        //Convert String to Integer variable
        int quantityNumber = Integer.parseInt(quantityText);
        String titleText = bookTitle.getText().toString();
        //Create a content values
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_PRODUCT_NAME, titleText);
        values.put(BookEntry.COLUMN_QUANTITY, quantityNumber);
        //Update info in database
        int rowsAffected = getContentResolver().update(mCurrentBookUri, values, null, null);
        if (rowsAffected == 0) {
            Toast.makeText(this, getString(R.string.save_error), Toast.LENGTH_SHORT).show();
        }
    }


    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_PRODUCT_NAME,
                BookEntry.COLUMN_AUTHOR_NAME,
                BookEntry.COLUMN_PRICE,
                BookEntry.COLUMN_QUANTITY,
                BookEntry.COLUMN_SUPPLIER_NAME,
                BookEntry.COLUMN_SUPPLIER_PHONE};
        return new CursorLoader(this, mCurrentBookUri, projection, null, null, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //Bail early if cursor is null or contains less than 1 row
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        //Proceed with moving to the first row of the cursor and reading data from it
        //(this should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            //Find the columns of the book attributes we want to display
            int titleColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
            int authorColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_AUTHOR_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NAME);
            int supPhoneIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_PHONE);

            //Extract out the value from the cursor for the given column index
            String title = cursor.getString(titleColumnIndex);
            String author = cursor.getString(authorColumnIndex);
            double price = cursor.getDouble(priceColumnIndex);
            int quantity = cursor.getInt(quantityColumnIndex);
            String supplierName = cursor.getString(supplierColumnIndex);
            String supplierPhone = cursor.getString(supPhoneIndex);

            //Update the views on screen with the values from the database
            bookTitle.setText(title);
            bookAuthor.setText(author);
            bookPrice.setText(Double.toString(price));
            bookQuantity.setText(Integer.toString(quantity));
            bookSupplier.setText(supplierName);
            bookSupPhone.setText(supplierPhone);
        }


    }

    public void onLoaderReset(Loader<Cursor> loader) {
        loader = null;
    }
}
