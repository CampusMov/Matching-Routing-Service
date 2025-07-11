package com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.jpa.repositories;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Carpool;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.ECarpoolStatus;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.EDay;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.DriverId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.ScheduleId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.VehicleId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Disabled("Skipping until Neo4j configuration issues are resolved")
class CarpoolRepositoryIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private CarpoolRepository carpoolRepository;

    private Carpool carpool1;
    private Carpool carpool2;
    private Carpool carpool3;

    @BeforeEach
    void setUp() {
        carpool1 = new Carpool();
        carpool1.setDriverId(new DriverId("driver-1"));
        carpool1.setVehicleId(new VehicleId("vehicle-1"));
        carpool1.setMaxPassengers(3);
        carpool1.setAvailableSeats(3);
        carpool1.setScheduleId(new ScheduleId("schedule-1"));
        carpool1.setRadius(30);
        carpool1.setOrigin(new Location());
        carpool1.getOrigin().setName("Facultad A");
        carpool1.getOrigin().setAddress("Calle Principal 123");
        carpool1.getOrigin().setLatitude(-12.0450);
        carpool1.getOrigin().setLongitude(-77.0200);
        carpool1.setDestination(new Location());
        carpool1.getDestination().setName("Campus B");
        carpool1.getDestination().setAddress("Avenida Universitaria 456");
        carpool1.getDestination().setLatitude(-12.0400);
        carpool1.getDestination().setLongitude(-77.0150);
        carpool1.setStartedClassTime(LocalTime.of(7, 30));
        carpool1.setEndedClassTime(LocalTime.of(8, 30));
        carpool1.setClassDay(EDay.MONDAY);
        carpool1.setStatus(ECarpoolStatus.CREATED);

        carpool2 = new Carpool();
        carpool2.setDriverId(new DriverId("driver-2"));
        carpool2.setVehicleId(new VehicleId("vehicle-2"));
        carpool2.setMaxPassengers(2);
        carpool2.setAvailableSeats(2);
        carpool2.setScheduleId(new ScheduleId("schedule-2"));
        carpool2.setRadius(50);
        carpool2.setOrigin(new Location());
        carpool2.getOrigin().setName("Facultad C");
        carpool2.getOrigin().setAddress("Calle Estudiantes 789");
        carpool2.getOrigin().setLatitude(-12.0500);
        carpool2.getOrigin().setLongitude(-77.0300);
        carpool2.setDestination(new Location());
        carpool2.getDestination().setName("Campus D");
        carpool2.getDestination().setAddress("Calle Académicos 321");
        carpool2.getDestination().setLatitude(-12.0550);
        carpool2.getDestination().setLongitude(-77.0250);
        carpool2.setStartedClassTime(LocalTime.of(9, 0));
        carpool2.setEndedClassTime(LocalTime.of(10, 0));
        carpool2.setClassDay(EDay.TUESDAY);
        carpool2.setStatus(ECarpoolStatus.CREATED);

        carpool3 = new Carpool();
        carpool3.setDriverId(new DriverId("driver-3"));
        carpool3.setVehicleId(new VehicleId("vehicle-3"));
        carpool3.setMaxPassengers(4);
        carpool3.setAvailableSeats(4);
        carpool3.setScheduleId(new ScheduleId("schedule-3"));
        carpool3.setRadius(75);
        carpool3.setOrigin(new Location());
        carpool3.getOrigin().setName("Facultad E");
        carpool3.getOrigin().setAddress("Calle Estudio 987");
        carpool3.getOrigin().setLatitude(-12.0600);
        carpool3.getOrigin().setLongitude(-77.0400);
        carpool3.setDestination(new Location());
        carpool3.getDestination().setName("Campus F");
        carpool3.getDestination().setAddress("Calle Investigación 654");
        carpool3.getDestination().setLatitude(-12.0650);
        carpool3.getDestination().setLongitude(-77.0350);
        carpool3.setStartedClassTime(LocalTime.of(11, 0));
        carpool3.setEndedClassTime(LocalTime.of(12, 0));
        carpool3.setClassDay(EDay.WEDNESDAY);
        carpool3.setStatus(ECarpoolStatus.CREATED);

        carpoolRepository.saveAll(List.of(carpool1, carpool2, carpool3));
    }

    @Test
    void existsByDriverIdAndStatusIn() {
        assertTrue(carpoolRepository.existsByDriverIdAndStatusIn(
                carpool1.getDriverId(),
                List.of(ECarpoolStatus.CREATED))
        );
    }

    @Test
    void findByDriverIdAndStatusIn() {
        var found = carpoolRepository.findByDriverIdAndStatusIn(
                carpool2.getDriverId(),
                List.of(ECarpoolStatus.CREATED)
        );
        assertEquals(carpool2.getId(), found.get().getId());
    }

    @Test
    void findAllByDriverId() {
        var list = carpoolRepository.findAllByDriverId(carpool3.getDriverId());
        assertEquals(1, list.size());
        assertEquals(carpool3.getId(), list.stream().filter(c -> c.getDriverId().equals(carpool3.getDriverId())).findFirst().get().getId());
    }

    @Test
    void findAllByDestinationLatitudeAndDestinationLongitudeAndStartedClassTimeAndClassDayAndAvailableSeatsGreaterThanEqualAndStatusIn() {
        var list = carpoolRepository
                .findAllByDestinationLatitudeAndDestinationLongitudeAndStartedClassTimeAndClassDayAndAvailableSeatsGreaterThanEqualAndStatusIn(
                        -12.0400,
                        -77.0150,
                        LocalTime.of(7, 30),
                        EDay.MONDAY,
                        1,
                        List.of(ECarpoolStatus.CREATED)
                );
        assertEquals(1, list.size());
    }
}