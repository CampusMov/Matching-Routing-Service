package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries;

import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.DriverId;

public record GetAllCarpoolsByDriverIdQuery(
        DriverId driverId
) {
}
