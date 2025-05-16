package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.controllers;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.AcceptPassengerRequestCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.RejectPassengerRequestCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetAllPassengerRequestsByCarpoolIdAndStatusIsPendingQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetPassengerRequestByIdQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.PassengerRequestCommandService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.PassengerRequestQueryService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreatePassengerRequestResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.PassengerRequestResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.swagger.PassengerRequestController;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform.CreatePassengerRequestCommandFromResourceAssembler;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform.PassengerRequestResourceFromEntityAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class PassengerRequestControllerImpl implements PassengerRequestController {
    private final PassengerRequestCommandService passengerRequestCommandService;
    private final PassengerRequestQueryService passengerRequestQueryService;

    @Override
    public ResponseEntity<PassengerRequestResource> createPassengerRequest(CreatePassengerRequestResource resource) {
        var command = CreatePassengerRequestCommandFromResourceAssembler.toCommandFromResource(resource);
        var passengerRequest = passengerRequestCommandService.handle(command);
        if (passengerRequest.isEmpty() || passengerRequest.get().getId().isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var passengerRequestResource = PassengerRequestResourceFromEntityAssembler.toResourceFromEntity(passengerRequest.get());
        return new ResponseEntity<>(passengerRequestResource, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PassengerRequestResource> acceptPassengerRequest(String passengerRequestId) {
        var command = new AcceptPassengerRequestCommand(passengerRequestId);
        var acceptedPassengerRequest = passengerRequestCommandService.handle(command);
        if (acceptedPassengerRequest.isEmpty() || acceptedPassengerRequest.get().getId().isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var passengerRequestResource = PassengerRequestResourceFromEntityAssembler.toResourceFromEntity(acceptedPassengerRequest.get());
        return new ResponseEntity<>(passengerRequestResource, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PassengerRequestResource> rejectPassengerRequest(String passengerRequestId) {
        var command = new RejectPassengerRequestCommand(passengerRequestId);
        var rejectedPassengerRequest = passengerRequestCommandService.handle(command);
        if (rejectedPassengerRequest.isEmpty() || rejectedPassengerRequest.get().getId().isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var passengerRequestResource = PassengerRequestResourceFromEntityAssembler.toResourceFromEntity(rejectedPassengerRequest.get());
        return new ResponseEntity<>(passengerRequestResource, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PassengerRequestResource> getPassengerRequestById(String passengerRequestId) {
        var query = new GetPassengerRequestByIdQuery(passengerRequestId);
        var passengerRequest = passengerRequestQueryService.handle(query);
        if (passengerRequest.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var passengerRequestResource = PassengerRequestResourceFromEntityAssembler.toResourceFromEntity(passengerRequest.get());
        return new ResponseEntity<>(passengerRequestResource, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Collection<PassengerRequestResource>> getPassengerRequestsByCarpoolId(String carpoolId) {
        var query = new GetAllPassengerRequestsByCarpoolIdAndStatusIsPendingQuery(carpoolId);
        var passengerRequests = passengerRequestQueryService.handle(query);
        var passengerRequestResources = passengerRequests.stream()
                .map(PassengerRequestResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return new ResponseEntity<>(passengerRequestResources, HttpStatus.OK);
    }
}
