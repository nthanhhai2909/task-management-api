package io.github.nthanhhai2909.taskmanagement.internal.domain;

import java.time.LocalDateTime;

public class TimeAt implements ValueObject {
    private final LocalDateTime time;

    public TimeAt(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime time() {
        return this.time;
    }

    public boolean isEmpty() {
        return this.time == null;
    }

    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    public long toEpochMilli() {
        return this.time.toInstant(java.time.ZoneOffset.UTC).toEpochMilli();
    }

    public long toEpochSecond() {
        return this.time.toEpochSecond(java.time.ZoneOffset.UTC);
    }

    public boolean isEqual(TimeAt other) {
        return this.time.isEqual(other.time);
    }

    public boolean isNotEqual(TimeAt other) {
        return !this.time.isEqual(other.time);
    }

    public boolean isBefore(TimeAt other) {
        return this.time.isBefore(other.time);
    }

    public boolean isEqualOrBefore(TimeAt other) {
        return time.isEqual(other.time) || time.isBefore(other.time);
    }

    public boolean isBeforeNow() {
        return this.time.isBefore(LocalDateTime.now());
    }

    public boolean isBeforeOrEqualNow() {
        LocalDateTime now = LocalDateTime.now();
        return this.time.isEqual(now) || this.time.isBefore(now);
    }

    public boolean isAfter(TimeAt other) {
        return this.time.isAfter(other.time);
    }

    public boolean isEqualOrAfter(TimeAt other) {
        return this.time.isEqual(other.time) || this.time.isAfter(other.time);
    }

    public boolean isAfterNow() {
        return this.time.isAfter(LocalDateTime.now());
    }

    public boolean isAfterOrEqualNow() {
        LocalDateTime now = LocalDateTime.now();
        return this.time.isEqual(now) || this.time.isAfter(now);
    }


    @Override
    public void validate() throws DomainException {
        // default validation
    }

}
