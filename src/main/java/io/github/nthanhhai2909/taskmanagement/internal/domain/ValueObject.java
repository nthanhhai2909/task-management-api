package io.github.nthanhhai2909.taskmanagement.internal.domain;

public interface ValueObject {
    void validate() throws DomainException;
}
