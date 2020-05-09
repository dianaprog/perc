package com.university.diploma.practice;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Interval2 {


    // Основные переменные
    private static String fname = "/Users/dianashageeva/IdeaProjects/diploma/src/main/resources/EURUSD.csv"; // файл с данными
    private static  float k[][] = new float[172][4];

    // разделитель данных в файле
    private static final String separator = ";";

    // Основная программа
    public static void main(String[] args) {

        // Считаем данные из файла
//        ReadCSV();
//
//        for (int i = 0; i < 362; i++) {
//            for (int j = 0; j < 4; j++) {
//                System.out.print(k[i][j] + "\t");
//            }
//            System.out.println("");
//        }
    }

    // Прочитать данные из файла в массив
    public static void ReadCSV(double[] r, double[][] pat, String separator, String fname) {
        File file = new File(fname);
        try(BufferedReader br =
                new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));) {
            String line = "";
            int i=0;
            // Считываем файл построчно  \n
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(separator);
                if (!(Float.parseFloat(elements[1]) == 0 &&
                        Float.parseFloat(elements[2]) == 0 &&
                        Float.parseFloat(elements[3]) == 0))
                {
                    r[i] = Float.parseFloat(elements[0]);
                    if (r[i] == 2)
                        r[i] = 0;
                    pat[i][0] = Float.parseFloat(elements[1]);
                    pat[i][1] = Float.parseFloat(elements[2]);
                    pat[i][2] = Float.parseFloat(elements[3]);
                    i++;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

