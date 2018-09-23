package com.example.android.inventoryapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.inventoryapp.data.BookContract.BookEntry;
import com.example.android.inventoryapp.data.BookDbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProductDetails extends AppCompatActivity {

    //EditText field to add book title
    @BindView(R.id.addTitle)
    EditText title;
    //EditText field to add book author
    @BindView(R.id.addAuthor)
    EditText author;
    //EditText field to add book price
    @BindView(R.id.addPrice)
    EditText price;
    //EditText field to add quantity of book in stock
    @BindView(R.id.addQuantity)
    EditText quantity;
    //EditText field to add name of supplier for book
    @BindView(R.id.addSupplierName)
    EditText supplierName;
    //EditText field to add supplier's phone number
    @BindView(R.id.addSupplierNo)
    EditText supplierNo;
    //Variable for rows affected when adding a new book
    int rowsAffected;
    //Database helper instance
    private BookDbHelper mDbHelper;
    //Variable for current book uri
    private Uri mCurrentBookUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    private void saveBook() {
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
            Uri newUri = this.getContentResolver().insert(BookEntry.CONTENT_URI, values);
        } else {
            rowsAffected = this.getContentResolver().update(mCurrentBookUri, values, null, null);
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
                Toast.makeText(this, R.string.delete_succesful, Toast.LENGTH_SHORT).show();
            }
        }

    }

}

//TODO Implement loader and callback methods