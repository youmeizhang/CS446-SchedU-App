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
            System.out.println("start: " + s + " end: " + e + " diff: " + difference);
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

    public static void main(String[] args){
        String s1 = "0830";
        String s2 = "1020";
        //timeDifference(s1, s2);
        timeAdd(s2);
    }
}
