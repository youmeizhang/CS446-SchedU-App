package com.example.schedu;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "usersdb";
    private static final String TABLE_Users = "userdetails";
    private static final String KEY_ID = "id";
    private static final String KEY_SUB = "subject";
    private static final String KEY_COU = "course";
    private static final String KEY_SESS = "session";
    private static final String KEY_PRIO = "priority";

    SQLiteDatabase db;

    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
        //onCreate(db);
    }
    @Override
    public void onCreate(SQLiteDatabase db){

        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);

        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SUB + " TEXT,"
            + KEY_COU + " TEXT,"
            + KEY_SESS + " TEXT,"
            + KEY_PRIO + " TEXT"
            + ")";
        System.out.println(CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        onCreate(db);
    }

    public void insertUserDetails(String subject, String course, String session, String priority){
        //onCreate(db);
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_SUB, subject);
        cValues.put(KEY_COU, course);
        cValues.put(KEY_SESS, session);
        cValues.put(KEY_PRIO, priority);
        long newRowId = db.insert(TABLE_Users,null, cValues);
        System.out.println("Data inserted as: " + cValues);
        db.close();
    }

    public ArrayList<HashMap<String, String>> GetUsers(){
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT id, subject, course, session, priority FROM "+ TABLE_Users + " ORDER BY " + KEY_PRIO + " DESC";

        System.out.println("QUERY is " + query);

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put("subject",cursor.getString(cursor.getColumnIndex(KEY_SUB)));
            user.put("course",cursor.getString(cursor.getColumnIndex(KEY_COU)));
            user.put("session",cursor.getString(cursor.getColumnIndex(KEY_SESS)));
            user.put("priority", cursor.getString(cursor.getColumnIndex(KEY_PRIO)));
            userList.add(user);
        }
        return  userList;
    }

    // Get User Details based on userid
    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT subject, course, session, priority FROM "+ TABLE_Users + "ORDER BY " + KEY_PRIO + " DESC";
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_SUB, KEY_COU, KEY_SESS, KEY_PRIO}, KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("subject",cursor.getString(cursor.getColumnIndex(KEY_SUB)));
            user.put("course",cursor.getString(cursor.getColumnIndex(KEY_COU)));
            user.put("session",cursor.getString(cursor.getColumnIndex(KEY_SESS)));
            user.put("priority", cursor.getString(cursor.getColumnIndex(KEY_PRIO)));
            userList.add(user);
        }
        return  userList;
    }

    // Delete User Details
    public void DeleteUser(long userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users, KEY_ID+" = ?",new String[]{String.valueOf(userid)});
        db.close();
    }

    // Update User Details
    public int UpdateUserDetails(String course, String session, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_COU, course);
        cVals.put(KEY_SESS, session);
        int count = db.update(TABLE_Users, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }
}
