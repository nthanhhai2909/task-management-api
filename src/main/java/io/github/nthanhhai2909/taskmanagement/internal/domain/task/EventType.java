package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

public enum EventType {
    TASK_CREATED("task-created", "Task created");

    EventType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    private final String type;
    private final String description;

    public String type() {
        return type;
    }

    public String description() {
        return description;
    }
}

