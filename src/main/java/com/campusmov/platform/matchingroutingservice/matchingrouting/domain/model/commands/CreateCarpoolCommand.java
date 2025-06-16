package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.EDay;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.DriverId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.ScheduleId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.VehicleId;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreateCarpoolCommand(
        DriverId driverId,
        VehicleId vehicleId,
        Integer maxPassengers,
        ScheduleId scheduleId,
        Integer radius,
        Location origin,
        Location destination,
        LocalDateTime startedClassTime,
        LocalDateTime endedClassTime,
        EDay classDay
) {
}
