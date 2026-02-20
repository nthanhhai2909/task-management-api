package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskPriorityTest {

    @Test
    void fromString_validValues_parsed() {
        assertEquals("low", TaskPriority.fromString("LOW").stringValue());
        assertEquals("medium", TaskPriority.fromString("medium").stringValue());
        assertEquals("high", TaskPriority.fromString("HIGH").stringValue());
        assertEquals("critical", TaskPriority.fromString("Critical").stringValue());
    }

    @Test
    void fromString_invalidValue_returnsUnknown() {
        assertTrue(TaskPriority.fromString("INVALID").isUnknown());
        assertTrue(TaskPriority.fromString(null).isUnknown());
        assertTrue(TaskPriority.fromString("").isUnknown());
    }

    @Test
    void validate_unknownPriority_throwsDomainException() {
        TaskPriority priority = TaskPriority.fromString("bad");
        DomainException ex = assertThrows(DomainException.class, priority::validate);
        assertEquals(Error.TASK_PRIORITY_REQUIRED.code(), ex.code());
    }

    @Test
    void validate_validPriority_doesNotThrow() {
        assertDoesNotThrow(() -> TaskPriority.fromString("HIGH").validate());
    }
}
