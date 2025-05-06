package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects;

public enum EPassengerRequestStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    CANCELLED;

    public static EPassengerRequestStatus fromString(String status) {
        try {
            return EPassengerRequestStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
}
