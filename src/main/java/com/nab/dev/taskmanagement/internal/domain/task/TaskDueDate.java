package com.nab.dev.taskmanagement.internal.domain.task;

import com.nab.dev.taskmanagement.internal.domain.DomainException;
import com.nab.dev.taskmanagement.internal.domain.TimeAt;
import com.nab.dev.taskmanagement.internal.domain.ValueObject;

import java.time.LocalDateTime;

public class TaskDueDate extends TimeAt implements ValueObject {
    private TaskDueDate(LocalDateTime time) {
        super(time);
    }

    @Override
    public void validate() throws DomainException {
        // TODO: Implement validation
    }
}
