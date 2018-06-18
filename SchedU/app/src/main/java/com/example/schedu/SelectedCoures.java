package com.example.schedu;

import java.util.*;

public class SelectedCoures {

    List<Course> selectedCourses;

    public SelectedCoures(){
        selectedCourses = new ArrayList<>();
    }

    public SelectedCoures(Course c){
        selectedCourses = new ArrayList<>();
        selectedCourses.add(c);
    }

    public SelectedCoures(ArrayList<HashMap<String, String>> courseList){
        selectedCourses = new ArrayList<>();
        String KEY_ID = "id";
        String KEY_SUB = "subject";
        String KEY_COU = "course";
        String KEY_SESS = "session";
        String KEY_PRIO = "priority";


        for(HashMap pair: courseList){
            String courseNum = pair.get(KEY_COU).toString();
            String courseName = pair.get(KEY_SUB).toString();

        }
        
        //selectedCourses.addAll(courseList);
    }

    public SelectedCoures(List<Course> courseList){
        selectedCourses = new ArrayList<>();
        selectedCourses.addAll(courseList);
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

    public void genCombination(){

        for(Course c: selectedCourses){

        }
    }

    public static void main(String[] args){

    

    }


}
