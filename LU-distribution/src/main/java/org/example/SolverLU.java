package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SolverLU {
    public static void main(String[] args) {
        try {

            double[][] matrix = readMatrixFromFile("P1_LU.txt");
            double[] vector = readVectorFromFile("P2_LU.txt");

            solveLU(matrix, vector);

        } catch (FileNotFoundException e) {
            System.err.println("Nie można odnaleźć pliku.");
        }
    }

    private static double[][] readMatrixFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        int rows = getNumberOfRows(fileName);
        double[][] matrix = new double[rows][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        scanner.close();
        return matrix;
    }

    private static double[] readVectorFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        int rows = getNumberOfRows(fileName);
        double[] vector = new double[rows];
        for (int i = 0; i < rows; i++) {
            vector[i] = scanner.nextDouble();
        }
        scanner.close();
        return vector;
    }

    private static int getNumberOfRows(String file) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File(file));
        int rowsCounter = 0;

        while (scanner.hasNextLine()) {
            scanner.nextLine();
            rowsCounter++;
        }

        scanner.close();
        return rowsCounter;
    }

    public static void solveLU(double[][] A, double[] B) {
        int matrixSize = A.length;
        int vectorSize = B.length;
        double[][] L = new double[matrixSize][matrixSize];
        double[][] U = new double[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            L[i][i] = 1;
        }

        // Dekompozycja Doolittle'a
        for (int k = 0; k < matrixSize; k++) {
            for (int j = k; j < matrixSize; j++) {
                double sum = 0;
                for (int p = 0; p < k; p++) {
                    sum += L[k][p] * U[p][j];
                }
                U[k][j] = A[k][j] - sum;
            }

            for (int i = k + 1; i < matrixSize; i++) {
                double sum = 0;
                for (int p = 0; p < k; p++) {
                    sum += L[i][p] * U[p][k];
                }
                L[i][k] = (A[i][k] - sum) / U[k][k];
            }
        }

        System.out.println("Macierz L:");
        printMatrix(L);
        System.out.println("Macierz U:");
        printMatrix(U);

        double[] Y = new double[vectorSize];
        for (int i = 0; i < vectorSize; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) {
                sum += L[i][j] * Y[j];
            }
            Y[i] = B[i] - sum;
        }

        System.out.println("Wektor Y:");
        printVector(Y);

        double[] X = new double[matrixSize];
        for (int i = matrixSize - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < matrixSize; j++) {
                sum += U[i][j] * X[j];
            }
            if (U[i][i] == 0) {
                System.err.println("Układ równań jest nieoznaczony.");
                return;
            }
            X[i] = (Y[i] - sum) / U[i][i];
        }

        System.out.println("Wektor rozwiązań X:");
        printVector(X);
    }

    public static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

    public static void printVector(double[] vector) {
        for (double element : vector) {
            System.out.println(element);
        }
    }
}
