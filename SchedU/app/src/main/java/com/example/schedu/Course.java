package com.example.schedu;

import java.util.*;

public class Course {
    String name;
    String number;

    List<CourseInfo> lectures;
    List<CourseInfo> tutorials;
    List<CourseInfo> tests;

    public Course(String name, String number) {
        this.name = name;
        this.number = number;

        lectures = new ArrayList<CourseInfo>();
        tutorials = new ArrayList<CourseInfo>();
        tests = new ArrayList<CourseInfo>();
    }

    public void InitValue(){

        ReadJsonFile.readFromWeb(this);

    }
}
