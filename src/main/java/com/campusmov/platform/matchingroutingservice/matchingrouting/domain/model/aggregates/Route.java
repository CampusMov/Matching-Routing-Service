package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateRouteCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateWayPointCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.UpdateCurrentLocationRouteCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.entities.WayPoint;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.CarpoolId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import java.util.Collection;

@Entity
@Getter
@Setter
public class Route extends AuditableAbstractAggregateRoot<Route> {
    @Embedded
    private CarpoolId carpoolId;

    private DateTime realStartedTime;

    private DateTime estimatedEndedTime;

    private Double estimatedDurationMinutes;

    private Double estimatedDistanceKm;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name",      column = @Column(name = "origin_name")),
            @AttributeOverride(name = "address",   column = @Column(name = "origin_address")),
            @AttributeOverride(name = "longitude", column = @Column(name = "origin_longitude")),
            @AttributeOverride(name = "latitude",  column = @Column(name = "origin_latitude"))
    })
    private Location origin;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name",      column = @Column(name = "dest_name")),
            @AttributeOverride(name = "address",   column = @Column(name = "dest_address")),
            @AttributeOverride(name = "longitude", column = @Column(name = "dest_longitude")),
            @AttributeOverride(name = "latitude",  column = @Column(name = "dest_latitude"))
    })
    private Location destination;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name",      column = @Column(name = "carpool_current_location_name")),
            @AttributeOverride(name = "address",   column = @Column(name = "carpool_current_location_address")),
            @AttributeOverride(name = "longitude", column = @Column(name = "carpool_current_location_longitude")),
            @AttributeOverride(name = "latitude",  column = @Column(name = "carpool_current_location_latitude"))
    })
    private Location carpoolCurrentLocation;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "route")
    private Collection<WayPoint> wayPoints;

    public Route() {
        super();
        this.realStartedTime = null;
        this.estimatedEndedTime = null;
        this.estimatedDurationMinutes = null;
        this.estimatedDistanceKm = null;
        this.origin = new Location();
        this.destination = new Location();
        this.carpoolCurrentLocation = new Location();
    }

    public Route(CreateRouteCommand command){
        this();
        this.carpoolId = command.carpoolId();
        this.origin = command.origin();
        this.destination = command.destination();
        this.carpoolCurrentLocation = command.origin();
    }

    public void updateCurrentLocation(UpdateCurrentLocationRouteCommand command) {
        if (command.currentLocation().getName() != null)
            this.carpoolCurrentLocation.setName(command.currentLocation().getName());
        if (command.currentLocation().getAddress() != null)
            this.carpoolCurrentLocation.setAddress(command.currentLocation().getAddress());
        if (command.currentLocation().getLongitude() != null)
            this.carpoolCurrentLocation.setLongitude(command.currentLocation().getLongitude());
        if (command.currentLocation().getLatitude() != null)
            this.carpoolCurrentLocation.setLatitude(command.currentLocation().getLatitude());
    }

    public WayPoint addWayPoint(CreateWayPointCommand command) {
        if (isPassengerInRoute(command.passengerId().passengerId())) {
            throw new IllegalArgumentException("Passenger is already in the route.");
        }
        WayPoint wayPoint = new WayPoint(this, command);
        this.wayPoints.add(wayPoint);
        return wayPoint;
    }

    private Boolean isPassengerInRoute(String passengerId) {
        return this.wayPoints.stream()
                .anyMatch(wayPoint -> wayPoint.getPassengerId().passengerId().equals(passengerId));
    }
}
