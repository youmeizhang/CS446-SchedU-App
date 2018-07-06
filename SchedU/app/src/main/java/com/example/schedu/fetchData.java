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

public class fetchData extends AsyncTask<DatabaseHelper, Void, Void> {
    String data = "";
    String courseList = "";
    String subject = "";
    String catalog_number = "";

    String data_parsed = "";
    DatabaseHelper databaseHelper = null;
    String units_number;
    String title;
    String section;
    String campus;
    String enrollment_capacity;
    String enrollment_totle;
    String start_time;
    String end_time;
    String weekdays;
    String building;
    String room;
    String instructor;
    String instructor_rating;
    String academic_level;

    public fetchData(DatabaseHelper databaseHelper) {
        super();
        this.databaseHelper = databaseHelper;
    }

    @Override
    protected Void doInBackground(DatabaseHelper ... args) {
        if(android.os.Debug.isDebuggerConnected())        // these two lines for debugging
            android.os.Debug.waitForDebugger();
        try {
            URL url = new URL("https://api.uwaterloo.ca/v2/courses.json?key=6e9e2a8c5b5114e5b4fe9d30387fec4d");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONObject JO = new JSONObject(data);
            courseList  = JO.getString("data");
            JSONArray JA = new JSONArray(courseList);
            int ct = 0;
            //for (int i = 0; i < JA.length(); i++) {
            for (int i = 100; i < JA.length(); i++) {
                JSONObject courseObj = (JSONObject) JA.get(i);
                subject  = courseObj.getString("subject");
                catalog_number  = courseObj.getString("catalog_number");
//                units_number = courseObj.getString("units_numer");
//                title = courseObj.getString("title");
//                section = courseObj.getString("section");
//                campus = courseObj.getString("campus");
//                enrollment_capacity = courseObj.getString("enrollment_capacity");
//                enrollment_totle = courseObj.getString("enrollment_totle");
//                start_time = courseObj.getString("start_time");
//                end_time = courseObj.getString("end_time");
//                weekdays = courseObj.getString("weekdays");
//                building = courseObj.getString("building");
//                room = courseObj.getString("room");
//                instructor = courseObj.getString("instructor");
//                instructor_rating = courseObj.getString("instructor_rating");
//                academic_level = courseObj.getString("academic_level");

                OneCourseFetch oneCourseFetch = new OneCourseFetch(databaseHelper, subject, catalog_number, false);
                oneCourseFetch.execute();// fill in or update this course info in classTable


                /*
                URL courseUrl = new URL("https://api.uwaterloo.ca/v2/courses/cs/136/schedule.json?key=6e9e2a8c5b5114e5b4fe9d30387fec4d");
                HttpURLConnection courseHttpURLConnection = (HttpURLConnection) courseUrl.openConnection();
                InputStream courseInputStream = courseHttpURLConnection.getInputStream();
                BufferedReader courseBufferedReader = new BufferedReader(new InputStreamReader(courseInputStream));
                String courseLine = "";
                String totalLine = "";
                while (courseLine != null) {
                    courseLine = courseBufferedReader.readLine();
                    totalLine = totalLine + courseLine;
                }
                JSONObject courseJO = new JSONObject(data);
                courseList  = courseJO.getString("data");
                JSONArray courseJA = new JSONArray(courseList);
                JSONObject onecourseObj = (JSONObject) courseJA.get(0);
                subject = onecourseObj.getString("title");
                catalog_number = onecourseObj.getString("class_number");
                data_parsed = subject + " " + catalog_number;
                */



                //data_parsed = subject + " " + catalog_number;
                //if (ct == 10) break;
                //break;
            }



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

        //MainActivity.data.setText(this.data_parsed);
        //MainActivity.mydb.fillClassTable();
    }
}
