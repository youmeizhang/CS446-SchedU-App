package com.example.schedu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static void main(String[] args){
        String s1 = "0830";
        String s2 = "1020";

        String s3 = "1019";
        String s4 = "1050";

        if (isOverlapping(s1,s2,s3,s4))
            System.out.println("overlapped!");
    }
}
