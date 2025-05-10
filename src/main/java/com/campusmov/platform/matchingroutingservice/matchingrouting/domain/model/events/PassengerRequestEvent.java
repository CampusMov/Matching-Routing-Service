package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events;

import com.campusmov.platform.matchingroutingservice.shared.domain.model.events.DomainEvent;

public sealed interface PassengerRequestEvent extends DomainEvent permits
        PassengerRequestAcceptedEvent {
}
