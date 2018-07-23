package com.example.schedu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BASE_URL = "https://api.uwaterloo.ca/v2/courses"; // base URL for UW API course info
    private static final String KEY = "6e9e2a8c5b5114e5b4fe9d30387fec4d"; // Uwaterloo open data API access key


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


    public DatabaseHelper(Context context) {

        super(context, "UWCourseDB", null, 52);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Start create new course detail table.......");
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

    public List<String> getAllLabels(){
        List<String> sub_from_db = new ArrayList<String>();
        String selectQuery = "SELECT DISTINCT subject FROM " + CLASS_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i = 0;
        while (cursor.moveToNext()) {
            sub_from_db.add(cursor.getString(cursor.getColumnIndex("SUBJECT")));
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
        String selectQuery = "SELECT CATALOG_NUMBER, TITLE FROM " + CLASS_TABLE + " WHERE SUBJECT = " + subject;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            course_with_num.add(cursor.getString(cursor.getColumnIndex("CATALOG_NUMBER")));
        }
        cursor.close();
        db.close();
        return course_with_num;
    }

    public List<String> getAllCourse(String sub) {
        List<String> course_from_db = new ArrayList<String>();

        String subject = "\""+ sub + "\"";
        String selectQuery = "SELECT DISTINCT CATALOG_NUMBER, TITLE FROM " + CLASS_TABLE + " WHERE SUBJECT = " + subject;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            course_from_db.add(cursor.getString(cursor.getColumnIndex("CATALOG_NUMBER")) + " " + cursor.getString(cursor.getColumnIndex("TITLE")));
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

    public List<String> getAllSection(String sub, String course_num) {
        List<String> course_from_db = new ArrayList<String>();
        course_from_db.add("ALL");
        String subject = "\""+ sub + "\"";
        String selectQuery = "SELECT SECTION FROM " + CLASS_TABLE + " WHERE SUBJECT = " + subject + " AND CATALOG_NUMBER = " + course_num;
        System.out.println(selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            course_from_db.add(cursor.getString(cursor.getColumnIndex("SECTION")));
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


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CLASS_TABLE);
        onCreate(db);
    }
    //public static void fillClassTable(){
    public void fillClassTable() {
        //new JSONTask().execute();
        /*
        try {
            new JSONTask().execute("f");

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        */

        /*sun.net.www.protocol.https.DelegateHttpsURLConnection:https://api.uwaterloo.ca/v2/courses.json?key=6e9e2a8c5b5114e5b4fe9d30387fec4d
        //public void fillClassTable() {
        //SQLiteDatabase db = this.getWritableDatabase();
        Vector subject_v = new Vector();
        Vector catalog_num_v = new Vector();

        // get all subjects and the catalog_number, such as CS 136
        try
        {
            String inline = "";
            String urlString  = BASE_URL + ".json?key=" + KEY; //https://api.uwaterloo.ca/v2/courses.json?key=6e9e2a8c5b5114e5b4fe9d30387fec4d
            URL url = new URL(urlString);
            // Open URL connection to access json data
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            // Send HTTP GET request
            conn.setRequestMethod("GET");
            conn.connect();
            //Get the response status of the UW API
            int responsecode = conn.getResponseCode();
            System.out.println("Response code is: " +responsecode);
            // Throw a runtime exception if request does not succeed
            if(responsecode != 200) {
                System.out.println("BAD CONNECTION");
                throw new RuntimeException("HttpResponseCode: " +responsecode);
            }
            else
            {
                // Read the JSON data
                Scanner sc = new Scanner(url.openStream());
                while(sc.hasNext())
                {
                    inline+=sc.nextLine();
                }
                //System.out.println("\nJSON Response in String format");
                //System.out.println(inline);
                sc.close();
            }

            // Reads the data from string object into key value pairs
            JSONParser parse = new JSONParser();
            JSONObject jobj = (JSONObject)parse.parse(inline);

            // Array of "data"
            JSONArray dataArr = (JSONArray) jobj.get("data");

            for(int i=0;i<dataArr.size();i++)
            {
                // Get general info of a course
                org.json.simple.JSONObject general = (org.json.simple.JSONObject)dataArr.get(i);

                subject_v.addElement((String)general.get("subject"));
                catalog_num_v.addElement((String)general.get("catalog_number"));
            }
            //Disconnect the HttpURLConnection stream
            conn.disconnect();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        // get all classes and fill in the class_table
        System.out.println("total record is " + subject_v.size());
        int ct = 0;
        for ( int i = 0; i < subject_v.size(); i++) {
            ct ++;
            //System.out.println("DOING " + subject_v.get(i) + " " + catalog_num_v.get(i));
            try
            {
                String subject = (String)subject_v.get(i);
                String catalog_number = (String) catalog_num_v.get(i);
                String inline = "";
                String urlString  = BASE_URL + "/" + subject + "/" + catalog_number + "/schedule.json?key=" + KEY; //https://api.uwaterloo.ca/v2/courses.json?key=6e9e2a8c5b5114e5b4fe9d30387fec4d
                URL url = new URL(urlString);
                // Open URL connection to access json data
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                // Send HTTP GET request
                conn.setRequestMethod("GET");
                conn.connect();
                //Get the response status of the UW API
                int responsecode = conn.getResponseCode();
                //System.out.println("Response code is: " +responsecode);
                // Throw a runtime exception if request does not succeed

                if(responsecode != 200)
                    throw new RuntimeException("HttpResponseCode: " +responsecode);
                else
                {
                    // Read the JSON data
                    Scanner sc = new Scanner(url.openStream());
                    while(sc.hasNext())
                    {
                        inline+=sc.nextLine();
                    }
                    //System.out.println(inline);
                    sc.close();
                }

                // Reads the data from string object into key value pairs
                JSONParser parse = new JSONParser();
                JSONObject jobj = (JSONObject)parse.parse(inline);

                // Array of "data"
                JSONArray dataArr = (JSONArray) jobj.get("data");
                //System.out.println(subject_v.get(i) + " " + catalog_num_v.get(i) + " size is " + dataArr.size());

                for(int j = 0; j < dataArr.size(); j++)
                {
                    //System.out.println("j is " + j);
                    // Get general info of a course
                    org.json.simple.JSONObject general = (org.json.simple.JSONObject)dataArr.get(j);

                    String class_number = Long.toString((Long)general.get("class_number"));
                    String units = (general.get("units") instanceof Double) ? Double.toString((Double)general.get("units")) : Long.toString((Long)general.get("units"));
                    String title = (String)general.get("title");
                    String section = (String)general.get("section");
                    String campus = (String)general.get("campus");
                    String enrollment_capacity = Long.toString((Long)general.get("enrollment_capacity"));
                    String enrollment_total = Long.toString((Long)general.get("enrollment_total"));
                    String academic_level = (String)general.get("academic_level");

                    // Get class info, three key-value pair: date, location, and instructor
                    JSONArray classArr = (JSONArray) general.get("classes");
                    JSONObject classInfo = (JSONObject) classArr.get(0);

                    // class time and weekdays;
                    JSONObject dateObj = (JSONObject)classInfo.get("date");
                    //if (dateObj.get("start_time") == null) System.out.println("NULL LAAAAAAAAAA");
                    String start_time = (dateObj.get("start_time") == null)? "N/A" : (String) dateObj.get("start_time").toString();
                    String end_time = (dateObj.get("end_time") == null)? "N/A" : (String) dateObj.get("end_time").toString();
                    String weekdays = (dateObj.get("weekdays") == null)? "N/A" : (String) dateObj.get("weekdays").toString();

                    // location info
                    JSONObject locationOjb = (JSONObject)classInfo.get("location");
                    String building, room;
                    if (locationOjb.get("building") == null || locationOjb.get("room") == null) {
                        building = "N/A";
                        room = "N/A";
                    }
                    else {
                        building = (String) locationOjb.get("building").toString();
                        room = (String) locationOjb.get("room").toString();
                    }

                    // instructor info
                    String instructor = (String) classInfo.get("instructors").toString();
                    String instructor_rating = "N/A";
                    insertClass(class_number, subject, catalog_number,
                            units, title, section,
                            campus, enrollment_capacity, enrollment_total,
                            start_time, end_time, weekdays,
                            building, room, instructor,
                            instructor_rating, academic_level);
                }
                //Disconnect the HttpURLConnection stream
                conn.disconnect();
                if (ct == 10) break;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("TOTAL classes" + ct);
        */
    }

    // to insert a row of data
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

        // insert this row into the table
        long rev = db.insert(CLASS_TABLE, null, contentValues);

        return -1 != rev;
    }

    public static void main(String [] args) {

        System.out.println("hello");
        //fillClassTable();
    }



//    private class JSONTask extends AsyncTask<String, String, String> {
//        protected String doInBackground(String... urls) {
//            try{
//                String inline = "";
//                String urlString = "https://api.uwaterloo.ca/v2/courses.json?key=6e9e2a8c5b5114e5b4fe9d30387fec4d";
//                URL url = new URL(urlString);
//                HttpURLConnection conn = (HttpURLConnection)url.openConnection();// after run this, conn.setRequestMethod("GET");
//                conn.setRequestMethod("GET");
//                conn.connect();
//                int responsecode = conn.getResponseCode();
//                System.out.println(responsecode);
//            }
//            catch(Exception e)
//            {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        protected void onProgressUpdate(Integer... progress) {
//            super.onProgressUpdate();
//            //setProgressPercent(progress[0]);
//        }
//
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            //showDialog("Downloaded " + result + " bytes");
//        }
//    }

}













