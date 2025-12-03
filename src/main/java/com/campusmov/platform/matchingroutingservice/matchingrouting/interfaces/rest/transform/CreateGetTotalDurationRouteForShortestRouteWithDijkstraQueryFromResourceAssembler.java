package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetTotalDurationRouteForShortestRouteWithDijkstraQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateRouteResource;

public class CreateGetTotalDurationRouteForShortestRouteWithDijkstraQueryFromResourceAssembler {
    public static GetTotalDurationRouteForShortestRouteWithDijkstraQuery toQueryFromResource(CreateRouteResource resource) {
        return GetTotalDurationRouteForShortestRouteWithDijkstraQuery.builder()
                .startLatitude(resource.startLatitude())
                .startLongitude(resource.startLongitude())
                .endLatitude(resource.endLatitude())
                .endLongitude(resource.endLongitude())
                .build();
    }
}
