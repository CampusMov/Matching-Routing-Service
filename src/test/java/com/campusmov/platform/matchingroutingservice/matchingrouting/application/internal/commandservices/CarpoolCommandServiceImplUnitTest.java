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
    private static final DriverId DRIVER_ID = new DriverId("d1");
    private static final VehicleId VEHICLE_ID = new VehicleId("v1");
    private static final ScheduleId SCHEDULE_ID = new ScheduleId("s1");
    private static final Location ORIGIN_LOCATION = new Location("A", "Addr", 0.0, 0.0);
    private static final Location DESTINATION_LOCATION = new Location("B", "Addr", -2.0, 2.0);

    private Carpool existingCarpool;

    @BeforeEach
    void setUp() {
        carpoolCommandService = new CarpoolCommandServiceImpl(carpoolRepository, carpoolWebSocketPublisherService);

        existingCarpool = new Carpool();
        existingCarpool.setId(CARPOOL_ID);
        existingCarpool.setDriverId(DRIVER_ID);
        existingCarpool.setVehicleId(VEHICLE_ID);
        existingCarpool.setScheduleId(SCHEDULE_ID);
        existingCarpool.setOrigin(ORIGIN_LOCATION);
        existingCarpool.setDestination(DESTINATION_LOCATION);
        existingCarpool.setStatus(ECarpoolStatus.CREATED);
        existingCarpool.setMaxPassengers(2);
        existingCarpool.setAvailableSeats(2);
        existingCarpool.setRadius(25);  // Set radius
        existingCarpool.setStartedClassTime(LocalTime.of(7, 0));
        existingCarpool.setEndedClassTime(LocalTime.of(8, 0));
        existingCarpool.setClassDay(EDay.MONDAY);  // Set class day
    }

    @Test
    void TestCreateCarpool_ValidCommand_ShouldPass() {
        // Given
        var cmd = CreateCarpoolCommand.builder()
                .driverId(DRIVER_ID)
                .vehicleId(VEHICLE_ID)
                .maxPassengers(3)
                .scheduleId(SCHEDULE_ID)
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
        Mockito.verify(carpoolRepository, Mockito.times(2)).save(Mockito.any(Carpool.class));
        
        Mockito.verify(carpoolRepository, Mockito.atLeast(1)).save(carpoolArgumentCaptor.capture());
        Carpool saved = carpoolArgumentCaptor.getAllValues().get(0);
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
        Mockito.verify(carpoolWebSocketPublisherService).handleStartedCarpool(Mockito.any());

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

    @Test
    void TestCreateCarpool_DriverAlreadyActive_ShouldThrowException() {
        // Given
        var cmd = CreateCarpoolCommand.builder()
                .driverId(DRIVER_ID)
                .vehicleId(VEHICLE_ID)
                .maxPassengers(3)
                .scheduleId(SCHEDULE_ID)
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
        )).thenReturn(true);

        // When & Then
        Assertions.assertThatThrownBy(() -> carpoolCommandService.handle(cmd))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Driver is already in an active carpool");
    }

    @Test
    void TestStartCarpool_CarpoolNotFound_ShouldThrowException() {
        // Given
        var cmd = new StartCarpoolCommand("non-existent-id", ORIGIN_LOCATION);

        Mockito.when(carpoolRepository.findById("non-existent-id"))
                .thenReturn(Optional.empty());

        // When & Then
        Assertions.assertThatThrownBy(() -> carpoolCommandService.handle(cmd))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Carpool with ID non-existent-id not found");
    }

    @Test
    void TestCreateLinkedPassenger_CarpoolNotFound_ShouldThrowException() {
        // Given
        var cmd = CreateLinkedPassengerCommand.builder()
                .carpoolId(new CarpoolId("non-existent-id"))
                .passengerId(new PassengerId("p1"))
                .requestedSeats(1)
                .build();

        Mockito.when(carpoolRepository.findById("non-existent-id"))
                .thenReturn(Optional.empty());

        // When & Then
        Assertions.assertThatThrownBy(() -> carpoolCommandService.handle(cmd))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Carpool with ID CarpoolId[carpoolId=non-existent-id] not found");
    }
}