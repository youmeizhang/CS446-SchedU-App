package com.example.schedu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


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

    public void printInfo(){
        for(CourseInfo i: contents){
            i.printAll();
        }
    }
}
