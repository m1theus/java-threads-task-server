package io.github.m1theus.server.commands;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;

public class RandomIntCommand2 implements ServerCommand<String> {

    private final Socket socket;

    public RandomIntCommand2(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public String call() {
        try {
            final var clientOutput = new PrintStream(socket.getOutputStream());
            System.out.println("processing command c2 - random int");
            clientOutput.println("[SERVER] processing command c2 - random int");
            Thread.sleep(3000);

            int randomNumber = new Random().nextInt(100) + 1;
            return Integer.toString(randomNumber);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
