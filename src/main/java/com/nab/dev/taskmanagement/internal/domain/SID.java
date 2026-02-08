package com.nab.dev.taskmanagement.internal.domain;

import org.apache.commons.lang3.StringUtils;

public class SID implements ValueObject {
    private final String id;

    protected SID(String id) {
        this.id = id;
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(this.id);
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isEqual(SID other) {
        return this.id.equals(other.id);
    }

    public boolean isNotEqual(SID other) {
        return !isEqual(other);
    }

    public String id() {
        return id;
    }

    @Override
    public void validate() throws DomainException {
        // default validation
    }
}
