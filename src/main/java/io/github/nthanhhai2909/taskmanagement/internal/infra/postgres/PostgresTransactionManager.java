package io.github.nthanhhai2909.taskmanagement.internal.infra.postgres;

import io.github.nthanhhai2909.taskmanagement.internal.application.db.TransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.function.Supplier;

public class PostgresTransactionManager implements TransactionManager {

    private final PlatformTransactionManager platformTransactionManager;

    public PostgresTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public <T> T required(Supplier<T> action) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = platformTransactionManager.getTransaction(definition);
        try {
            T result = action.get();
            platformTransactionManager.commit(status);
            return result;
        } catch (RuntimeException | Error e) {
            platformTransactionManager.rollback(status);
            throw e;
        } catch (Throwable t) {
            platformTransactionManager.rollback(status);
            throw new RuntimeException(t);
        }
    }
}