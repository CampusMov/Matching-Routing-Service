package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto;

import lombok.Builder;

@Builder
public record CreateRouteResource(
        Double startLatitude,
        Double startLongitude,
        Double endLatitude,
        Double endLongitude
) {
}
