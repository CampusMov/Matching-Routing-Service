package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.commandservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.PassengerRequest;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreatePassengerRequestCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.PassengerRequestCommandService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.jpa.repositories.PassengerRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PassengerRequestCommandServiceImpl implements PassengerRequestCommandService {
    private final PassengerRequestRepository passengerRequestRepository;

    @Override
    public Optional<PassengerRequest> handle(CreatePassengerRequestCommand command) {
        PassengerRequest newPassengerRequest = new PassengerRequest(command);
        // TODO: Implement the logic to check if maximum passengers are reached (MEDIUM)
        // TODO: Implement the logic to check if the requested seats are less than the available seats (MEDIUM)
        try {
            passengerRequestRepository.save(newPassengerRequest);
        } catch (Exception e) {
            throw new RuntimeException("Error saving passenger request", e);
        }
        return Optional.of(newPassengerRequest);
    }
}
