package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.entities;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Carpool;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateLinkedPassengerCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.ELinkedPassengerStatus;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.entities.AuditableModel;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.PassengerId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class LinkedPassenger extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "carpool_id", nullable = false)
    private Carpool carpool;

    @Embedded
    private PassengerId passengerId;

    private LocalDateTime pickupTime;

    @NotNull
    private Integer requestedSeats;

    @Enumerated(EnumType.STRING)
    private ELinkedPassengerStatus status;

    public LinkedPassenger() {
        super();
        this.status = ELinkedPassengerStatus.WAITING;
        this.requestedSeats = 1;
        this.pickupTime = null;
    }

    public LinkedPassenger(Carpool carpool, CreateLinkedPassengerCommand command) {
        this();
        this.carpool = carpool;
        this.passengerId = command.passengerId();
        this.requestedSeats = command.requestedSeats();

    }
}
