package com.nab.dev.taskmanagement.internal.domain.task;

import com.nab.dev.taskmanagement.internal.domain.DomainException;
import com.nab.dev.taskmanagement.internal.domain.SID;
import com.nab.dev.taskmanagement.internal.domain.ValueObject;

public class TaskID extends SID implements ValueObject {
    private TaskID(String id) {
        super(id);
    }
    @Override
    public void validate() throws DomainException {
        // TODO: Implement validation
    }
}
