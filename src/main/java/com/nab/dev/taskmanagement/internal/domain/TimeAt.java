package com.nab.dev.taskmanagement.internal.domain;

import java.time.LocalDateTime;

public class TimeAt implements ValueObject {
    private final LocalDateTime time;

    protected TimeAt(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime time() {
        return time;
    }

    public boolean isEmpty() {
        return time == null;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public long toEpochMilli() {
        return time.toInstant(java.time.ZoneOffset.UTC).toEpochMilli();
    }

    public long toEpochSecond() {
        return time.toEpochSecond(java.time.ZoneOffset.UTC);
    }

    public boolean isEqual(TimeAt other) {
        return time.isEqual(other.time);
    }

    public boolean isNotEqual(TimeAt other) {
        return !time.isEqual(other.time);
    }

    public boolean isBefore(TimeAt other) {
        return time.isBefore(other.time);
    }

    public boolean isEqualOrBefore(TimeAt other) {
        return time.isEqual(other.time) || time.isBefore(other.time);
    }

    public boolean isAfter(TimeAt other) {
        return time.isAfter(other.time);
    }

    public boolean isEqualOrAfter(TimeAt other) {
        return time.isEqual(other.time) || time.isAfter(other.time);
    }

    @Override
    public void validate() throws DomainException {
        // default validation
    }

}
