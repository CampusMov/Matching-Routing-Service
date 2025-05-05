package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.commandservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Carpool;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateCarpoolCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.CarpoolCommandService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.jpa.repositories.CarpoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CarpoolCommandServiceImpl implements CarpoolCommandService {
    private final CarpoolRepository carpoolRepository;

    @Override
    public Optional<Carpool> handle(CreateCarpoolCommand command) {
        Carpool newCarpool = new Carpool(command);
        // TODO: Implement the logic to check if the driver is already in a carpool (HIGH)
        // TODO: Consume the profile service to check if the vehicle belongs to the driver (LOW)
        // TODO: Consume the profile service to get the vehicle information (LOW)
        // TODO: Implement the logic to check if maximum passengers are reached (MEDIUM)

        try {
            carpoolRepository.save(newCarpool);
        } catch (Exception e) {
            throw new RuntimeException("Error saving carpool", e);
        }
        return Optional.of(newCarpool);
    }
}
