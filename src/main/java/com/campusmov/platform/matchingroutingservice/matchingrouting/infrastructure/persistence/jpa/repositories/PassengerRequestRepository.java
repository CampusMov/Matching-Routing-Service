package com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.jpa.repositories;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.PassengerRequest;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.EPassengerRequestStatus;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.CarpoolId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PassengerRequestRepository extends JpaRepository<PassengerRequest, String> {
    Collection<PassengerRequest> findAllByCarpoolIdAndStatusIs(CarpoolId carpoolId, EPassengerRequestStatus status);
}