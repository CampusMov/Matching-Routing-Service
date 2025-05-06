package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.queryservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.PassengerRequest;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetPassengerRequestByIdQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.PassengerRequestQueryService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.jpa.repositories.PassengerRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PassengerRequestQueryServiceImpl implements PassengerRequestQueryService {
    private final PassengerRequestRepository passengerRequestRepository;

    @Override
    public Optional<PassengerRequest> handle(GetPassengerRequestByIdQuery query) {
        return passengerRequestRepository.findById(query.passengerRequestId());
    }
}
