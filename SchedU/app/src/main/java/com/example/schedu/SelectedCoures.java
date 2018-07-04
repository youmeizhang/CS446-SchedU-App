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


    public ArrayList<TimeTable> genTimeTable() {

        ArrayList<CourseInfo> Monday = new ArrayList<>();
        ArrayList<CourseInfo> Tuesday = new ArrayList<>();
        ArrayList<CourseInfo> Wednesday = new ArrayList<>();
        ArrayList<CourseInfo> Thursday = new ArrayList<>();
        ArrayList<CourseInfo> Friday = new ArrayList<>();



        for (Course c : selectedCourses) {

            if (c.lectures.size() > 0) {
                for (CourseInfo courseInfo : c.lectures) {
                    String curWeeday = courseInfo.weekdays.toUpperCase();
                    if (curWeeday.contains("M")) {
                        Monday.add(courseInfo);
                        break;
                    }
                    if ((curWeeday.contains("T") && !curWeeday.contains("H"))
                            || curWeeday.contains("TTH")) {
                        Tuesday.add(courseInfo);
                        break;
                    }
                    if (curWeeday.contains("W")) {
                        Wednesday.add(courseInfo);
                        break;
                    }
                    if (curWeeday.contains("TH")) {
                        Thursday.add(courseInfo);
                        break;
                    }
                    if (curWeeday.contains("F")) {
                        Friday.add(courseInfo);
                        break;
                    }

                }
            }
        }

        return null;
    }

    public ArrayList<TimeTable> genTimeTable(int k, int n) {
        ArrayList<TimeTable> allTimeTables = new ArrayList<>();
        int timeTableID = 1;
        TimeTable timeTable = new TimeTable(timeTableID);

        int[] num = {1,2,3,4,5,6,7,8,9};
        List<List<Integer>> result = new ArrayList<List<Integer>>();
       // helper(allTimeTables, timeTable, num, k, n,0);
        return allTimeTables;
    }

    public void helper(ArrayList<TimeTable> allTimeTable, TimeTable timeTable, ArrayList<CourseInfo> allCourse, int k, int start){



/*
        if (k == 0 && target == 0){
            allTimeTable.add(timeTable);
        } else {
            for (int i = start; i < num.length && target > 0 && k >0; i++){
                list.add(num[i]);
                helper(result, list, num, k-1,target-num[i],i+1);
                list.remove(list.size()-1);
            }
        }*/
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
