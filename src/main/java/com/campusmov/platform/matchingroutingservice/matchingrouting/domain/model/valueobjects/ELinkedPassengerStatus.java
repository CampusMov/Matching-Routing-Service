package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects;

public enum ELinkedPassengerStatus {
    WAITING,
    BOARDED;

    public static ELinkedPassengerStatus fromString(String status) {
        try {
            return ELinkedPassengerStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid linked passenger status: " + status);
        }
    }
}
