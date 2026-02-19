package io.github.nthanhhai2909.taskmanagement.internal.application.db;

public interface TransactionManager {
    void required(Runnable action);
}