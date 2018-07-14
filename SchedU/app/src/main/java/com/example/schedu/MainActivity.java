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
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private static final int PROFILE_PIC_SIZE = 400;
    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean mShouldResolve;
    private ConnectionResult connectionResult;

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

    public static ArrayList<TimeTable> allTimetables = new ArrayList<>();
    public static ArrayList<Integer> colorList = new ArrayList<>();

    public static DatabaseManager databaseManager;

    String string_subject;
    String string_course;
    String string_session;
    String string_priority;
    String s;
    Button login;

    long int_course;

    List<String> course_from_db;
    List<String> section_from_db;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subject = (Spinner)findViewById(R.id.spinner_subject);
        course = (Spinner)findViewById(R.id.spinner_course);
        session = (Spinner)findViewById(R.id.spinner_session);
        priority = (Spinner)findViewById(R.id.spinner_priority);

        textView1 = (TextView)findViewById(R.id.display_info);
        textView2 = (TextView)findViewById(R.id.display_read);

        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, GoogleCalendar.class);
                startActivity(i);
            }
        });

        // get database manger
        databaseManager = DatabaseManager.getHelper(this);
        FetchAllCourse process = new FetchAllCourse(databaseManager);
        //process.execute(); //fill all class information into classTable

        final List<String> sub_from_db = databaseManager.getAllLabels();
        System.out.println("all subjects are here: " + sub_from_db);

        subAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, sub_from_db);
        subject.setAdapter(subAdapter);

        subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string_subject = parent.getItemAtPosition(position).toString();
                course_from_db = databaseManager.getAllCourse(string_subject);
                System.out.println(course_from_db);

                couAdapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, course_from_db);
                course.setAdapter(couAdapter);

                //String[] subject = getResources().getStringArray(R.array.subjects);
                //textView.setText("the subject you choose is:" + subject[position]);
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

                String[] list = string_course.split(" ");
                String courseNumber = list[0];
                System.out.println(courseNumber);

                section_from_db = databaseManager.getAllSection(string_subject, courseNumber);

                System.out.println(section_from_db);

                sesAdapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, section_from_db);
                session.setAdapter(sesAdapter);
                //s = string_subject + string_course;
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
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("idNumber", 0);

                startActivity(i);
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

        System.out.println("selected courses: ");

        for (Course c: data){
            System.out.println("=====" + c.name + " " + c.number + " " + c.sectionNumber + " " + c.priority + "=====");

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
            System.out.println("=============================================");
        }

        PermutationGenerator pg = new PermutationGenerator();
        ArrayList<ArrayList<CourseInfo>> input = new ArrayList<>();

        for(Course c: data){
            if (c.lectures.size() != 0)
                input.add(c.lectures);
            if (c.tutorials.size() != 0)
                input.add(c.tutorials);
        }

        ArrayList<TimeTable> output = pg.permutate(input);
        for (TimeTable t : output) {
            for(CourseInfo c : t.contents){
                System.out.println(c.name + " " + c.section);
            }
            System.out.println("---------------------------------");
        }
        System.out.println("TOTAL: " + output.size());

        for(TimeTable t: output){
            // validated schedules
            if (pg.validate(t.contents))
                allTimeTables.add(t);

        }

        return allTimeTables;
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
