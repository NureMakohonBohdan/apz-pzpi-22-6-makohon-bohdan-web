package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service;

import java.util.Arrays;


public class MathUtils {

    public static double mean(double[] data) {
        if (data.length == 0) return 0;
        double sum = 0;
        for (double d : data) sum += d;
        return sum / data.length;
    }

    public static double median(double[] data) {
        if (data.length == 0) return 0;
        double[] sorted = Arrays.copyOf(data, data.length);
        Arrays.sort(sorted);
        int mid = sorted.length / 2;
        return (sorted.length % 2 != 0) ? sorted[mid] : (sorted[mid - 1] + sorted[mid]) / 2.0;
    }

    public static double standardDeviation(double[] data) {
        if (data.length == 0) return 0;
        double mean = mean(data);
        double sum = 0;
        for (double d : data) sum += Math.pow(d - mean, 2);
        return Math.sqrt(sum / data.length);
    }

    public static double min(double[] data) {
        return Arrays.stream(data).min().orElse(0);
    }

    public static double max(double[] data) {
        return Arrays.stream(data).max().orElse(0);
    }

    public static double sum(double[] data) {
        return Arrays.stream(data).sum();
    }

    // 1. Фільтрація за діапазоном
    public static double[] filterByRange(double[] data, double min, double max) {
        return Arrays.stream(data)
                .filter(d -> d >= min && d <= max)
                .toArray();
    }

    // 2. Видалення аномалій (outlier removal, за 3 sigma)
    public static double[] filterOutliers(double[] data) {
        double mean = mean(data);
        double std = standardDeviation(data);
        return Arrays.stream(data)
                .filter(d -> Math.abs(d - mean) <= 3 * std)
                .toArray();
    }

    // 3. Згладжування рухомим середнім (moving average)
    public static double[] movingAverage(double[] data, int window) {
        if (window <= 1 || window > data.length) return data;
        double[] result = new double[data.length - window + 1];
        for (int i = 0; i < result.length; i++) {
            double sum = 0;
            for (int j = 0; j < window; j++) {
                sum += data[i + j];
            }
            result[i] = sum / window;
        }
        return result;
    }

    // 4. Фільтрація NaN та Inf значень (на всяк випадок)
    public static double[] filterNaNandInf(double[] data) {
        return Arrays.stream(data)
                .filter(d -> !Double.isNaN(d) && !Double.isInfinite(d))
                .toArray();
    }
}
