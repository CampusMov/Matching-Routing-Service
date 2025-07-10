package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.commandservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.outboundservices.CarpoolWebSocketPublisherService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.outboundservices.transform.CarpoolStartedPayloadFromEntityAssembler;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Carpool;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateCarpoolCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateLinkedPassengerCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.FinishCarpoolCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.StartCarpoolCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.ECarpoolStatus;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.CarpoolCommandService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.jpa.repositories.CarpoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CarpoolCommandServiceImpl implements CarpoolCommandService {
    private final CarpoolRepository carpoolRepository;
    private final CarpoolWebSocketPublisherService carpoolWebSocketPublisherService;

    @Override
    public Optional<Carpool> handle(CreateCarpoolCommand command) {
        Carpool newCarpool = new Carpool(command);
        var carpoolStatusesForActive = List.of(ECarpoolStatus.CREATED, ECarpoolStatus.IN_PROGRESS);
        var isDriverAlreadyInCarpool = carpoolRepository.existsByDriverIdAndStatusIn(
                newCarpool.getDriverId(), carpoolStatusesForActive
        );
        if (isDriverAlreadyInCarpool) throw new IllegalArgumentException("Driver is already in an active carpool");
        try {
            carpoolRepository.save(newCarpool);
            newCarpool.sendCarpoolCreatedEvent();
            carpoolRepository.save(newCarpool);
        } catch (Exception e) {
            throw new RuntimeException("Error saving carpool", e);
        }
        return Optional.of(newCarpool);
    }

    @Override
    public Optional<Carpool> handle(StartCarpoolCommand command) {
        Carpool carpool = carpoolRepository
                .findById(command.carpoolId())
                .orElseThrow(() -> new IllegalArgumentException("Carpool with ID %s not found".formatted(command.carpoolId())));
        carpool.start(command.currentLocation());
        var carpoolStartedPayload = CarpoolStartedPayloadFromEntityAssembler.fromEntity(carpool);
        try {
            carpoolRepository.save(carpool);
            carpoolWebSocketPublisherService.handleStartedCarpool(carpoolStartedPayload);
        } catch (Exception e) {
            throw new RuntimeException("Error saving carpool", e);
        }
        return Optional.of(carpool);
    }

    @Override
    public Optional<Carpool> handle(FinishCarpoolCommand command) {
        Carpool carpool = carpoolRepository
                .findById(command.carpoolId())
                .orElseThrow(() -> new IllegalArgumentException("Carpool with ID %s not found".formatted(command.carpoolId())));
        carpool.finish();
        try {
            var finishedCarpool = carpoolRepository.save(carpool);
            return Optional.of(finishedCarpool);
        } catch (Exception e) {
            throw new RuntimeException("Error saving carpool", e);
        }
    }

    @Override
    public void handle(CreateLinkedPassengerCommand command) {
        Carpool carpool = carpoolRepository
                .findById(command.carpoolId().carpoolId())
                .orElseThrow(() -> new IllegalArgumentException("Carpool with ID %s not found".formatted(command.carpoolId())));
        carpool.addLinkedPassenger(command);
        try {
            carpoolRepository.save(carpool);
        } catch (Exception e) {
            throw new RuntimeException("Error saving linked passenger", e);
        }
    }
}
