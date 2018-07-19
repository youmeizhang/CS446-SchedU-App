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
        addCalendarEvent();
        return null;
    }

    public void addCalendarEvent() {
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


/*
    public void createEvent(Calendar cal) throws IOException {

        Event event = new Event();
        event.setSummary("Event name here");
        event.setLocation("event place here");

        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 3600000);
        DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
        event.setStart(new EventDateTime().setDateTime(start));
        DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
        event.setEnd(new EventDateTime().setDateTime(end));

        //get calendar id

        //CalendarListEntry calendarListEntry = mService.calendarList().get("calendarId").execute();
        //System.out.println(calendarListEntry.getSummary());

        Event createdEvent = cal.events().insert("primary", event).execute();
        System.out.println("Created event id: " + createdEvent.getId());
    }


}
        String calendarId = "primary";
        try {
            mService.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}
