package com.ncr.connections.datamigration;

import org.springframework.context.annotation.Bean;

public interface DataMigrator {

    String getDataName();

    MigrationResult migrate();

    int getOrder();
}
