package com.example.android.inventoryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

// TODO Write queryData method that uses a Cursor to read data from the database
// TODO Write insertData method to add items to the database
// TODO Write Contract Class that defines name of table and constants
// TODO Inside Contract Class ad inner class for each table created
// TODO Write DbHelper Class which extends SQLIteOpenHelper class and overrides onCreate and onUpgrade
// TODO Add some code to test that SQLite commands are working properly