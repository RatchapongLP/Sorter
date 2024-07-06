package com.satsumaimo.config;

import com.satsumaimo.model.ArrayTaskTester;
import com.satsumaimo.model.TaskTester;
import com.satsumaimo.service.ArrayUtilTestingService;
import com.satsumaimo.service.PerformSortingService;

import java.util.Arrays;

/**
 * This class's methods can be public if, and only if they are used for creating beans only
 */
public class TasksApplicationConfiguration {
    public static final Class[] beanClasses;
    private final static String[] sortingTasks;
    private final static String[] arrayUtilTestTasks;
    private final static Object[] defaultParameters;
    private final Object[] parameters;

    static {
        beanClasses = new Class[]{TaskTester.class};

        String[] sortings = PerformSortingService.sortings();
        sortingTasks = new String[]{sortings[3], sortings[4], sortings[5]};

        arrayUtilTestTasks = ArrayUtilTestingService.tasks();

        defaultParameters = new Object[]{TaskTester.DEFAULT_NUM_TESTS, ArrayTaskTester.DEFAULT_NUMS_SIZE};
    }

    public TasksApplicationConfiguration() {
        this.parameters = Arrays.copyOf(defaultParameters, defaultParameters.length);
    }

    public PerformSortingService initializePerformSorting() {
        int numTests = (int) parameters[0];
        int size = (int) parameters[1];
        return new PerformSortingService(sortingTasks, numTests, size);
    }

    public ArrayUtilTestingService initializeArrayUtilTesting() {
        int numTests = (int) parameters[0];
        int size = (int) parameters[1];
        return new ArrayUtilTestingService(arrayUtilTestTasks, numTests, size);
    }
}
