package com.example.android.inventoryapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void insertData() {
        //add items to database
    }

    private Cursor queryData() {
        //use cursor to read from database
    }
}

// TODO Write queryData method that uses a Cursor to read data from the database
// TODO Write insertData method to add items to the database
// TODO Write Contract Class that defines name of table and constants DONE
// TODO Inside Contract Class add inner class for each table created DONE
// TODO Write DbHelper Class which extends SQLIteOpenHelper class and overrides onCreate and onUpgrade DONE
// TODO Add some code to test that SQLite commands are working properly