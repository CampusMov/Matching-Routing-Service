package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries;

import lombok.Builder;

@Builder
public record GetTotalDistanceRouteForShortestRouteWithAQuery(
        Double startLatitude,
        Double startLongitude,
        Double endLatitude,
        Double endLongitude
) {
    public GetTotalDistanceRouteForShortestRouteWithAQuery {
        if (startLatitude == null || startLongitude == null || endLatitude == null || endLongitude == null) {
            throw new IllegalArgumentException("All coordinates must be provided.");
        }
    }
}