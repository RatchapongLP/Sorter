package com.satsumaimo.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MergeSort<T extends Comparable<T>> {

    public static final String ARRAY = "ARRAY";
    public static final String LIST = "LIST";

    public static void sort(int[] nums) {
        if (nums == null) throw new IllegalArgumentException();

        int[] aux = new int[nums.length];
        System.arraycopy(nums, 0, aux, 0, nums.length);
        sort(nums, aux, 0, nums.length - 1);
    }

    private static void sort(int[] nums, int[] aux, int start, int end) {
        if (nums == null || aux == null) throw new IllegalArgumentException();

        if (start == end) return;

        // Divides it into halves and mergeSort each half
        int mid = start + (end - start) / 2;
        sort(aux, nums, start, mid);
        sort(aux, nums, mid + 1, end);
        mergeSort(nums, aux, start, end);
//        System.out.printf("done sorting array at start = %d, end = %d%n%s%n", start, end, Arrays.toString(nums));
    }

    private static void mergeSort(int[] nums, int[] aux, int start, int end) {
        int i = start;
        int mid = start + (end - start) / 2;
        int j = mid + 1;
        for (int k = start; k <= end; k++) {
            if (i > mid) nums[k] = aux[j++];
            else if (j > end) nums[k] = aux[i++];
            else if (aux[j] < aux[i]) nums[k] = aux[j++];
            else nums[k] = aux[i++];
        }
    }

    public static void sort(double[] nums) {
        if (nums == null) throw new IllegalArgumentException();

        double[] aux = new double[nums.length];
        System.arraycopy(nums, 0, aux, 0, nums.length);
        sort(nums, aux, 0, nums.length - 1);
    }

    private static void sort(double[] nums, double[] aux, int start, int end) {
        if (nums == null || aux == null) throw new IllegalArgumentException();

        if (start == end) return;

        // Divides it into halves and mergeSort each half
        int mid = start + (end - start) / 2;
        sort(aux, nums, start, mid);
        sort(aux, nums, mid + 1, end);
        mergeSort(nums, aux, start, end);
//        System.out.printf("done sorting array at start = %d, end = %d%n%s%n", start, end, Arrays.toString(nums));
    }

    private static void mergeSort(double[] nums, double[] aux, int start, int end) {
        int i = start;
        int mid = start + (end - start) / 2;
        int j = mid + 1;
        for (int k = start; k <= end; k++) {
            if (i > mid) nums[k] = aux[j++];
            else if (j > end) nums[k] = aux[i++];
            else if (aux[j] < aux[i]) nums[k] = aux[j++];
            else nums[k] = aux[i++];
        }
    }

    public static <T extends Comparable<T>> List<T> sort(List<T> list) {

        int size = list.size();
        if (list.size() < 2) return list;

        // If the size of the input is at least two, we divide it into halves and mergeSort each half
        return mergeArray(sort(list.subList(0, size / 2)), sort(list.subList(size / 2, size)));
    }

    public static <T extends Comparable<T>> List<T> mergeArray(List<T> list1, List<T> list2) {

        int len1 = list1.size(), len2 = list2.size();
        List<T> result = new ArrayList<>(len1 + len2);

        int p1 = 0, p2 = 0;
        while (p1 < len1 && p2 < len2) {
            if (list1.get(p1).compareTo(list2.get(p2)) <= 0) {
                result.add(list1.get(p1));
                p1++;
            } else {
                result.add(list2.get(p2));
                p2++;
            }
        }
        while (p2 < len2) {
            result.add(list2.get(p2));
            p2++;
        }
        while (p1 < len1) {
            result.add(list1.get(p1));
            p1++;
        }
        return result;
    }

    private static List<Integer> getRandomIntList(int size) {

        List<Integer> list = new ArrayList<>(size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            list.add(random.nextInt(-100, 101));
        }
        // list.sort(Integer::compareTo);
        return list;
    }
    private static List<Character> getRandomCharList(int size) {

        List<Character> list = new ArrayList<>(size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            list.add((char) random.nextInt(65, 91));
        }
        // list.sort(Character::compareTo);
        return list;
    }

    public static void main(String[] args) {

        System.out.println("Merge Sort");
//        var test = new MergeSort<Integer>();
//        List<Integer> list1 = List.of(1,3,5,7,8);
//        List<Integer> list2 = List.of(2,3,4);
//        List<Integer> list3 = new ArrayList<>();
//
//        var result = test.mergeArray(list1, list2);
//        System.out.println(result);
//
//        var result1 = test.mergeArray(list1, list3);
//        System.out.println(result1);
//
//        for (int i = 0; i < 10; i++) {
//            System.out.println(new MergeSort<Integer>().mergeArray(getRandomIntList(5), getRandomIntList(6)));
//        }

//        System.out.println("\n".repeat(5));
        for (int i = 0; i < 10; i++) {
            System.out.println(sort(getRandomIntList(10)));
        }

        System.out.println("\n".repeat(5));
        for (int i = 0; i < 10; i++) {
            System.out.println(sort(getRandomCharList(10)));
        }

        int size = 20;
        int[] random = new int[size];
        for (int i = 0; i < size; i++) {
            random[i] = (int) Math.round(size * Math.random()) - size / 2;
        }
        System.out.printf("random = %s%n%n", Arrays.toString(random));

        int[] copy = Arrays.copyOf(random, random.length);
        MergeSort.sort(copy);
        System.out.printf("%-20s = %s%n", "merge sorted", Arrays.toString(copy));

    }
}
