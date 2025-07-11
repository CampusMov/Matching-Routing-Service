package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetTotalDistanceRouteForShortestRouteWithAQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateRouteResource;

public class CreateGetTotalDistanceRouteForShortestRouteWithAQueryFromResourceAssembler {
    public static GetTotalDistanceRouteForShortestRouteWithAQuery toQueryFromResource(CreateRouteResource resource) {
        return GetTotalDistanceRouteForShortestRouteWithAQuery.builder()
                .startLatitude(resource.startLatitude())
                .startLongitude(resource.startLongitude())
                .endLatitude(resource.endLatitude())
                .endLongitude(resource.endLongitude())
                .build();
    }
}
