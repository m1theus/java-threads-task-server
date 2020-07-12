package io.github.m1theus.server.commands;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;

public class RandomLongCommand2 implements ServerCommand<String> {

    private final Socket socket;

    public RandomLongCommand2(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public String call() {
        try {
            final var clientOutput = new PrintStream(socket.getOutputStream());
            System.out.println("processing command c2 - random long");
            clientOutput.println("[SERVER] processing command c2 - random long");
            Thread.sleep(3000);

            long randomNumber = new Random().nextLong() + 1L;
            return Long.toString(randomNumber);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
