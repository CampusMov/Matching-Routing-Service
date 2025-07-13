package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateCarpoolCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateLinkedPassengerCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.entities.LinkedPassenger;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events.CarpoolCreatedEvent;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events.LinkedPassengerCreatedEvent;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.ECarpoolStatus;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.EDay;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.DriverId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.ScheduleId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.VehicleId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

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

    @NotNull
    private LocalTime startedClassTime;

    @NotNull
    private LocalTime endedClassTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EDay classDay;

    @NotNull
    private Boolean isVisible;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "carpool")
    private Collection<LinkedPassenger> linkedPassengers;

    public Carpool(){
        super();
        this.status = ECarpoolStatus.CREATED;
        this.availableSeats = 0;
        this.maxPassengers = 0;
        this.radius = 50;
        this.origin = new Location();
        this.destination = new Location();
        this.isVisible = true;
        this.linkedPassengers = new ArrayList<>();
    }

    public Carpool(CreateCarpoolCommand command) {
        this();
        this.driverId = command.driverId();
        this.vehicleId = command.vehicleId();
        verifyMaxPassengers(command.maxPassengers());
        this.scheduleId = command.scheduleId();
        verifyRadius(command.radius());
        this.origin = command.origin();
        this.destination = command.destination();
        this.startedClassTime = command.startedClassTime();
        this.endedClassTime = command.endedClassTime();
        this.classDay = command.classDay();
    }

    public void verifyMaxPassengers(Integer maxPassengers) {
        if (maxPassengers == null || maxPassengers <= 0) {
            throw new IllegalArgumentException("Max passengers must be greater than zero");
        }
        if (maxPassengers > 4) {
            throw new IllegalArgumentException("Max passengers cannot exceed 4");
        }
        this.maxPassengers = maxPassengers;
        this.availableSeats = maxPassengers; // Initialize available seats to max passengers
    }

    public void verifyRadius(Integer radius) {
        if (radius == null || radius <= 0) {
            throw new IllegalArgumentException("Radius must be greater than zero");
        }
        if (radius > 100) {
            throw new IllegalArgumentException("Radius cannot exceed 100 meters");
        }
        this.radius = radius;
    }

    private Boolean isAvailableToStart() {
        return this.status == ECarpoolStatus.CREATED;
    }

    private Boolean isAtOriginLocation(Location location) {
        final int MAX_RADIUS_METERS_TO_ORIGIN = 20;
        return this.isWithinRadius(this.origin, location, MAX_RADIUS_METERS_TO_ORIGIN);
    }

    public void start(Location currentLocation) {
        if (!isAvailableToStart()) throw new IllegalArgumentException("Carpool with ID %s is not allowed to start".formatted(this.getId()));
        //if (!isAtOriginLocation(currentLocation)) throw new IllegalArgumentException("Carpool with ID %s is not at the origin location".formatted(this.getId()));
        this.status = ECarpoolStatus.IN_PROGRESS;
    }

    public void finish() {
        if (this.status != ECarpoolStatus.IN_PROGRESS) throw new IllegalArgumentException("Carpool with ID %s is not in progress".formatted(this.getId()));
        //if (!isAtDestinationLocation(currentLocation)) throw new IllegalArgumentException("Carpool with ID %s is not at the destination location".formatted(this.getId()));
        this.status = ECarpoolStatus.COMPLETED;
    }

    public void cancel() {
        if (this.status == ECarpoolStatus.COMPLETED) throw new IllegalArgumentException("Carpool with ID %s is already completed".formatted(this.getId()));
        this.status = ECarpoolStatus.CANCELLED;
    }

    public Double haversineDistanceMeters(Location loc1, Location loc2) {
        final int EARTH_RADIUS_METERS = 6_371_000;

        double deltaLatRad = Math.toRadians(loc2.getLatitude() - loc1.getLatitude());
        double deltaLngRad = Math.toRadians(loc2.getLongitude() - loc1.getLongitude());

        double sinHalfDeltaLat = Math.sin(deltaLatRad / 2);
        double sinHalfDeltaLng = Math.sin(deltaLngRad / 2);

        double haversineFormula = sinHalfDeltaLat * sinHalfDeltaLat
                + Math.cos(Math.toRadians(loc1.getLatitude()))
                * Math.cos(Math.toRadians(loc2.getLatitude()))
                * sinHalfDeltaLng * sinHalfDeltaLng;

        double angularDistance = 2 * Math.atan2(
                Math.sqrt(haversineFormula),
                Math.sqrt(1 - haversineFormula)
        );

        return EARTH_RADIUS_METERS * angularDistance;
    }

    private Boolean isWithinRadius(Location loc1, Location loc2, Integer radius) {
        return haversineDistanceMeters(loc1, loc2) <= radius;
    }

    public void addLinkedPassenger(CreateLinkedPassengerCommand command) {
        if (isPassengerAlreadyLinked(command.passengerId().passengerId())) throw new IllegalArgumentException("Passenger with ID %s is already linked to this carpool".formatted(command.passengerId().passengerId()));
        if (!isThereAvailableSeats()) throw new IllegalArgumentException("Carpool with ID %s has no available seats".formatted(this.getId()));
        if (!hasAvailableSeatsToCoverRequestedSeats(command.requestedSeats())) throw new IllegalArgumentException("Carpool with ID %s has not enough available seats to cover the requested seats".formatted(this.getId()));
        LinkedPassenger linkedPassenger = new LinkedPassenger(this, command);
        this.linkedPassengers.add(linkedPassenger);
        LinkedPassengerCreatedEvent event = linkedPassenger.sendLinkedPassengerCreatedEvent();
        this.registerEvent(event);
    }

    private Boolean isPassengerAlreadyLinked(String passengerId) {
        return this.linkedPassengers.stream().anyMatch(lp -> lp.getPassengerId().passengerId().equals(passengerId));
    }

    private Boolean isThereAvailableSeats() {
        return this.availableSeats > 0;
    }

    private Boolean hasAvailableSeatsToCoverRequestedSeats(Integer requestedSeats) {
        return this.availableSeats >= requestedSeats;
    }

    public void sendCarpoolCreatedEvent() {
        CarpoolCreatedEvent event = new CarpoolCreatedEvent(
                this.getId(),
                this.driverId.driverId(),
                this.vehicleId.vehicleId(),
                this.scheduleId.scheduleId(),
                this.status.name(),
                this.radius,
                this.origin,
                this.destination,
                this.isVisible
        );
        this.registerEvent(event);
    }

    private void sendAddLinkedPassengerRejectedEvent(String passengerId) {
        //
    }
}
