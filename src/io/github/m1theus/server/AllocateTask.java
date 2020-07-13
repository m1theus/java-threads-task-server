package io.github.m1theus.server;

import io.github.m1theus.server.commands.Command;
import io.github.m1theus.server.commands.Command3;
import io.github.m1theus.server.commands.MergeCommand2;
import io.github.m1theus.server.commands.RandomIntCommand2;
import io.github.m1theus.server.commands.RandomLongCommand2;
import io.github.m1theus.server.commands.ServerCommand;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

public class AllocateTask implements Runnable {
    private static final Logger log = Logger.getLogger(AllocateTask.class.getName());
    private static final String EXIT = "exit";

    private final BlockingQueue<ServerCommand> commandQueue;
    private final ExecutorService threadPool;
    private final Socket socket;
    private final TaskServer server;

    public AllocateTask(final BlockingQueue<ServerCommand> commandQueue, final ExecutorService threadPool,
                        final Socket socket, final TaskServer taskServer) {
        this.commandQueue = commandQueue;
        this.threadPool = threadPool;
        this.socket = socket;
        this.server = taskServer;
    }

    @Override
    public void run() {

        try {
            log.info("Allocating task to client " + socket);

            final var clientInput = new Scanner(socket.getInputStream());
            final var clientOutput = new PrintStream(socket.getOutputStream());

            while (clientInput.hasNextLine()) {
                final var command = clientInput.nextLine();

                switch (command) {
                    case "c1": {
                        final var serverCommand = new Command(this.socket);
                        this.threadPool.submit(serverCommand);
                        break;
                    }
                    case "c2": {
                        final var randomIntCommand2 = new RandomIntCommand2(this.socket);
                        final var randomLongCommand2 = new RandomLongCommand2(this.socket);
                        final var futureRandomInt = this.threadPool.submit(randomIntCommand2);
                        final var futureRandomLong = this.threadPool.submit(randomLongCommand2);
                        this.threadPool.submit(new MergeCommand2(this.socket, futureRandomInt, futureRandomLong));
                        break;
                    }
                    case "c3": {
                        this.commandQueue.put(new Command3(this.socket));
                        clientOutput.println("[SERVER] command c3 added in the queue");
                        break;
                    }
                    case EXIT: {
                        clientOutput.println("[SERVER] stopping the server...");
                        this.server.stop();
                        break;
                    }
                    default: {
                        clientOutput.println("[SERVER] command does'nt exist!");
                    }
                }
            }

            clientOutput.close();
            clientInput.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
