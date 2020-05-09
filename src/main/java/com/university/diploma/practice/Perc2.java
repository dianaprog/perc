package com.university.diploma.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Perc2 {
    double[] x;
    double y;
    double[][] wxh;
    double[] why;
    double[] h;
    double[][] pat = new double[172][3];
    double[] r = new double[172];
    private static String fname = "/Users/dianashageeva/IdeaProjects/diploma/src/main/resources/EURUSD.csv";
    private static String OUTPUT_FILE = "/Users/dianashageeva/IdeaProjects/diploma/src/main/resources/output.csv";
    private static final String separator = ";";

    private List<String> output = new ArrayList<>();
    public Perc2() {
        x = new double[pat[0].length];
        h = new double[2];
        wxh = new double[x.length][h.length];
        why = new double[h.length];
        Interval2.ReadCSV(r, pat, separator, fname);
        pat = normalize(pat);
       // printpat(pat);
       // printArray(r);
        init();
        study();
        for (int p = 0; p < pat.length; p++) {
            for (int i = 0; i < x.length; i++)
                x[i]=pat[p][i];
            cy();
            System.out.println("y="+y);
            output.add("y="+y);
            //readFile.writeToFile(y); доделать пункт 5
        }
        saveToFile(output);
    }

    public void init(){
        System.out.println("Начальные значения весов первого слоя");
        for (int i = 0; i < wxh.length; i++) {
            for (int j = 0; j < wxh[i].length; j++) {
                wxh[i][j] = Math.random() * 0.3 + 0.1;
                System.out.println("wxh["+i+"]["+j+"]="+wxh[i][j]);
            }
        }
		/*wxh[0][0]=0.5;
		wxh[0][1]=0.4;
		wxh[1][0]=0.4;
		wxh[1][1]=0.5;
		why[0]=-0.1;
		why[1]=0.6;*/
        System.out.println("Начальные значения весов второго слоя");
        for (int i = 0; i < why.length; i++) {
            why[i] = Math.random() * 0.3 + 0.1;
            System.out.println("why["+i+"]="+why[i]);
        }
    }
    public void cy() {
        for (int i = 0; i < h.length; i++) {
            h[i] = 0;
            for (int j = 0; j < x.length; j++) {
                //1.0 / (1 + np.exp(-x))
                h[i] += x[j] * wxh[j][i];
            }

            h[i] = 1.0 / (1 + Math.exp(-h[i]));

        }
        y = 0;
        for (int i = 0; i < h.length; i++) {
            y += h[i] * why[i];
        }
        if (y > 0.5)
            y = 1;
        else
            y = 0;

    }

    public void study() {
        double[] err = new double [h.length];
        double gEr = 0;
        double m=0;
        do {
            gEr = 0;

            for (int p = 0; p < pat.length; p++) {
                for (int i = 0; i < x.length; i++)
                    x[i]=pat[p][i];
                cy();
                double lEr=r[p]-y;

                gEr += Math.abs(lEr);
                for (int i = 0; i < h.length; i++)
                    err[i] =  lEr * why[i];
                for (int i = 0; i < x.length; i++) {
                    for (int j = 0; j < h.length; j++) {
                        wxh[i][j]+=0.1*err[j]*x[i];
                    }}
                for (int i = 0; i < h.length; i++)
                    why[i]+=0.1*lEr*h[i];

            }
            m++;
        } while (gEr!=0 && m <= 100);

        System.out.println("Количество итераций равно "+m);
        System.out.println("wxh[0][0]="+wxh[0][0]);
        System.out.println("wxh[0][1]="+wxh[0][1]);
        System.out.println("wxh[1]01]="+wxh[1][0]);
        System.out.println("wxh[1][1]="+wxh[1][1]);
        System.out.println("why[0]="+why[0]);
        System.out.println("why[0]="+why[1]);
    }

    public static void main(String[] args) {

        Perc2 perc2=new Perc2();


    }

    public void printpat(double[][] pat)
    {
        for (int i = 0; i < 172; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(pat[i][j] + "\t");
            }
            System.out.println("");
        }
    }

    public void printArray (double[] array)
    {
        for (int i=0; i<array.length; i++)
        {
            System.out.println(array[i]);
        }
    }

    private static void saveToFile(List<String> content) {
        try {
            Files.write(Paths.get(OUTPUT_FILE), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double[][] normalize(double[][] arr) {
        double[] maxArr = new double[arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                maxArr[j] = Math.max(maxArr[j], arr[i][j]);
            }
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] /= maxArr[j];
            }
        }
        return arr;
    }
}