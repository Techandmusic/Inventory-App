package com.example.android.inventoryapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.inventoryapp.data.BookContract.BookEntry;
import com.example.android.inventoryapp.data.BookDbHelper;




public class EditProductDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //Loader constant
    private static final int EXISTING_BOOK_LOADER = 0;
    //Default for price fields
    private static final double DEFAULT_PRICE = 0.00;
    //EditText field to add book title
    EditText mTitle = findViewById(R.id.addTitle);
    //EditText field to add book author
    EditText mAuthor = findViewById(R.id.addAuthor);
    //EditText field to add book price
    EditText mPrice = findViewById(R.id.addPrice);
    //EditText field to add quantity of book in stock
    EditText mQuantity = findViewById(R.id.addQuantity);
    //EditText field to add name of supplier for book
    EditText mSupplierName = findViewById(R.id.addSupplierName);
    //EditText field to add supplier's phone number
    EditText mSupplierNo = findViewById(R.id.addSupplierNo);
    //Variable for rows affected when adding a new book
    int rowsAffected;
    //Database helper instance
    private BookDbHelper mDbHelper;
    //Variable for current book uri
    private Uri mCurrentBookUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_details);

        Intent intent = getIntent();
        mCurrentBookUri = intent.getData();
        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();
            }
        });

        mDbHelper = new BookDbHelper(this);

    }



    private void addBook() {
        //Get writable database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        //Get values from EditText fields
        String titleString = mTitle.getText().toString().trim();
        String authorString = mAuthor.getText().toString().trim();
        String priceDouble = mPrice.getText().toString().trim();
        String quantityInt = mQuantity.getText().toString().trim();
        String supNameString = mSupplierName.getText().toString().trim();
        String supPhoneString = mSupplierNo.getText().toString().trim();

        //Parse numerical values accordingly
        Double itemPrice = 0.00;
        if (!TextUtils.isEmpty(priceDouble)) {
            itemPrice = Double.parseDouble(priceDouble);
        }
        int itemQuantity = 0;
        if (!TextUtils.isEmpty(quantityInt)) {
            itemQuantity = Integer.parseInt(quantityInt);
        }
        //Bail early if no data is present in EditText fields
        if (mCurrentBookUri == null && TextUtils.isEmpty(titleString) && TextUtils.isEmpty(authorString) &&
                TextUtils.isEmpty(priceDouble) && TextUtils.isEmpty(quantityInt) && TextUtils.isEmpty(supNameString) &&
                TextUtils.isEmpty(supPhoneString)) {
            return;
        }

        //Create a ContentValues object where column names are keys and
        //book attributes are values
        ContentValues values = new ContentValues();
        //Add items to values
        values.put(BookEntry.COLUMN_PRODUCT_NAME, titleString);
        values.put(BookEntry.COLUMN_AUTHOR_NAME, authorString);
        values.put(BookEntry.COLUMN_PRICE, itemPrice);
        values.put(BookEntry.COLUMN_QUANTITY, itemQuantity);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, supNameString);
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE, supPhoneString);

        //Use insert to add book to database
        Uri newUri = getContentResolver().insert(BookEntry.CONTENT_URI, values);

    }

    private void saveBook() {
        //Get values from EditText fields
        String titleString = mTitle.getText().toString().trim();
        String authorString = mAuthor.getText().toString().trim();
        String priceDouble = mPrice.getText().toString().trim();
        String quantityInt = mQuantity.getText().toString().trim();
        String supNameString = mSupplierName.getText().toString().trim();
        String supPhoneString = mSupplierNo.getText().toString().trim();

        //Parse numerical values accordingly
        Double itemPrice = 0.00;
        if (!TextUtils.isEmpty(priceDouble)) {
            itemPrice = Double.parseDouble(priceDouble);
        }
        int itemQuantity = 0;
        if (!TextUtils.isEmpty(quantityInt)) {
            itemQuantity = Integer.parseInt(quantityInt);
        }
        //Bail early if no data is present in EditText fields
        if (mCurrentBookUri == null && TextUtils.isEmpty(titleString) && TextUtils.isEmpty(authorString) &&
                TextUtils.isEmpty(priceDouble) && TextUtils.isEmpty(quantityInt) && TextUtils.isEmpty(supNameString) &&
                TextUtils.isEmpty(supPhoneString)) {
            return;
        }

        //Create a ContentValues object where column names are keys and
        //book attributes are values
        ContentValues values = new ContentValues();
        //Add items to values
        values.put(BookEntry.COLUMN_PRODUCT_NAME, titleString);
        values.put(BookEntry.COLUMN_AUTHOR_NAME, authorString);
        values.put(BookEntry.COLUMN_PRICE, itemPrice);
        values.put(BookEntry.COLUMN_QUANTITY, itemQuantity);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, supNameString);
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE, supPhoneString);

        if (mCurrentBookUri == null) {
            Uri newUri = getContentResolver().insert(BookEntry.CONTENT_URI, values);
            if (newUri == null) {
                Toast.makeText(this, getString(R.string.save_error), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.save_successful), Toast.LENGTH_SHORT).show();
            }
        } else {
            rowsAffected = getContentResolver().update(mCurrentBookUri, values, null, null);
        }

        //Display toast message to show if save was successful or not
        if (rowsAffected == 0) {
            Toast.makeText(this, getString(R.string.save_error), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.save_successful), Toast.LENGTH_SHORT).show();
        }


    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirm_delete);
        builder.setPositiveButton(R.string.delete_dialog_option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteBook();
            }
        });
        builder.setNegativeButton(R.string.negative_dialog_option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });


    }

    private void deleteBook() {
        if (mCurrentBookUri != null) {
            int rowsDeleted = this.getContentResolver().delete(mCurrentBookUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, R.string.delete_failed, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.delete_successful, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
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

    @Override
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

            //Update views on screen with values from database
            mTitle.setText(title);
            mAuthor.setText(author);
            mPrice.setText(Double.toString(price));
            mQuantity.setText(Integer.toString(quantity));
            mSupplierName.setText(supplierName);
            mSupplierNo.setText(supplierPhone);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //If loader is invalidated, clear data from all input fields
        mTitle.setText("");
        mAuthor.setText("");
        mPrice.setText(Double.toString(DEFAULT_PRICE));
        mQuantity.setText(Integer.toString(EXISTING_BOOK_LOADER));
        mSupplierName.setText("");
        mSupplierNo.setText("");
    }
}


