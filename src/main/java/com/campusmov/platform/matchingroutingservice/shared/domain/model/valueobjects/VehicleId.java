package com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record VehicleId(String vehicleId) {
    public VehicleId {
        if (vehicleId.isBlank() || vehicleId.isEmpty()) {
            throw new IllegalArgumentException("Vehicle ID cannot be null or empty");
        }
    }
}
