package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;
import io.github.nthanhhai2909.taskmanagement.internal.domain.SID;
import io.github.nthanhhai2909.taskmanagement.internal.domain.ValueObject;

public class TaskID extends SID implements ValueObject {
    TaskID(String id) {
        super(id);
    }

    public static TaskID of(String id) {
        return new TaskID(id);
    }

    @Override
    public void validate() throws DomainException {
        if (this.isEmpty()) {
            throw new DomainException(
                    Error.TASK_ID_REQUIRED.code(),
                    Error.TASK_ID_REQUIRED.description()
            );
        }
    }
}
