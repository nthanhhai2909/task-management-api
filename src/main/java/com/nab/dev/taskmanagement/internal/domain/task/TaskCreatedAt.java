package com.nab.dev.taskmanagement.internal.domain.task;

import com.nab.dev.taskmanagement.internal.domain.TimeAt;
import com.nab.dev.taskmanagement.internal.domain.DomainException;

import java.time.LocalDateTime;

public class TaskCreatedAt extends TimeAt {
    private TaskCreatedAt(LocalDateTime time) {
        super(time);
    }

    @Override
    public void validate() throws DomainException {
        // TODO: Implement validation
    }
}
