package com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.jpa.repositories;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Carpool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarpoolRepository extends JpaRepository<Carpool, String> {
}
