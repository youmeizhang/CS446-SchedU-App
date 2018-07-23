package com.example.schedu;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.vision.text.Text;

import java.util.ArrayList;
import java.util.List;

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
        super(context, "UWCourseDB", null, 53);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //only run once when database is created.
        db.execSQL("create table " + CLASS_TABLE + " (CLASS_NUMBER INTEGER PRIMARY KEY," +
                "subject TEXT," +
                "catalog_number TEXT," +
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

        db.execSQL("create table " + "prof_ratings" + " (TID INTEGER PRIMARY KEY," +
                "DEPARTMENT TEXT," +
                "FIRST_NAME TEXT," +
                "MIDDLE_NAME TEXT," +
                "LAST_NAME TEXT," +
                "RATING TEXT)");
        //fillClassTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CLASS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + "prof_ratings");
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

    public boolean fillProfRatings (String tid, String department, String firstName, String middleName,
                                    String lastName, String rating) {
        SQLiteDatabase db = this.getWritableDatabase(); // use this sqlitedatabase instance to insert data into this db.

        ContentValues contentValues = new ContentValues(); // instance of a row of values, used to insert row into table
        // add column values into this contentValues
        contentValues.put("TID", Integer.parseInt(tid));
        contentValues.put("DEPARTMENT", department);
        contentValues.put("FIRST_NAME", firstName);
        contentValues.put("MIDDLE_NAME", middleName);
        contentValues.put("LAST_NAME", lastName);
        contentValues.put("RATING", rating);

        boolean profExist = false;
        String Query = "Select * from " + "prof_ratings" + " where TID = " + tid;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() > 0){
            profExist = true;
        }

        // insert or update this row in the table prof_ratings
        long rev = 0;
        if (profExist == true) {
            rev = db.update("prof_ratings", contentValues, "TID="+tid, null);
            if (rev == -1) System.out.println("DatabaseManager.java, prof_rating: " + firstName + " " + lastName + " " + " insertion failed.");
        } else {
            rev = db.insert("prof_ratings", null, contentValues);
            if (rev == -1) System.out.println("DatabaseManager.java, prof_rating: " + firstName + " " + lastName + " " + " update failed.");
        }
        cursor.close();
        db.close();
        return -1 != rev;
    }

    public String getRating(String firstName, String lastName) {
        String retval = "N/A";
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + "prof_ratings" + " where FIRST_NAME = '" + firstName + "' AND LAST_NAME = '"+lastName+"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            System.out.println("Prof " + firstName + " " + lastName + "was not in ratemyprofessors database.");
        } else if (cursor.getCount() == 1){
            cursor.moveToNext();
            //System.out.println("DDDFFF" + cursor.getString(cursor.getColumnIndex("rating")));
            retval = cursor.getString(cursor.getColumnIndex("RATING"));

        } else {
            System.out.println("More than one " + firstName + " " + lastName);
        }
        cursor.close();
        return retval;
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

    public List<String> getAllLabels(){
        List<String> sub_from_db = new ArrayList<String>();

        String selectQuery = "SELECT DISTINCT subject FROM " + CLASS_TABLE + " ORDER BY subject";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i = 0;
        while (cursor.moveToNext()) {
            sub_from_db.add(cursor.getString(cursor.getColumnIndex("subject")));
            i++;
        }
        //System.out.println("Total rows in table: " + i);
        cursor.close();
        db.close();
        return sub_from_db;
    }
    public List<String> getAllCourseNum(String sub) {
        List<String> course_with_num = new ArrayList<>();

        String subject = "\""+ sub + "\"";
        String selectQuery = "SELECT catalog_number, TITLE FROM " + CLASS_TABLE + " WHERE subject = " + subject;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            course_with_num.add(cursor.getString(cursor.getColumnIndex("catalog_number")));
        }
        cursor.close();
        db.close();
        return course_with_num;
    }

    public List<String> getProfRating(String catalogNumber, String subjectName, String sectionNumber) {
        List<String> prof_rating = new ArrayList<String>();

        String new_sub = "\""+ subjectName + "\"";
        String sess = "\"" + sectionNumber +"\"";
        System.out.println("new sess is here: " + sess);
        String selectQuery = "SELECT INSTRUCTOR, INSTRUCTOR_RATING FROM " + CLASS_TABLE + " WHERE subject = " + new_sub + " AND CATALOG_NUMBER = " + catalogNumber + " AND SECTION = " + sess;
        System.out.println("prof rating query is: " + selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            prof_rating.add(cursor.getString(cursor.getColumnIndex("INSTRUCTOR")) + " " + cursor.getString(cursor.getColumnIndex("INSTRUCTOR_RATING")));
        }
        cursor.close();
        db.close();

        return prof_rating;
    }


    public List<String> getAllCourse(String sub) {
        List<String> course_from_db = new ArrayList<String>();
        String selectQuery;
        // boolean filters0
        String subject = "\""+ sub + "\"";
        // String selectQuery = "SELECT DISTINCT CATALOG_NUMBER, TITLE FROM " + CLASS_TABLE + " WHERE SUBJECT = " + subject;
        selectQuery = "SELECT DISTINCT CATALOG_NUMBER, TITLE FROM " + CLASS_TABLE + " WHERE SUBJECT = " + subject + " ORDER BY CATALOG_NUMBER";
        System.out.println("selected course number and title with filters: " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            course_from_db.add(cursor.getString(cursor.getColumnIndex("catalog_number")) + " " + cursor.getString(cursor.getColumnIndex("TITLE")));
        }
        cursor.close();
        db.close();
        return course_from_db;
    }

    public List<String> getAllTitle(String sub, String course_num){

        List<String> title_from_db = new ArrayList<String>();
        String selectQuery = "SELECT title FROM " + CLASS_TABLE + " WHERE subject = " + sub + " AND course_num = " + course_num;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            title_from_db.add(cursor.getString(cursor.getColumnIndex("TITLE")));
        }
        cursor.close();
        db.close();
        return title_from_db;
    }

    public List<String> getAllSection(String sub, String course_num, boolean[] filters) {
        List<String> course_from_db = new ArrayList<String>();
        course_from_db.add("ALL");

        String query_laterthan10 = "";
        String query_indaytime = "";
        String final_query = "";
        String query_notbetween = "";
        String query_notbetween2="";
        String subject = "\""+ sub + "\"";
        if(filters != null) {
            if (filters[0]) {
                query_laterthan10 = " AND strftime('%H:%M:%S', START_TIME) > '10:00:00'";
            }
            if (filters[1]) {
                query_notbetween = " AND (strftime('%H:%M:%S', START_TIME) NOT BETWEEN '12:00:00' AND '13:00:00')";
                query_notbetween2 = " AND (strftime('%H:%M:%S', END_TIME) NOT BETWEEN '13:00:00' AND '13:59:00')";
            }
            if (filters[2]) {
                query_indaytime = " AND (strftime('%H:%M:%S', START_TIME) NOT BETWEEN '19:00:00' AND '23:00:00')";
            }
            course_num = "\"" + course_num + "\"";
            final_query = "SELECT SECTION, WEEKDAYS, START_TIME, END_TIME FROM " + CLASS_TABLE + " WHERE SUBJECT = " + subject + " AND CATALOG_NUMBER = " + course_num + query_laterthan10 + query_indaytime + query_notbetween + query_notbetween2;
        }else{
            course_num = "\"" + course_num + "\"";
            final_query = "SELECT SECTION, WEEKDAYS, START_TIME, END_TIME FROM " + CLASS_TABLE + " WHERE SUBJECT = " + subject + " AND CATALOG_NUMBER = " + course_num;

        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(final_query, null);

        while (cursor.moveToNext()) {
            course_from_db.add(cursor.getString(cursor.getColumnIndex("SECTION")) + " " + cursor.getString(cursor.getColumnIndex("WEEKDAYS")) + " " + cursor.getString(cursor.getColumnIndex("START_TIME")) + "-" + cursor.getString(cursor.getColumnIndex("END_TIME")));
        }
        cursor.close();
        db.close();
        return course_from_db;
    }

    public ArrayList<CourseInfo> getCourseDetails(String sub, String course_num) {
        ArrayList<CourseInfo> courseDetails = new ArrayList<>();
        String subject = "\""+ sub + "\"";
        String selectQuery = "SELECT * FROM " + CLASS_TABLE + " WHERE SUBJECT = " + subject + " AND CATALOG_NUMBER = " + course_num;
        System.out.println(selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            CourseInfo courseInfo = new CourseInfo(sub, course_num);
            courseInfo.section = cursor.getString(cursor.getColumnIndex("SECTION"));
            courseInfo.title = cursor.getString(cursor.getColumnIndex("TITLE"));
            courseInfo.capacity = cursor.getString(cursor.getColumnIndex("ENROLLMENT_CAPACITY"));
            courseInfo.enrollmentNum = cursor.getString(cursor.getColumnIndex("ENROLLMENT_TOTLE"));
            courseInfo.instructor = cursor.getString(cursor.getColumnIndex("INSTRUCTOR"));
            courseInfo.location = cursor.getString(cursor.getColumnIndex("BUILDING")) + cursor.getString(cursor.getColumnIndex("ROOM"));
            courseInfo.startTime = cursor.getString(cursor.getColumnIndex("START_TIME"));
            courseInfo.endTime = cursor.getString(cursor.getColumnIndex("END_TIME"));
            courseInfo.weekdays = cursor.getString(cursor.getColumnIndex("WEEKDAYS"));
            courseDetails.add(courseInfo);
        }
        cursor.close();
        db.close();
        return courseDetails;
    }

    public static void main(String [] args) {

        System.out.println("hello");
        //fillClassTable();
    }
}