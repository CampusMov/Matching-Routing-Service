package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.FinishCarpoolCommand;

public class FinishCarpoolCommandFromResourceAssembler {
    public static FinishCarpoolCommand toCommandFromResource(String carpoolId) {
        return FinishCarpoolCommand.builder()
                .carpoolId(carpoolId)
                .build();
    }
}
