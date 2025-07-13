package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CancelCarpoolCommand;

public class CancelCarpoolCommandFromResourceAssembler {
    public static CancelCarpoolCommand toCommandFromResource(String carpoolId) {
        return CancelCarpoolCommand.builder()
                .carpoolId(carpoolId)
                .build();
    }
}
