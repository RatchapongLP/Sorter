package com.satsumaimo.sort;

import java.util.Comparator;

public class SelectionSort {

    public enum Order {
        ASCENDING,
        DESCENDING
    }

    public static void sort(int[] input) {
        if (input == null) throw new IllegalArgumentException("Require non null argument");
        sortAscend(input);
    }

    public static void sort(double[] input) {
        if (input == null) throw new IllegalArgumentException("Require non null argument");
        sortAscend(input);
    }

    public static <T extends Comparable<T>> void sort(T[] input) {
        if (input == null) throw new IllegalArgumentException("Require non null argument");
        sortAscend(input);
    }

    public static void sort(int[] input, Order order) {
        if (input == null) throw new IllegalArgumentException("Require non null argument");

        if (order == null || order.equals(Order.ASCENDING)) sortAscend(input);
        else sortDescend(input);
    }

    public static void sort(double[] input, Order order) {
        if (input == null) throw new IllegalArgumentException("Require non null argument");

        if (order == null || order.equals(Order.ASCENDING)) sortAscend(input);
        else sortDescend(input);
    }

    public static <T extends Comparable<T>> void sort(T[] input, Order order) {
        if (input == null) throw new IllegalArgumentException("Require non null argument");

        if (order == null || order.equals(Order.ASCENDING)) sortAscend(input);
        else sortDescend(input);
    }

    public static <T extends Comparable<T>> void sort(T[] input, Order order, Comparator<T> comparator) {
        if (input == null) throw new IllegalArgumentException("Require non null argument");

        if (order == null || order.equals(Order.ASCENDING)) sortAscend(input, comparator);
        else sortDescend(input, comparator);
    }

    public static void sortAscend(int[] input) {
        if (input == null) throw new IllegalArgumentException("Require non null argument");

        for (int i = 0; i < input.length - 1; i++) {
            for (int j = i + 1; j < input.length; j++) {
                if (input[j] < input[i]) {
                    int tmp = input[j];
                    input[j] = input[i];
                    input[i] = tmp;
                }
            }
        }
    }

    public static void sortAscend(double[] input) {
        if (input == null) throw new IllegalArgumentException("Require non null argument");

        for (int i = 0; i < input.length - 1; i++) {
            for (int j = i + 1; j < input.length; j++) {
                if (input[j] < input[i]) {
                    double tmp = input[j];
                    input[j] = input[i];
                    input[i] = tmp;
                }
            }
        }
    }

    public static <T extends Comparable<T>> void sortAscend(T[] input) {
        if (input == null) throw new IllegalArgumentException("Require non null argument");

        for (int i = 0; i < input.length - 1; i++) {
            for (int j = i + 1; j < input.length; j++) {
                if (input[j].compareTo(input[i]) < 0) {
                    T tmp = input[j];
                    input[j] = input[i];
                    input[i] = tmp;
                }
            }
        }
    }

    public static <T extends Comparable<T>> void sortAscend(T[] input, Comparator<T> comparator) {
        if (input == null) throw new IllegalArgumentException("Require non null argument");

        if (comparator == null) {
            sortAscend(input);
            return;
        }

        for (int i = 0; i < input.length - 1; i++) {
            for (int j = i + 1; j < input.length; j++) {
                if (comparator.compare(input[j], input[i]) < 0) {
                    T tmp = input[j];
                    input[j] = input[i];
                    input[i] = tmp;
                }
            }
        }
    }

    public static void sortDescend(int[] input) {
        if (input == null) throw new IllegalArgumentException("Require non null argument");

        for (int i = 0; i < input.length - 1; i++) {
            for (int j = i + 1; j < input.length; j++) {
                if (input[j] > input[i]) {
                    int tmp = input[j];
                    input[j] = input[i];
                    input[i] = tmp;
                }
            }
        }
    }

    public static void sortDescend(double[] input) {
        if (input == null) throw new IllegalArgumentException("Require non null argument");

        for (int i = 0; i < input.length - 1; i++) {
            for (int j = i + 1; j < input.length; j++) {
                if (input[j] > input[i]) {
                    double tmp = input[j];
                    input[j] = input[i];
                    input[i] = tmp;
                }
            }
        }
    }

    public static <T extends Comparable<T>> void sortDescend(T[] input) {
        if (input == null) throw new IllegalArgumentException("Require non null argument");

        for (int i = 0; i < input.length - 1; i++) {
            for (int j = i + 1; j < input.length; j++) {
                if (input[j].compareTo(input[i]) > 0) {
                    T tmp = input[j];
                    input[j] = input[i];
                    input[i] = tmp;
                }
            }
        }
    }

    public static <T extends Comparable<T>> void sortDescend(T[] input, Comparator<T> comparator) {
        if (input == null) throw new IllegalArgumentException("Require non null argument");

        if (comparator == null) {
            sortDescend(input);
            return;
        }

        for (int i = 0; i < input.length - 1; i++) {
            for (int j = i + 1; j < input.length; j++) {
                if (comparator.compare(input[j], input[i]) > 0) {
                    T tmp = input[j];
                    input[j] = input[i];
                    input[i] = tmp;
                }
            }
        }
    }
}
