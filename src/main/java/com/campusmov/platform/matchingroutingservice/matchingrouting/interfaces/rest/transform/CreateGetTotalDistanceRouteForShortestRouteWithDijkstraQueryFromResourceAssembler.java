package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetTotalDistanceRouteForShortestRouteWithDijkstraQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateRouteResource;

public class CreateGetTotalDistanceRouteForShortestRouteWithDijkstraQueryFromResourceAssembler {
    public static GetTotalDistanceRouteForShortestRouteWithDijkstraQuery toQueryFromResource(CreateRouteResource resource) {
        return GetTotalDistanceRouteForShortestRouteWithDijkstraQuery.builder()
                .startLatitude(resource.startLatitude())
                .startLongitude(resource.startLongitude())
                .endLatitude(resource.endLatitude())
                .endLongitude(resource.endLongitude())
                .build();
    }
}
