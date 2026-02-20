package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;
import io.github.nthanhhai2909.taskmanagement.internal.domain.ValueObject;
import org.apache.commons.lang3.StringUtils;

public class TaskStatus implements ValueObject {
    public enum Status {
        TODO, IN_PROGRESS, IN_REVIEW, BLOCKED, DONE, CANCELLED, UNKNOWN
    }

    private final Status value;

    TaskStatus(Status value) {
        this.value = value;
    }

    public static TaskStatus fromString(String value) {
        if (StringUtils.isBlank(value)) {
            return TaskStatus.of(Status.UNKNOWN);
        }

        try {
            return TaskStatus.of(Status.valueOf(value.trim().toUpperCase(java.util.Locale.ROOT)));
        } catch (IllegalArgumentException ex) {
            // do nothing
        }
        return TaskStatus.of(Status.UNKNOWN);
    }

    public static TaskStatus of(Status value) {
        return new TaskStatus(value);
    }

    public static TaskStatus todo() {
        return new TaskStatus(Status.TODO);
    }

    public static TaskStatus inProgress() {
        return new TaskStatus(Status.IN_PROGRESS);
    }

    public static TaskStatus inReview() {
        return new TaskStatus(Status.IN_REVIEW);
    }

    public static TaskStatus blocked() {
        return new TaskStatus(Status.BLOCKED);
    }

    public static TaskStatus done() {
        return new TaskStatus(Status.DONE);
    }

    public static TaskStatus cancelled() {
        return new TaskStatus(Status.CANCELLED);
    }

    public String stringValue() {
        return this.value.toString().toLowerCase(java.util.Locale.ROOT);
    }

    public boolean isTodo() {
        return Status.TODO.equals(this.value);
    }

    public boolean isInProgress() {
        return Status.IN_PROGRESS.equals(this.value);
    }

    public boolean isInReview() {
        return Status.IN_REVIEW.equals(this.value);
    }

    public boolean isBlocked() {
        return Status.BLOCKED.equals(this.value);
    }

    public boolean isDone() {
        return Status.DONE.equals(this.value);
    }

    public boolean isCancelled() {
        return Status.CANCELLED.equals(this.value);
    }

    public boolean isUnknown() {
        return Status.UNKNOWN.equals(this.value);
    }

    public boolean isEmpty() {
        return this.value == null;
    }

    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    @Override
    public void validate() throws DomainException {
        if (this.isUnknown()) {
            throw new DomainException(
                    Error.TASK_STATUS_REQUIRED.code(),
                    Error.TASK_STATUS_REQUIRED.description()
            );
        }
    }
}