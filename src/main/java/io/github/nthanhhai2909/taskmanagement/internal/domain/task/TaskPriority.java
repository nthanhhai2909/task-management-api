package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;
import io.github.nthanhhai2909.taskmanagement.internal.domain.ValueObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public final class TaskPriority implements ValueObject {
    public enum Priority {
        LOW, MEDIUM, HIGH, CRITICAL, UNKNOWN
    }

    private final Priority value;

    TaskPriority(Priority value) {
        this.value = value;
    }

    public static TaskPriority fromString(String value) {
        if (StringUtils.isBlank(value)) {
            return TaskPriority.of(Priority.UNKNOWN);
        }

        try {
            return TaskPriority.of(Priority.valueOf(value.trim().toUpperCase(Locale.ROOT)));
        } catch (IllegalArgumentException ex) {
            // do nothing
        }
        return TaskPriority.of(Priority.UNKNOWN);
    }

    public static TaskPriority of(Priority value) {
        return new TaskPriority(value);
    }

    public static TaskPriority low() {
        return TaskPriority.of(Priority.LOW);
    }

    public static TaskPriority medium() {
        return TaskPriority.of(Priority.MEDIUM);
    }

    public static TaskPriority high() {
        return TaskPriority.of(Priority.HIGH);
    }

    public static TaskPriority critical() {
        return TaskPriority.of(Priority.CRITICAL);
    }

    public String stringValue() {
        return this.value.toString().toLowerCase(Locale.ROOT);
    }

    public boolean isEmpty() {
        return this.value == null;
    }

    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    public boolean isLow() {
        return Priority.LOW.equals(this.value);
    }

    public boolean isMedium() {
        return Priority.MEDIUM.equals(this.value);
    }

    public boolean isHigh() {
        return Priority.HIGH.equals(this.value);
    }

    public boolean isCritical() {
        return Priority.CRITICAL.equals(this.value);
    }

    public boolean isUnknown() {
        return Priority.UNKNOWN.equals(this.value);
    }

    @Override
    public void validate() throws DomainException {
        if (this.isUnknown()) {
            throw new DomainException(Error.TASK_PRIORITY_REQUIRED.description());
        }
    }
}