package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;
import io.github.nthanhhai2909.taskmanagement.internal.domain.ValueObject;
import org.apache.commons.lang3.StringUtils;

public class TaskDescription implements ValueObject {
    private static final int MAX_LEN = 10000;
    private final String description;

    TaskDescription(String description) {
        this.description = description;
    }

    public String stringValue() {
        return this.description;
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(this.description);
    }

    public boolean isNotEmpty() {
        return !this.isEmpty();
    }


    @Override
    public void validate() throws DomainException {
        if (this.isEmpty()) {
            throw new DomainException(Error.TASK_DESCRIPTION_REQUIRED.code(), Error.TASK_DESCRIPTION_REQUIRED.description());
        }

        int actualLen = this.description.length();
        if (actualLen > MAX_LEN) {
            throw new DomainException(Error.TASK_DESCRIPTION_TOO_LONG.code(), String.format(Error.TASK_DESCRIPTION_TOO_LONG.description(), MAX_LEN, actualLen));
        }
    }
}
