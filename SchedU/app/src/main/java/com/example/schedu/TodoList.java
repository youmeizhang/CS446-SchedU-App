package com.example.schedu;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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

    private String text2;
    private String text;
    private Context mContext;
    private Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        final EditText edit_todo = (EditText) findViewById(R.id.edit_todo);
        final EditText show_begin_time = (EditText) findViewById(R.id.show_time);

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
                                show_begin_time.setText(hour_minute);
                            }
                        }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
                        int weekday_begin_tmp = currentDate.get(Calendar.DAY_OF_WEEK);
                        weekday_begin = Integer.toString(weekday_begin_tmp - 1);
                        monthOfYear = monthOfYear + 1;
                        text = year + "." + monthOfYear + "." + dayOfMonth + " " + show_begin_time.getText().toString();
                        // problem: does not show data time
                        show_begin_time.setText(text);
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
                                show_end_time.setText(hour_minute_end);
                            }
                        }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();

                        int weekday_end_tmp = currentDate.get(Calendar.DAY_OF_WEEK);
                        weekday_end = Integer.toString(weekday_end_tmp - 1);
                        monthOfYear2 = monthOfYear2 + 1;
                        text2 = year2 + "." + monthOfYear2 + "." + dayOfMonth2 + " " + show_end_time.getText().toString();
                        // problem: does not show date time
                        show_end_time.setText(text2);
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
            }
        });

        add_todo = (Button) findViewById(R.id.add_todo);
        add_todo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int hour_begin = Integer.parseInt(hour_minute.split(":")[0]);
                int hour_end = Integer.parseInt(hour_minute_end.split(":")[0]);

                int minute_begin = Integer.parseInt(hour_minute.split(":")[1]);
                int minute_end = Integer.parseInt(hour_minute_end.split(":")[1]);

                content = edit_todo.getText().toString();

                if (hour_begin > hour_end || (hour_begin == hour_end && minute_begin > minute_end)) {
                    Toast.makeText(getApplicationContext(), "Invalid Time Period! Please Choose A New One", Toast.LENGTH_SHORT).show();
                } else if (content == null && hour_minute != null && hour_minute_end != null) {

                    Toast.makeText(getApplicationContext(), "Task Is Null", Toast.LENGTH_SHORT).show();
                } else {
                    edit_todo.setHint("Enter To-do Task:");
                    Intent i = new Intent(TodoList.this, Main2Activity.class);
                    i.putExtra("content", content);
                    i.putExtra("weekday_begin", weekday_begin);
                    i.putExtra("weekday_end", weekday_end);
                    i.putExtra("hour_minute", hour_minute);
                    i.putExtra("hour_minute_end", hour_minute_end);
                    startActivity(i);
                }
            }
        });

        getWindow().setLayout((int) (width * .8), (int) (height * .7));
        mContext = getApplicationContext();
        mActivity = TodoList.this;
    }
}

