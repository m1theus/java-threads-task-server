package io.github.m1theus.server;

import java.util.concurrent.BlockingQueue;

public class TaskConsume implements Runnable {

    private final BlockingQueue<String> commandQueue;

    public TaskConsume(final BlockingQueue<String> commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public void run() {
        try {
            String command;
            while ((command = commandQueue.take()) != null) {

                System.out.println("consuming command " + command);
                Thread.sleep(5000);
            }
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
