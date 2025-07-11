package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries;

import lombok.Builder;

@Builder
public record FindShortestRouteWithAQuery(
        Double startLatitude,
        Double startLongitude,
        Double endLatitude,
        Double endLongitude
) {
    public FindShortestRouteWithAQuery {
        if (startLatitude == null || startLongitude == null || endLatitude == null || endLongitude == null) {
            throw new IllegalArgumentException("All coordinates must be provided.");
        }
    }
}
