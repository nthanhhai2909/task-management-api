package io.github.nthanhhai2909.taskmanagement.internal.application.task.command;

import io.github.nthanhhai2909.taskmanagement.internal.application.db.GeneratedId;
import io.github.nthanhhai2909.taskmanagement.internal.application.db.IDGenerator;
import io.github.nthanhhai2909.taskmanagement.internal.application.db.TransactionManager;
import io.github.nthanhhai2909.taskmanagement.internal.application.exception.DomainRuleViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.nthanhhai2909.taskmanagement.internal.domain.task.Task;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskID;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class CreateTaskHandlerTest {

    private CreateTaskHandler handler;

    @BeforeEach
    void setUp() {
        IDGenerator idGenerator = () -> new GeneratedId(1L, UUID.randomUUID().toString());

        TransactionManager tx = new TransactionManager() {
            @Override
            public <T> T required(Supplier<T> action) {
                return action.get();
            }
        };

        TaskRepository repo = new TaskRepository() {
            @Override
            public void save(List<Task> tasks) {}

            @Override
            public Optional<Task> findById(TaskID id) {
                return Optional.empty();
            }
        };

        handler = new CreateTaskHandler(repo, tx, idGenerator);
    }

    @Test
    void execute_nullCommand_throwsDomainRuleViolation() {
        assertThrows(DomainRuleViolationException.class, () -> handler.execute(null));
    }

    @Test
    void execute_missingTitle_throwsDomainRuleViolation() {
        CreateTaskHandler.Command command = CreateTaskHandler.Command.builder()
                .title("")
                .description("desc")
                .createdBy("user1")
                .assignee("user2")
                .priority("HIGH")
                .status("TODO")
                .dueDate(LocalDateTime.now().plusDays(1))
                .build();

        DomainRuleViolationException ex = assertThrows(DomainRuleViolationException.class,
                () -> handler.execute(command));
        assertNotNull(ex.code());
    }

    @Test
    void execute_invalidPriority_throwsDomainRuleViolation() {
        CreateTaskHandler.Command command = CreateTaskHandler.Command.builder()
                .title("Test Task")
                .description("desc")
                .createdBy("user1")
                .assignee("user2")
                .priority("INVALID")
                .status("TODO")
                .dueDate(LocalDateTime.now().plusDays(1))
                .build();

        DomainRuleViolationException ex = assertThrows(DomainRuleViolationException.class,
                () -> handler.execute(command));
        assertNotNull(ex.code());
    }

    @Test
    void execute_pastDueDate_throwsDomainRuleViolation() {
        CreateTaskHandler.Command command = CreateTaskHandler.Command.builder()
                .title("Test Task")
                .description("desc")
                .createdBy("user1")
                .assignee("user2")
                .priority("HIGH")
                .status("TODO")
                .dueDate(LocalDateTime.now().minusDays(1))
                .build();

        assertThrows(DomainRuleViolationException.class, () -> handler.execute(command));
    }

    @Test
    void execute_validCommand_returnsResult() {
        CreateTaskHandler.Command command = CreateTaskHandler.Command.builder()
                .title("Test Task")
                .description("A valid description")
                .createdBy("user1")
                .assignee("user2")
                .priority("HIGH")
                .status("TODO")
                .dueDate(LocalDateTime.now().plusDays(1))
                .build();

        CreateTaskHandler.Result result = assertDoesNotThrow(() -> handler.execute(command));
        assertEquals("Test Task", result.getTitle().stringValue());
        assertEquals("A valid description", result.getDescription().stringValue());
    }
}
