package com.example.schedu.SATSolver;

import android.content.Context;

import com.example.schedu.MainActivity;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class Solver {

    public static String header = "";
    public static String data = "";


    public static ArrayList<ArrayList<Integer>> runSolver() {
        ArrayList<ArrayList<Integer>> allSolutions = new ArrayList<>();
        long startTime = System.nanoTime();
        while (true) {
            final Formula formula = new Formula(header + data);
            try {
                while (!formula.validSolution()) {
                    if (formula.getCachedClauseSizeZeroResult()) {
                        formula.backTrack();
                    } else {
                        formula.forwardTrack();
                    }
                }
                String newSolution = formula.printSolution();
                allSolutions.add(banSolution(newSolution));
            } catch (NoSuchElementException e) {

                long endTime   = System.nanoTime();
                long totalTime = endTime - startTime;
                System.out.println(totalTime);
                return allSolutions;
            }
        }
    }

    public static ArrayList<Integer> banSolution(String solution){
        Scanner scanner = new Scanner(solution).useDelimiter(" ");;
        StringBuilder sb = new StringBuilder();
        ArrayList<Integer> retval = new ArrayList<Integer>();
        while(scanner.hasNextInt()) {
            int element = scanner.nextInt();
            if (element > 0) {
                sb.append(-element + " ");
                retval.add(element);
            }
        }
        sb.append(0);
        sb.append("\n");
        updateClauses(sb.toString());
        return retval;
    }

    public static void updateClauses(String newClause) {

        String[] tmp = header.split(" ");
        int clauseNum = Integer.parseInt(tmp[tmp.length-1]);
        tmp[tmp.length-1] = String.valueOf(clauseNum+1);
        header = String.join(" ", tmp);
        data += newClause;

    }

}
