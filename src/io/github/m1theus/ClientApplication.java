package io.github.m1theus;

import io.github.m1theus.client.TaskClient;

public class ClientApplication {

    public static void main(String[] args) {
        try {
            // Run the ServerApplication first!
            final var taskClient = new TaskClient();
            taskClient.run();
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
