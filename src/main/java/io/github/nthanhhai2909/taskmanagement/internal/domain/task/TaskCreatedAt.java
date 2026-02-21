package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.TimeAt;
import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;

import java.time.Instant;

public class TaskCreatedAt extends TimeAt {
    TaskCreatedAt(Instant time) {
        super(time);
    }

    public static TaskCreatedAt of(Instant time) {
        return new TaskCreatedAt(time);
    }

    public static TaskCreatedAt ofEpochMilli(long epochMilli) {
        Instant time = Instant.ofEpochMilli(epochMilli);
        return new TaskCreatedAt(time);
    }

    public static TaskCreatedAt ofEpochSecond(long epochSecond) {
        Instant time = Instant.ofEpochSecond(epochSecond);
        return new TaskCreatedAt(time);
    }

    public static TaskCreatedAt now() {
        return new TaskCreatedAt(Instant.now());
    }

    @Override
    public void validate() throws DomainException {
        if (this.isEmpty()) {
            throw new DomainException(Error.TASK_CREATED_AT_REQUIRED.description());
        }

        if (this.isAfterNow()) {
            throw new DomainException(Error.TASK_CREATED_AT_MUST_NOT_BE_IN_FUTURE.description());
        }
    }
}
