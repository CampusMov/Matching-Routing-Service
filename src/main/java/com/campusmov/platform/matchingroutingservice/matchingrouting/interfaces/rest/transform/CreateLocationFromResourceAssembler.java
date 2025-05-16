package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateLocationResource;

public class CreateLocationFromResourceAssembler {
    public static Location toCommandFromResource(CreateLocationResource origin) {
        return Location.builder()
                .name(origin.name())
                .address(origin.address())
                .longitude(origin.longitude())
                .latitude(origin.latitude())
                .build();
    }
}
