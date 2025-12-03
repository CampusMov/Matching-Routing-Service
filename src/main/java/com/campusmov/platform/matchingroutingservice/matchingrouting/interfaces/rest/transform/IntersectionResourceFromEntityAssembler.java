package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Intersection;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.IntersectionResource;

public class IntersectionResourceFromEntityAssembler {
    public static IntersectionResource toResourceFromEntity(Intersection intersection) {
        return IntersectionResource.builder()
                .latitude(intersection.getLatitude())
                .longitude(intersection.getLongitude())
                .build();
    }
}
