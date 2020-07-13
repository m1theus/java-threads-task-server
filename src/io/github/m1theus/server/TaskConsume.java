package io.github.m1theus.server;

import io.github.m1theus.server.commands.ServerCommand;

import java.util.concurrent.BlockingQueue;

public class TaskConsume implements Runnable {

    private final BlockingQueue<ServerCommand> commandQueue;

    public TaskConsume(final BlockingQueue<ServerCommand> commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public void run() {
        try {
            ServerCommand command;
            while ((command = commandQueue.take()) != null) {

                System.out.println("consuming command " + command);
                Thread.sleep(5000);
            }
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
