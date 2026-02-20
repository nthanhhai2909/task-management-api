package io.github.nthanhhai2909.taskmanagement.internal.domain;

public class ID {
    protected final Long id;
    protected final String sid;

    protected ID(Long id, String sid) {
        this.id = id;
        this.sid = sid;
    }

    public static ID of(long id, String sid) {
        return new ID(id, sid);
    }

    public Long id() {
        return id;
    }

    public String sid() {
        return sid;
    }

    public String stringValue() {
        return sid;
    }

    public boolean isEmpty() {
        return id == null;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

}
