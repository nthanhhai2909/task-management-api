package com.nab.dev.taskmanagement.internal.domain;

import java.time.LocalDateTime;

public interface Event<ID> {
    ID id(); // id of event
    LocalDateTime occurredOn(); // The time of event occurrence
    String aggregateType(); // The type of aggregate that generated the event
    String aggregateId(); // The id of the aggregate that generated the event
    String version(); // The version of the aggregate that generated the event
    String payload(); // The event payload
}
