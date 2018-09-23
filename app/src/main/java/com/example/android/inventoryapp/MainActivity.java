package com.example.android.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import com.example.android.inventoryapp.data.BookContract.BookEntry;
import com.example.android.inventoryapp.data.BookDbHelper;
import android.widget.AdapterView;
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
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

        //Setup FAB to open EditProductDetails Fragment
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editorIntent = new Intent(MainActivity.this, EditProductDetails.class);
                startActivity(editorIntent);
            }
        });


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

        //Set onItemClickListener for ListView
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailsIntent = new Intent(MainActivity.this, ProductDetailsActivity.class);
                //Set Uri with appended id
                Uri currentBookUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, id);
                //Call intent.setData method with uri passed as argument
                detailsIntent.setData(currentBookUri);
                //call startActivity
                startActivity(detailsIntent);
            }
        });

        getLoaderManager().initLoader(BOOK_LOADER, null, this);




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

