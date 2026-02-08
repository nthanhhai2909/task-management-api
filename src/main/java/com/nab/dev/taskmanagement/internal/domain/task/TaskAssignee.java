package com.nab.dev.taskmanagement.internal.domain.task;

import com.nab.dev.taskmanagement.internal.domain.DomainException;
import com.nab.dev.taskmanagement.internal.domain.ValueObject;

public class TaskAssignee implements ValueObject {
    private String assignee;

    private TaskAssignee(String assignee) {
        this.assignee = assignee;
    }

    @Override
    public void validate() throws DomainException {
        // TODO: Implement validation
    }
}
