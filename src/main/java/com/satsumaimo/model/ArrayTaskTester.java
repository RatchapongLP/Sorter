package com.satsumaimo.model;

import com.satsumaimo.util.ArrayUtil;

public abstract class ArrayTaskTester extends TaskTester {
    public static final int DEFAULT_NUMS_SIZE = 100;
    protected static final ArrayUtil staticArrayUtil;

    protected ArrayUtil arrayUtil;
    protected int[] nums;
    protected int size;

    static {
        staticArrayUtil = new ArrayUtil();
    }

    private void init(int size) {
        if (this.arrayUtil == null) {
            arrayUtil = staticArrayUtil;
        }
        this.size = size;
    }
    public ArrayTaskTester(String[] tasks) {
        this(tasks, DEFAULT_NUMS_SIZE);
    }

    public ArrayTaskTester(String[] tasks, int size) {
        super(tasks);
        init(size);
    }

    public ArrayTaskTester(String[] tasks, int numTests, int size) {
        super(tasks, numTests);
        init(size);
    }

    public ArrayTaskTester(String[] tasks, int progressGaugeLength, int numTests, int size) {
        super(tasks, progressGaugeLength, numTests);
        init(size);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
