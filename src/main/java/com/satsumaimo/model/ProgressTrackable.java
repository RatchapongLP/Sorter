package com.satsumaimo.model;

public interface ProgressTrackable {
    String[] getTasks();
    void initProgressTracking();
    void preExecute(String taskName);
    void executeTask(String taskName);
    void postExecute(String taskName);
}
