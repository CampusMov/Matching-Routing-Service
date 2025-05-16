package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.StartCarpoolCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateLocationResource;

public class StartCarpoolCommandFromResourceAssembler {
    public static StartCarpoolCommand toCommandFromResource(String carpoolId, CreateLocationResource resource) {
        return StartCarpoolCommand.builder()
                .carpoolId(carpoolId)
                .currentLocation(CreateLocationFromResourceAssembler.toCommandFromResource(resource))
                .build();
    }
}
