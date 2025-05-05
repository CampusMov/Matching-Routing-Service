package com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ScheduleId(String scheduleId) {
    public ScheduleId {
        if (scheduleId.isBlank() || scheduleId.isEmpty()) {
            throw new IllegalArgumentException("Schedule ID cannot be null or empty");
        }
    }
}
