package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class JacobiMethod {
    private static final double EPSILON_1 = 0.001;
    private static final double EPSILON_2 = 0.000001;
    private static final int MAX_ITERATIONS = 1000;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("jacoby-method/src/main/resources/Jacobi.txt"));
            int n = 4;
            double[][] A = new double[n][n];
            double[] b = new double[n];
            for (int i = 0; i < n; i++) {
                String[] values = reader.readLine().split("\t");
                for (int j = 0; j < n; j++) {
                    A[i][j] = Double.parseDouble(values[j]);
                }
                b[i] = Double.parseDouble(values[n]);
            }
            reader.close();

            System.out.println("Układ równań:");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(A[i][j] + "x" + (j+1) + " ");
                }
                System.out.println("= " + b[i]);
            }

            boolean isDiagonallyDominant = checkDiagonallyDominant(A);
            if (!isDiagonallyDominant) {
                System.out.println("Macierz nie jest diagonalnie słabo dominująca.");
                return;
            }

            double[][] LplusU = new double[n][n];
            double[][] Dinv = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        LplusU[i][j] = A[i][j];
                    }
                }
                Dinv[i][i] = 1.0 / A[i][i];
            }

            System.out.println("\nMacierz L+U:");
            printMatrix(LplusU);
            System.out.println("\nMacierz D^(-1):");
            printMatrix(Dinv);

            System.out.println("Podaj liczbę iteracji: ");
            Scanner scanner = new Scanner(System.in);
            int iterations = scanner.nextInt();

            double[] x = new double[n];
            double[] xPrev = new double[n];
            for (int iter = 0; iter < iterations; iter++) {
                System.arraycopy(x, 0, xPrev, 0, n);
                for (int i = 0; i < n; i++) {
                    double sum = b[i];
                    for (int j = 0; j < n; j++) {
                        if (j != i) {
                            sum -= A[i][j] * xPrev[j];
                        }
                    }
                    x[i] = Dinv[i][i] * sum;
                }
            }

            System.out.println("\nWyniki po " + iterations + " iteracjach:");
            for (int i = 0; i < n; i++) {
                System.out.println("x" + (i+1) + " = " + x[i]);
            }

            solveWithEpsilon(A, b, Dinv, n, EPSILON_1);
            solveWithEpsilon(A, b, Dinv, n, EPSILON_2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkDiagonallyDominant(double[][] A) {
        int n = A.length;
        for (int i = 0; i < n; i++)
        {
            int sum = 0;
            for (int j = 0; j < n; j++)
                sum += Math.abs(A[i][j]);

            sum -= Math.abs(A[i][i]);

            if (Math.abs(A[i][i]) < sum)
                return false;
        }

        return true;
    }

    private static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    private static void solveWithEpsilon(double[][] A, double[] b, double[][] Dinv, int n, double epsilon) {
        System.out.println("\nRozwiązanie dla epsilon = " + epsilon + ":");

        double[] x = new double[n];
        double[] xPrev = new double[n];

        int iterations = 0;
        while (true) {
            iterations++;
            System.arraycopy(x, 0, xPrev, 0, n);

            for (int i = 0; i < n; i++) {
                double sum = b[i];
                for (int j = 0; j < n; j++) {
                    if (j != i) {
                        sum -= A[i][j] * xPrev[j];
                    }
                }
                x[i] = Dinv[i][i] * sum;
            }

            double maxDiff = 0;
            for (int i = 0; i < n; i++) {
                maxDiff = Math.max(maxDiff, Math.abs(x[i] - xPrev[i]));
            }
            if (maxDiff < epsilon || iterations >= MAX_ITERATIONS) {
                break;
            }
        }

        if (iterations >= MAX_ITERATIONS) {
            System.out.println("Brak rozwiązania z zadaną dokładnością.");
        } else {
            System.out.println("Liczba wykonanych iteracji: " + iterations);
            System.out.println("Rozwiązanie układu równań:");
            for (int i = 0; i < n; i++) {
                System.out.println("x" + (i+1) + " = " + x[i]);
            }

            double[] errors = new double[n];
            for (int i = 0; i < n; i++) {
                errors[i] = Math.abs(b[i] - computeAx(A, x, i));
            }
            System.out.println("Błędy dla każdego x:");
            for (int i = 0; i < n; i++) {
                System.out.println("x" + (i+1) + ": " + errors[i]);
            }
        }
    }

    private static double computeAx(double[][] A, double[] x, int i) {
        int n = A.length;
        double sum = 0;
        for (int j = 0; j < n; j++) {
            sum += A[i][j] * x[j];
        }
        return sum;
    }
}
