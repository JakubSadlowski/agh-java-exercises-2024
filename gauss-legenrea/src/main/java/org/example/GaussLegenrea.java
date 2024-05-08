package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

public class GaussLegenrea {
    public static void main(String[] args) {
        Function<Double, Double>[] functions = new Function[3];
        functions[0] = x -> Math.sin(x);
        functions[1] = x -> x * x + 2 * x + 5;
        functions[2] = Math::exp;

        double[][] intervals = {{0.5, 2.5}, {0.5, 5}, {0.5, 5}};
        String[] functionNames = {"sin(x)", "x^2 + 2x + 5", "exp(x)"};
        int n = 20;

        try {
            FileWriter writer = new FileWriter("GaussLegenrea.txt");
            for (int i = 0; i < functions.length; i++) {
                Function<Double, Double> f = functions[i];
                String functionName = functionNames[i];
                double a = intervals[i][0];
                double b = intervals[i][1];
                writer.write("Całkowana funkcja: " + functionName + "\n");
                writer.write("Przedział całkowania: [" + a + ", " + b + "]\n");

                    double rectangleResult = integrateByRectangle(f, a, b, n);
                    double trapezoidResult = integrateByTrapezoid(f, a, b, n);
                    double simpsonResult = integrateBySimpson(f, a, b, n);
                    double gaussLegendre2Result = integrateByGaussLegendre2(f, a, b);
                    double gaussLegendre3Result = integrateByGaussLegendre3(f, a, b);
                    double gaussLegendre4Result = integrateByGaussLegendre4(f, a, b);
                    writer.write("Liczba przedziałów: " + n + "\n");
                    writer.write("Wynik obliczony metodą prostokątów: " + rectangleResult + "\n");
                    writer.write("Wynik obliczony metodą trapezów: " + trapezoidResult + "\n");
                    writer.write("Wynik obliczony metodą Simpsona: " + simpsonResult + "\n");
                    writer.write("Wynik obliczony metodą Gaussa-Legendre'a (2 węzły): " + gaussLegendre2Result + "\n");
                    writer.write("Wynik obliczony metodą Gaussa-Legendre'a (3 węzły): " + gaussLegendre3Result + "\n");
                    writer.write("Wynik obliczony metodą Gaussa-Legendre'a (4 węzły): " + gaussLegendre4Result + "\n");

                writer.write("\n");
            }
            writer.close();
            System.out.println("Wyniki zostały zapisane do pliku GaussLegenrea.txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double integrateByRectangle(Function<Double, Double> f, double a, double b, int n) {
        double sum = 0;
        double s = (b - a) / n;
        for (int i = 0; i < n; i++) {
            double x = a + (i + 0.5) * s;
            sum += f.apply(x);
        }
        return s * sum;
    }

    public static double integrateByTrapezoid(Function<Double, Double> f, double a, double b, int n) {
        double sum = 0;
        double dx = (b - a) / n;
        sum += (f.apply(a) + f.apply(b)) / 2;
        for (int i = 1; i < n; i++) {
            double x = a + i * dx;
            sum += f.apply(x);
        }
        return dx * sum;
    }

    public static double integrateBySimpson(Function<Double, Double> f, double a, double b, int n) {
        double sum = 0;
        double dx = (b - a) / n;
        for (int i = 0; i < n; i += 2) {
            double x0 = a + i * dx;
            double x1 = a + (i + 1) * dx;
            double x2 = a + (i + 2) * dx;
            sum += (f.apply(x0) + 4 * f.apply(x1) + f.apply(x2)) * dx / 3;
        }
        return sum;
    }

    public static double integrateByGaussLegendre2(Function<Double, Double> f, double a, double b) {
        double[] x = {-0.5773502691896257, 0.5773502691896257}; // Węzły
        double[] w = {1.0, 1.0}; // Wagi

        double integral = 0.0;
        for (int i = 0; i < x.length; i++) {
            double xi = 0.5 * (b - a) * x[i] + 0.5 * (b + a); // Transformacja argumentu
            integral += w[i] * f.apply(xi);
        }
        return 0.5 * (b - a) * integral;
    }

    public static double integrateByGaussLegendre3(Function<Double, Double> f, double a, double b) {
        double[] x = {-0.7745966692414834, 0.0, 0.7745966692414834}; // Węzły
        double[] w = {0.5555555555555556, 0.8888888888888888, 0.5555555555555556}; // Wagi

        double integral = 0.0;
        for (int i = 0; i < x.length; i++) {
            double xi = 0.5 * (b - a) * x[i] + 0.5 * (b + a); // Transformacja argumentu
            integral += w[i] * f.apply(xi);
        }
        return 0.5 * (b - a) * integral;
    }

    public static double integrateByGaussLegendre4(Function<Double, Double> f, double a, double b) {
        double[] x = {-0.8611363115940526, -0.3399810435848563, 0.3399810435848563, 0.8611363115940526}; // Węzły
        double[] w = {0.3478548451374538, 0.6521451548625461, 0.6521451548625461, 0.3478548451374538}; // Wagi

        double integral = 0.0;
        for (int i = 0; i < x.length; i++) {
            double xi = 0.5 * (b - a) * x[i] + 0.5 * (b + a); // Transformacja argumentu
            integral += w[i] * f.apply(xi);
        }
        return 0.5 * (b - a) * integral;
    }
}
