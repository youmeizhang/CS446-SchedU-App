package com.example.schedu;

import java.util.*;

public class Course {
    String name;
    String number;
    String courseId;
    String courseTitle;

    List<CourseInfo> lectures;
    List<CourseInfo> tutorials;
    List<CourseInfo> tests;

    public Course() {
        lectures = new ArrayList<CourseInfo>();
        tutorials = new ArrayList<CourseInfo>();
        tests = new ArrayList<CourseInfo>();
    }

    public void InitValue() {

        ReadJsonFile.readFromWeb(this);

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
