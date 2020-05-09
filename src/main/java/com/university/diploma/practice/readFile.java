package com.university.diploma.practice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class readFile {

    public static void main(String[] args) {
        BufferedReader br;
        double[] a = new double[10];
        double y = 1.0;
        int i = 0; // счетчик цикла
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("a.csv"))));
            String line;
            // Считываем файл построчно
            while ((line = br.readLine()) != null) {
                line = line.replace(',', '.');
                a[i++] = Double.parseDouble(line);
            }
            System.out.println(a[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
        public static void writeToFile(double y)
        {
        try {
            FileWriter writer = new FileWriter("y.csv", true);
            // запись всей строки
            String text = String.valueOf(y).replace('.', ',');
            writer.write(text);
            // запись по символам
            writer.append('\n');
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}