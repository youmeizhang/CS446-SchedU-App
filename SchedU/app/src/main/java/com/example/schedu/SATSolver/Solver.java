package com.example.schedu.SATSolver;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class Solver {

    public static String filePath = "app/src/main/java/com/example/schedu/SATSolver/SATcoursefile";

    public static void main(final String[] args) {
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
                allSolutions.add(banSolution(newSolution));
            } catch (NoSuchElementException e) {
                for(String solution : allSolutions) {
                    System.out.println(solution);
                }

                System.out.println("Unsolvable Solution");
                System.exit(0);
            }
        }
    }

    public static String banSolution(String solution){
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
        updateClauses(sb.toString());
        return retval.toString();
    }

    public static void updateClauses(String newClause) {
        try {
            BufferedReader file = new BufferedReader(new FileReader(filePath));
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
            FileOutputStream fileOut = new FileOutputStream(filePath);
            fileOut.write(inputStr.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }

}
