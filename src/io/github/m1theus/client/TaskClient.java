package io.github.m1theus.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class TaskClient {
    private static final Logger log = Logger.getLogger(TaskClient.class.getName());

    private static final String HOST = "localhost";
    private static final int PORT = 1337;
    private static final String EXIT = "exit";

    public void run() throws IOException, InterruptedException {
        try (var socket = new Socket(HOST, PORT)) {
            log.info("Connection established");
            log.info("Available commands: [c1, c2, c3, exit]");

            final var clientInputThread = new Thread(() -> {

                try {
                    log.info("can send commands!");

                    final var clientInput = new PrintStream(socket.getOutputStream());
                    final var scanner = new Scanner(System.in);

                    while (scanner.hasNextLine()) {
                        final var command = scanner.nextLine();

                        if (EXIT.equals(command.trim())) {
                            clientInput.println(EXIT);
                            break;
                        }
                        clientInput.println(command);
                    }

                    clientInput.close();
                    scanner.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            final var serverOutputThread = new Thread(() -> {
                try {
                    final var serverOutput = new Scanner(socket.getInputStream());

                    while (serverOutput.hasNextLine()) {
                        log.info(serverOutput.nextLine());
                    }

                    serverOutput.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            serverOutputThread.start();
            clientInputThread.start();

            clientInputThread.join();

            log.info("Closing client socket");
        }
    }

}
