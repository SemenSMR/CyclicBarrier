package org.example;

import java.util.concurrent.TimeUnit;

public class ComplexTask  {
    private final int taskId;


    public ComplexTask(int taskId) {
        this.taskId = taskId;
    }

    public void execute() {

        System.out.println("Task " + taskId + " is being executed by " + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
