package com.satsumaimo;

import com.satsumaimo.config.TasksApplicationConfiguration;
import com.satsumaimo.context.TasksApplicationContext;

public class TasksApplication {

    public static void main(String[] args) {
        TasksApplication.run();
    }

    private static void run() {
        TasksApplicationContext context = new TasksApplicationContext(new TasksApplicationConfiguration());
        context.runAllTasks();
    }
}
