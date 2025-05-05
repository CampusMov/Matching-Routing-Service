package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.controllers;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.CarpoolCommandService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CarpoolResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateCarpoolResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.swagger.CarpoolController;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform.CarpoolResourceFromEntityAssembler;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform.CreateCarpoolCommandFromResourceAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CarpoolControllerImpl implements CarpoolController {
    private final CarpoolCommandService carpoolCommandService;

    @Override
    public ResponseEntity<CarpoolResource> createCarpool(CreateCarpoolResource resource) {
        var command = CreateCarpoolCommandFromResourceAssembler.toCommandFromResource(resource);
        var carpool = carpoolCommandService.handle(command);
        if (carpool.isEmpty() || carpool.get().getId().isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var carpoolResource = CarpoolResourceFromEntityAssembler.toResourceFromEntity(carpool.get());
        return new ResponseEntity<>(carpoolResource, HttpStatus.CREATED);
    }
}
