package com.nab.dev.taskmanagement.internal.domain;

public interface ValueObject {
    void validate() throws DomainException;
}
