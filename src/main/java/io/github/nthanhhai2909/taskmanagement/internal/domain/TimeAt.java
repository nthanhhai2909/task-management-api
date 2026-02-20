package io.github.nthanhhai2909.taskmanagement.internal.domain;

import java.time.Instant;

public class TimeAt implements ValueObject {
    private final Instant time;

    public TimeAt(Instant time) {
        this.time = time;
    }

    public Instant toInstant() {
        return this.time;
    }

    public boolean isEmpty() {
        return this.time == null;
    }

    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    public long toEpochMilli() {
        return this.time.toEpochMilli();
    }

    public long toEpochSecond() {
        return this.time.getEpochSecond();
    }

    public boolean isEqual(TimeAt other) {
        return this.time.equals(other.time);
    }

    public boolean isNotEqual(TimeAt other) {
        return !this.time.equals(other.time);
    }

    public boolean isBefore(TimeAt other) {
        return this.time.isBefore(other.time);
    }

    public boolean isEqualOrBefore(TimeAt other) {
        return time.equals(other.time) || time.isBefore(other.time);
    }

    public boolean isBeforeNow() {
        return this.time.isBefore(Instant.now());
    }

    public boolean isBeforeOrEqualNow() {
        Instant now = Instant.now();
        return this.time.equals(now) || this.time.isBefore(now);
    }

    public boolean isAfter(TimeAt other) {
        return this.time.isAfter(other.time);
    }

    public boolean isEqualOrAfter(TimeAt other) {
        return this.time.equals(other.time) || this.time.isAfter(other.time);
    }

    public boolean isAfterNow() {
        return this.time.isAfter(Instant.now());
    }

    public boolean isAfterOrEqualNow() {
        Instant now = Instant.now();
        return this.time.equals(now) || this.time.isAfter(now);
    }


    @Override
    public void validate() throws DomainException {
        // default validation
    }

}
