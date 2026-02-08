package io.github.nthanhhai2909.taskmanagement.internal.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class SID implements ValueObject {
    private final String id;

    public SID(String id) {
        this.id = id;
    }

    public static SID random() {
        return new SID(UUID.randomUUID().toString());
    }

    public String stringValue() {
        return id;
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

    @Override
    public void validate() throws DomainException {
        // default validation
    }
}
