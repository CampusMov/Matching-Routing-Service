package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.commandservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.outboundservices.CarpoolWebSocketPublisherService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Carpool;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateCarpoolCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateLinkedPassengerCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.StartCarpoolCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.ECarpoolStatus;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.EDay;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.CarpoolCommandService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.jpa.repositories.CarpoolRepository;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CarpoolCommandServiceImplUnitTest {
    @Mock
    private CarpoolRepository carpoolRepository;

    @Mock
    private CarpoolWebSocketPublisherService carpoolWebSocketPublisherService;

    @Captor
    private ArgumentCaptor<Carpool> carpoolArgumentCaptor;

    private CarpoolCommandService carpoolCommandService;

    private static final String CARPOOL_ID = "cp-123";
    private static final Location ORIGIN_LOCATION = new Location("A", "Addr", 0.0, 0.0);

    private Carpool existingCarpool;

    @BeforeEach
    void setUp() {
        carpoolCommandService = new CarpoolCommandServiceImpl(carpoolRepository, carpoolWebSocketPublisherService);

        existingCarpool = new Carpool();
        existingCarpool.setId(CARPOOL_ID);
        existingCarpool.setOrigin(ORIGIN_LOCATION);
        existingCarpool.setStatus(ECarpoolStatus.CREATED);
        existingCarpool.setMaxPassengers(2);
        existingCarpool.setAvailableSeats(2);
    }

    @Test
    void TestCreateCarpool_ValidCommand_ShouldPass() {
        // Given
        var cmd = CreateCarpoolCommand.builder()
                .driverId(new DriverId("d1"))
                .vehicleId(new VehicleId("v1"))
                .maxPassengers(3)
                .scheduleId(new ScheduleId("s1"))
                .radius(25)
                .origin(new Location("A", "Addr", -1.0, 1.0))
                .destination(new Location("B", "Addr", -2.0, 2.0))
                .startedClassTime(LocalTime.of(7, 0))
                .endedClassTime(LocalTime.of(8, 0))
                .classDay(EDay.MONDAY)
                .build();

        Mockito.when(carpoolRepository.existsByDriverIdAndStatusIn(
                Mockito.eq(cmd.driverId()),
                Mockito.eq(List.of(ECarpoolStatus.CREATED, ECarpoolStatus.IN_PROGRESS))
        )).thenReturn(false);
        Mockito.when(carpoolRepository.save(Mockito.any(Carpool.class)))
                .thenAnswer(i -> i.getArgument(0));

        // When
        var result = carpoolCommandService.handle(cmd);

        // Then
        Assertions.assertThat(result).isPresent();
        Mockito.verify(carpoolRepository).existsByDriverIdAndStatusIn(Mockito.eq(cmd.driverId()), Mockito.anyList());
        Mockito.verify(carpoolRepository).save(carpoolArgumentCaptor.capture());

        Carpool saved = carpoolArgumentCaptor.getValue();
        Assertions.assertThat(saved.getDriverId()).isEqualTo(cmd.driverId());
        Assertions.assertThat(saved.getVehicleId()).isEqualTo(cmd.vehicleId());
        Assertions.assertThat(saved.getMaxPassengers()).isEqualTo(3);
        Assertions.assertThat(saved.getAvailableSeats()).isEqualTo(3);
        Assertions.assertThat(saved.getStatus()).isEqualTo(ECarpoolStatus.CREATED);
    }

    @Test
    void TestStartCarpool_ValidCommand_ShouldPass() {
        // Given
        var cmd = new StartCarpoolCommand(CARPOOL_ID, ORIGIN_LOCATION);

        Mockito.when(carpoolRepository.findById(CARPOOL_ID))
                .thenReturn(Optional.of(existingCarpool));
        Mockito.when(carpoolRepository.save(Mockito.any(Carpool.class)))
                .thenAnswer(i -> i.getArgument(0));

        // When
        var result = carpoolCommandService.handle(cmd);

        // Then
        Assertions.assertThat(result).isPresent();
        Mockito.verify(carpoolRepository).findById(CARPOOL_ID);
        Mockito.verify(carpoolRepository).save(carpoolArgumentCaptor.capture());

        Carpool updated = carpoolArgumentCaptor.getValue();
        Assertions.assertThat(updated.getStatus()).isEqualTo(ECarpoolStatus.IN_PROGRESS);
    }

    @Test
    void TestCreateLinkedPassenger_ValidCommand_ShouldPass() {
        // Given
        var cmd = CreateLinkedPassengerCommand.builder()
                .carpoolId(new CarpoolId(CARPOOL_ID))
                .passengerId(new PassengerId("p1"))
                .requestedSeats(1)
                .build();

        Mockito.when(carpoolRepository.findById(CARPOOL_ID))
                .thenReturn(Optional.of(existingCarpool));
        Mockito.when(carpoolRepository.save(Mockito.any(Carpool.class)))
                .thenAnswer(i -> i.getArgument(0));

        // When
        carpoolCommandService.handle(cmd);

        // Then
        Mockito.verify(carpoolRepository).findById(CARPOOL_ID);
        Mockito.verify(carpoolRepository).save(carpoolArgumentCaptor.capture());

        Carpool after = carpoolArgumentCaptor.getValue();
        Assertions.assertThat(after.getLinkedPassengers()).hasSize(1);
        Assertions.assertThat(after.getLinkedPassengers().iterator().next().getPassengerId().passengerId())
                .isEqualTo("p1");
    }
}