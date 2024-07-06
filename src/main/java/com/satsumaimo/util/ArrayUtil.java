package com.satsumaimo.util;

import java.util.Arrays;
import java.util.Random;

public class ArrayUtil {

    public boolean areEqual(int[] arr1, int[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) return false;
        }
        return true;
    }

    public boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return false;
            }
        }
        return true;
    }

    public int[] createShuffledArray(int size) {
        return createShuffledArray(size, -size / 2, (int) Math.ceil(size / 2.0));
    }

    public int[] createShuffledArray(int size, int lo, int hi) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(lo, hi + 1);
        }
        return array;
    }

/**
    Creates a new shuffled array from the {@code src} array. The {@code src} array remains untouched.
 */
    public int[] getShuffled(int[] src) {
        Random random = new Random();
        int[] shuffled = Arrays.copyOf(src, src.length);
        int randomIdx;
        for (int i = 0; i < shuffled.length; i++) {
            randomIdx = random.nextInt(i + 1);
            swap(shuffled, randomIdx, i);
        }
        return shuffled;
    }

/**
    Shuffles the {@code src} array.
 */
    public void shuffle(int[] src) {
        Random random = new Random();
        int randomIdx;
        for (int i = 0; i < src.length; i++) {
            randomIdx = random.nextInt(i + 1);
            swap(src, randomIdx, i);
        }
    }

    public void swap(int[] array, int i, int j) {
//        System.out.printf("swapping i = %d, j = %d%n", i, j);
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
