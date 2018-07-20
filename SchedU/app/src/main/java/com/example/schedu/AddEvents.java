
package com.example.schedu;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.CalendarListEntry;

import android.os.AsyncTask;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.Calendar;

import java.io.IOException;
import java.util.Arrays;

import java.util.Date;
import java.util.TimeZone;

public class AddEvents extends AsyncTask<Void, Void, Void> {
    Calendar mService;

    AddEvents(Calendar mService) {
        this.mService = mService;
    }

    @Override
    protected Void doInBackground(Void... params) {
        //addCalendarEvent();
        exportTimeTable();
        return null;
    }

    // test event
    public void addCalendarEvent() {
        System.out.println("trying add events");
        Event event = new Event()
                .setSummary("CS341")
                .setLocation("University of Waterloo")
                .setDescription("Algorithm");

        DateTime startDateTime = new DateTime("2018-07-20T14:30:00-04:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setStart(start);

        DateTime endDateTime = new DateTime("2018-07-20T15:50:00-04:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setEnd(end);

        String[] recurrence = new String[]{"RRULE:FREQ=WEEKLY;UNTIL=20180811T170000Z"};
        event.setRecurrence(Arrays.asList(recurrence));

        EventAttendee[] attendees = new EventAttendee[]{
                new EventAttendee().setEmail("test@example.com"),
                new EventAttendee().setEmail("sbrin@example.com"),
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        try {
            mService.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportTimeTable() {
        TimeTable toExport = MainActivity.curTimeTable;
        for (CourseInfo c : toExport.contents) {
            String summary = c.name + c.number + " " + c.section;
            String location = c.location;
            String description = c.title;

            String[] tmp = c.startTime.split(":");
            String[] tmp1 = c.endTime.split(":");
            String startTime = "";
            String endTime = "";
            if (c.weekdays.contains("M")) {
                startTime = Calculation.convertDateTime(tmp[0], tmp[1], "M");
                endTime = Calculation.convertDateTime(tmp1[0], tmp1[1], "M");
                addCalendarEvent(summary, location, description, startTime, endTime);
            }
            if (c.weekdays.contains("W")) {
                startTime = Calculation.convertDateTime(tmp[0], tmp[1], "W");
                endTime = Calculation.convertDateTime(tmp1[0], tmp1[1], "W");
                addCalendarEvent(summary, location, description, startTime, endTime);
            }
            if (c.weekdays.contains("F")) {
                startTime = Calculation.convertDateTime(tmp[0], tmp[1], "F");
                endTime = Calculation.convertDateTime(tmp1[0], tmp1[1], "F");
                addCalendarEvent(summary, location, description, startTime, endTime);
            }
            if (c.weekdays.contains("Th")) {
                startTime = Calculation.convertDateTime(tmp[0], tmp[1], "Th");
                endTime = Calculation.convertDateTime(tmp1[0], tmp1[1], "Th");
                addCalendarEvent(summary, location, description, startTime, endTime);
            }
            if (c.weekdays.contains("T") && !c.weekdays.contains("h") || c.weekdays.contains("TT")) {
                startTime = Calculation.convertDateTime(tmp[0], tmp[1], "T");
                endTime = Calculation.convertDateTime(tmp1[0], tmp1[1], "T");
                addCalendarEvent(summary, location, description, startTime, endTime);
            }

            System.out.println(summary + " " + location + " " + description + " " + startTime + " " + endTime);

        }
    }



    public void addCalendarEvent(String summary, String location, String description, String startTime, String endTime) {
        Event event = new Event()
                .setSummary(summary)
                .setLocation(location)
                .setDescription(description);

        DateTime startDateTime = new DateTime(startTime);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setStart(start);

        DateTime endDateTime = new DateTime(endTime);
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setEnd(end);

        String[] recurrence = new String[]{"RRULE:FREQ=WEEKLY;UNTIL=20180811T170000Z"};
        event.setRecurrence(Arrays.asList(recurrence));

        EventAttendee[] attendees = new EventAttendee[]{
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        try {
            mService.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}