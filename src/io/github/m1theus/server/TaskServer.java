package io.github.m1theus.server;

import io.github.m1theus.server.commands.ServerCommand;
import io.github.m1theus.server.exception.ExceptionThreadHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class TaskServer {
    private static final Logger log = Logger.getLogger(TaskServer.class.getName());

    public static final int PORT = 1337;

    private final ServerSocket server;
    private final BlockingQueue<ServerCommand> commandQueue;
    private final ExecutorService threadPool;
    private AtomicBoolean isRunning;


    public TaskServer() throws IOException {
        this.server = new ServerSocket(PORT);
        this.isRunning = new AtomicBoolean(true);
        this.commandQueue = new ArrayBlockingQueue<>(2);
        this.threadPool = Executors.newCachedThreadPool(runnable -> {
            final var threadName = "TaskServerThread - " + System.currentTimeMillis();
            final var thread = new Thread(runnable, threadName);
            thread.setUncaughtExceptionHandler(ExceptionThreadHandler.getInstance());
            return thread;
        });
        initConsumers();
    }

    private void initConsumers() {
        for (int i = 0; i < 2; i++) {
            final var taskConsume = new TaskConsume(this.commandQueue);
            this.threadPool.execute(taskConsume);
        }
    }

    public void run() throws IOException {
        log.info("Starting server on port " + PORT);
        while (this.isRunning.get()) {
            try {
                var socket = this.server.accept();
                log.info("Accepting client on port" + socket.getPort());

                final var allocateTask = new AllocateTask(commandQueue, threadPool, socket, this);
                this.threadPool.execute(allocateTask);

            } catch (final SocketException exception) {
                log.warning("closing server");
            }
        }
    }

    public void stop() throws IOException {
        this.server.close();
        this.threadPool.shutdown();
        this.isRunning.set(false);
    }

}
