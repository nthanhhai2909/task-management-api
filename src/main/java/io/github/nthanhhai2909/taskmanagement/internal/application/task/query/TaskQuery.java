package io.github.nthanhhai2909.taskmanagement.internal.application.task.query;

import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskPriority;

import java.util.Optional;

public final class TaskQuery {
    private final String userId;
    private final String title;
    private final String description;
    private final String assignee;
    private final TaskPriority priority;

    public TaskQuery(String userId, String title, String description, String assignee, TaskPriority priority) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.assignee = assignee;
        this.priority = priority;
    }

    public Optional<String> getUserId() {
        return Optional.ofNullable(userId);
    }

    public Optional<String> getTitle() {
        return Optional.ofNullable(title);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<String> getAssignee() {
        return Optional.ofNullable(assignee);
    }

    public Optional<TaskPriority> getPriority() {
        return Optional.ofNullable(priority);
    }

    public static TaskQuery of(String userId, String title, String description, String assignee, TaskPriority priority) {
        return new TaskQuery(userId, title, description, assignee, priority == null ? null : priority);
    }
}

