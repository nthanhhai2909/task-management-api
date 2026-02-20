package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTitleTest {

    @Test
    void validate_blankTitle_throwsDomainException() {
        TaskTitle title = TaskTitle.of("   ");
        DomainException ex = assertThrows(DomainException.class, title::validate);
        assertEquals(Error.TASK_TITLE_REQUIRED.code(), ex.code());
    }

    @Test
    void validate_nullTitle_throwsDomainException() {
        TaskTitle title = TaskTitle.of(null);
        DomainException ex = assertThrows(DomainException.class, title::validate);
        assertEquals(Error.TASK_TITLE_REQUIRED.code(), ex.code());
    }

    @Test
    void validate_titleTooLong_throwsDomainException() {
        String longTitle = "a".repeat(501);
        TaskTitle title = TaskTitle.of(longTitle);
        DomainException ex = assertThrows(DomainException.class, title::validate);
        assertEquals(Error.TASK_TITLE_TOO_LONG.code(), ex.code());
    }

    @Test
    void validate_validTitle_doesNotThrow() {
        TaskTitle title = TaskTitle.of("Valid Task Title");
        assertDoesNotThrow(title::validate);
    }
}
