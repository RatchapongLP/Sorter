package com.satsumaimo.service;

import com.satsumaimo.model.ArrayTaskTester;
import com.satsumaimo.sort.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PerformSortingService extends ArrayTaskTester {
    private static final byte SELECTION_SORT = 0;
    private static final byte INSERTION_SORT = 1;
    private static final byte SHELL_SORT = 2;
    private static final byte MERGE_SORT = 3;
    private static final byte QUICK_SORT_SEDGEWICK = 4;
    private static final byte QUICK_SORT = 5;
    private static final Method[] sortingMethods;
    private static final String[] sortings;

    private int correctTest;
    private long totalTime;
    private long sortTime;
    private byte sortAlgorithm;

    static {
        try {
            sortingMethods = new Method[]{SelectionSort.class.getMethod("sort", int[].class),
                    InsertionSort.class.getMethod("sort", int[].class),
                    ShellSort.class.getMethod("sort", int[].class),
                    MergeSort.class.getMethod("sort", int[].class),
                    QuickSort.class.getMethod("sortSedgeWick", int[].class),
                    QuickSort.class.getMethod("sort", int[].class)};
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        sortings = new String[sortingMethods.length];

        for (int i = 0; i < sortings.length; i++) {
            sortings[i] = sortingMethods[i].getDeclaringClass().getSimpleName() + "." + sortingMethods[i].getName();
        }
//        System.out.println(Arrays.toString(sortings));
    }

    /**
     * Creates a new copy of the static variable - a String array of sorting methods' names - {@code sortings}
     */
    public static String[] sortings() {
        return Arrays.copyOf(sortings, sortings.length);
    }

    public PerformSortingService(String[] tasks) {
        super(tasks);
    }

    public PerformSortingService(String[] tasks, int size) {
        super(tasks, size);
    }

    public PerformSortingService(String[] tasks, int numTests, int size) {
        super(tasks, numTests, size);
    }

    public PerformSortingService(String[] tasks, int progressGaugeLength, int numTests, int size) {
        super(tasks, progressGaugeLength, numTests, size);
    }

    @Override
    protected void preGroupTest(String taskName) {
//        System.out.println("preTest in " + PerformSortingService.class.getSimpleName());
        sortAlgorithm = searchSorting(taskName);
        correctTest = 0;
        nums = arrayUtil.createShuffledArray(size);
        totalTime = System.currentTimeMillis();
        sortTime = 0;
    }

    @Override
    protected final void performSingleTest() {
        arrayUtil.shuffle(nums);

        try {
            callSorting(nums);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (arrayUtil.isSorted(nums)) correctTest++;
    }

    @Override
    protected void postGroupTest(String taskName) {
//        System.out.println("postTest in " + PerformSortingService.class.getSimpleName());
        totalTime = System.currentTimeMillis() - totalTime;
        printResult(sortAlgorithm, size, getNumTests(), correctTest, sortTime, totalTime);
    }

    private byte searchSorting(String taskName) {
        for (byte i = 0; i < sortings.length; i++) {
            if (sortings[i].equals(taskName)) return i;
        }
        throw new IllegalArgumentException("the passed task name did not match any sorting");
    }

    private void callSorting(int[] arr) throws InvocationTargetException, IllegalAccessException {
        long timeElapsed = System.currentTimeMillis();
        sortingMethods[sortAlgorithm].invoke(null, arr);
        sortTime += System.currentTimeMillis() - timeElapsed;
    }

    private void printResult(byte sortAlgorithm, int size, int numTests, int correctTests, long sortTime, long totalTime) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('_');
        formatter.setDecimalFormatSymbols(symbols);

        String sizeStr = formatter.format(size);
        String numTestStr = formatter.format(numTests);
        double percentCorrect = ((double) correctTests) / numTests * 100;

        String reportStr = """
                %15s    tested %s with %s sized input for %s times
                %15s    %.2f %%
                %15s    %.3f sec (sorting), %.3f sec (total) %n
                """;
//        System.out.printf("Tested %s with %s sized input for %s times%n    passed: %.2f %% %n    elapsed sorting time: %.3f sec%n    elapsed testing time: %.3f sec%n",
        System.out.printf(reportStr,
                "report:",
                sortings[sortAlgorithm],
                sizeStr,
                numTestStr,
                "passed:",
                percentCorrect,
                "elapsed time:",
                sortTime / 1000.0,
                totalTime / 1000.0);
    }

    private static void testMergeSortAscend(int numTest, int size, String type) {
        long startTime = System.currentTimeMillis();
        int correctTest = 0;
        int[] random;
        boolean isCorrect;
        for (int t = 0; t < numTest; t++) {
            isCorrect = true;
            random = new int[size];
            for (int i = 0; i < size; i++) {
                random[i] = (int) Math.round(size * Math.random()) - size / 2;
            }
            if (MergeSort.ARRAY.equals(type)) {
                MergeSort.sort(random);
                for (int i = 1; i < size; i++) {
                    if (random[i] < random[i - 1]) {
                        isCorrect = false;
                        break;
                    }
                }
            }
            if (MergeSort.LIST.equals(type)) {
                List<Integer> list = Arrays.stream(random).boxed().toList();
                list = MergeSort.sort(list);
                for (int i = 1; i < size; i++) {
                    if (list.get(i) < list.get(i - 1)) {
                        isCorrect = false;
                        break;
                    }
                }
            }
            if (isCorrect) correctTest++;
        }
        long finishTime = System.currentTimeMillis();
        System.out.printf("Tested Merge Sort(%s) for %,d times%n    passed: %.2f %% %n    elapsed time: %.3f sec%n",
                type.toLowerCase(), numTest, (double) correctTest / numTest * 100, (double) (finishTime - startTime) / 1000);
    }

    private void testSimpleSort(int[] array) {
        String beforeSorted = Arrays.toString(array);

        try {
            callSorting(array);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        String afterSorted = Arrays.toString(array);

        System.out.printf("%-20s %n\t%s %n\t%s %n\tsorted: %s %n%n",
                sortings[sortAlgorithm], beforeSorted, afterSorted, arrayUtil.isSorted(array));
    }

    public static void main(String[] args) {
        int numTests = 1000;
        int size = 1_000_000;

        if (args.length > 2 || args.length == 1)
            throw new IllegalArgumentException("Usage: java PerformSortingService [numTests] [size]");
        if (args.length == 2) {
            try {
                if ((numTests = Integer.parseInt(args[0])) < 1) {
                    throw new IllegalArgumentException("Usage: The number of testing must be at least one");
                }
                if ((size = Integer.parseInt(args[1])) < 0) {
                    throw new IllegalArgumentException("Usage: The size of the requested array must be a non-negative number");
                }
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            }
        }
//
//        String[] actualTasks = new String[3];
//        actualTasks[0] = sortings[3];
//        actualTasks[1] = sortings[4];
//        actualTasks[2] = sortings[5];
//        PerformSortingService performSortingService = new PerformSortingService(actualTasks, numTests, size);
//        TasksApplicationContext context = new TasksApplicationContext(new ProgressTrackable[]{performSortingService});
//        context.start();

//        int[] random = createShuffledArray(size);
//        performSortingService.testSimpleSort(Arrays.copyOf(random, random.length), QUICK_SORT);
//        int[] copy = Arrays.copyOf(random, random.length);
//        List<Integer> list = Arrays.stream(copy).boxed().toList();
//        list = MergeSort.sort(list);
    }
}
