package org.example;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

public class GeometricalAndNumericalIntegration {
    public static void main(String[] args) {
        Function<Double, Double>[] functions = new Function[3];
        functions[0] = x -> Math.sin(x);
        functions[1] = x -> x * x + 2 * x + 5;
        functions[2] = Math::exp;

        double[][] intervals = {{0.5, 2.5}, {0.5, 5}, {0.5, 5}};

        String[] functionNames = {"sin(x)", "x^2 + 2x + 5", "exp(x)"};

        int[] ns = {2, 4, 6, 8, 10};

        try {
            FileWriter writer = new FileWriter("wyniki.txt");
            for (int i = 0; i < functions.length; i++) {
                Function<Double, Double> f = functions[i];
                String functionName = functionNames[i];
                double a = intervals[i][0];
                double b = intervals[i][1];
                writer.write("Całkowana funkcja: " + functionName + "\n");
                writer.write("Przedział całkowania: [" + a + ", " + b + "]\n");
                for (int n : ns) {
                    double rectangleResult = integrateByRectangle(f, a, b, n);
                    double trapezoidResult = integrateByTrapezoid(f, a, b, n);
                    double simpsonResult = integrateBySimpson(f, a, b, n);
                    writer.write("Liczba przedziałów: " + n + "\n");
                    writer.write("Wynik obliczony metodą prostokątów: " + rectangleResult + "\n");
                    writer.write("Wynik obliczony metodą trapezów: " + trapezoidResult + "\n");
                    writer.write("Wynik obliczony metodą Simpsona: " + simpsonResult + "\n");
                }
                writer.write("\n");
            }
            writer.close();
            System.out.println("Wyniki zostały zapisane do pliku wyniki.txt.");
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
        sum += (f.apply(a) + f.apply(b)) / 2; // Pierwszy i ostatni element
        for (int i = 1; i < n; i++) {
            double x = a + i * dx;
            sum += f.apply(x);
        }
        return dx * sum;
    }

    public static double integrateBySimpson(Function<Double, Double> f, double a, double b, int n) {
        if (n % 2 != 0) {
            throw new IllegalArgumentException("Liczba przedziałów musi być parzysta dla metody Simpsona.");
        }
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
}
