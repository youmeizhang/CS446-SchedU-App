package com.example.schedu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import android.widget.TableLayout;
import android.widget.TextView;
import java.util.ArrayList;
import com.example.schedu.SimpleGestureFilter.SimpleGestureListener;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements SimpleGestureListener{

    private Button like;
    private Button todo;
    private ArrayList<TextView> tvList;
    private TableLayout tableLayout;

    private String capacity;
    private String coursename;
    private String starttime;
    private String endtime;
    private String enrollmentNumber;
    private String location;
    private String title;
    private int id;

    private SimpleGestureFilter detector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // Detect touched area
        detector = new SimpleGestureFilter(this,this);

        tvList = new ArrayList<>();

        int curColor = 0;
        id = getIntent().getIntExtra("idNumber", 0);
        TimeTable timeTable = MainActivity.allTimetables.get(id);

        final View.OnClickListener todoList = new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, TodoList.class);
                startActivity(intent);
            }
        };

        for (CourseInfo i : timeTable.contents) {
            String courseName = i.name + i.number;
            String start = i.startTime.replace(":", "");
            String end = i.endTime.replace(":", "");
            String weekdays = i.weekdays.toUpperCase();
            String sectionNum = i.section;

            coursename = courseName + "\n" + sectionNum;
            starttime = i.startTime;
            endtime = i.endTime;
            capacity = i.capacity;
            enrollmentNumber = i.enrollmentNum;
            location = i.location;
            title = i.title;

            String textViewId;
            int color = MainActivity.colorList.get(curColor);
            int duration_start = 0;
            int duration_end = (int) Calculation.timeDifference(start, end) / 30;

            final View.OnClickListener myClickListener = new View.OnClickListener() {
                String coursename_tmp = coursename;
                String starttime_tmp = starttime;
                String endtime_tmp = endtime;
                String capacity_tmp = capacity;
                String enrollmentNumber_tmp = enrollmentNumber;
                String location_tmp = location;
                String title_tmp = title;
                public void onClick(View view) {
                    Intent intent = new Intent(Main2Activity.this, CourseDetail.class);
                    intent.putExtra("CourseName", coursename_tmp);
                    intent.putExtra("StartTime", starttime_tmp);
                    intent.putExtra("EndTime", endtime_tmp);
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
                    if (duration_start == 1)
                        textView.setText(sectionNum);
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
                    if (duration_start == 1)
                        textView.setText(sectionNum);
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
                    if (duration_start == 1)
                        textView.setText(sectionNum);
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
                    if (duration_start == 1)
                        textView.setText(sectionNum);
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
                    if (duration_start == 1)
                        textView.setText(sectionNum);
                    textView.setBackgroundColor(color);
                    textView.setOnClickListener(myClickListener);
                    tvList.add(textView);
                }

                start = Calculation.timeAdd(start);
                duration_start++;
            }
            curColor++;

        }
        todo = (Button) findViewById(R.id.todo);
        todo.setOnClickListener(todoList);

        String content = getIntent().getStringExtra("content");
        String weekday_begin = getIntent().getStringExtra("weekday_begin");
        String weekday_end = getIntent().getStringExtra("weekday_end");
        String hour_minute = getIntent().getStringExtra("hour_minute");
        String hour_minute_end = getIntent().getStringExtra("hour_minute_end");
        System.out.println("show content:" + content);

        if(content != null){
            String begin = hour_minute.replace(":", "");
            String end = hour_minute_end.replace(":", "");
            int duration_end_time = (int) Calculation.timeDifference(begin, end) / 30;
            System.out.println("begin" + begin);
            System.out.println("end" + end);
            System.out.println("duration is:" + duration_end_time);
            System.out.println("weekday_begin is: " + weekday_begin);
            // boolean tmp = weekday_begin.equals("4");

            // System.out.println(tmp);

            if(weekday_begin.equals("1")) {
                String textViewId = "M" + begin;
                int resID = getResources().getIdentifier(textViewId, "id", getPackageName());

                TextView textView = new TextView(this);
                textView = (TextView) findViewById(resID);
                //if (duration_end_time == 0)
                    //textView.setText(content);
                //if (duration_end_time == 1)
                textView.setText(content);
                textView.setBackgroundColor(getResources().getColor(R.color.light_orange));
                //textView.setOnClickListener(myClickListener);
                //tvList.add(textView);
            }
            if(weekday_begin == "2") {
                String textViewId = "T" + begin;
                int resID = getResources().getIdentifier(textViewId, "id", getPackageName());

                TextView textView = new TextView(this);
                textView = (TextView) findViewById(resID);
                //if (duration_end_time == 0)
                //textView.setText(content);
                //if (duration_end_time == 1)
                textView.setText(content);
                textView.setBackgroundColor(getResources().getColor(R.color.light_orange));
                //textView.setOnClickListener(myClickListener);
                //tvList.add(textView);
            }
            if(weekday_begin == "3") {
                String textViewId = "W" + begin;
                int resID = getResources().getIdentifier(textViewId, "id", getPackageName());
                System.out.println(resID);

                TextView textView = new TextView(this);
                textView = (TextView) findViewById(resID);
                //if (duration_end_time == 0)
                //textView.setText(content);
                //if (duration_end_time == 1)
                textView.setText(content);
                textView.setBackgroundColor(getResources().getColor(R.color.light_orange));
                //textView.setOnClickListener(myClickListener);
                //tvList.add(textView);
            }
            if(weekday_begin == "4") {
                String textViewId = "Th" + begin;

                int resID = getResources().getIdentifier(textViewId, "id", getPackageName());

                TextView textView = new TextView(this);
                textView = (TextView) findViewById(resID);
                //if (duration_end_time == 0)
                //textView.setText(content);
                //if (duration_end_time == 1)
                textView.setText(content);
                textView.setBackgroundColor(getResources().getColor(R.color.light_orange));
                //textView.setOnClickListener(myClickListener);
                //tvList.add(textView);
            }
            if(weekday_begin == "5") {
                String textViewId = "F" + begin;
                int resID = getResources().getIdentifier(textViewId, "id", getPackageName());

                TextView textView = new TextView(this);
                textView = (TextView) findViewById(resID);
                //if (duration_end_time == 0)
                //textView.setText(content);
                //if (duration_end_time == 1)
                textView.setText(content);
                textView.setBackgroundColor(getResources().getColor(R.color.light_orange));
                //textView.setOnClickListener(myClickListener);
                //tvList.add(textView);
            }

        }

        like = (Button)findViewById(R.id.like);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, CalenderVersion.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT : str = "Swipe Right";
                if (id > 0)
                    id--;
                break;
            case SimpleGestureFilter.SWIPE_LEFT :  str = "Swipe Left";
                if (id < MainActivity.allTimetables.size())
                    id++;
                break;
        }

        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

        if (id > 0 && id < MainActivity.allTimetables.size()){
            Intent i = new Intent(Main2Activity.this, Main2Activity.class);
            i.putExtra("idNumber", id);
            startActivity(i);
        }
    }

    @Override
    public void onDoubleTap() {
        Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
    }


}












