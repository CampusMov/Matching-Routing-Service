package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetTotalDurationRouteForShortestRouteWithAQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateRouteResource;

public class CreateGetTotalDurationRouteForShortestRouteWithAQueryFromResourceAssembler {
    public static GetTotalDurationRouteForShortestRouteWithAQuery toQueryFromResource(CreateRouteResource resource) {
        return GetTotalDurationRouteForShortestRouteWithAQuery.builder()
                .startLatitude(resource.startLatitude())
                .startLongitude(resource.startLongitude())
                .endLatitude(resource.endLatitude())
                .endLongitude(resource.endLongitude())
                .build();
    }
}
