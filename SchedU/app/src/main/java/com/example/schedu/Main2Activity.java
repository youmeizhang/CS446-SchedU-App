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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {
    private TextView textView2;
    private Button btn3;
    private Button btnMon8;
    String value;
    private Context mContext;
    private Activity mActivity;
    private TextView tv;
    private TableLayout tableLayout;

    private PopupWindow mPopupWindow;

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TimeTable timeTable = findCourse();
        System.out.println("items in timetable " + timeTable.contents.size());

        for(CourseInfo i: timeTable.contents){
            String courseName = i.name + i.number + " " + i.section;
            String start = i.startTime.replace(":", "");
            String end = i.endTime.replace(":", "");
            String weekdays = i.weekdays;
            System.out.println("course info: " + courseName + " " + start + " " + end + " " + weekdays);
        }

        value = getIntent().getStringExtra("getData");
        tv = (TextView) findViewById(R.id.tv);
        /*btn3 = (Button)findViewById(R.id.new_button);
        btn3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                btnMon8.setText(value);
            }
        });*/

        tv1 = (TextView) findViewById(R.id.M8);
        tv2 = (TextView) findViewById(R.id.T8);
        tv3 = (TextView) findViewById(R.id.W8);

        tv2.setText("Hello");
        tv2.setBackgroundColor(Color.GRAY);

        // bind listeners
        tv1.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    return true;
                }
                return false;
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        mContext = getApplicationContext();
        mActivity = Main2Activity.this;
/*
        tableLayout = (TableLayout) findViewById(R.id.rl);

        btnMon8 = (Button) findViewById(R.id.M8);
        btnMon8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View courseView = inflater.inflate(R.layout.coursedetail, null);

                mPopupWindow = new PopupWindow(
                        courseView,
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                );

                ((TextView)mPopupWindow.getContentView().findViewById(R.id.tv)).setText(value);

                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }

                ImageButton closeButton = (ImageButton) courseView.findViewById(R.id.ib_close);

                closeButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        mPopupWindow.dismiss();
                    }
                });

                mPopupWindow.showAtLocation(tableLayout, Gravity.CENTER, 0, 0);
            }
        });*/


    }
}












