package com.satsumaimo.sort;

import com.satsumaimo.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuickSort<T extends Comparable<T>> {

    private static final ArrayUtil arrayUtil;

    static {
        arrayUtil = new ArrayUtil();
    }
    public static void sortSedgeWick(int[] array) {
        arrayUtil.shuffle(array);
        sortSedgeWick(array, 0, array.length - 1);
    }

    public static void sort(int[] array) {
        arrayUtil.shuffle(array);
        sort(array, 0, array.length - 1);
    }

    public static void sortSedgeWick(int[] array, int lo, int hi) {
        if (lo >= hi) return;

        int i = lo, j = hi + 1;
        while (true) {
            while (array[++i] < array[lo]) {
                if (i == hi) break;
            }
            while (array[--j] > array[lo]) {
                if (j == lo) break;
            }
            if (i >= j) break;
            arrayUtil.swap(array, i, j);
        }
        arrayUtil.swap(array, j, lo);

        sortSedgeWick(array, lo, j - 1);
        sortSedgeWick(array, j + 1, hi);
    }

    public static void sort(int[] array, int lo, int hi) {
        if (lo >= hi) return;

        int pivot = array[lo];
        int lastOfLessIdx = lo;
        int temp;
        for (int i = lo + 1; i <= hi; i++) {
//            System.out.println(Arrays.toString(array));
            if (array[i] < pivot) {
                temp = array[lastOfLessIdx];
                array[lastOfLessIdx++] = array[i];
                array[i] = array[lastOfLessIdx];
                array[lastOfLessIdx] = temp;
            }
        }
        sort(array, lo, lastOfLessIdx - 1);
        sort(array, lastOfLessIdx + 1, hi);
    }

    public static <T extends Comparable<T>> void sort(T[] array, int lo, int hi) {
        if (lo >= hi) return;

        int i = lo, j = hi + 1;
        while (true) {
            while (array[++i].compareTo(array[lo]) < 0) {
                if (i == hi) break;
            }
            while (array[--j].compareTo(array[lo]) > 0) {
                if (j == lo) break;
            }
            if (i >= j) break;
            arrayUtil.swap(array, i, j);
        }
        arrayUtil.swap(array, j, lo);
        sort(array, lo, j - 1);
        sort(array, j + 1, hi);
    }

    public List<T> quickSort(List<T> list) {
        int size = list.size();
        if (size < 2) return list;

        // If the size of the input is at least two, we split it with a pivot and quickSort each two group
        // Transfer all the elements that are less than the pivot to the left group
        // Create a variable to keep track of the total number of the left group
        int leftCount = 0;
        // In this case, we choose a pivot that is the first element of the list
        T pivot = list.get(0);
        // So we iterate through the list starting from the third element, index = 2,
        // to arrayUtil.swap it with the one at index = leftCount + 1
        for (int i = 1; i < size; i++) {
            if (list.get(i).compareTo(pivot) <= 0) {
                T temp = list.get(i);
                list.set(i, list.get(leftCount + 1));
                list.set(leftCount + 1, temp);
                leftCount++;
            }
        }
        List<T> result = new ArrayList<>();
        if (leftCount > 0)
            result = quickSort(new ArrayList<>(list.subList(1, leftCount + 1)));
        result.add(pivot);
        if (size - leftCount > 0)
            result.addAll(quickSort(new ArrayList<>(list.subList(leftCount + 1, size))));
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

        System.out.println("Quick Sort Integers");
        for (int i = 0; i < 10; i++) {
            System.out.println(new QuickSort<Integer>().quickSort(getRandomIntList(10)));
        }

        System.out.println("\nQuick Sort Characters");
        for (int i = 0; i < 10; i++) {
            System.out.println(new QuickSort<Character>().quickSort(getRandomCharList(10)));
        }

        System.out.println();
        int[] arr = {5,2,2,2};
        sort(arr);
        System.out.println(Arrays.toString(arr));

        arr = new int[]{2, 5};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
