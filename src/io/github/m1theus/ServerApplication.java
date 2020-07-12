package io.github.m1theus;

import io.github.m1theus.server.TaskServer;

public class ServerApplication {

    public static void main(String[] args) {
        try {
            final var taskServer = new TaskServer();
            taskServer.run();
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
