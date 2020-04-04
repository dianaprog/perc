package com.university.diploma.multilayer;

import smile.feature.Normalizer;

import java.util.Arrays;

public class CustomNormalizer extends Normalizer {

    private double max;

    private double min;

    private double diff;

    public void setMaxDiff(double[][] x) {
        final double max = Arrays.stream(x)
                                 .flatMapToDouble(Arrays::stream)
                                 .max().getAsDouble();

        final double min = Arrays.stream(x)
                                 .flatMapToDouble(Arrays::stream)
                                 .min().getAsDouble();

        this.max = max;
        this.min = min;
        this.diff = max - min;

    }

    @Override
    public double[] transform(double[] x) {
        double[] y = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = (x[i] - min) / diff;
            final double v = y[i] / 2;
        }
        return y;
    }
}
