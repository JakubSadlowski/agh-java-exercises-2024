package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileReader {
    static Data[] readFile(File file) {
        int numberOfRows = getNumberOfRows(file);
        Data[] data = new Data[numberOfRows];
        int counter = 0;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(";");
                data[counter] = new Data(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                System.out.println("x: " + data[counter].getX() + " y: " + data[counter].getY());
                counter++;
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read file " + file.getAbsolutePath(), e);
        }
        return data;
    }

    private static int getNumberOfRows(File file) {
        int rowsCounter = 0;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            while (in.readLine() != null) {
                rowsCounter++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rowsCounter;
    }
}
