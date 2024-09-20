package org.example;

import java.util.concurrent.*;

public class ComplexTaskExecutor {
    private final int numberOfTasks;
    private final CyclicBarrier cyclicBarrier;


    public ComplexTaskExecutor(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;

        this.cyclicBarrier = new CyclicBarrier(numberOfTasks, () -> {
            System.out.println("All tasks completed. Сombining results.");
        });

    }


    public void executeTasks(int numberOfTasks) {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfTasks);
        for (int i = 0; i < numberOfTasks; i++) {
            int taskId = i + 1;
            executorService.submit(() -> {
                try {
                    ComplexTask task = new ComplexTask(taskId);
                    task.execute();
                    System.out.println(Thread.currentThread().getName() + " is waiting at the barrier.");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }

            });
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
