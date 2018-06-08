package com.example.schedu;



public class CourseInfo {

    String name;
    String number;
    String title;

    String section;
    String capacity;
    String enrollmentNum;
    String instructor;
    String location;

    String startTime;
    String endTime;
    String weekdays;

    // constructor
    public CourseInfo(String name, String number){// String title, String section, String capacity, String enrollmentNum, String instructor, String location, String startTime, String endTime, String weekdays){
        this.name = name;
        this.number = number;

        /*
        this.title = title;
        this.section = section;
        this.capacity = capacity;
        this.enrollmentNum = enrollmentNum;
        this.instructor = instructor;
        this.location = location;

        this.startTime = startTime;
        this.endTime = endTime;
        this.weekdays = weekdays;*/
    }

    public String getName(){
        return this.name;
    }

    public String getNumber(){
        return this.number;
    }

    public void printAll(){
        System.out.println("Course name: ");
        System.out.println("Course title: ");
        System.out.println("Section Number: ");
        System.out.println("Course capacity: ");
        System.out.println("Course enrollment number: ");
        System.out.println("Instructor: ");
        System.out.println("location: ");
        System.out.println("Start time: ");
        System.out.println("End time: ");
        System.out.println("Weekdays: ");
    }

    public void getCourseInfo(){

    }


}
