package com.example.android.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.inventoryapp.data.BookContract.BookEntry;
import com.example.android.inventoryapp.data.BookDbHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Database helper object
    private BookDbHelper mDbHelper;
    //ListView for books
    private ListView productView;
    //CursorAdapter
    private BookCursorAdapter mCursorAdapter;
    //Loader constant
    private static final int BOOK_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiate DbHelper object
        mDbHelper = new BookDbHelper(this);
        //Instantiate ListView
        ListView bookListView = (ListView) findViewById(R.id.mainList);
        //Set emptyView
        View emptyView = (TextView) findViewById(R.id.defaultText);
        bookListView.setEmptyView(emptyView);
        //Instantiate CursorAdapter
        mCursorAdapter = new BookCursorAdapter(this, null);
        //Call set adapter method on ListView
        bookListView.setAdapter(mCursorAdapter);




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





    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_PRODUCT_NAME,
                BookEntry.COLUMN_PRICE,
                BookEntry.COLUMN_QUANTITY
        };
        return new CursorLoader(this, BookEntry.CONTENT_URI, projection, null, null, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}


//TODO Setup Click listener for sale button
//TODO Setup logic so that no negative quantities are displayed
//TODO Setup Increase and Decrease quantity buttons respective clicl listeners
//TODO Setup Delete Button click listener in product detail view
//TODO Setup Order Button click listener in product detail view that uses a phone intent with supplier phone number
//TODO Add ViewPager
//TODO Add Button to activity_main.xml to ad inventory to database
//TODO Add Loader and callbacks to activity

