package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateCarpoolCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.ECarpoolStatus;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.DriverId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.ScheduleId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.VehicleId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Carpool extends AuditableAbstractAggregateRoot<Carpool> {
    @Embedded
    private DriverId driverId;

    @Embedded
    private VehicleId vehicleId;

    @Enumerated(EnumType.STRING)
    private ECarpoolStatus status;

    @NotNull
    private Integer availableSeats;

    @NotNull
    private Integer maxPassengers;

    @Embedded
    private ScheduleId scheduleId;

    @NotNull
    private Integer radius;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name",      column = @Column(name = "origin_name")),
            @AttributeOverride(name = "address",   column = @Column(name = "origin_address")),
            @AttributeOverride(name = "coordinates", column = @Column(name = "origin_coordinates"))
    })
    private Location origin;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name",      column = @Column(name = "dest_name")),
            @AttributeOverride(name = "address",   column = @Column(name = "dest_address")),
            @AttributeOverride(name = "coordinates", column = @Column(name = "dest_coordinates"))
    })
    private Location destination;

    @NotNull
    private Boolean isVisible;

    public Carpool(){
        super();
        this.status = ECarpoolStatus.CREATED;
        this.availableSeats = 0;
        this.maxPassengers = 0;
        this.radius = 50;
        this.origin = new Location();
        this.destination = new Location();
        this.isVisible = true;
    }

    public Carpool(CreateCarpoolCommand command) {
        this();
        this.driverId = command.driverId();
        this.vehicleId = command.vehicleId();
        this.maxPassengers = command.maxPassengers();
        this.scheduleId = command.scheduleId();
        this.radius = command.radius();
        this.origin = command.origin();
        this.destination = command.destination();
    }
}
