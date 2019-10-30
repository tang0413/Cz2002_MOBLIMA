package modules.boundary;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Console {
    private Scanner sc;
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private String styleChar = "+";
    public Console() {
        sc = new Scanner(System.in);
    }
    public void log(String outputMsg) {
        System.out.println(outputMsg);
    }
    public void logWarning(String outputMsg) {
        System.out.println(ANSI_RED + outputMsg + ANSI_RESET);
    }
    public void logText(String outputMsg) {
        String repeated = new String(new char[outputMsg.length()]).replace("\0", styleChar);
        System.out.println(repeated);
        System.out.println(outputMsg);
        System.out.println(repeated);
    }
    public void logMenu(ArrayList<String> menuItems) {
        int i = 1;
        for (String str : menuItems) {
            System.out.println(i + ". " + str);
            i ++;
        }
        System.out.println(" ");
    }
    public int getInt(String label, int lowerBound, int upperBound) {
        while (true) {
            try {
                this.log(">> " + label + " (number): ");
                int num = sc.nextInt();
                sc.nextLine();
                if (num >= lowerBound & num <= upperBound) {
                    return num;
                } else {
                    throw new InputMismatchException("OutOfRange");
                }
            } catch (InputMismatchException e) {
                if (e.getMessage() != "OutOfRange"){
                    sc.nextLine();
                }
                this.logWarning("Invalid input! Please try again.");
            }
        }
    }
    public String getStr(String title) {
        this.log(">> " + title + ": ");
        return sc.nextLine();
    }
    public void destoryScanner(){
        sc.close();
    }
}
