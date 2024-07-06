package com.satsumaimo.context;

import com.satsumaimo.config.TasksApplicationConfiguration;
import com.satsumaimo.model.ProgressTrackable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TasksApplicationContext {
    private TasksApplicationConfiguration tasksApplicationConfiguration;
    private Method[] beanCreatingMethods;
    private ProgressTrackable[] progressTrackables;

    public TasksApplicationContext(TasksApplicationConfiguration tasksApplicationConfiguration) {
        this.tasksApplicationConfiguration = tasksApplicationConfiguration;
        beanCreatingMethods = TasksApplicationConfiguration.class.getMethods();
        System.out.println(Arrays.toString(beanCreatingMethods));

        List<Method> beanCreatingMethodList = new LinkedList<>();
        Class[] beanClasses = TasksApplicationConfiguration.beanClasses;
        for (Method beanCreatingMethod : beanCreatingMethods) {
            for (Class clazz : beanClasses) {
                if (clazz.isAssignableFrom(beanCreatingMethod.getReturnType())) {
                    beanCreatingMethodList.add(beanCreatingMethod);
                    break;
                }
            }
        }
        beanCreatingMethods = beanCreatingMethodList.toArray(new Method[0]);
        System.out.println(Arrays.toString(beanCreatingMethods));

        createBeans();
    }

    private void createBeans() {
        progressTrackables = new ProgressTrackable[beanCreatingMethods.length];

        for (int i = 0; i < beanCreatingMethods.length; i++) {
            if (beanCreatingMethods[i] == null) continue;

            try {
                progressTrackables[i] = (ProgressTrackable) beanCreatingMethods[i].invoke(this.tasksApplicationConfiguration, new Object[0]);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void runAllTasks() {
        for (ProgressTrackable progressTrackable : progressTrackables) {
            String[] tasks = progressTrackable.getTasks();
            if (tasks == null) return;

            for (String taskName : tasks) {
                System.out.print("running task: " + taskName + " ");
                runTask(progressTrackable, taskName);
            }
        }
    }

    public void runTask(ProgressTrackable progressTrackable, String taskName) {
        progressTrackable.initProgressTracking();
        progressTrackable.preExecute(taskName);
        progressTrackable.executeTask(taskName);
        progressTrackable.postExecute(taskName);
    }
}
