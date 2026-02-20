package io.github.nthanhhai2909.taskmanagement.internal.application.db;

import java.util.function.Supplier;

public interface TransactionManager {
    /**
     * Execute an action in a REQUIRED transaction and return a result.
     */
    <T> T required(Supplier<T> action);

    /**
     * Convenience for actions that don't return a value.
     */
    default void required(Runnable action) {
        required(() -> { action.run(); return null; });
    }
}