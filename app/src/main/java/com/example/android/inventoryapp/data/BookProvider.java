package com.example.android.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class BookProvider extends ContentProvider {
    //Tag for log messages
    public static final String LOG_TAG = BookProvider.class.getSimpleName();
    //URI matcher code for the books table
    private static final int BOOKS = 100;
    //URI matcher code for individual books in the table
    private static final int BOOK_ID = 101;
    //URI matcher object
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    //Static initializer
    static {
        sUriMatcher.addURI(BookContract.CONTENT_AUTHORITY, BookContract.PATH_BOOKS, BOOKS);
        sUriMatcher.addURI(BookContract.CONTENT_AUTHORITY, BookContract.PATH_BOOKS + "/#", BOOK_ID);
    }
    //Initializer provider and database helper
    private BookDbHelper mDbHelper;


    @Override
    public boolean onCreate() {
        mDbHelper = new BookDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //Get readable database
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        //Create cursor to hold query results
        Cursor cursor = null;
        //Figure out if the uri matcher matches a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                cursor = db.query(BookContract.BookEntry.TABLE_NAME, projection, selection, selectionArgs, null,
                        null, sortOrder);
            break;
            case BOOK_ID:
                selection = BookContract.BookEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(BookContract.BookEntry.TABLE_NAME, projection, selection, selectionArgs, null,
                        null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    private Uri insertBook(Uri uri, ContentValues values){
        //Make sure the title field is not null
        String title = values.getAsString(BookContract.BookEntry.COLUMN_PRODUCT_NAME);
        if (title == null) {
            throw new IllegalArgumentException("Book requires a title");
        }
        String author = values.getAsString(BookContract.BookEntry.COLUMN_AUTHOR_NAME);
        if (author == null) {
            throw new IllegalArgumentException("You must enter an author");
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


}
