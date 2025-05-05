package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Carpool;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetCarpoolByIdQuery;

import java.util.Optional;

public interface CarpoolQueryService {
    Optional<Carpool> handle(GetCarpoolByIdQuery query);
}
