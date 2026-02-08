package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;
import io.github.nthanhhai2909.taskmanagement.internal.domain.TimeAt;
import io.github.nthanhhai2909.taskmanagement.internal.domain.ValueObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TaskDueDate extends TimeAt implements ValueObject {
    TaskDueDate(LocalDateTime time) {
        super(time);
    }

    public static TaskDueDate of(LocalDateTime time) {
        return new TaskDueDate(time);
    }

    public static TaskDueDate ofEpochMilli(long epochMilli) {
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneOffset.UTC);
        return new TaskDueDate(time);
    }

    public static TaskDueDate ofEpochSecond(long epochSecond) {
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSecond), ZoneOffset.UTC);
        return new TaskDueDate(time);
    }

    public static TaskDueDate now() {
        return new TaskDueDate(LocalDateTime.now());
    }

    @Override
    public void validate() throws DomainException {
        if (this.isEmpty()) {
            return;
        }
        if (this.isBeforeOrEqualNow()) {
            throw new DomainException(Error.TASK_DUE_DATE_MUST_BE_IN_FUTURE.code(), Error.TASK_DUE_DATE_MUST_BE_IN_FUTURE.description());
        }
    }
}
