package com.example.schedu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

public class PermutationGenerator {
    private ArrayList<ArrayList<CourseInfo>> result;
    private ArrayList<ArrayList<CourseInfo>> data;

    public ArrayList<ArrayList<CourseInfo>> permutate(ArrayList<ArrayList<CourseInfo>> data) {
        this.data = data;
        this.result = new ArrayList<>();

        ArrayList<CourseInfo> timeTable = new ArrayList<CourseInfo>();
        for(int i=0; i<data.size(); i++)
            timeTable.add(null);
        
        recursion(0, data.size() - 1, timeTable);
        return result;
    }

    private void recursion(int index, int maxIndex, ArrayList<CourseInfo> output) {
        ArrayList<CourseInfo> curArrayList = data.get(index);
        for (int i = 0; i < curArrayList.size(); i++) {

            output.set(index, curArrayList.get(i));
            if (index == maxIndex) {
                ArrayList<CourseInfo> tmp = new ArrayList<>(output);
                result.add(tmp);
            } else {
                recursion(index + 1, maxIndex, output);
            }
        }
    }

    public static void validate(ArrayList<CourseInfo> timeTables){

        ArrayList<CourseInfo> Monday = new ArrayList<>();
        ArrayList<CourseInfo> Tuesday = new ArrayList<>();
        ArrayList<CourseInfo> Wednesday = new ArrayList<>();
        ArrayList<CourseInfo> Thursday = new ArrayList<>();
        ArrayList<CourseInfo> Friday = new ArrayList<>();

        for(CourseInfo c: timeTables){
            System.out.println(c.name + " " + c.section + " " + c.weekdays);
            String curWeeday = c.weekdays.toUpperCase();
            if (curWeeday.contains("M")) {
                Monday.add(c);
            }
            if ((curWeeday.contains("T") && !curWeeday.contains("H"))
                    || curWeeday.contains("TTH")) {
                Tuesday.add(c);
            }
            if (curWeeday.contains("W")) {
                Wednesday.add(c);
            }
            if (curWeeday.contains("TH")) {
                Thursday.add(c);
            }
            if (curWeeday.contains("F")) {
                Friday.add(c);
            }
        }
        System.out.println("course on monday " + Monday.size());
        if (!checkWeekday(Monday))
            System.out.println("this schedule doesnt work");
        if (!checkWeekday(Tuesday))
            System.out.println("this schedule doesnt work");

    }

    public static boolean checkWeekday(ArrayList<CourseInfo> courseInfos){

        for (int i=0; i<courseInfos.size(); i++){
            for (int j=0; j<courseInfos.size() && j!=i; j++){
                String start1 = courseInfos.get(i).startTime;
                String end1 = courseInfos.get(i).endTime;
                String start2 = courseInfos.get(j).startTime;
                String end2 = courseInfos.get(j).endTime;
                if (Calculation.isOverlapping(start1, end1, start2, end2)) {
                    System.out.println("conflict course " + courseInfos.get(i).getName() + " and " + courseInfos.get(j).getName());
                    return false;
                }
            }
        }
        return true;
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

        PermutationGenerator pg = new PermutationGenerator();

        ArrayList<CourseInfo> list1 = new ArrayList<>();
        list1.add(c1);
        list1.add(c3);

        ArrayList<CourseInfo> list2 = new ArrayList<>();
        list2.add(c2);
        list2.add(c4);

        ArrayList<CourseInfo> list3 = new ArrayList<>();
        list3.add(c5);

        ArrayList<ArrayList<CourseInfo>> input = new ArrayList<>();
        input.add(list1);
        input.add(list2);
        input.add(list3);

        ArrayList<ArrayList<CourseInfo>> output = pg.permutate(input);
        for (ArrayList<CourseInfo> courseInfos : output) {
            for(CourseInfo c : courseInfos){
                System.out.println(c.name + " " + c.section);
            }
            System.out.println("---------------------------------");
        }
        System.out.println("TOTAL: " + output.size());

        for(ArrayList<CourseInfo> schedule: output){
            validate(schedule);
        }
    }


}




/*
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PermutationGenerator {
        private List<List<Integer>> result;
        private List<List<Integer>> data;

        public List<List<Integer>> permutate(List<List<Integer>> data) {
            this.data = data;
            this.result = new ArrayList<>();

            List<Integer> integers = new ArrayList<Integer>(Collections.nCopies(data.size(), 0));

            System.out.println(integers);
            recursion(0, data.size() - 1, integers);
            return result;
        }

        private void recursion(Integer index, Integer maxIndex, List<Integer> output) {
            List<Integer> curList = data.get(index);
            for (int i = 0; i < curList.size(); i++) {

                output.set(index, curList.get(i));
                if (index == maxIndex) {
                    List<Integer> tmp = new ArrayList<>(output);
                    result.add(tmp);
                } else {
                    recursion(index + 1, maxIndex, output);
                }
            }
        }


    public static void main(String[] args){

        PermutationGenerator pg = new PermutationGenerator();

        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(4, 5));
        List<Integer> list3 = new ArrayList<>(Arrays.asList(6, 7, 8, 9));

        List<List<Integer>> input = new ArrayList<>(Arrays.asList(list1, list2, list3));

        // when
        List<List<Integer>> output = pg.permutate(input);
        for (List<Integer> list : output) {
            System.out.println(Arrays.toString(list.toArray()));
        }
        System.out.println("TOTAL: " + output.size());
    }


}

 */