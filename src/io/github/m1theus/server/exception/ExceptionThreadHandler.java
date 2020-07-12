package io.github.m1theus.server.exception;

import java.util.logging.Logger;

import static java.lang.String.format;
import static java.lang.Thread.UncaughtExceptionHandler;
import static java.util.logging.Level.ALL;

public class ExceptionThreadHandler implements UncaughtExceptionHandler {

    private static final Logger log = Logger.getLogger(ExceptionThreadHandler.class.getName());

    public static ExceptionThreadHandler getInstance() {
        return new ExceptionThreadHandler();
    }

    @Override
    public void uncaughtException(final Thread t, final Throwable e) {
        log.setLevel(ALL);
        log.warning(format("Exception occurred in thread=%s, e=%s", t.getName(), e.getLocalizedMessage()));
    }

}
