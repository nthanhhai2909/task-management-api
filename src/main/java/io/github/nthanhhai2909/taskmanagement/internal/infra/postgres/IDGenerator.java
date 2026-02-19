package io.github.nthanhhai2909.taskmanagement.internal.infra.postgres;

import org.springframework.stereotype.Component;

@Component
public class IDGenerator implements io.github.nthanhhai2909.taskmanagement.internal.application.db.IDGenerator {
    @Override
    public String get() {
        // TODO: Implement id generation logic, for example using UUIDs or a sequence from the database
        // This is a placeholder implementation and should be replaced with actual id generation logic
        return java.util.UUID.randomUUID().toString();
    }
}
