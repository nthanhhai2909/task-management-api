package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;
import io.github.nthanhhai2909.taskmanagement.internal.domain.ID;
import io.github.nthanhhai2909.taskmanagement.internal.domain.ValueObject;

public class TaskID extends ID implements ValueObject {
    TaskID(Long id, String sid) {
        super(id, sid);
    }

    public static TaskID of(String sid) {
        return new TaskID(null, sid);
    }

    public static TaskID of(long id, String sid) {
        return new TaskID(id, sid);
    }

    @Override
    public void validate() throws DomainException {
        if (this.isEmpty()) {
            throw new DomainException(Error.TASK_ID_REQUIRED.description());
        }
    }
}
