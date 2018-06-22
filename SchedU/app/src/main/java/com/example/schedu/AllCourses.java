package com.example.schedu;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.*;


public class AllCourses
{
    // base URL for UW API all course info and access key
    private static String urlString = "https://api.uwaterloo.ca/v2/courses.json?key=6e9e2a8c5b5114e5b4fe9d30387fec4d";

    // list of all offered courses
    public static List<Course> courseOfferList = new ArrayList<>();

    // print list
    public static void printAllCourses(){
        for (Course c: courseOfferList) {
            System.out.println(c.courseId + "; " + c.name + " " + c.number + "; " + c.courseTitle);
        }
    }


    // read from UW open source json data
    public static void readFromWeb (){

        //inline will store the JSON data streamed in string format
        String inline = "";

        try
        {
            String urlString = "https://api.uwaterloo.ca/v2/courses.json?key=6e9e2a8c5b5114e5b4fe9d30387fec4d";
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
            // Loop all sets in 'data'
            for(int i=0;i<dataArr.size();i++)
            {
                // Create a new CourseInfo object
                Course course = new Course();

                // Get general info of a course
                JSONObject general = (JSONObject)dataArr.get(i);

                // General course info
                course.courseId = general.get("course_id").toString();
                course.name = general.get("subject").toString();
                course.number = general.get("catalog_number").toString();
                course.courseTitle = general.get("title").toString();

                courseOfferList.add(course);
            }

            printAllCourses();
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

        readFromWeb();
    }

}