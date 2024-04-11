package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GaussEliminationPivot {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Podaj numer zadania od 1 do 2: ");
            int exerciseNo = scanner.nextInt();
            scanner.close();

            switch(exerciseNo){
                case 1:
                    double[][] matrixC = readSmallerMatrixFromFile("C3.txt");
                    printMatrix("Macierz rozszerzona (przed obliczeniami):", matrixC);
                    solveGaussPartialPivot(matrixC);
                    break;

                case 2:
                    double[][] matrixA = readMatrixFromFile("A3.txt");
                    double[] matrixB = readVectorFromFile("B3.txt");
                    double[][] extendedMatrix = combineMatrix(matrixA, matrixB);
                    printMatrix("Macierz rozszerzona (przed obliczeniami):", extendedMatrix);
                    solveGaussCrout(extendedMatrix);
                    break;

                default:
                    System.out.println("Zły numer zadania. Koniec programu");
                    break;
            }

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

    private static double[][] readSmallerMatrixFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        int rows = 4;
        int columns = 5;
        double[][] matrix = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
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

    private static void solveGaussPartialPivot(double[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            int maxRow = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(matrix[j][i]) > Math.abs(matrix[maxRow][i])) {
                    maxRow = j;
                }
            }

            swapRows(matrix, i, maxRow);
            calculate(matrix, i, n);
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

    private static void solveGaussCrout(double[][] matrix) {
        int n = matrix.length;

        int[] pivot = new int[n];
        for (int i = 0; i < n; i++) {
            pivot[i] = i;
        }

        for (int i = 0; i < n; i++) {
            int maxColumn = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(matrix[j][i]) > Math.abs(matrix[j][maxColumn])) {
                    maxColumn = j;
                }
            }

            storeColumns(pivot, i, maxColumn);
            swapColumns(matrix, n, i, maxColumn);
            calculate(matrix, i, n);
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

        System.out.println("Wektor przesunięć kolumn:\n");
        System.out.print("[");
        for (int i = 0; i < n; i++) {
            System.out.print(pivot[i] + ", ");
            if(i == n - 1)
                System.out.print("]\n");
        }

        double[] correctSolution = new double[n];
        for (int i = 0; i < n; i++) {
            correctSolution[pivot[i]] = solution[i];
        }

        System.out.println("Rozwiązanie układu równań (x0 - xn):");
        for (int i = 0; i < n; i++) {
            System.out.println("x" + i + " = " + correctSolution[i]);
        }
    }

    private static void swapColumns(double[][] matrix, int n, int i, int maxColumn) {
        for (int j = 0; j < n; j++) {
            double temp = matrix[j][i];
            matrix[j][i] = matrix[j][maxColumn];
            matrix[j][maxColumn] = temp;
        }
    }

    private static void storeColumns(int[] pivot, int i, int maxColumn) {
        int tempPivot = pivot[i];
        pivot[i] = pivot[maxColumn];
        pivot[maxColumn] = tempPivot;
    }

    private static void swapRows(double[][] matrix, int i, int maxRow) {
        double[] temp = matrix[i];
        matrix[i] = matrix[maxRow];
        matrix[maxRow] = temp;
    }

    private static void calculate(double[][] matrix, int i, int n) {
        for (int j = i + 1; j < n; j++) {
            double factor = matrix[j][i] / matrix[i][i];
            for (int k = i; k <= n; k++) {
                matrix[j][k] -= factor * matrix[i][k];
            }
        }
    }
}
