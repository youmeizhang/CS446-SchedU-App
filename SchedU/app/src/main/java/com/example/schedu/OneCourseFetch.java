package com.example.schedu;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OneCourseFetch extends AsyncTask<String, String, Void>{
    String data = "";
    String courseList = "";
    String subject = "";
    String catalog_number = "";
    boolean update = false; // true if this instance is used to update tableClass; false for inserting data

    String data_parsed = "";
    DatabaseHelper databaseHelper = null;

    public OneCourseFetch(DatabaseHelper databaseHelper, String subject, String catalog_number, Boolean update) {
        super();
        this.subject = subject;
        this.catalog_number = catalog_number;
        this.databaseHelper = databaseHelper;
        this.update = update;
    }

    @Override
    protected Void doInBackground(String... args) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
        try {
            System.out.println("Doing " + this.subject + " " + this.catalog_number);
            URL courseUrl = new URL("https://api.uwaterloo.ca/v2/courses/"+this.subject+"/"+this.catalog_number+"/schedule.json?key=6e9e2a8c5b5114e5b4fe9d30387fec4d");
            //URL courseUrl = new URL("https://api.uwaterloo.ca/v2/courses/ANTH/660/schedule.json?key=6e9e2a8c5b5114e5b4fe9d30387fec4d");
            HttpURLConnection courseHttpURLConnection = (HttpURLConnection) courseUrl.openConnection();
            InputStream courseInputStream = courseHttpURLConnection.getInputStream();
            BufferedReader courseBufferedReader = new BufferedReader(new InputStreamReader(courseInputStream));
            String courseLine = "";
            String totalLine = "";
            while (courseLine != null) {
                courseLine = courseBufferedReader.readLine();
                totalLine = totalLine + courseLine;
            }

            JSONObject courseJO = new JSONObject(totalLine);
            courseList  = courseJO.getString("data");
            JSONArray courseJA = new JSONArray(courseList);
            for ( int i = 0; i < courseJA.length(); i++)     {
                // General information of a course
                JSONObject onecourseObj = (JSONObject) courseJA.get(i);
                String class_number = (String) onecourseObj.getString("class_number");
                String title = (String)onecourseObj.getString("title");
                String units_number =  (onecourseObj.get("units") instanceof Double) ? Double.toString((Double)onecourseObj.getDouble("units")) : (String) onecourseObj.getString("units");
                String section = (String)onecourseObj.getString("section");
                String campus = (String)onecourseObj.getString("campus");
                String enrollment_capacity = Long.toString((Long)onecourseObj.getLong("enrollment_capacity"));
                String enrollment_total = Long.toString((Long)onecourseObj.getLong("enrollment_total"));
                String academic_level = (String)onecourseObj.getString("academic_level");


                // Get class info, three key-value pair: date, location, and instructor
                JSONArray classArr = (JSONArray) onecourseObj.get("classes");
                JSONObject classInfo = (JSONObject) classArr.get(0);

                // class time and weekdays;
                JSONObject dateObj = (JSONObject)classInfo.get("date");
                String start_time = (dateObj.get("start_time") == null)? "N/A" : (String) dateObj.getString("start_time").toString();
                String end_time = (dateObj.get("end_time") == null)? "N/A" : (String) dateObj.getString("end_time").toString();
                String weekdays = (dateObj.get("weekdays") == null)? "N/A" : (String) dateObj.getString("weekdays").toString();

                // location info
                JSONObject locationOjb = (JSONObject)classInfo.get("location");
                String building, room;
                if (locationOjb.get("building") == null || locationOjb.get("room") == null) {
                    building = "N/A";
                    room = "N/A";
                }
                else {
                    building = (String) locationOjb.getString("building").toString();
                    room = (String) locationOjb.getString("room").toString();
                }

                // instructor info
                String instructor = (String) classInfo.getString("instructors").toString();
                String instructor_rating = "N/A";

                boolean retval = databaseHelper.insertClass(class_number, subject, catalog_number,
                        units_number, title, section, campus, enrollment_capacity,
                        enrollment_total, start_time, end_time, weekdays, building,
                        room, instructor, instructor_rating, academic_level);
                if (retval == false) {
                    System.out.println(subject + " " + catalog_number + " " + section + " insertion failed.");
                }
            }
            data_parsed = subject + " " + catalog_number + " is inserted";

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        System.out.println(this.data_parsed + " inserted.");
        //MainActivity.data.setText(this.data_parsed);
        //MainActivity.mydb.fillClassTable();
    }
}
