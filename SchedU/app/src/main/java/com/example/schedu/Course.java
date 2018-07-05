package com.example.schedu;

import java.util.*;
import java.util.zip.CheckedOutputStream;

public class Course {
    String name;
    String number;
    String courseId;
    String courseTitle;
    String sectionNumber;   // could be user selected section, or default as 'ALL'
    String priority;        // dont really know why need this

    ArrayList<CourseInfo> lectures;
    ArrayList<CourseInfo> tutorials;
    ArrayList<CourseInfo> tests;

    ReadJsonFile reader;

    public Course() {
        lectures = new ArrayList<CourseInfo>();
        tutorials = new ArrayList<CourseInfo>();
        tests = new ArrayList<CourseInfo>();

        reader = new ReadJsonFile();
    }

    // get values from api
    public void InitValue() {
        reader.readFromWeb(this);
        printLectureInfo();
    }

    public void printLectureInfo() {

        System.out.println("Number of lectures: " + lectures.size()+ "\n");
        for (CourseInfo c: lectures) {
            c.printAll();
        }
    }

    public void printTutInfo() {

        System.out.println("Number of tutorials: " + tutorials.size() + "\n");
        for (CourseInfo c: tutorials) {
            c.printAll();
        }
    }

    public void printTstInfo() {

        System.out.println("Number of Tests: " + tests.size()+ "\n");
        for (CourseInfo c: tests) {
            c.printAll();
        }
    }

    public void printCourseSummary() {

        // total section info
        System.out.println("Summary of " + name + number+ "\n");
        printLectureInfo();
        printTutInfo();
        printTstInfo();
    }


}
