package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.commandservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.PassengerRequest;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.AcceptPassengerRequestCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreatePassengerRequestCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.RejectPassengerRequestCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.PassengerRequestCommandService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.jpa.repositories.PassengerRequestRepository;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.CarpoolId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.PassengerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PassengerRequestCommandServiceUnitTest {
    @Mock
    private PassengerRequestRepository passengerRequestRepository;

    @Captor
    private ArgumentCaptor<PassengerRequest> passengerRequestCaptor;

    private PassengerRequestCommandService passengerRequestCommandService;

    private static final String PASSENGER_REQUEST_ID = "pr-123";
    private static final String CARPOOL_ID = "cp-456";
    private static final String PASSENGER_ID = "p-789";

    private PassengerRequest existingPassengerRequest;

    @BeforeEach
    void setUp() {
        passengerRequestCommandService = new PassengerRequestCommandServiceImpl(passengerRequestRepository);

        existingPassengerRequest = new PassengerRequest();
        existingPassengerRequest.setId(PASSENGER_REQUEST_ID);
        existingPassengerRequest.setCarpoolId(new CarpoolId(CARPOOL_ID));
        existingPassengerRequest.setPassengerId(new PassengerId(PASSENGER_ID));
        existingPassengerRequest.setPickupLocation(new Location("Pt", "Addr", -12.0, -77.0));
        existingPassengerRequest.setRequestedSeats(1);
    }

    @Test
    void TestCreatePassengerRequest_ValidCommand_ShouldPass() {
        // Given
        var pickupLocation = new Location("Pickup Point", "Calle Principal 123", -12.0450, -77.0200);
        var command = CreatePassengerRequestCommand.builder()
                .carpoolId(new CarpoolId(CARPOOL_ID))
                .passengerId(new PassengerId(PASSENGER_ID))
                .pickupLocation(pickupLocation)
                .requestedSeats(2)
                .build();

        Mockito.when(passengerRequestRepository.save(Mockito.any(PassengerRequest.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Optional<PassengerRequest> result = passengerRequestCommandService.handle(command);

        // Then
        Assertions.assertThat(result).isPresent();

        Mockito.verify(passengerRequestRepository).save(passengerRequestCaptor.capture());

        PassengerRequest savedRequest = passengerRequestCaptor.getValue();
        Assertions.assertThat(savedRequest.getCarpoolId()).isEqualTo(command.carpoolId());
        Assertions.assertThat(savedRequest.getPassengerId()).isEqualTo(command.passengerId());
        Assertions.assertThat(savedRequest.getPickupLocation()).isEqualTo(command.pickupLocation());
        Assertions.assertThat(savedRequest.getRequestedSeats()).isEqualTo(command.requestedSeats());
    }

    @Test
    void TestCreatePassengerRequest_SaveThrowsException_ShouldThrowRuntimeException() {
        // Given
        var command = CreatePassengerRequestCommand.builder()
                .carpoolId(new CarpoolId(CARPOOL_ID))
                .passengerId(new PassengerId(PASSENGER_ID))
                .pickupLocation(new Location("Test", "Test Address", 0.0, 0.0))
                .requestedSeats(1)
                .build();

        Mockito.when(passengerRequestRepository.save(Mockito.any(PassengerRequest.class)))
                .thenThrow(new RuntimeException("Database error"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> passengerRequestCommandService.handle(command));

        Assertions.assertThat(exception.getMessage()).isEqualTo("Error saving passenger request");
        Assertions.assertThat(exception.getCause()).isInstanceOf(RuntimeException.class);
    }

    @Test
    void TestAcceptPassengerRequest_ValidCommand_ShouldPass() {
        // Given
        var command = AcceptPassengerRequestCommand.builder()
                .passengerRequestId(PASSENGER_REQUEST_ID)
                .build();

        Mockito.when(passengerRequestRepository.findById(PASSENGER_REQUEST_ID))
                .thenReturn(Optional.of(existingPassengerRequest));
        Mockito.when(passengerRequestRepository.save(Mockito.any(PassengerRequest.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Optional<PassengerRequest> result = passengerRequestCommandService.handle(command);

        // Then
        Assertions.assertThat(result).isPresent();

        Mockito.verify(passengerRequestRepository).findById(PASSENGER_REQUEST_ID);

        Mockito.verify(passengerRequestRepository).save(passengerRequestCaptor.capture());

        PassengerRequest savedRequest = passengerRequestCaptor.getValue();
        Assertions.assertThat(savedRequest.getId()).isEqualTo(PASSENGER_REQUEST_ID);
    }

    @Test
    void TestAcceptPassengerRequest_PassengerRequestNotFound_ShouldThrowException() {
        // Given
        var command = AcceptPassengerRequestCommand.builder()
                .passengerRequestId("non-existent-id")
                .build();

        Mockito.when(passengerRequestRepository.findById("non-existent-id"))
                .thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> passengerRequestCommandService.handle(command));

        Assertions.assertThat(exception.getMessage())
                .isEqualTo("Passenger Request with ID non-existent-id not found");
    }

    @Test
    void TestAcceptPassengerRequest_SaveThrowsException_ShouldThrowRuntimeException() {
        // Given
        var command = AcceptPassengerRequestCommand.builder()
                .passengerRequestId(PASSENGER_REQUEST_ID)
                .build();

        Mockito.when(passengerRequestRepository.findById(PASSENGER_REQUEST_ID))
                .thenReturn(Optional.of(existingPassengerRequest));
        Mockito.when(passengerRequestRepository.save(Mockito.any(PassengerRequest.class)))
                .thenThrow(new RuntimeException("Database error"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> passengerRequestCommandService.handle(command));

        Assertions.assertThat(exception.getMessage()).isEqualTo("Error saving passenger request");
    }

    @Test
    void TestRejectPassengerRequest_ValidCommand_ShouldPass() {
        // Given
        var command = RejectPassengerRequestCommand.builder()
                .passengerRequestId(PASSENGER_REQUEST_ID)
                .build();

        Mockito.when(passengerRequestRepository.findById(PASSENGER_REQUEST_ID))
                .thenReturn(Optional.of(existingPassengerRequest));
        Mockito.when(passengerRequestRepository.save(Mockito.any(PassengerRequest.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Optional<PassengerRequest> result = passengerRequestCommandService.handle(command);

        // Then
        Assertions.assertThat(result).isPresent();

        Mockito.verify(passengerRequestRepository).findById(PASSENGER_REQUEST_ID);

        Mockito.verify(passengerRequestRepository).save(passengerRequestCaptor.capture());

        PassengerRequest savedRequest = passengerRequestCaptor.getValue();
        Assertions.assertThat(savedRequest.getId()).isEqualTo(PASSENGER_REQUEST_ID);
    }

    @Test
    void TestRejectPassengerRequest_PassengerRequestNotFound_ShouldThrowException() {
        // Given
        var command = RejectPassengerRequestCommand.builder()
                .passengerRequestId("non-existent-id")
                .build();

        Mockito.when(passengerRequestRepository.findById("non-existent-id"))
                .thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> passengerRequestCommandService.handle(command));

        Assertions.assertThat(exception.getMessage())
                .isEqualTo("Passenger Request with ID non-existent-id not found");
    }

    @Test
    void TestRejectPassengerRequest_SaveThrowsException_ShouldThrowRuntimeException() {
        // Given
        var command = RejectPassengerRequestCommand.builder()
                .passengerRequestId(PASSENGER_REQUEST_ID)
                .build();

        Mockito.when(passengerRequestRepository.findById(PASSENGER_REQUEST_ID))
                .thenReturn(Optional.of(existingPassengerRequest));
        Mockito.when(passengerRequestRepository.save(Mockito.any(PassengerRequest.class)))
                .thenThrow(new RuntimeException("Database error"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> passengerRequestCommandService.handle(command));

        Assertions.assertThat(exception.getMessage()).isEqualTo("Error saving passenger request");
    }
}