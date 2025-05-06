package com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PassengerId(String passengerId) {
    public PassengerId {
        if (passengerId.isBlank() || passengerId.isEmpty()) {
            throw new IllegalArgumentException("Passenger ID cannot be null or empty");
        }
    }
}
