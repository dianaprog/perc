package com.university.diploma.multilayer;

import org.apache.commons.csv.CSVFormat;
import smile.base.mlp.Layer;
import smile.base.mlp.OutputFunction;
import smile.classification.MLP;
import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.io.Read;
import smile.math.MathEx;
import smile.validation.Error;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class SmileMLP {

    public static void main(String[] args) throws IOException, URISyntaxException {

        String[] strings = new String[] {"friends", "followers", "photos", "pages", "videos"};

        final CSVFormat format = CSVFormat.newFormat('\t').withFirstRecordAsHeader().withIgnoreEmptyLines()
                                          .withTrim();
        String directory = "/Users/dianashageeva/Documents/dipl/";
        final DataFrame csv = Read.csv(directory + "strong.tsv", format).slice(0,750);
        final DataFrame strong = csv.select(strings);
        final DataFrame weak = Read.csv(directory + "weak.tsv", format).select(strings);
        final DataFrame middle = Read.csv(directory + "middle.tsv", format).select(strings);
        final int[] videos = middle.vector("videos").toIntArray();
        middle.drop("videos");
        final DataFrame merge =
                middle.select("friends", "followers", "photos", "pages").merge(IntVector.of("videos", videos));

        final CustomNormalizer normalizer = new CustomNormalizer();
        final DataFrame union = strong.union(weak).union(merge);
        final double[][] data = union.toArray();
        normalizer.setMaxDiff(data);
        final double[][] x = normalizer.transform(data);
        final int[] y = new int[x.length];
        Arrays.fill(y, 0, strong.size()-1, 1);
        Arrays.fill(y, strong.size(), weak.size()+strong.size()-1, 0);
        Arrays.fill(y, weak.size()+strong.size(), y.length, 2);

        int p = x[0].length;
        int k = MathEx.max(y) + 1;

        final long start = System.currentTimeMillis();
        int[] prediction = CustomCrossValidation.classification(10, x, y, (xi, yi) -> {
            final MLP model = new MLP(p,
                    Layer.sigmoid(210),
                    Layer.sigmoid(10),
                    Layer.mse(k, OutputFunction.SIGMOID)
            );
            for (int epoch = 1; epoch <= 10; epoch++) {
                int[] permutation = MathEx.permutate(xi.length);
                for (int i : permutation) {
                    model.update(xi[i], yi[i]);
                }
            }
            return model;
        });

        final long end = System.currentTimeMillis() - start;

        int error = Error.of(y, prediction);
        System.out.println("Error = " + error);
        System.out.println("Accuracy = " + (1 - (double)error/y.length));
        System.out.println("Seconds "+ end/1000);
    }
}
