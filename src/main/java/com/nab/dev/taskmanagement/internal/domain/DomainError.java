package com.nab.dev.taskmanagement.internal.domain;

public interface DomainError {
    String code(); // stable identifier, e.g. "task_not_found"
    String description(); // human-readable description
}
