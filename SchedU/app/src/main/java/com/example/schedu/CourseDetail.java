package com.example.schedu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CourseDetail extends Activity {

    private ArrayList<TextView> tvList;
    private Context mContext;
    private Activity mActivity;
    private String catalogNumber;
    private String subjectName;
    private String sectionNumber;
    public static DatabaseManager databaseManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coursedetail);


        databaseManager = DatabaseManager.getHelper(this);

        tvList = new ArrayList<>();

        String info = getIntent().getStringExtra("CourseName")+ "\n"+
                getIntent().getStringExtra("Capacity")+ "\n"+
                getIntent().getStringExtra("EnrollmentNumber")+ "\n"+
                getIntent().getStringExtra("Location")+ "\n"+
                getIntent().getStringExtra("Title")+ "\n";

        System.out.println("info:" + info);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        sectionNumber = getIntent().getStringExtra("CourseName").split("\n")[1];
        catalogNumber = getIntent().getStringExtra("catalogNumber");
        subjectName = getIntent().getStringExtra("subjectName");

        System.out.println("subjectName is:" + subjectName);
        System.out.println("catalogNumber is:" + catalogNumber);
        System.out.println("sectionNumber is: " + sectionNumber);

        List<String> rating = databaseManager.getProfRating(catalogNumber, subjectName, sectionNumber);
        //String tmp = rating.get(0);
        //System.out.println("get rating here: " + rating);


        TextView tv1 = new TextView(this);
        tv1 = (TextView)findViewById(R.id.title);
        tv1.setText(getIntent().getStringExtra("Title"));
        tvList.add(tv1);

        TextView tv2 = new TextView(this);
        tv2 = (TextView)findViewById(R.id.location);
        tv2.setText("Location: " + getIntent().getStringExtra("Location"));
        tvList.add(tv2);

        TextView tv3 = new TextView(this);
        tv3 = (TextView)findViewById(R.id.enrollment);
        tv3.setText("Enrollment Number: " + getIntent().getStringExtra("EnrollmentNumber"));
        tvList.add(tv3);

        TextView tv4 = new TextView(this);
        tv4 = (TextView)findViewById(R.id.coursename);
        tv4.setText(getIntent().getStringExtra("CourseName") + "\n" +
                getIntent().getStringExtra("StartTime") + " - " +
                getIntent().getStringExtra("EndTime"));
        tvList.add(tv4);

        TextView tv5 = new TextView(this);
        tv5 = (TextView)findViewById(R.id.capacity);
        tv5.setText("Course Capacity: " + getIntent().getStringExtra("Capacity"));
        tvList.add(tv5);

        TextView tv_rating = new TextView(this);
        tv_rating = (TextView) findViewById(R.id.prof_rating);
        if (rating.size() > 0)
            tv_rating.setText("Prof Rating: " + rating.get(0));
        else
            tv_rating.setText("Prof Rating: NA");

        getWindow().setLayout((int) (width*.6), (int) (height*.60));

        mContext = getApplicationContext();
        mActivity = CourseDetail.this;
    }
}
