package com.example.schedu;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.*;
import java.lang.Object;

public class ReadJsonFile
{
    // base URL for UW API course info
    private static String baseURL = "https://api.uwaterloo.ca/v2/courses";

    // Uwaterloo open data API access key
    private static String key = "6e9e2a8c5b5114e5b4fe9d30387fec4d";

    // read from UW open source json data
    public static void readFromWeb (Course course){    //(String courseName, String courseNumber){

        //inline will store the JSON data streamed in string format
        String inline = "";
        String urlString  = baseURL + "/" + course.name + "/" + course.number + "/schedule.json?key=" + key;

        try
        {
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
                System.out.println("\nJSON Response in String format");
                //System.out.println(inline);
                sc.close();
            }

            // Reads the data from string object into key value pairs
            JSONParser parse = new JSONParser();
            JSONObject jobj = (JSONObject)parse.parse(inline);

            // Array of "data"
            JSONArray dataArr = (JSONArray) jobj.get("data");

            // Create a new CourseInfo object
            //CourseInfo courseInfo = new CourseInfo(course.name, course.number);

            // Loop all sets in 'data'
            for(int i=0;i<dataArr.size();i++)
            {
                CourseInfo courseInfo = new CourseInfo(course.name, course.number);

                // Get general info of a course
                JSONObject general = (JSONObject)dataArr.get(i);

                // Print info
                courseInfo.title = (String)general.get("title");
                courseInfo.capacity = (String)general.get("enrollment_capacity");
                courseInfo.section = (String)general.get("section");
                courseInfo.enrollmentNum = (String)general.get("enrollment_total");

                System.out.println("title: " +general.get("title"));
                System.out.println("class_number: " +general.get("class_number"));
                System.out.println("section: " +general.get("section"));
                System.out.println("enrollment_capacity: " +general.get("enrollment_capacity"));

                // Array of 'classes'
                JSONArray classArr = (JSONArray) general.get("classes");

                // Loop all sets in 'classes'
                for(int j=0;j<classArr.size();j++)
                {
                    // Get section info of a course
                    JSONObject sections = (JSONObject) classArr.get(j);

                    // class time and weekdays;
                    JSONObject dateObj = (JSONObject)sections.get("date");

                    System.out.println("start_time: " + dateObj.get("start_time"));
                    System.out.println("end_time: " + dateObj.get("end_time"));
                    System.out.println("weekdays: " + dateObj.get("weekdays"));

                    // location info
                    JSONObject locationOjb = (JSONObject)sections.get("location");

                    System.out.println("building: " +locationOjb.get("building"));
                    System.out.println("room: " +locationOjb.get("room"));

                    // instructor info
                    System.out.println("instructors: " +sections.get("instructors"));

                    System.out.println("\n");
                }

            }
            //Disconnect the HttpURLConnection stream
            conn.disconnect();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // test
    public static void main(String [] args) {

        String courseName = "CS";
        String courseNumber = "136";

        //readFromWeb(courseName, courseNumber);
    }

}