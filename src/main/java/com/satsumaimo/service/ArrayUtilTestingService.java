package com.satsumaimo.service;

import com.satsumaimo.model.ArrayTaskTester;
import com.satsumaimo.util.ArrayUtil;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ArrayUtilTestingService extends ArrayTaskTester {

    private static final byte SHUFFLE = 0;
    private static final byte GET_SHUFFLED = 1;
    private static final Method[] arrayUtilMethods;
    private static final String[] tasks;

    private byte task;
    private int miss;
    private int[] shuffled;

    static {
        try {
            arrayUtilMethods = new Method[]{ArrayUtil.class.getMethod("shuffle", int[].class),
                    ArrayUtil.class.getMethod("getShuffled", int[].class)};
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        tasks = new String[arrayUtilMethods.length];

        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = arrayUtilMethods[i].getDeclaringClass().getSimpleName() + "." + arrayUtilMethods[i].getName();
        }
    }

    /**
     * Creates a new copy of the static variable - a String array of shuffle methods' names - {@code tasks}
     */
    public static String[] tasks() {
        return Arrays.copyOf(tasks, tasks.length);
    }

    public ArrayUtilTestingService(String[] tasks) {
        super(tasks);
    }

    public ArrayUtilTestingService(String[] tasks, int size) {
        super(tasks, size);
    }

    public ArrayUtilTestingService(String[] tasks, int numTests, int size) {
        super(tasks, numTests, size);
    }

    public ArrayUtilTestingService(String[] tasks, int progressGaugeLength, int numTests, int size) {
        super(tasks, progressGaugeLength, numTests, size);
    }

    @Override
    protected void preGroupTest(String taskName) {
        task = searchTask(taskName);
        miss = 0;

        nums = new int[size];
        for (int i = 0; i < size; i++) {
            nums[i] = i;
        }
    }

    private byte searchTask(String taskName) {
        for (byte i = 0; i < tasks.length; i++) {
            if (tasks[i].equals(taskName)) return i;
        }
        throw new IllegalArgumentException("the passed task name did not match any task");
    }

    @Override
    protected void performSingleTest() {
        if (task == SHUFFLE) testSingleShuffle();
        if (task == GET_SHUFFLED) testSingleGetShuffled();

        if (arrayUtil.isSorted(shuffled) || arrayUtil.areEqual(shuffled, nums)) miss++;

        nums = shuffled;
    }

    private void testSingleShuffle() {
        shuffled = Arrays.copyOf(nums, nums.length);
        arrayUtil.shuffle(shuffled);
    }

    private void testSingleGetShuffled() {
        shuffled = arrayUtil.getShuffled(nums);
    }

    @Override
    protected void postGroupTest(String taskName) {
        printReport(arrayUtilMethods[task].getName(), size, miss);
    }

    private void printReport(String methodName, int size, int miss) {
        System.out.printf("Tested %s at %d-sized input %n\ttotal = %d, miss = %d%n%n", methodName, size, getNumTests(), miss);
    }
}
