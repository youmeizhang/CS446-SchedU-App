package com.example.schedu;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class SelectedCoures {

    ArrayList<Course> selectedCourses;

    public SelectedCoures(){
        selectedCourses = new ArrayList<>();
    }

    public SelectedCoures(Course c){
        selectedCourses = new ArrayList<>();
        selectedCourses.add(c);
    }
    /*
    public SelectedCoures(ArrayList<HashMap<String, String>> courseList){
        selectedCourses = new ArrayList<>();
        String KEY_ID = "id";
        String KEY_SUB = "subject";
        String KEY_COU = "course";
        String KEY_SESS = "session";
        String KEY_PRIO = "priority";


        for(HashMap pair: courseList){
            String courseNum = pair.get(KEY_COU).toString();
            String[] tmp = courseNum.split(" ");

            courseNum = tmp[0];
            String courseName = pair.get(KEY_SUB).toString();
            System.out.println("parsed name " + courseName + courseNum);

            Course c = new Course();
            c.name = courseName;
            c.number = courseNum;
            selectedCourses.add(c);
        }

    }*/


    public void add(ArrayList<Course> courseList){
        selectedCourses.addAll(courseList);
    }

    public ArrayList<Course> getSelectedCourses(){
        return selectedCourses;
    }

    public void add(Course c){
        selectedCourses.add(c);
    }

    public void remove(String name, String number){
        for(Course c: selectedCourses){
            if(c.name == name && c.number == number) {
                selectedCourses.remove(c);
                break;
            }
        }
    }

    public void getDetailFromWeb(){

        // grab details from web for each course
        for(Course c: selectedCourses){
            c.InitValue();
        }
    }

    public void printInfo(){
        for(Course c: selectedCourses){
            System.out.println(c.name + " " + c.number);
        }
    }


    public TimeTable genCombination(){
        System.out.println(selectedCourses.size());
        TimeTable timeTable = new TimeTable(1);

        for(Course c: selectedCourses){

            if(c.lectures.size() > 0)
                timeTable.contents.add(c.lectures.get(0));
            if(c.tutorials.size() > 0)
                timeTable.contents.add(c.tutorials.get(0));
        }

        for(CourseInfo i: timeTable.contents){
            i.printAll();
        }
        return timeTable;
    }
    // Hard coded need to change!!!!!!
    public TimeTable hardCode(){
        TimeTable timeTable = new TimeTable(1);

        // hard coded
        CourseInfo c1 = new CourseInfo("ACC", "610");
        c1.section = "LEC 001";
        c1.capacity = "90";
        c1.enrollmentNum = "88";
        c1.startTime = "14:30";
        c1.endTime = "15:50";
        c1.weekdays = "TTh";
        c1.title = "Public Accounting Practice";
        c1.location = "RCH109";

        CourseInfo c2 = new CourseInfo("AMATH", "250");

        c2.section = "LEC 001";
        c2.capacity = "114";
        c2.enrollmentNum = "116";
        c2.startTime = "11:30";
        c2.endTime = "12:50";
        c2.weekdays = "MW";
        c2.title = "Introduction to Differential Equations";
        c2.location = "AHS1023";

        CourseInfo c3 = new CourseInfo("AFM", "231");

        c3.section = "LEC 001";
        c3.capacity = "88";
        c3.enrollmentNum = "71";
        c3.startTime = "12:30";
        c3.endTime = "13:20";
        c3.weekdays = "MWF";
        c3.title = "Business Law";
        c3.location = "MC2065";

        CourseInfo c4 = new CourseInfo("ACTCS", "231");

        c4.section = "LEC 002";
        c4.capacity = "95";
        c4.enrollmentNum = "89";
        c4.startTime = "08:30";
        c4.endTime = "09:20";
        c4.weekdays = "MWF";
        c4.title = "Introductory Financial Mathematics";
        c4.location = "MC4012";

        CourseInfo c5 = new CourseInfo("CS", "350");

        c5.section = "LEC 002";
        c5.capacity = "115";
        c5.enrollmentNum = "108";
        c5.startTime = "15:30";
        c5.endTime = "16:20";
        c5.weekdays = "TTh";

        timeTable.contents.add(c1);
        timeTable.contents.add(c2);
        timeTable.contents.add(c3);
        timeTable.contents.add(c4);
        //timeTable.contents.add(c5);

        return timeTable;
    }


}
