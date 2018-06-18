package com.example.schedu;

import java.util.ArrayList;

public class TimeTable {

    int tableID;
    ArrayList<CourseInfo> contents;
    boolean userLike;

    public TimeTable(int id){
        tableID = id;
        contents = new ArrayList<>();
        userLike = false;
    }

    public void add(CourseInfo c){
        contents.add(c);
    }
    
}
