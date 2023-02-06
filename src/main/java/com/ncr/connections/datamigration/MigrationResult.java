package com.ncr.connections.datamigration;

public class MigrationResult {

    private boolean result;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(final boolean result) {
        this.result = result;
    }
}
