package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Carpool;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.*;

import java.util.Collection;
import java.util.Optional;

public interface CarpoolQueryService {
    Optional<Carpool> handle(GetCarpoolByIdQuery query);
    Optional<Carpool> handle(GetCarpoolByDriverIdAndIsActiveQuery query);
    Collection<Carpool> handle(GetAllCarpoolsByDriverIdQuery query);
    Collection<Carpool> handle(SearchAvailableCarpoolsQuery query);
}
