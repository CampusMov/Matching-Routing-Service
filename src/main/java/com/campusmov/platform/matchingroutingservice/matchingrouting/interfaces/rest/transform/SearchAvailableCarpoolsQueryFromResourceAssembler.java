package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.SearchAvailableCarpoolsQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.SearchAvailableCarpoolsResource;

public class SearchAvailableCarpoolsQueryFromResourceAssembler {
    public static SearchAvailableCarpoolsQuery toQueryFromResource(SearchAvailableCarpoolsResource resource) {
        return SearchAvailableCarpoolsQuery.builder()
                .startedClassTime(resource.startedClassTime())
                .origin(Location.builder()
                        .name(resource.originName())
                        .address(resource.originAddress())
                        .longitude(resource.originLongitude())
                        .latitude(resource.originLatitude())
                        .build())
                .destination(Location.builder()
                        .name(resource.destinationName())
                        .address(resource.destinationAddress())
                        .longitude(resource.destinationLongitude())
                        .latitude(resource.destinationLatitude())
                        .build())
                .requestedSeats(resource.requestedSeats())
                .build();
    }
}
