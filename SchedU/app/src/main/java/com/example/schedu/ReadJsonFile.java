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



            // Loop all sets in 'data'
            for(int i=0;i<dataArr.size();i++)
            {
                // Create a new CourseInfo object
                CourseInfo courseInfo = new CourseInfo(course.name, course.number);

                // Get general info of a course
                JSONObject general = (JSONObject)dataArr.get(i);

                // General course info
                courseInfo.title = general.get("title").toString();
                courseInfo.section = general.get("section").toString();
                courseInfo.capacity = general.get("enrollment_capacity").toString();
                courseInfo.enrollmentNum = general.get("enrollment_total").toString();

              // Array of 'classes'
                JSONArray classArr = (JSONArray) general.get("classes");

                // Loop all sets in 'classes'
                for(int j=0;j<classArr.size();j++)
                {
                    // Get section info of a course
                    JSONObject sections = (JSONObject) classArr.get(j);

                    // class time and weekdays;
                    JSONObject dateObj = (JSONObject)sections.get("date");

                    courseInfo.startTime = dateObj.get("start_time").toString();
                    courseInfo.endTime = dateObj.get("end_time").toString();
                    courseInfo.weekdays = dateObj.get("weekdays").toString();

                    // location info
                    JSONObject locationOjb = (JSONObject)sections.get("location");
                    if (locationOjb.get("building") == null || locationOjb.get("room") == null)
                        courseInfo.location = "NA";
                    else
                        courseInfo.location = locationOjb.get("building").toString() + locationOjb.get("room").toString();

                    // instructor info
                    courseInfo.instructor = sections.get("instructors").toString();

                    // find out the type of section: TUT, LEC or TST
                    if (courseInfo.section.toLowerCase().contains("lec"))
                        course.lectures.add(courseInfo);
                    if (courseInfo.section.toLowerCase().contains("tut"))
                        course.tutorials.add(courseInfo);
                    if (courseInfo.section.toLowerCase().contains("tst"))
                        course.tests.add(courseInfo);
                }
            }
            course.printCourseSummary();
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

        Course c = new Course();
        c.name = "CS";
        c.number = "136";

        readFromWeb(c);
    }

}