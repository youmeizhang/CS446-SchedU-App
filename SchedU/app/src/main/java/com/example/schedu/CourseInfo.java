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
    public CourseInfo(String name, String number){
        this.name = name;
        this.number = number;
    }

    public boolean isFull(){
        int c = Integer.parseInt(capacity);
        int e = Integer.parseInt(enrollmentNum);
        return (c <= e);
    }

    public String getName(){
        return this.name;
    }

    public String getNumber(){
        return this.number;
    }

    public void printAll(){
        System.out.println("Course name: " + name + " " + number);
        System.out.println("Course title: " + title);
        System.out.println("Section Number: " + section);
        System.out.println("Course capacity: " + capacity);
        System.out.println("Course enrollment number: " + enrollmentNum);
        System.out.println("Instructor: "+ instructor);
        System.out.println("location: " + location);
        System.out.println("Start time: " + startTime);
        System.out.println("End time: " + endTime);
        System.out.println("Weekdays: " + weekdays);
        System.out.println("\n");
    }

}
