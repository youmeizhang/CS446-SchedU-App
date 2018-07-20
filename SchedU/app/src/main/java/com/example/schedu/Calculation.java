package com.example.schedu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Calculation {

    public static long timeDifference(String start, String end){
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        long difference = 0;

        try {
            Date s = format.parse(start);
            Date e = format.parse(end);
            difference = e.getTime() - s.getTime();
            difference = difference /(60 * 1000);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return difference;
    }

    public static String timeAdd(String start){
        String newTime = "";
        SimpleDateFormat df = new SimpleDateFormat("HHmm");
        try {
            Date d = df.parse(start);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.MINUTE, 30);
            newTime = df.format(cal.getTime());
        }catch (ParseException e){
            e.printStackTrace();
        }
        return newTime;
    }

    public static boolean isOverlapping(String s1, String e1, String s2, String e2) {

        boolean retval = false;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        try {
            Date start1 = df.parse(s1);
            Date start2 = df.parse(s2);
            Date end1 = df.parse(e1);
            Date end2 = df.parse(e2);

            retval = start1.before(end2) && start2.before(end1);
        }catch (ParseException e){
            e.printStackTrace();
        }

        return retval;
    }

    public static String convertDateTime(String hour, String minute, String w){
        int wanted = 0;
        switch (w){
            case "M": wanted = Calendar.MONDAY;
                break;
            case "T": wanted = Calendar.TUESDAY;
                break;
            case "W": wanted = Calendar.WEDNESDAY;
                break;
            case "Th": wanted = Calendar.THURSDAY;
                break;
            case "F": wanted = Calendar.FRIDAY;
                break;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-04:00");
        Calendar now = Calendar.getInstance();
        int curWeekday = now.get(Calendar.DAY_OF_WEEK);
        if (curWeekday != wanted) {
            int days = (wanted - curWeekday + 7) % 7;
            now.add(Calendar.DAY_OF_YEAR, days);
        }
        now.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        now.set(Calendar.MINUTE, Integer.parseInt(minute));
        now.set(Calendar.SECOND, 0);

        Date date = now.getTime();
        String retval = format.format(date);
        return retval;
    }



    public static void main(String[] args){
        String s1 = "0830";
        String s2 = "1020";

        String s3 = "1019";
        String s4 = "1050";

        //convertDateTime(8, 30, "F");
    }
}
