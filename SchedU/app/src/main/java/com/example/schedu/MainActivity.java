package com.example.schedu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity  {

    public static Context mainContext;

    private TextView textView1;
    private TextView textView2;
    private Spinner subject;
    private Spinner course;
    private Spinner session;
    private Spinner priority;
    private Button btn_gen;
    private Button btn_add;
    private Button btn_finish;
    private Button btn_finish_new;
    private String[][] dataStrings = { {"446 Software Architecture and Design", "486 Introduction to Artificial Intelligence", "698 Introduction to Research Topics"},
            {"656 Database System", "657A Data Model & Knowledge", "653 Quality Assurance"}, {"641 Text Analysis", "640 Big Data"}, };
    private ArrayAdapter<CharSequence> adapter;
    private ArrayAdapter<String> subAdapter;
    private ArrayAdapter<String> couAdapter;
    private ArrayAdapter<String> sesAdapter;

    private boolean[] filters = {false, false, false, false, false};
    //private boolean[] filters;
    private boolean gotofilter;
    private Button btn_filter;
    private CheckBox cb_filter;

    public static TimeTable curTimeTable;
    public static ArrayList<TimeTable> allTimetables = new ArrayList<>();
    public static ArrayList<Integer> colorList = new ArrayList<>();

    public static DatabaseManager databaseManager;
    public static com.google.api.services.calendar.Calendar service;

    String string_subject;
    String string_course;
    String string_session;
    String string_priority;
    String s;
    Button login;

    List<String> course_from_db;
    List<String> section_from_db;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GoogleCalendar a = new GoogleCalendar();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainContext = getApplicationContext();

        subject = (Spinner)findViewById(R.id.spinner_subject);
        course = (Spinner)findViewById(R.id.spinner_course);
        session = (Spinner)findViewById(R.id.spinner_session);
        priority = (Spinner)findViewById(R.id.spinner_priority);

        textView1 = (TextView)findViewById(R.id.display_info);
        textView2 = (TextView)findViewById(R.id.display_read);

        cb_filter = findViewById(R.id.needfilter);


        // get database manger
        databaseManager = DatabaseManager.getHelper(this);
        //FetchAllCourse process = new FetchAllCourse(databaseManager);
        //process.execute(); //fill all class information into classTable

        filters = getIntent().getBooleanArrayExtra("filters");
        if(filters != null) {
            System.out.println("content of filters_new is :" + filters[0] + filters[1] + filters[2] + filters[3] + filters[4]);
        }


        gotofilter = getIntent().getBooleanExtra("needed", false);


        final List<String> sub_from_db = databaseManager.getAllLabels();

        subAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, sub_from_db);
        subject.setAdapter(subAdapter);

        subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string_subject = parent.getItemAtPosition(position).toString();
                course_from_db = databaseManager.getAllCourse(string_subject);

                System.out.println("all course with filters are here" + course_from_db);

                couAdapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, course_from_db);
                course.setAdapter(couAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //string_course = parent.getItemAtPosition(position).toString();
                string_course = parent.getItemAtPosition(position).toString();
                List<String> final_section = new ArrayList<String>();
                final_section.add("ALL");

                String[] list = string_course.split(" ");
                String courseNumber = list[0];
                System.out.println(courseNumber);

                section_from_db = databaseManager.getAllSection(string_subject, courseNumber, filters);

                System.out.println(section_from_db); // section are here
                for (int i=1; i < section_from_db.size(); i++) {
                    String[] tmp = section_from_db.get(i).split(" ")[3].split("-");
                    String startTime = tmp[0].replace(":", "");
                    String endTime = tmp[1].replace(":", "");
                    try {
                        int s_tmp = Integer.parseInt(startTime);
                        int e_tmp = Integer.parseInt(endTime);
                        long duration = Calculation.timeDifference(startTime, endTime);
                        if (s_tmp < 1200 && e_tmp > 1300) {
                            continue;
                        } else if (filters != null && duration > 120 && filters[4]) {
                            continue;
                        } else {
                            final_section.add(section_from_db.get(i));
                        }
                    } catch (Exception e){
                        continue;
                    }
                }
                System.out.println(final_section);
                sesAdapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, final_section);
                session.setAdapter(sesAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        session.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string_session = session.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string_priority = priority.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_gen = (Button)findViewById(R.id.generate);
        btn_gen.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                initColors();
                allTimetables = findCourse();
                System.out.println("Total " + allTimetables.size() + " is generated");
                if (allTimetables.size() > 0) {
                    curTimeTable = allTimetables.get(0);
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                    i.putExtra("idNumber", 0);

                    startActivity(i);
                }
                else
                    Toast.makeText(getApplicationContext(),"No valid schedule!",Toast.LENGTH_SHORT).show();
            }
        });

        btn_add = (Button)findViewById(R.id.add_course);

        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DbHandler dbHandler = new DbHandler(MainActivity.this);
                dbHandler.insertUserDetails(string_subject, string_course, string_session, string_priority);

                Toast.makeText(getApplicationContext(), "Course Added Successfully", Toast.LENGTH_SHORT).show();

            }
        });

        btn_filter = (Button)findViewById(R.id.btn_filter);
        btn_filter.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //filters = getIntent().getBooleanArrayExtra("filters");
                cb_filter.setChecked(true);
                System.out.println("User is going to add Filter...");

                Intent i = new Intent(MainActivity.this, AddFilters.class);
                //i.putExtra("filters",filters);
                startActivity(i);
            }
        });


        //filters = getIntent().getBooleanArrayExtra("filters");
        cb_filter.setChecked(gotofilter);

        btn_finish_new = (Button)findViewById(R.id.btn_finish);
        btn_finish_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });
    }
    public ArrayList<TimeTable> findCourse(){
        DbHandler dbHandler = new DbHandler(MainActivity.this);
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        ArrayList<TimeTable> allTimeTables = new ArrayList<>();

        ArrayList<Course> data = dbHandler.GetCourses();

        for (Course c: data){

            ArrayList<CourseInfo> courseInfos = databaseHelper.getCourseDetails(c.name, c.number);
            if (c.sectionNumber.contains("ALL")) {
                for (CourseInfo courseInfo: courseInfos){
                    if (courseInfo.section.toLowerCase().contains("lec"))
                        c.lectures.add(courseInfo);
                    if (courseInfo.section.toLowerCase().contains("tut"))
                        c.tutorials.add(courseInfo);
                }
            } else {
                for (CourseInfo courseInfo: courseInfos){
                    if (courseInfo.section.contains(c.sectionNumber) || c.sectionNumber.contains(courseInfo.section)){
                        if (c.sectionNumber.toLowerCase().contains("lec"))
                            c.lectures.add(courseInfo);
                        if (c.sectionNumber.toLowerCase().contains("tut"))
                            c.tutorials.add(courseInfo);
                    }
                }
            }
            c.printCourseSummary();

        }
        FindConstrains.executeSATSolver(data);

        return FindConstrains.allTimetables;
    }

    // colors for each course in timetable
    public void initColors(){
        colorList = new ArrayList<>();

        colorList.add(getResources().getColor(R.color.lightBlue));
        colorList.add(getResources().getColor(R.color.lightGrey));
        colorList.add(getResources().getColor(R.color.lightPink));
        colorList.add(getResources().getColor(R.color.lightGreen));
        colorList.add(getResources().getColor(R.color.lightPurple));
        colorList.add(getResources().getColor(R.color.greenBlue));
        colorList.add(getResources().getColor(R.color.darkPink));
        colorList.add(getResources().getColor(R.color.darkPurple));
        colorList.add(getResources().getColor(R.color.myOrange));
        colorList.add(getResources().getColor(R.color.yellow));
        colorList.add(getResources().getColor(R.color.darkBlue));
        colorList.add(getResources().getColor(R.color.greenYellow));
    }


}
