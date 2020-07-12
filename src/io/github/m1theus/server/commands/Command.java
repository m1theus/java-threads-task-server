package io.github.m1theus.server.commands;

import java.io.PrintStream;
import java.net.Socket;

public class Command implements ServerCommand<String> {

    private final Socket socket;

    public Command(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public String call() {
        try {
            final var clientOutput = new PrintStream(socket.getOutputStream());
            System.out.println("executing command c1");
            clientOutput.println("[SERVER] executing command c1");
            Thread.sleep(20000);
            clientOutput.println("[SERVER] Command c1 successfully finished!");
            return "";
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
