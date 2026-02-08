package io.github.nthanhhai2909.taskmanagement.internal.domain;

import java.time.LocalDateTime;

public interface Event<EventID, AggregateID> {
    EventID id(); // id of event
    String type(); // The type of event
    String description(); // Description of event
    AggregateID aggregateId(); // The id of the aggregate that generated the event
    LocalDateTime occurredOn(); // The time of event occurrence
    AggregateType aggregateType(); // The type of aggregate that generated the event
}
