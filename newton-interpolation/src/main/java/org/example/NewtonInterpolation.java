package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NewtonInterpolation {
    public static void main(String[] args) {
        try {
            BufferedReader br =
                    new BufferedReader(new FileReader("C:\\Users\\jakub\\Programming\\Java Projects\\AGH-Java-Exercises-2024\\newton-interpolation\\newtonData.txt"));

            int n = Integer.parseInt(br.readLine());

            double[] x = new double[n];
            double[] y = new double[n];

            for (int i = 0; i < n; i++) {
                String[] line = br.readLine().split(";");
                x[i] = Double.parseDouble(line[0]);
                y[i] = Double.parseDouble(line[1]);
            }

            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
            System.out.print("Podaj punkt, w którym chcesz policzyć wartość wielomianu: ");
            double point = Double.parseDouble(reader.readLine());

            // Obliczanie współczynników wielomianu interpolacyjnego Newtona
            double[] b = new double[n];
            for (int i = 0; i < n; i++) {
                b[i] = y[i];
            }
            for (int j = 1; j < n; j++) {
                for (int i = n-1; i >= j; i--) {
                    b[i] = (b[i] - b[i-1]) / (x[i] - x[i-j]);
                }
            }

            // Obliczanie wartości wielomianu Newtona w danym punkcie
            double result = b[n-1];
            for (int i = n-2; i >= 0; i--) {
                result = result * (point - x[i]) + b[i];
            }

            // Wyświetlanie wyników
            System.out.println("Liczba węzłów: " + n);
            System.out.println("Dane: Węzły interpolacji i wartości funkcji w węzłach:");
            for (int i = 0; i < n; i++) {
                System.out.println("Węzeł " + i + ": x = " + x[i] + ", y = " + y[i]);
            }
            System.out.println("Punkt, w którym liczymy wartość wielomianu: " + point);
            System.out.println("Wartość wielomianu Newtona w danym punkcie: " + result);
            System.out.println("Współczynniki wielomianu Newtona:");
            for (int i = 0; i < n; i++) {
                System.out.println("b" + i + " = " + b[i]);
            }

            br.close();
            reader.close();
        } catch (IOException e) {
            System.err.println("Błąd odczytu danych z pliku: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Błąd parsowania danych: " + e.getMessage());
        }
    }
}
