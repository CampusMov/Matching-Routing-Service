package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto;

import lombok.Builder;

@Builder
public record IntersectionResource(
        Double latitude,
        Double longitude
) {
}
