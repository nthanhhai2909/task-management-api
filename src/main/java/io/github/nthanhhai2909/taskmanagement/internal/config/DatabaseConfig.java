package io.github.nthanhhai2909.taskmanagement.internal.config;

import io.github.nthanhhai2909.taskmanagement.internal.application.db.TransactionManager;
import io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.PostgresTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DatabaseConfig {

    @Bean
    public TransactionManager postgresTransactionManager(PlatformTransactionManager platformTransactionManager) {
        return new PostgresTransactionManager(platformTransactionManager);
    }
}
