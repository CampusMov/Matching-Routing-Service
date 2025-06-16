package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateCarpoolCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.EDay;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateCarpoolResource;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.DriverId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.ScheduleId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.VehicleId;

public class CreateCarpoolCommandFromResourceAssembler {
    public static CreateCarpoolCommand toCommandFromResource(CreateCarpoolResource resource) {
        return CreateCarpoolCommand.builder()
                .driverId(new DriverId(resource.driverId()))
                .vehicleId(new VehicleId(resource.vehicleId()))
                .maxPassengers(resource.maxPassengers())
                .scheduleId(new ScheduleId(resource.scheduleId()))
                .radius(resource.radius())
                .origin(CreateLocationFromResourceAssembler.toCommandFromResource(resource.origin()))
                .destination(CreateLocationFromResourceAssembler.toCommandFromResource(resource.destination()))
                .startedClassTime(resource.startedClassTime())
                .endedClassTime(resource.endedClassTime())
                .classDay(EDay.valueOf(resource.classDay()))
                .build();
    }
}
