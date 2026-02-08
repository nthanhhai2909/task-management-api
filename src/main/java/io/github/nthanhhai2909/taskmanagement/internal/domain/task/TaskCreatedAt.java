package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.TimeAt;
import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TaskCreatedAt extends TimeAt {
    TaskCreatedAt(LocalDateTime time) {
        super(time);
    }

    public static TaskCreatedAt of(LocalDateTime time) {
        return new TaskCreatedAt(time);
    }

    public static TaskCreatedAt ofEpochMilli(long epochMilli) {
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneOffset.UTC);
        return new TaskCreatedAt(time);
    }

    public static TaskCreatedAt ofEpochSecond(long epochSecond) {
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSecond), ZoneOffset.UTC);
        return new TaskCreatedAt(time);
    }

    public static TaskCreatedAt now() {
        return new TaskCreatedAt(LocalDateTime.now());
    }

    @Override
    public void validate() throws DomainException {
        if (this.isEmpty()) {
            throw new DomainException(Error.TASK_CREATED_AT_REQUIRED.code(), Error.TASK_CREATED_AT_REQUIRED.description());
        }

        if (this.isAfterNow()) {
            throw new DomainException(Error.TASK_CREATED_AT_MUST_NOT_BE_IN_FUTURE.code(), Error.TASK_CREATED_AT_MUST_NOT_BE_IN_FUTURE.description());
        }
    }
}
