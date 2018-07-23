package com.example.schedu;

import com.example.schedu.SATSolver.Solver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.transform.Source;

public class FindConstrains {

    public static HashMap<Integer, CourseInfo> myHashMap  = new HashMap<>();
    public static ArrayList<TimeTable> allTimetables = new ArrayList<>();
    public static ArrayList<CourseInfo> allCourses = new ArrayList<>();
    public static ArrayList<CourseInfo> fullCourses = new ArrayList<>();
    public static StringBuilder SATinput  = new StringBuilder();
    public static int countCourse = 1;
    public static int countClause = 0;
    public static boolean onlyOne = true;

    public static void executeSATSolver(ArrayList<Course> data){
        resetConstrains();
        for(Course c: data){
            if (c.lectures.size() > 1 || c.tutorials.size() >1)
                onlyOne = false;

            for(CourseInfo courseInfo : c.lectures){
                allCourses.add(courseInfo);
                myHashMap.put(countCourse, courseInfo);
                courseInfo.id = countCourse;
                SATinput.append(countCourse + " ");
                countCourse++;
            }
            if (c.lectures.size() != 0) {
                SATinput.append(0);
                SATinput.append("\n");
                countClause++;
            }

            for(CourseInfo courseInfo : c.tutorials){
                allCourses.add(courseInfo);
                myHashMap.put(countCourse, courseInfo);
                courseInfo.id = countCourse;
                SATinput.append(countCourse + " ");
                countCourse++;
            }
            if (c.tutorials.size() != 0) {
                SATinput.append(0);
                SATinput.append("\n");
                countClause++;
            }
        }
        filterFull();
        String constrains = findConstrains(allCourses);
        String header = "p cnf " + String.valueOf(countCourse-1) + " " + String.valueOf(countClause);
        String rules = "\n" + SATinput.toString() + "\n";
        System.out.println("header " + header);
        System.out.println("data " + SATinput.toString());
        System.out.println("constrains " + constrains);

        Solver.header = header;
        Solver.data = rules + constrains;
        if (onlyOne){
            System.out.println("there's only one timetable");
            TimeTable curTimeTable = new TimeTable(1);

            for (Course c: data){
                for (CourseInfo courseInfo : c.lectures) {
                    curTimeTable.contents.add(courseInfo);
                }
                for (CourseInfo courseInfo : c.tutorials){
                    curTimeTable.contents.add(courseInfo);
                }
            }
            allTimetables.add(curTimeTable);
            return;
        } else
            convertTimetable(Solver.runSolver());

        System.out.println("final data " + Solver.data);
    }

    public static void filterFull(){

        Iterator<CourseInfo> i = allCourses.iterator();
        while (i.hasNext()) {
            CourseInfo c = i.next();
            if (Integer.parseInt(c.capacity) < Integer.parseInt(c.enrollmentNum)) {
                i.remove();
                fullCourses.add(c);
                System.out.println(c.name + c.number + " " + c.section + " " + c.capacity + " " + c.enrollmentNum + " is full");
            }
        }
    }

    public static void convertTimetable(ArrayList<ArrayList<Integer>> solutions){
        int id = 1;
        for(ArrayList<Integer> s : solutions){
            TimeTable curTimeTable = new TimeTable(id);
            for(int i : s){
                CourseInfo c = myHashMap.get(i);
                curTimeTable.contents.add(c);
            }
            allTimetables.add(curTimeTable);
            id++;
        }
        System.out.println("SAT total generated: " + allTimetables.size());
    }

    public static void resetConstrains(){
        myHashMap  = new HashMap<>();
        allTimetables = new ArrayList<>();
        allCourses = new ArrayList<>();
        fullCourses = new ArrayList<>();
        SATinput  = new StringBuilder();
        countCourse = 1;
        countClause = 0;
    }


    public static String findConstrains(ArrayList<CourseInfo> courseInfos){
        String retval = "";
        for (int i=0; i<courseInfos.size(); i++){

            for (int j=0; j<courseInfos.size() && j!=i; j++){
                CourseInfo c1 = courseInfos.get(i);
                CourseInfo c2 = courseInfos.get(j);
                if (sameWeekdays(c1.weekdays.toUpperCase(), c2.weekdays.toUpperCase())) {
                    String start1 = c1.startTime;
                    String end1 = c1.endTime;
                    String start2 = c2.startTime;
                    String end2 = c2.endTime;
                    if (Calculation.isOverlapping(start1, end1, start2, end2)) {
                        retval += String.valueOf(-c1.id) + " " + String.valueOf(-c2.id) + " 0\n";
                        countClause++;
                        //System.out.println("conflict course " + c1.getName() + " and " + c2.getName() + " -> " + retval);
                    }
                }
            }
        }
        return retval;
    }

    private static boolean sameWeekdays(String firstStr, String secondStr) {
        
        boolean retval = ((firstStr.contains("M") && secondStr.contains("M")) ||
                (firstStr.contains("W") && secondStr.contains("W")) ||
                (firstStr.contains("F") && secondStr.contains("F")) ||
                (firstStr.contains("H") && secondStr.contains("H")) ||
                (firstStr.contains("T") && secondStr.contains("T")));
        return retval;
    }

    public static void main(String[] args){
        // hard coded
        CourseInfo c1 = new CourseInfo("ACC", "610");
        c1.section = "LEC 001";
        c1.capacity = "90";
        c1.enrollmentNum = "88";
        c1.startTime = "09:30";
        c1.endTime = "10:50";
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
        c4.startTime = "09:30";
        c4.endTime = "10:20";
        c4.weekdays = "TTh";
        c4.title = "Introductory Financial Mathematics";
        c4.location = "MC4012";

        CourseInfo c5 = new CourseInfo("CS", "350");

        c5.section = "LEC 002";
        c5.capacity = "115";
        c5.enrollmentNum = "108";
        c5.startTime = "15:30";
        c5.endTime = "16:20";
        c5.weekdays = "MWF";

        ArrayList<CourseInfo> testinfo = new ArrayList<>();
        testinfo.add(c1);
        testinfo.add(c2);
        testinfo.add(c3);
        testinfo.add(c4);
        testinfo.add(c5);

        findConstrains(testinfo);
    }



}
