package com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.jpa.repositories;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.PassengerRequest;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.EPassengerRequestStatus;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.CarpoolId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.PassengerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PassengerRequestRepositoryIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private PassengerRequestRepository passengerRequestRepository;

    private PassengerRequest req1;
    private PassengerRequest req2;
    private PassengerRequest req3;

    @BeforeEach
    void setUp() {
        req1 = new PassengerRequest();
        req1.setCarpoolId(new CarpoolId("carpool-1"));
        req1.setPassengerId(new PassengerId("passenger-1"));
        req1.setPickupLocation(new Location("Origin A", "Av. Uno 123", -12.01, -77.01));

        req2 = new PassengerRequest();
        req2.setCarpoolId(new CarpoolId("carpool-1"));
        req2.setPassengerId(new PassengerId("passenger-2"));
        req2.setPickupLocation(new Location("Origin B", "Calle Dos 456", -12.02, -77.02));
        req2.accept();

        req3 = new PassengerRequest();
        req3.setCarpoolId(new CarpoolId("carpool-2"));
        req3.setPassengerId(new PassengerId("passenger-1"));
        req3.setPickupLocation(new Location("Origin C", "Jr. Tres 789", -12.03, -77.03));

        passengerRequestRepository.saveAll(List.of(req1, req2, req3));
    }

    @Test
    void findAllByCarpoolIdAndStatusIs() {
        var results = passengerRequestRepository
                .findAllByCarpoolIdAndStatusIs(new CarpoolId("carpool-1"), EPassengerRequestStatus.PENDING);
        assertEquals(1, results.size());
        PassengerRequest only = results.iterator().next();
        assertEquals(req1.getId(), only.getId(), "El ID de la petición no coincide");
    }

    @Test
    void findAllByPassengerId() {
        var results = passengerRequestRepository
                .findAllByPassengerId(new PassengerId("passenger-1"));

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(r -> r.getId().equals(req1.getId())), "Debe contener la petición req1");
        assertTrue(results.stream().anyMatch(r -> r.getId().equals(req3.getId())), "Debe contener la petición req3");
    }
}