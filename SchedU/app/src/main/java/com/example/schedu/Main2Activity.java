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
import java.util.ArrayList;
import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {


    private Context mContext;
    private Activity mActivity;
    private ArrayList<TextView> tvList;
    private TableLayout tableLayout;
    private ArrayList<Integer> colorList;

    private String capacity;
    private String coursename;
    private String enrollmentNumber;
    private String location;
    private String title;

    public TimeTable findCourse(){
        DbHandler dbHandler = new DbHandler(Main2Activity.this);
        ArrayList<Course> data = dbHandler.GetCourses();


        // selected courses
        SelectedCoures sc = new SelectedCoures();
        sc.add(data);
        /*
        // read details for selected courses
        sc.getDetailFromWeb();
        TimeTable t = sc.genCombination();*/
        TimeTable t = sc.hardCode();

        return t;
    }

    public void initColors(){
        colorList = new ArrayList<>();
        int c1 = getResources().getColor(R.color.lightBlue);
        int c2 = getResources().getColor(R.color.lightGrey);
        int c3 = getResources().getColor(R.color.lightPink);
        int c4 = getResources().getColor(R.color.lightGreen);
        int c5 = getResources().getColor(R.color.lightPurple);
        colorList.add(c1);
        colorList.add(c2);
        colorList.add(c3);
        colorList.add(c4);
        colorList.add(c5);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvList = new ArrayList<>();
        initColors();

        int curColor = 0;

        TimeTable timeTable = findCourse();
        System.out.println("items in timetable " + timeTable.contents.size());

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


            View.OnClickListener myClickListener = new View.OnClickListener() {
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
                    /*System.out.println("textview id: " + textViewId);
                    if (textView == null) {
                        System.out.println("NULL!!!!!!!");
                        break;
                    }*/
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
                System.out.println("start is" + start);
            }
            curColor++;

        }

        mContext = getApplicationContext();
        mActivity = Main2Activity.this;

    }

}












