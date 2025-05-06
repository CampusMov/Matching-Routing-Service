package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.controllers;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.AcceptPassengerRequestCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.RejectPassengerRequestCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.PassengerRequestCommandService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreatePassengerRequestResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.PassengerRequestResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.swagger.PassengerRequestController;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform.CreatePassengerRequestCommandFromResourceAssembler;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform.PassengerRequestResourceFromEntityAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PassengerRequestControllerImpl implements PassengerRequestController {
    private final PassengerRequestCommandService passengerRequestCommandService;

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
}
