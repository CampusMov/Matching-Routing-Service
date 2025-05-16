package com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record DriverId(String driverId) {
    public DriverId {
        if (driverId.isBlank() || driverId.isEmpty()) {
            throw new IllegalArgumentException("Driver ID cannot be null or empty");
        }
    }
}
