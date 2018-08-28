package com.example.android.inventoryapp.data;

import android.provider.BaseColumns;

public final class BookContract {

    public static abstract class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "Product Name";
        public static final String COLUMN_PRICE = "Price";
        public static final String COLUMN_QUANTITY = "Quantity";
        public static final String COLUMN_SUPPLIER_NAME = "Supplier Name";
        public static final String COLUMN_SUPPLIER_PHONE = "Supplier Phone Number";
    }
}
