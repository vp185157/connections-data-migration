package com.ncr.connections.datamigration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class ConnectionsDataMigrationApplication implements CommandLineRunner {

    private final List<DataMigrator> dataMigrators;

    Logger logger = LoggerFactory.getLogger(ConnectionsDataMigrationApplication.class);

    @Autowired
    public ConnectionsDataMigrationApplication(
            final List<DataMigrator> dataMigrators) {
        this.dataMigrators = dataMigrators;
    }

    public static void main(final String[] args) {
        SpringApplication.run(ConnectionsDataMigrationApplication.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {
        logger.info("######### DataMigrator List {}", dataMigrators.size());
        List<MigrationResult> migrationResults =
                dataMigrators.stream().sorted(Comparator.comparing(DataMigrator::getOrder)).map(DataMigrator::migrate).collect(Collectors.toList());
        logger.info("######### Migration Results {}", migrationResults.size());
        migrationResults.forEach(migrationResult -> {
            if (migrationResult.isResult()) {
                logger.info("Migration Result For {} is {}", migrationResult.getName(), "PASSED ");
            } else {
                logger.error("Migration Result For {} is {}", migrationResult.getName(), "FAILED ");
            }
        });
    }
}
