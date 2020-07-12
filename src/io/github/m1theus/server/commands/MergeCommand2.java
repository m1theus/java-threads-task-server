package io.github.m1theus.server.commands;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MergeCommand2 implements ServerCommand<Void> {

    private PrintStream clientOutput;
    private final Future<String> randomIntCommand2;
    private final Future<String> randomLongCommand2;

    public MergeCommand2(final Socket socket, final Future<String> randomIntCommand2,
                         final Future<String> randomLongCommand2) throws IOException {
        this.clientOutput = new PrintStream(socket.getOutputStream());
        this.randomIntCommand2 = randomIntCommand2;
        this.randomLongCommand2 = randomLongCommand2;
    }

    @Override
    public Void call() {
        try {
            final var randomInt = randomIntCommand2.get(3, SECONDS);
            final var randomLong = randomLongCommand2.get(3, SECONDS);
            this.clientOutput.println("[SERVER] command c2 output: " + randomInt + randomLong);
        } catch (final InterruptedException | ExecutionException | TimeoutException exception) {
            clientOutput.println("[SERVER] Timeout: executing command c2");
            this.randomIntCommand2.cancel(true);
            this.randomLongCommand2.cancel(true);
        }
        return null;
    }
}
