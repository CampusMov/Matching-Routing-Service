package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects;

public enum EDay {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public static EDay fromString(String day) {
        try {
            return EDay.valueOf(day.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid day: " + day);
        }
    }
}
