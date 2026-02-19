package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;
import io.github.nthanhhai2909.taskmanagement.internal.domain.ValueObject;
import org.apache.commons.lang3.StringUtils;

public class TaskCreatedBy implements ValueObject {
    private final String userId;

    public static TaskCreatedBy of(String userId) {
        return new TaskCreatedBy(userId);
    }

    TaskCreatedBy(String userId) {
        this.userId = userId;
    }

    public String stringValue() {
        return userId;
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(this.userId);
    }

    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    @Override
    public void validate() throws DomainException {
        if (this.isEmpty()) {
            throw new DomainException(Error.TASK_CREATED_BY_REQUIRED.code(), Error.TASK_CREATED_BY_REQUIRED.description());
        }
    }
}
