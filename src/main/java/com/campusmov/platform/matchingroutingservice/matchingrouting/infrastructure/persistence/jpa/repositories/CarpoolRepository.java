package com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.jpa.repositories;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Carpool;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.ECarpoolStatus;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.DriverId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.ScheduleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarpoolRepository extends JpaRepository<Carpool, String> {
    Boolean existsByDriverIdAndStatusIn(DriverId driverId, List<ECarpoolStatus> statuses);
    Optional<Carpool> findByDriverIdAndStatusIn(DriverId driverId, List<ECarpoolStatus> status);
    Collection<Carpool> findAllByDriverId(DriverId driverId);
    Collection<Carpool> findAllByScheduleIdInAndStatusIn(Collection<ScheduleId> scheduleIds, List<ECarpoolStatus> status);
}
