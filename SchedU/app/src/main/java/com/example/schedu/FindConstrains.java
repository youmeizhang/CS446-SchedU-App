package com.example.schedu;

import com.example.schedu.SATSolver.Solver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FindConstrains {

    public static HashMap<Integer, CourseInfo> myHashMap;
    public static ArrayList<TimeTable> allTimetables;

    public static void executeSATSolver(ArrayList<Course> data){
        ArrayList<CourseInfo> allCourses = new ArrayList<>();

        myHashMap = new HashMap<>();
        StringBuilder SATinput  = new StringBuilder();
        int countCourse = 1;
        int countClause = 0;

        for(Course c: data){

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

        String constrains = findConstrains(allCourses);
        String header = "p cnf " + String.valueOf(countCourse-1) + " " + String.valueOf(countClause);
        String rules = "\n" + SATinput.toString() + "\n";
        System.out.println("header " + header);
        System.out.println("data " + SATinput.toString());
        System.out.println("constrains " + constrains);

        Solver.header = header;
        Solver.data = rules;
        convertTimetable(Solver.runSolver());

        System.out.println("final data " + Solver.data);
    }

    public static void convertTimetable(ArrayList<ArrayList<Integer>> solutions){
        allTimetables = new ArrayList<>();
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




    public static String findConstrains(ArrayList<CourseInfo> courseInfos){
        String retval = "";
        for (int i=0; i<courseInfos.size(); i++){
            for (int j=0; j<courseInfos.size() && j!=i; j++){
                CourseInfo c1 = courseInfos.get(i);
                CourseInfo c2 = courseInfos.get(j);
                if (sameChars(c1.weekdays, c2.weekdays)) {
                    String start1 = c1.startTime;
                    String end1 = c1.endTime;
                    String start2 = c2.startTime;
                    String end2 = c2.endTime;
                    if (Calculation.isOverlapping(start1, end1, start2, end2)) {
                        retval += String.valueOf(-c1.id) + " " + String.valueOf(-c2.id) + " 0\n";
                        System.out.println("conflict course " + c1.getName() + " and " + c2.getName());
                    }
                }
            }
        }
        return retval;
    }

    private static boolean sameChars(String firstStr, String secondStr) {
        char[] first = firstStr.toCharArray();
        char[] second = secondStr.toCharArray();
        Arrays.sort(first);
        Arrays.sort(second);
        return Arrays.equals(first, second);
    }


}
