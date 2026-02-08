package com.nab.dev.taskmanagement.internal.domain.task;

import com.nab.dev.taskmanagement.internal.domain.DomainException;
import com.nab.dev.taskmanagement.internal.domain.ValueObject;

public class TaskCreatedBy implements ValueObject {
    private String createdBy;
    private TaskCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    @Override
    public void validate() throws DomainException {
        // TODO: Implement validation
    }
}
