package com.example.gg.jsonfetch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WorkerThread implements Runnable {
    private String courseList = "";
    private String subject;
    private String catalog_number;
    private final int NUM_TRY_MAX = 150; // Number comes by tuning.  school server is not strong enough; it won't respond given too many requests.
    private final int TIME_OUT = 45000; // milliseconds
    private DatabaseManager databaseManager = MainActivity.databaseManager;



    public WorkerThread(String subject, String catalog_number){
        this.subject=subject;
        this.catalog_number=catalog_number;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" Worker Start. course = "+subject+" " + catalog_number);
        int numTry = 0;
        while (doJob() == false) { // try to build connection and read data twice at most
            numTry ++;
            if (numTry >= NUM_TRY_MAX) {
                System.out.println("Worker. Getting course info failed: " + this.subject + " " + this.catalog_number);
                return;
            } else {
                System.out.println("Worker. Retry " + this.subject +  " " + this.catalog_number);
                continue;
            }
        }
        System.out.println(Thread.currentThread().getName()+" Worker "+this.subject +  " " + this.catalog_number + " inserted");
    }

    private boolean doJob() {
        //System.out.println("Worker. dojob head ");
        boolean retval = false;
        HttpURLConnection courseHttpURLConnection = null;
        try {
            URL courseUrl = new URL("https://api.uwaterloo.ca/v2/courses/"+this.subject+"/"+this.catalog_number+"/schedule.json?key=6e9e2a8c5b5114e5b4fe9d30387fec4d");
            //URL courseUrl = new URL("https://api.uwaterloo.ca/v2/courses/cs/446/schedule.json?key=6e9e2a8c5b5114e5b4fe9d30387fec4d");

            courseHttpURLConnection = (HttpURLConnection) courseUrl.openConnection();

            courseHttpURLConnection.setConnectTimeout(TIME_OUT); // timeout 30 sec
            courseHttpURLConnection.setReadTimeout(TIME_OUT);    // readtimeout 30 sec
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
            System.out.println("Worker. dojob obtained all sections ");
            //if (courseJA.length() == 0) System.out.println("NO COURSE OFFERED - "+subject + " " + catalog_number);
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

                boolean DBretval = false;
                //System.out.println("Worker time. Start inserting "+subject + " " + catalog_number + " " + section);
                //int time = (int) (System.currentTimeMillis());
                FetchAllCourse.increment();
                //System.out.println("Worker time. Inserting done "+subject + " " + catalog_number + " " + section + ". time: " + ((int) (System.currentTimeMillis()) - time));


                DBretval = databaseManager.insertClass(class_number, subject, catalog_number,
                        units_number, title, section, campus, enrollment_capacity,
                        enrollment_total, start_time, end_time, weekdays, building,
                        room, instructor, instructor_rating, academic_level);

                // Getting course info failed      insertion failed      time out
                if (DBretval == false) {
                    System.out.println("Worker. courseINFO "+ subject + " " + catalog_number + " " + section + " insertion failed.");
                } else {
                    System.out.println("Worker. WcourseINFO " + subject + " " + catalog_number + " " + section + " inserted");
                }

            }
            retval = true;

        } catch (java.net.SocketTimeoutException e) {
            e.printStackTrace();
            System.out.println("Worker. fetching-data time out: " + this.subject + " " + this.catalog_number);
            System.out.println("AAAAA11 " + subject + " " + catalog_number + ". " + e);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("AAAAA22 " + subject + " " + catalog_number + ". " + e);
        } catch (IOException e) {
            e.printStackTrace();
            if (!(e instanceof FileNotFoundException)) System.out.println("AAAAA33 " + subject + " " + catalog_number + ". " + e);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("AAAAA44 " + subject + " " + catalog_number + ". " + e);
        }finally {
            courseHttpURLConnection.disconnect();
        }


        return retval;
    }

    @Override
    public String toString(){
        return this.subject + "** " + this.catalog_number;
    }
}