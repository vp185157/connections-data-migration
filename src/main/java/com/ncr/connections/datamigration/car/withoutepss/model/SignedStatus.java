package com.ncr.connections.datamigration.car.withoutepss.model;

public enum SignedStatus
{
    SIGNED, UNSIGNED;

    @Override
    public String toString() {
        switch (this) {
            case SIGNED:
                return "Signed";
            case UNSIGNED:
                return "Unsigned";
            default:
                throw new IllegalStateException("SignedStatus enum value is not handled by toString");
        }
    }
}
