package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto;

import lombok.Builder;

@Builder
public record CreateLocationResource(
        String name,
        String address,
        Double longitude,
        Double latitude
) {
}
