package com.ncr.connections.datamigration.car.withepss.model;

public enum SignedStatus {
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

    public static SignedStatus convertFromWithoutEpss(
            final com.ncr.connections.datamigration.car.withoutepss.model.SignedStatus signedStatus) {
        if (signedStatus.equals(com.ncr.connections.datamigration.car.withoutepss.model.SignedStatus.SIGNED)) {
            return SIGNED;
        } else {
            return UNSIGNED;
        }
    }
}
