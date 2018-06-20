package com.example.schedu;

import java.util.*;
import java.util.zip.CheckedOutputStream;

public class Course {
    String name;
    String number;
    String courseId;
    String courseTitle;

    List<CourseInfo> lectures;
    List<CourseInfo> tutorials;
    List<CourseInfo> tests;

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


    public ArrayList<Pair> genCombination(Course c){

        ArrayList<Pair> jumbo = new ArrayList<>();
        for(CourseInfo lec: c.lectures){

            for(CourseInfo tut: c.tutorials){
                Pair p = new Pair(lec, tut);
                jumbo.add(p);
            }
        }

        for(Pair i: jumbo){
            System.out.println(i.lec.section + i.lec.section);
        }

        return jumbo;
    }

    class Pair{
        CourseInfo lec;
        CourseInfo tut;

        Pair(CourseInfo l, CourseInfo t){
            lec = l;
            tut = t;
        }
    }
}
