package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GaussSolver {

    public static void main(String[] args) {
        try {
            double[][] matrixA = readMatrixFromFile("A3.txt");
            double[] matrixB = readVectorFromFile("B3.txt");

            double[][] extendedMatrix = combineMatrix(matrixA, matrixB);

            printMatrix("Macierz rozszerzona (przed obliczeniami):", extendedMatrix);
            solveGauss(extendedMatrix);
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku.");
        }
    }

    private static double[][] readMatrixFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        int n = 20;
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        scanner.close();
        return matrix;
    }

    private static double[] readVectorFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        int n = 20;
        double[] vector = new double[n];
        for (int i = 0; i < n; i++) {
            vector[i] = scanner.nextDouble();
        }
        scanner.close();
        return vector;
    }

    private static double[][] combineMatrix(double[][] matrixA, double[] vectorB) {
        int n = matrixA.length;
        double[][] combinedMatrix = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrixA[i], 0, combinedMatrix[i], 0, n);
            combinedMatrix[i][n] = vectorB[i];
        }
        return combinedMatrix;
    }

    private static void printMatrix(String title, double[][] matrix) {
        System.out.println(title);
        for (double[] row : matrix) {
            for (double element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void solveGauss(double[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            if (matrix[i][i] == 0) {
                System.out.println("Znaleziono zero na przekątnej macierzy. Program nie może kontynuować.");
                return;
            }

            for (int j = i + 1; j < n; j++) {
                double factor = matrix[j][i] / matrix[i][i];
                for (int k = i; k <= n; k++) {
                    matrix[j][k] -= factor * matrix[i][k];
                }
            }
            printMatrix("Macierz rozszerzona (po etapie " + (i + 1) + " obliczeń - postępowanie proste):", matrix);
        }

        double[] solution = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            solution[i] = matrix[i][n];
            for (int j = i + 1; j < n; j++) {
                solution[i] -= matrix[i][j] * solution[j];
            }
            solution[i] /= matrix[i][i];
        }

        System.out.println("Rozwiązanie układu równań (x0 - xn):");
        for (int i = 0; i < n; i++) {
            System.out.println("x" + i + " = " + solution[i]);
        }
    }
}
