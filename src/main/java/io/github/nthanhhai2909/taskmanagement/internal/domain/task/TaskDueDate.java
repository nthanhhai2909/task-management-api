package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;
import io.github.nthanhhai2909.taskmanagement.internal.domain.TimeAt;
import io.github.nthanhhai2909.taskmanagement.internal.domain.ValueObject;

import java.time.Instant;

public class TaskDueDate extends TimeAt implements ValueObject {
    TaskDueDate(Instant time) {
        super(time);
    }

    public static TaskDueDate of(Instant time) {
        return new TaskDueDate(time);
    }

    public static TaskDueDate ofEpochMilli(long epochMilli) {
        Instant time = Instant.ofEpochMilli(epochMilli);
        return new TaskDueDate(time);
    }

    public static TaskDueDate ofEpochSecond(long epochSecond) {
        Instant time = Instant.ofEpochSecond(epochSecond);
        return new TaskDueDate(time);
    }

    public static TaskDueDate now() {
        return new TaskDueDate(Instant.now());
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
