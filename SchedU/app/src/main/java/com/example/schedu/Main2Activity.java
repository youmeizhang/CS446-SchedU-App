package com.example.schedu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;

import org.w3c.dom.Text;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {


    private Context mContext;
    private Activity mActivity;
    private Button like;
    private TextView M0830;
    private ArrayList<TextView> tvList;
    private TableLayout tableLayout;
    private ArrayList<Integer> colorList;

    private String capacity;
    private String coursename;
    private String enrollmentNumber;
    private String location;
    private String title;
    private int id;

    public ArrayList<TimeTable> findCourse(){
        DbHandler dbHandler = new DbHandler(Main2Activity.this);
        DatabaseHelper databaseHelper = new DatabaseHelper(Main2Activity.this);
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
        // selected courses
        SelectedCoures sc = new SelectedCoures();
        sc.add(data);

        TimeTable t = sc.hardCode();

        return allTimeTables;
    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvList = new ArrayList<>();
        initColors();

        int curColor = 0;
        id = getIntent().getIntExtra("idNumber", 0);

        TimeTable timeTable = MainActivity.allTimetables.get(id);

        for (CourseInfo i : timeTable.contents) {
            final String courseName = i.name + i.number + " " + i.section;
            String start = i.startTime.replace(":", "");
            String end = i.endTime.replace(":", "");
            String weekdays = i.weekdays.toUpperCase();

            coursename = courseName;
            capacity = i.capacity;
            enrollmentNumber = i.enrollmentNum;
            location = i.location;
            title = i.title;

            System.out.println("course info: " + courseName + " " + start + " " + end + " " + weekdays+ " "+ capacity);


            String textViewId;
            int color = colorList.get(curColor);
            int duration_start = 0;
            int duration_end = (int) Calculation.timeDifference(start, end) / 30;


            final View.OnClickListener myClickListener = new View.OnClickListener() {
                String coursename_tmp = coursename;
                String capacity_tmp = capacity;
                String enrollmentNumber_tmp = enrollmentNumber;
                String location_tmp = location;
                String title_tmp = title;
                public void onClick(View view) {
                    Intent intent = new Intent(Main2Activity.this, CourseDetail.class);
                    intent.putExtra("CourseName", coursename_tmp);
                    intent.putExtra("Capacity", capacity_tmp);
                    intent.putExtra("EnrollmentNumber", enrollmentNumber_tmp);
                    intent.putExtra("Location", location_tmp);
                    intent.putExtra("Title", title_tmp);
                    startActivity(intent);
                }
            };

            final View.OnClickListener todoList = new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(Main2Activity.this, TodoList.class);
                    startActivity(intent);
                }
            };

            while (duration_start <= duration_end) {
                if (weekdays.contains("M")) {
                    textViewId = "M" + start;
                    int resID = getResources().getIdentifier(textViewId, "id", getPackageName());
                    TextView textView = new TextView(this);
                    textView = (TextView) findViewById(resID);
                    if (duration_start == 0)
                        textView.setText(courseName);
                    textView.setBackgroundColor(color);
                    textView.setOnClickListener(myClickListener);
                    tvList.add(textView);
                }
                if ((weekdays.contains("T") && !weekdays.contains("H")) || weekdays.contains("TTH")) {
                    textViewId = "T" + start;
                    int resID = getResources().getIdentifier(textViewId, "id", getPackageName());

                    TextView textView = new TextView(this);
                    textView = (TextView) findViewById(resID);

                    if (duration_start == 0)
                        textView.setText(courseName);
                    textView.setBackgroundColor(color);
                    textView.setOnClickListener(myClickListener);
                    tvList.add(textView);
                }
                if (weekdays.contains("W")) {
                    textViewId = "W" + start;
                    int resID = getResources().getIdentifier(textViewId, "id", getPackageName());

                    TextView textView = new TextView(this);
                    textView = (TextView) findViewById(resID);
                    if (duration_start == 0)
                        textView.setText(courseName);
                    textView.setBackgroundColor(color);
                    textView.setOnClickListener(myClickListener);
                    tvList.add(textView);
                }
                if (weekdays.contains("TH")) {
                    textViewId = "Th" + start;
                    int resID = getResources().getIdentifier(textViewId, "id", getPackageName());

                    TextView textView = new TextView(this);
                    textView = (TextView) findViewById(resID);
                    if (duration_start == 0)
                        textView.setText(courseName);
                    textView.setBackgroundColor(color);
                    textView.setOnClickListener(myClickListener);
                    tvList.add(textView);
                }
                if (weekdays.contains("F")) {
                    textViewId = "F" + start;
                    int resID = getResources().getIdentifier(textViewId, "id", getPackageName());

                    TextView textView = new TextView(this);
                    textView = (TextView) findViewById(resID);
                    if (duration_start == 0)
                        textView.setText(courseName);
                    textView.setBackgroundColor(color);
                    textView.setOnClickListener(myClickListener);
                    tvList.add(textView);
                }

                start = Calculation.timeAdd(start);
                duration_start++;
            }
            curColor++;

            M0830 = (TextView) findViewById(R.id.M0830);
            M0830.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String tmp = M0830.getText().toString();
                    System.out.println(tmp);
                    if(!tmp.matches(""))
                    {
                        M0830.setOnClickListener(myClickListener);
                    }else {
                        M0830.setOnClickListener(todoList);
                    }

                }

            });

        }

        mContext = getApplicationContext();
        mActivity = Main2Activity.this;

        like = (Button)findViewById(R.id.like);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, CalenderVersion.class);
                startActivity(intent);
            }
        });

    }

}












