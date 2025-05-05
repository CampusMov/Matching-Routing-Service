package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.queryservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Carpool;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetAllCarpoolsByDriverIdQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetCarpoolByDriverIdAndIsActiveQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetCarpoolByIdQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.ECarpoolStatus;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.CarpoolQueryService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.jpa.repositories.CarpoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CarpoolQueryServiceImpl implements CarpoolQueryService {
    private final CarpoolRepository carpoolRepository;

    @Override
    public Optional<Carpool> handle(GetCarpoolByIdQuery query) {
        return carpoolRepository.findById(query.carpoolId());
    }

    @Override
    public Optional<Carpool> handle(GetCarpoolByDriverIdAndIsActiveQuery query) {
        var carpoolStatusesForActive = List.of(
                ECarpoolStatus.CREATED,
                ECarpoolStatus.IN_PROGRESS
        );
        return carpoolRepository.findByDriverIdAndStatusIn(query.driverId(), carpoolStatusesForActive);
    }

    @Override
    public Collection<Carpool> handle(GetAllCarpoolsByDriverIdQuery query) {
        return carpoolRepository.findAllByDriverId(query.driverId());
    }
}
