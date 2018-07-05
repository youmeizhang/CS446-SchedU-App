package com.example.schedu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.Date;
import java.util.List;

public class CalenderVersion extends AppCompatActivity {
    public TextView event0;
    public TextView event1;
    public TextView year_month;
    public String coursename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_version);

        final CompactCalendarView compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        Event ev1 = new Event(Color.GREEN, 1530574595661L, "ECE657");
        Event ev2 = new Event(Color.GREEN, 1530728253329L, "CS446");
        Event ev3 = new Event(Color.GREEN, 1530728263329L, "CS246");

        compactCalendarView.addEvent(ev1, false);
        compactCalendarView.addEvent(ev2, false);
        compactCalendarView.addEvent(ev3, false);

        List<Event> events = compactCalendarView.getEvents(1530728270526L); // can also take a Date object

        event0 = (TextView) findViewById(R.id.event0);
        event1 = (TextView) findViewById(R.id.event1);
        coursename = "course";
        year_month = (TextView) findViewById(R.id.year_month);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                for(Event e: events){
                    coursename = coursename + "," + e.getData().toString();
                }

                String[] tmp = coursename.split(",");

                for(int i = 0; i < tmp.length - 1; i++)
                {
                    String ev = tmp[i+1].toString();
                    String name = "event" + i;
                    int id = getResources().getIdentifier(name, "id", getPackageName());
                    if (id != 0) {
                        TextView textView = (TextView) findViewById(id);
                        textView.setText(ev);
                        textView.setBackgroundColor(getResources().getColor(R.color.lightPink));
                    }
                }
                coursename = "course";
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                String date = firstDayOfNewMonth.toString();
                String month = date.split(" ")[1];
                String year = date.split(" ")[5];
                year_month.setText(year + ' ' + month);
            }
        });
    }
}
