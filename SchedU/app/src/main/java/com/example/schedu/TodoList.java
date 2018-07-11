package com.example.schedu;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TodoList extends Activity {
        private Button add_todo;
        private TextView show_todo;
        private String content;
        private Calendar date;
        private Calendar date_end;
        private String weekday_begin;
        private String weekday_end;

        private String hour_minute;
        private String hour_minute_end;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.todolist);

            final EditText edit_todo = (EditText) findViewById(R.id.edit_todo);
            final EditText show_begin_time = (EditText) findViewById(R.id.show_time);
            // final EditText show_date = (EditText) findViewById(R.id.show_date);

            show_begin_time.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final Calendar currentDate = Calendar.getInstance();
                    date = Calendar.getInstance();

                    new DatePickerDialog(TodoList.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            date.set(year, monthOfYear, dayOfMonth);
                            new TimePickerDialog(TodoList.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    date.set(Calendar.MINUTE, minute);
                                    hour_minute = hourOfDay + ":" + minute;
                                }
                            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
                            int weekday_begin_tmp = currentDate.get(Calendar.DAY_OF_WEEK);
                            weekday_begin = Integer.toString(weekday_begin_tmp);
                            monthOfYear = monthOfYear + 1;

                            show_begin_time.setText(year + "." + monthOfYear + "." + dayOfMonth + " " + hour_minute);
                        }
                    }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
                }
            });

            final EditText show_end_time = (EditText) findViewById(R.id.show_time2);
            show_end_time.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final Calendar currentDate = Calendar.getInstance();
                    date_end = Calendar.getInstance();

                    new DatePickerDialog(TodoList.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year2, int monthOfYear2, int dayOfMonth2) {
                            date_end.set(year2, monthOfYear2, dayOfMonth2);
                            new TimePickerDialog(TodoList.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay2, int minute2) {
                                    date_end.set(Calendar.HOUR_OF_DAY, hourOfDay2);
                                    date_end.set(Calendar.MINUTE, minute2);
                                    hour_minute_end = hourOfDay2 + ":" + minute2;
                                }
                            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();

                            int weekday_end_tmp = currentDate.get(Calendar.DAY_OF_WEEK);
                            weekday_end = Integer.toString(weekday_end_tmp);
                            monthOfYear2 = monthOfYear2 + 1;
                            show_end_time.setText(year2 + "." + monthOfYear2 + "." + dayOfMonth2 + " " + hour_minute_end);
                        }
                    }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
                }
            });

            add_todo = (Button) findViewById(R.id.add_todo);
            add_todo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    content = edit_todo.getText().toString();
                    // show_todo.setText(content + " " +  + " to " + real_end_time);
                    edit_todo.setHint("Enter To-do Task:");
                    Intent i = new Intent(TodoList.this, Main2Activity.class);
                    i.putExtra("content", content);
                    i.putExtra("weekday_begin", weekday_begin);
                    i.putExtra("weekday_end", weekday_end);
                    i.putExtra("hour_minute", hour_minute);
                    i.putExtra("hour_minute_end", hour_minute_end);
                    startActivity(i);
                }
            });
        }
    }

