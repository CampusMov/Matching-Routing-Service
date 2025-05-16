package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.PassengerRequest;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetAllPassengerRequestsByCarpoolIdAndStatusIsPendingQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetPassengerRequestByIdQuery;

import java.util.Collection;
import java.util.Optional;

public interface PassengerRequestQueryService {
    Optional<PassengerRequest> handle(GetPassengerRequestByIdQuery query);
    Collection<PassengerRequest> handle(GetAllPassengerRequestsByCarpoolIdAndStatusIsPendingQuery query);
}
