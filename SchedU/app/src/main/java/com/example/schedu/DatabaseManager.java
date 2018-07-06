package com.example.gg.jsonfetch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String BASE_URL = "https://api.uwaterloo.ca/v2/courses"; // base URL for UW API course info
    private static final String KEY = "6e9e2a8c5b5114e5b4fe9d30387fec4d"; // Uwaterloo open data API access key

    private static DatabaseManager instance;//singoton
    public static synchronized DatabaseManager getHelper(Context context)
    {
        if (instance == null)
            instance = new DatabaseManager(context);

        return instance;
    }

    public static final String CLASS_TABLE = "class_table";  // Table of all subjects, like CS, ECE, ACC
    public static final String CLASS_TABLE_COL_1 = "class_number";
    public static final String CLASS_TABLE_COL_2 = "subject";
    public static final String CLASS_TABLE_COL_3 = "catalog_number";
    public static final String CLASS_TABLE_COL_4 = "units_number";
    public static final String CLASS_TABLE_COL_5 = "title";
    public static final String CLASS_TABLE_COL_6 = "section";
    public static final String CLASS_TABLE_COL_7 = "campus";
    public static final String CLASS_TABLE_COL_8 = "enrollment_capacity";
    public static final String CLASS_TABLE_COL_9 = "enrollment_totle";
    public static final String CLASS_TABLE_COL_10 = "start_time";
    public static final String CLASS_TABLE_COL_11 = "end_time";
    public static final String CLASS_TABLE_COL_12 = "weekdays";
    public static final String CLASS_TABLE_COL_13 = "building";
    public static final String CLASS_TABLE_COL_14 = "room";
    public static final String CLASS_TABLE_COL_15 = "instructor";
    public static final String CLASS_TABLE_COL_16 = "instructor_rating";
    public static final String CLASS_TABLE_COL_17 = "academic_level";


    public DatabaseManager(Context context) {
        super(context, "UWCourseDB", null, 42);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //only run once when database is created.
        db.execSQL("create table " + CLASS_TABLE + " (CLASS_NUMBER INTEGER PRIMARY KEY," +
                "SUBJECT TEXT," +
                "CATALOG_NUMBER TEXT," +
                "UNITS_NUMBER TEXT," +
                "TITLE TEXT," +
                "SECTION TEXT," +
                "CAMPUS TEXT," +
                "ENROLLMENT_CAPACITY TEXT," +
                "ENROLLMENT_TOTLE TEXT," +
                "START_TIME TEXT," +
                "END_TIME TEXT," +
                "WEEKDAYS TEXT," +
                "BUILDING TEXT," +
                "ROOM TEXT," +
                "INSTRUCTOR TEXT," +
                "INSTRUCTOR_RATING TEXT," +
                "ACADEMIC_LEVEL TEXT)");
        //fillClassTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CLASS_TABLE);
        onCreate(db);
    }
    // to insert or update a row of data. Thread safe.
    public boolean insertClass (String class_number, String subject, String catalog_number,
                                String units_number, String title, String section,
                                String campus, String enrollment_capacity, String enrollment_totle,
                                String start_time, String end_time, String weekdays,
                                String building, String room, String instructor,
                                String instructor_rating, String academic_level) {
        SQLiteDatabase db = this.getWritableDatabase(); // use this sqlitedatabase instance to insert data into this db.


        ContentValues contentValues = new ContentValues(); // instance of a row of values, used to insert row into table
        // add column values into this contentValues
        contentValues.put(CLASS_TABLE_COL_1, Integer.parseInt(class_number));
        contentValues.put(CLASS_TABLE_COL_2, subject);
        contentValues.put(CLASS_TABLE_COL_3, catalog_number);
        contentValues.put(CLASS_TABLE_COL_4, units_number);
        contentValues.put(CLASS_TABLE_COL_5, title);
        contentValues.put(CLASS_TABLE_COL_6, section);
        contentValues.put(CLASS_TABLE_COL_7, campus);
        contentValues.put(CLASS_TABLE_COL_8, enrollment_capacity);
        contentValues.put(CLASS_TABLE_COL_9, enrollment_totle);
        contentValues.put(CLASS_TABLE_COL_10, start_time);
        contentValues.put(CLASS_TABLE_COL_11, end_time);
        contentValues.put(CLASS_TABLE_COL_12, weekdays);
        contentValues.put(CLASS_TABLE_COL_13, building);
        contentValues.put(CLASS_TABLE_COL_14, room);
        contentValues.put(CLASS_TABLE_COL_15, instructor);
        contentValues.put(CLASS_TABLE_COL_16, instructor_rating);
        contentValues.put(CLASS_TABLE_COL_17, academic_level);

        // insert or update this row in the table
        long rev = 0;
        if (CheckIsDataAlreadyInDBorNot(class_number) == true) {
            rev = db.update(CLASS_TABLE, contentValues, "class_number="+class_number, null);
            if (rev == -1) System.out.println("DatabaseManager.java " + subject + " " + catalog_number + " " + section + " insertion failed.");
        } else {
            rev = db.insert(CLASS_TABLE, null, contentValues);
            if (rev == -1) System.out.println("DatabaseManager.java " + subject + " " + catalog_number + " " + section + " update failed.");
        }
        return -1 != rev;
    }

    public boolean CheckIsDataAlreadyInDBorNot(String class_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + CLASS_TABLE + " where class_number = " + class_number;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    public static void main(String [] args) {

        System.out.println("hello");
        //fillClassTable();
    }
}