package io.github.m1theus.server.commands;

import java.util.concurrent.Callable;

public interface ServerCommand<T> extends Callable<T> {
}
