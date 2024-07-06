package com.satsumaimo.model;

import java.util.Arrays;

public abstract class TaskTester implements ProgressTrackable{
    private static final int[] mileStonePercent = new int[]{10, 25, 50, 75, 100};
    private static final String[] mileStoneTags;
    public static final int DEFAULT_NUM_TESTS = 1000;
    private static final int DEFAULT_PROGRESS_GAUGE_LENGTH = 50;

    private final String[] tasks;
    private int progressGaugeLength;
    private int progressDotIdx;
    private int progressMileStoneTagIdx;
    private final int numTests;
    private int curTest;

    static {
        mileStoneTags = new String[mileStonePercent.length];

        for (int i = 0; i < mileStonePercent.length; i++) {
            mileStoneTags[i] = mileStonePercent[i] + "%";
        }
    }

    public TaskTester() {
        this(null);
    }

    public TaskTester(String[] tasks) {
        this(tasks, DEFAULT_NUM_TESTS);
    }

    public TaskTester(String[] tasks, int numTests) {
        this(tasks, DEFAULT_PROGRESS_GAUGE_LENGTH, numTests);
    }

    public TaskTester(String[] tasks, int progressGaugeLength, int numTests) {
        this.tasks = Arrays.copyOf(tasks, tasks.length);
        this.progressGaugeLength = progressGaugeLength;
        this.numTests = numTests;
    }

    @Override
    public final void initProgressTracking() {
        if (tasks == null) return;

        progressMileStoneTagIdx = -1;
        progressDotIdx = 0;
        curTest = 0;
    }

    public final void initProgressTracking(int gaugeLength) {
        initProgressTracking();
        progressGaugeLength = gaugeLength;
    }

    @Override
    public final void preExecute(String taskName) {
        preGroupTest(taskName);
    }

    @Override
    public final void executeTask(String taskName) {
//        System.out.println("executeTask in " + TaskTester.class.getSimpleName());
        System.out.print("|");
        for (int t = 1; t <= numTests; t++) {
            curTest++;
            performSingleTest();
            printTestProgress();
        }
        System.out.println("> Done");
    }

    @Override
    public final void postExecute(String taskName) {
        postGroupTest(taskName);
    }

    protected abstract void preGroupTest(String taskName);
    protected abstract void performSingleTest();
    protected abstract void postGroupTest(String taskName);

    /**
     * Prints a '.' or a percentage indicating the progress of the test. Required to get called at every time of testing,
     * otherwise the printed display is not accurate.
     */
    private void printTestProgress() {
        if (progressDotIdx == progressGaugeLength) return;

        double curProgressRatio = (double) curTest / numTests; // ratio in range 0.00 - 1.00
        double curProgressPercent = curProgressRatio * 100; // percent in range 0.00 - 100.00

        int curProgressDotIdx = (int) (curProgressRatio * progressGaugeLength); // in range 0 - progressGaugeLength
        if (curProgressDotIdx <= progressDotIdx) return;
        progressDotIdx = curProgressDotIdx;

        String str = "-";
        for (int i = 0; i < mileStonePercent.length; i++) {
            if (curProgressPercent >= mileStonePercent[i] &&
                    progressMileStoneTagIdx == i - 1) {
                str = mileStoneTags[++progressMileStoneTagIdx];
            }
        }

        System.out.print(str);
    }

    @Override
    public String[] getTasks() {
        return Arrays.copyOf(tasks, tasks.length);
    }

    public int getProgressGaugeLength() {
        return progressGaugeLength;
    }

    public int getProgressDotIdx() {
        return progressDotIdx;
    }

    public int getProgressMileStoneTagIdx() {
        return progressMileStoneTagIdx;
    }

    public int getNumTests() {
        return numTests;
    }

    public int getCurTest() {
        return curTest;
    }
}
