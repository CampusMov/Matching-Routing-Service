package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.queryservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.PassengerRequest;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetAllPassengerRequestsByCarpoolIdAndStatusIsPendingQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetAllPassengerRequestsByPassengerIdQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetPassengerRequestByIdQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.EPassengerRequestStatus;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.PassengerRequestQueryService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.jpa.repositories.PassengerRequestRepository;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.CarpoolId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.PassengerId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PassengerRequestQueryServiceImpl implements PassengerRequestQueryService {
    private final PassengerRequestRepository passengerRequestRepository;

    @Override
    public Optional<PassengerRequest> handle(GetPassengerRequestByIdQuery query) {
        return passengerRequestRepository.findById(query.passengerRequestId());
    }

    @Override
    public Collection<PassengerRequest> handle(GetAllPassengerRequestsByCarpoolIdAndStatusIsPendingQuery query) {
        return passengerRequestRepository.findAllByCarpoolIdAndStatusIs(new CarpoolId(query.carpoolId()), EPassengerRequestStatus.PENDING);
    }

    @Override
    public Collection<PassengerRequest> handle(GetAllPassengerRequestsByPassengerIdQuery query) {
        return passengerRequestRepository.findAllByPassengerId(new PassengerId(query.passengerId()));
    }
}
