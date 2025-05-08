package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreatePassengerRequestCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events.PassengerRequestAcceptedEvent;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.EPassengerRequestStatus;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.CarpoolId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.PassengerId;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PassengerRequest extends AuditableAbstractAggregateRoot<PassengerRequest> {
    @Embedded
    private CarpoolId carpoolId;

    @Embedded
    private PassengerId passengerId;

    @NotNull
    private Location pickupLocation;

    @Enumerated(EnumType.STRING)
    private EPassengerRequestStatus status;

    @NotNull
    @Min(value = 1)
    private Integer requestedSeats;

    public PassengerRequest() {
        super();
        this.status = EPassengerRequestStatus.PENDING;
        this.requestedSeats = 1;
    }

    public PassengerRequest(CreatePassengerRequestCommand command) {
        this();
        this.carpoolId = command.carpoolId();
        this.passengerId = command.passengerId();
        this.pickupLocation = command.pickupLocation();
        this.requestedSeats = command.requestedSeats() == null ? 1 : command.requestedSeats();
    }

    public void accept() {
        if (!this.isPending()) throw new IllegalStateException("Passenger request cannot be accepted in status: " + this.status);
        this.status = EPassengerRequestStatus.ACCEPTED;
        this.sendPassengerAcceptedEvent();
    }

    public void reject() {
        if (!this.isPending()) throw new IllegalStateException("Passenger request cannot be rejected in status: " + this.status);
        this.status = EPassengerRequestStatus.REJECTED;
    }

    private Boolean isPending() {
        return this.status == EPassengerRequestStatus.PENDING;
    }

    private void sendPassengerAcceptedEvent(){
        registerEvent(PassengerRequestAcceptedEvent
                .builder()
                .passengerRequestId(this.getId())
                .passengerId(this.passengerId.passengerId())
                .pickupLocationName(this.pickupLocation.getName())
                .pickupLocationAddress(this.pickupLocation.getAddress())
                .pickupLocationLongitude(this.pickupLocation.getLongitude())
                .pickupLocationLatitude(this.pickupLocation.getLatitude())
                .requestedSeats(this.requestedSeats)
                .status(this.status.toString())
                .build());
    }
}
