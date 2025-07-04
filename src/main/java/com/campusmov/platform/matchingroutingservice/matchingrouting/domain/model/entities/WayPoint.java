package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.entities;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Route;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateWayPointCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.entities.AuditableModel;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.PassengerId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

@Entity
@Getter
@Setter
public class WayPoint extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Embedded
    private PassengerId passengerId;

    @NotNull
    private Location location;

    private DateTime estimatedArrival;

    private DateTime realArrival;

    public WayPoint() {
        super();
        this.location = new Location();
        this.estimatedArrival = null;
        this.realArrival = null;
    }

    public WayPoint(Route route, CreateWayPointCommand command) {
        this();
        this.route = route;
        this.passengerId = command.passengerId();
        this.location = command.location();
    }
}
