package com.example.schedu;

import java.util.*;

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

    public void genCombination(){

        for(Course c: selectedCourses){

        }
    }
}
