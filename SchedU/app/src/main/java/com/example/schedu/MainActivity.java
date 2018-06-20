package com.example.schedu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
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
    private ArrayAdapter<CharSequence> adapter = null;

    String string_subject;
    String string_course;
    String string_session;
    String string_priority;
    String s;

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

        // create database
        //DatabaseHelper DatabaseHelper = new DatabaseHelper(MainActivity.this);
        //new fetchData(DatabaseHelper).execute();

        subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter = new ArrayAdapter<CharSequence>(MainActivity.this,
                        android.R.layout.simple_spinner_item,
                        dataStrings[position]);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                course.setAdapter(adapter);
                string_subject = parent.getItemAtPosition(position).toString();

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
                string_course = parent.getItemAtPosition(position).toString();
                s = string_subject + string_course;
                //textView1.setText(s);
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
                Intent i = new Intent(MainActivity.this, Main2Activity.class);

                i.putExtra("getData", s);

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
/*
        btn_finish = (Button)findViewById(R.id.finish_adding);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textView1.setText(s);
                intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });
        */
    }
}
