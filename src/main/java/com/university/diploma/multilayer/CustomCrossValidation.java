package com.university.diploma.multilayer;

import smile.classification.Classifier;
import smile.math.MathEx;
import smile.validation.CrossValidation;

import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class CustomCrossValidation extends CrossValidation {

    public CustomCrossValidation(int n, int k) {
        super(n, k);
    }

    public static <T> int[] classification(int k, T[] x, int[] y, BiFunction<T[], int[], Classifier<T>> trainer) {
        CrossValidation cv = new CustomCrossValidation(x.length, k);
        return cv.classification(x, y, trainer);
    }

    public <T> int[] classification(T[] x, int[] y, BiFunction<T[], int[], Classifier<T>> trainer) {
        int[] prediction = new int[x.length];

        ForkJoinPool customThreadPool = new ForkJoinPool(4);
        //  customThreadPool.submit(() ->
        IntStream.range(0, k)
                 .parallel()
                 .forEach(i -> {
                     T[] trainx = MathEx.slice(x, train[i]);
                     int[] trainy = MathEx.slice(y, train[i]);

                     Classifier<T> model = trainer.apply(trainx, trainy);

                     for (int j : test[i]) {
                         prediction[j] = model.predict(x[j]);
                     }
                 });
        //).join();
        //        for (int i = 0; i < k; i++) {
        //            T[] trainx = MathEx.slice(x, train[i]);
        //            int[] trainy = MathEx.slice(y, train[i]);
        //
        //            Classifier<T> model = trainer.apply(trainx, trainy);
        //
        //            for (int j : test[i]) {
        //                prediction[j] = model.predict(x[j]);
        //            }
        //        }

        return prediction;
    }
}
