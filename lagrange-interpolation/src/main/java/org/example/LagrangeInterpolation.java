package org.example;

public class LagrangeInterpolation {

    static double interpolate(Data[] f, double xi, int n)
    {
        double result = 0;

        for (int i = 0; i < n; i++)
        {
            double term = f[i].y;
            for (int j = 0; j < n; j++)
            {
                if (j != i)
                    term = term*(xi - f[j].x) / (f[i].x - f[j].x);
            }
            result += term;
        }

        return result;
    }
}
