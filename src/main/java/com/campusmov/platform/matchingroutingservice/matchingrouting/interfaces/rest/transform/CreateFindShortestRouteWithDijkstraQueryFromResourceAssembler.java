package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.FindShortestRouteWithDijkstraQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateRouteResource;

public class CreateFindShortestRouteWithDijkstraQueryFromResourceAssembler {
     public static FindShortestRouteWithDijkstraQuery toQueryFromResource(CreateRouteResource resource) {
         return FindShortestRouteWithDijkstraQuery.builder()
                 .startLatitude(resource.startLatitude())
                 .startLongitude(resource.startLongitude())
                 .endLatitude(resource.endLatitude())
                 .endLongitude(resource.endLongitude())
                 .build();
     }
}
