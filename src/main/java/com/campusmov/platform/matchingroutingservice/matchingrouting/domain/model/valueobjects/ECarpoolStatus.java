package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects;

public enum ECarpoolStatus {
    CREATED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED;

    public static ECarpoolStatus fromString(String status) {
        try {
            return ECarpoolStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid carpool status: " + status);
        }
    }
}
