package com.example.schedu;

import java.sql.Time;
import java.util.*;


public class TimeTable {

    int id;
    ArrayList<CourseInfo> allCourses;
    boolean userLike;


    public TimeTable(int n){
        id = n;
        userLike = false;
        allCourses = new ArrayList<>();
    }

    public void add(CourseInfo c){
        allCourses.add(c);
    }

}
