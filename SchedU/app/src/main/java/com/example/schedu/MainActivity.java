package com.example.schedu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView textView1;
    private Spinner spinner1;
    private Spinner spinner2;
    private Button btn1;
    private String[][] dataStrings = { {"446 Software Architecture and Design", "486 Introduction to Artificial Intelligence", "698 Introduction to Research Topics"},
            {"656 Database System", "657A Data Model & Knowledge", "653 Quality Assurance"}, {"641 Text Analysis", "640 Big Data"}, };
    private ArrayAdapter<CharSequence> adapter = null;

    String string1;
    String string2;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner1 = (Spinner)findViewById(R.id.spinner_subject);
        spinner2 = (Spinner)findViewById(R.id.spinner_course);
        textView1 = (TextView)findViewById(R.id.display_info);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter = new ArrayAdapter<CharSequence>(MainActivity.this,
                        android.R.layout.simple_spinner_item,
                        dataStrings[position]);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter);
                string1 = parent.getItemAtPosition(position).toString();

                //String[] subject = getResources().getStringArray(R.array.subjects);
                //textView.setText("the subject you choose is:" + subject[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string2 = parent.getItemAtPosition(position).toString();
                s = string1 + string2;
                textView1.setText(s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn1 = (Button)findViewById(R.id.generate);
        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, Main2Activity.class);

                i.putExtra("getData", s);
                startActivity(i);
            }
        });
    }
}
