package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.FindShortestRouteWithAQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateRouteResource;

public class CreateFindShortestRouteWithAQueryFromResourceAssembler {
    public static FindShortestRouteWithAQuery toQueryFromResource(CreateRouteResource resource) {
        return FindShortestRouteWithAQuery.builder()
                .startLatitude(resource.startLatitude())
                .startLongitude(resource.startLongitude())
                .endLatitude(resource.endLatitude())
                .endLongitude(resource.endLongitude())
                .build();
    }
}
