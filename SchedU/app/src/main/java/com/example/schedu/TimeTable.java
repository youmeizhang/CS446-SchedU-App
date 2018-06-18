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

    public void printInfo(){
        for(CourseInfo i: contents){
            i.printAll();
        }
    }

    public static void main(String[] args){

    }


}
