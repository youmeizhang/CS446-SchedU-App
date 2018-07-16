package com.example.schedu.SATSolver;

import android.content.Context;

import com.example.schedu.MainActivity;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class Solver {

    //public static String filePath = "app/src/main/java/com/example/schedu/SATSolver/SATcoursefile";
    public static void main(String[] arg){
        //SATSolver("/data/user/0/com.example.schedu/files/SATcoursefile");
    }

    public static void SATSolver(String filePath) {
        ArrayList<String> allSolutions = new ArrayList<>();

        while (true) {
            final Formula formula = new Formula(filePath);
            try {
                while (!formula.validSolution()) {
                    if (formula.getCachedClauseSizeZeroResult()) {
                        formula.backTrack();
                    } else {
                        formula.forwardTrack();
                    }
                }
                String newSolution = formula.printSolution();
                allSolutions.add(banSolution(newSolution, filePath));
            } catch (NoSuchElementException e) {
                for(String solution : allSolutions) {
                    System.out.println(solution);
                }

                System.out.println("Unsolvable Solution");
                return;
            }
        }
    }

    public static String banSolution(String solution, String filePath){
        Scanner scanner = new Scanner(solution).useDelimiter(" ");;
        StringBuilder sb = new StringBuilder();
        StringBuilder retval = new StringBuilder();
        while(scanner.hasNextInt()) {
            int element = scanner.nextInt();
            if (element > 0) {
                sb.append(-element + " ");
                retval.append(element + " ");
            }
        }
        sb.append(0);
        updateClauses(sb.toString(), filePath);
        return retval.toString();
    }

    public static void updateClauses(String newClause, String filePath) {
        try {
            InputStreamReader isr = new InputStreamReader(MainActivity.mainContext.openFileInput(filePath), "UTF-8");
            BufferedReader file = new BufferedReader(isr);
            String line;
            StringBuffer inputBuffer = new StringBuffer();

            while ((line = file.readLine()) != null) {
                if (line.contains("p cnf")){
                    String[] tmp = line.split(" ");
                    int clauseNum = Integer.parseInt(tmp[tmp.length-1]);
                    tmp[tmp.length-1] = String.valueOf(clauseNum+1);
                    line = String.join(" ", tmp);
                }

                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();

            inputBuffer.append(newClause);
            String inputStr = inputBuffer.toString();

            FileOutputStream outputStream = MainActivity.mainContext.openFileOutput(filePath, Context.MODE_PRIVATE);
            outputStream.write(inputStr.getBytes());
            outputStream.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }

}
