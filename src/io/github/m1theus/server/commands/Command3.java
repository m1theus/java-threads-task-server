package io.github.m1theus.server.commands;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;

public class Command3 implements ServerCommand<String> {

    private final Socket socket;

    public Command3(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public String call() {
        try {
            final var clientOutput = new PrintStream(socket.getOutputStream());
            System.out.println("executing command c3");
            clientOutput.println("[SERVER] executing command c3");
            Thread.sleep(3000);
            clientOutput.println("[SERVER] Command c3 successfully finished!");

            int randomNumber = new Random().nextInt(100) + 1;
            return Integer.toString(randomNumber);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
