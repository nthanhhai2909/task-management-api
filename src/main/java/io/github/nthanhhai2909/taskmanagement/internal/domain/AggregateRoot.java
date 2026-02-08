package io.github.nthanhhai2909.taskmanagement.internal.domain;

public interface AggregateRoot<ID> {
    ID id(); // id of aggregate
    AggregateType aggregateType(); // type of aggregate
    void validate() throws DomainException; // validate aggregate
}
