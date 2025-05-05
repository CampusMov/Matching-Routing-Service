package com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record CarpoolId(String carpoolId) {
    public CarpoolId {
        if (carpoolId.isBlank() || carpoolId.isEmpty()) {
            throw new IllegalArgumentException("Carpool ID cannot be null or empty");
        }
    }
}
