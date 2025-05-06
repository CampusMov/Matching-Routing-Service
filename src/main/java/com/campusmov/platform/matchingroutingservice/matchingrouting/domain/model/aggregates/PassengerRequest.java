package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.EPassengerRequestStatus;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.CarpoolId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.PassengerId;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Integer requestedSeats;

    public PassengerRequest() {
        super();
        this.status = EPassengerRequestStatus.PENDING;
        this.requestedSeats = 1;
    }
}
