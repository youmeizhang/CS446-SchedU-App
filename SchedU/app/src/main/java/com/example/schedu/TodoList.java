package com.example.schedu;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TodoList extends Activity {
        private Button add_todo;
        private TextView show_todo;
        private String real_start_time;
        private String real_end_time;
        private String start_time;
        private String end_time;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            final EditText edit_todo = (EditText) findViewById(R.id.edit_todo);
            final EditText show_Time = (EditText) findViewById(R.id.show_time);

            show_Time.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(TodoList.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            real_start_time = selectedHour + ":" + selectedMinute;
                            start_time = selectedHour + ""+ selectedMinute;
                            show_Time.setText( selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            });

            final EditText show_Time2 = (EditText) findViewById(R.id.show_time2);
            show_Time2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(TodoList.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            real_end_time = selectedHour + ":" + selectedMinute;
                            end_time = selectedHour + ""+ selectedMinute;
                            show_Time2.setText( selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            });

            show_todo = (TextView) findViewById(R.id.show_todo);

            add_todo = (Button) findViewById(R.id.add_todo);
            add_todo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String content = edit_todo.getText().toString();
                    show_todo.setText(content + " " + real_start_time + " to " + real_end_time);
                    edit_todo.setHint("Enter To-do Task:");
                    Intent i = new Intent(TodoList.this, Main2Activity.class);
                    i.putExtra("content", content);
                    i.putExtra("start_time", start_time);
                    i.putExtra("end_time", end_time);
                    startActivity(i);
                }
            });
        }
    }

