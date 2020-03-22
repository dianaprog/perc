package com.university.diploma;

import java.util.Arrays;

public class Perceptron {
    double[] x; //массив дробных чисел (слой)
    double y; //exit
    double[] w; //массив дробных чисел (вес)
    double[][] pat = { { 0, 0, 0 }, { 0, 1, 1 }, { 1, 0, 1 }, { 1, 1, 1 } }; //двумерный массив дробных чисел
    public Perceptron() { //персептрон
        x = new double[2]; //массив х будет иметь размер 2
        w = new double[x.length]; //массив w будет иметь размер, равный размеру массива x
        for (int i = 0; i < x.length; i++) { //количество операций в цикле=длине массива x=2
            w[i] = Math.random() * 0.2 + 0.1; //кладем в массив w рандомные значения

        }
    }

    public void cy() { //функция, которая ничего не возвращает
        y = 0; //присваиваем y значение 0
        for (int i = 0; i < x.length; i++) {//количество операций в цикле=длине массива x=2
            y += x[i] * w[i];//складываем в y сумму произведений x и w
        }
        if (y >0.5)
            y = 1;
        else
            y = 0;

    }

    public void study() { //обучение
        double gEr = 0;
        int m=0;
        do {
            gEr = 0;
            for (int p = 0; p < pat.length; p++) {
                x = Arrays.copyOf(pat[p], pat[p].length - 1);
                cy();
                double er = pat[p][2] - y;
                gEr += Math.abs(er);
                for (int i = 0; i < x.length; i++) {
                    w[i] += 0.1 * er * x[i];
                }
            }
            m++;
        } while (gEr!=0);
        System.out.println("m="+m);
    }

    public void test() {
        study();
        for (int p = 0; p < pat.length; p++) {
            x = Arrays.copyOf(pat[p], pat[p].length - 1);
            cy();
            System.out.println("y="+y);
        }

    }


    public static void main(String[] args) {

        new Perceptron().test();


    }

}
