package com.example.schedu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper {


    // create the database in constructor. When the ctr is called, db is created.
    public DatabaseHelper(Context context) {
        // when this constructor is called, the database "student.db" is created.
        //super(context, DATABASE_NAME, null, 1);
        // until here, the database and the table are created
        // DON"T forget to create an instance of this DatabaseHelper class. In MainActivity as class var
    }

    //@Override
    // when db is created, do the following
    public void onCreate(SQLiteDatabase db) {
        // first we create a table in this db
        //db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, SURNAME TEXT, MARKS INTEGER)");



    }

    //@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //onCreate(db);
    }


    // test
    public static void main(String [] args) {

        System.out.println("hello");
    }
}
