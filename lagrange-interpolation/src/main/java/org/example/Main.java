package org.example;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Exercise 1
        File file1 = new File("C:\\Users\\jakub\\Programming\\Java Projects\\AGH-Java-Exercises-2024\\lagrange-interpolation\\data1.txt");
        File file2 = new File("C:\\Users\\jakub\\Programming\\Java Projects\\AGH-Java-Exercises-2024\\lagrange-interpolation\\data2.txt");
        FileReader fileReader = new FileReader();
        Data[] data = fileReader.readFile(file1);
        int numberOfPoints2 = data.length;
        System.out.println("Podaj punkt w ktorym program ma obliczyc interpolacje: ");
        Scanner myObj2 = new Scanner(System.in);
        String input2 = myObj2.nextLine();
        double result2 = LagrangeInterpolation.interpolate(data, Integer.parseInt(input2), numberOfPoints2);
        System.out.println("Wynik interpolacji: " + result2);


        //Exercise 2
        Data[] data2 = fileReader.readFile(file2);
        int numberOfPoints = data.length;
        Scanner myObj = new Scanner(System.in);
        System.out.println("Podaj punkt w ktorym program ma obliczyc interpolacje: ");
        String input = myObj.nextLine();
        double result = LagrangeInterpolation.interpolate(data2, Integer.parseInt(input), numberOfPoints);
        System.out.println("Wynik interpolacji: " + result);
        double element = Math.pow(50, 1.0/3.0);
        System.out.println("Pierwiastek 3 stopnia z 50: " + element);
    }
}